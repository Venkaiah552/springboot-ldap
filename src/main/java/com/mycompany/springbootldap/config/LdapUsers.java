package com.mycompany.springbootldap.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix="app")
@PropertySource("classpath:ldapusers.properties")
@Data
public class LdapUsers {

	private Map<String, List<String>> userProp = new HashMap<>();

}
