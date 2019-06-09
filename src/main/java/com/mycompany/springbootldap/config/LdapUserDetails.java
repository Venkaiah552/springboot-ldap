package com.mycompany.springbootldap.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LdapUserDetails implements UserDetailsService {

	@Autowired
	LdapUsers ldapUsers;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Entry<String, List<String>>> user = ldapUsers.getUserProp().entrySet().stream()
				.filter(u -> u.getKey().equals(username)).findFirst();
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found by name: " + username);
		}
		return toUserDetails(user.get());
	}

	private UserDetails toUserDetails(Entry<String, List<String>> userObject) {
		return User.withUsername(userObject.getKey())//.password("{noop}" + userObject.getValue().get(0))
				.password(passwordEncoder().encode(userObject.getValue().get(0)))
				.roles(userObject.getValue().get(1)).build();
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
