package br.com.vistoriaOnline.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DaoAuthenticationProvider provider;
	
		//autenticaçao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}
	
		//autorizações
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/logged/**").hasAnyRole("ADMIN", "USER")
			.antMatchers("/admin/**").hasAnyRole("ADMIN", "USER")
			.antMatchers("/cadastro/usuario").hasAnyRole("ADMIN")
			.antMatchers("/vo/**").permitAll()
			.antMatchers("/*").permitAll()
			.antMatchers("/login/*").permitAll()
			.antMatchers("/cliente/**").permitAll()
			.anyRequest().authenticated()
			.and().formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/cadastro/cliente", true)
			.usernameParameter("email").passwordParameter("password")
			.loginProcessingUrl("/perform_login")
			.failureUrl("/login?error=true")
			.and()
			.logout()
			.deleteCookies("JSESSIONID")
			.logoutUrl("/perform_logout")
			.logoutSuccessUrl("/login");
		
	}
		//recursos estaticos
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
	
	
}



//form -> form
//.loginPage("/login")
//.defaultSuccessUrl("/home", true)
//.permitAll()

