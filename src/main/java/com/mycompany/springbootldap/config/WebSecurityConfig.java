package com.mycompany.springbootldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
//@EnableConfigurationProperties(LdapProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	LdapUserDetails ldapUserDetails;
	
	 /*private LdapProperties ldapProperties;

	    @Value("${ldap.userDnPattern}")
	    private String userDnPattern;

	    @Autowired
	    public void setLdapProperties(LdapProperties ldapProperties) {
	        this.ldapProperties = ldapProperties;
	    }*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/api/private**").authenticated().anyRequest().permitAll()
				.and().httpBasic().realmName("spring-ldap").authenticationEntryPoint(getBasicAuthEntryPoint())
		        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(ldapUserDetails);
		
		/*String url = String.format("%s/%s", ldapProperties.getUrls()[0], ldapProperties.getBase());

        auth.ldapAuthentication()
                .contextSource()
                .url(url)
                .managerDn(ldapProperties.getUsername())
                .managerPassword(ldapProperties.getPassword())
                .and()
                .userDnPatterns(userDnPattern);*/
	}

}
