package com.vvachev.movielibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vvachev.movielibrary.model.entity.enums.RoleEnum;

@Configuration
public class SecurityAppConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SecurityAppConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http//
				.authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()//
				.antMatchers("/users/login", "/users/register").anonymous()//
				.antMatchers("/", "/about").permitAll()
				.antMatchers("/users/all", "/movies/all").hasRole(RoleEnum.ADMIN.name())
				.antMatchers("/**").authenticated()//
				.and()//
				.formLogin()//
				.loginPage("/users/login")//
				.usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
				.passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
				.defaultSuccessUrl("/home") //
				.failureForwardUrl("/users/login-error") //
				.and()//
				.logout()//
				.logoutUrl("/users/logout").logoutSuccessUrl("/")//
				.invalidateHttpSession(true).deleteCookies("JSESSIONID");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)//
				.passwordEncoder(passwordEncoder);
	}

//	@Override
//	public void configure(WebSecurity web) {
//		web.ignoring().antMatchers("/resources/**", "/static/**");
//	}

}
