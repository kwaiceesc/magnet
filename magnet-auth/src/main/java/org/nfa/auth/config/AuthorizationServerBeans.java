package org.nfa.auth.config;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AuthorizationServerBeans {

	private static final int PASSWORD_ENCODER_STRENGTH = 10;

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		InMemoryUserDetailsManager userService = new InMemoryUserDetailsManager();
		UserDetails userDetails = User.builder().username("user").password(passwordEncoder.encode("qqq")).roles("USER").build();
		userService.createUser(userDetails);
		return userService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(PASSWORD_ENCODER_STRENGTH);
	}

	@Bean
	public TokenStore tokenStore(JwtAccessTokenConverter accessTokenConverter) {
		return new JwtTokenStore(accessTokenConverter);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() throws NoSuchAlgorithmException {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("auth-server.jks"), "Y4M9ti#jrSN@1T@X".toCharArray()).getKeyPair("auth-server",
				"CBPSW%OGXLdSzw0W".toCharArray());
		converter.setKeyPair(keyPair);
		return converter;
	}

}
