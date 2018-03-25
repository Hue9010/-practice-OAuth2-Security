package practice;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import practice.model.MemberRole;
import practice.service.SecurityMemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Resource(name="securityMemberService")
	private SecurityMemberService securityMemberService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
  		http.csrf().disable();
  		http.headers().frameOptions().disable();
  		http.httpBasic();
  		
		http
			.authorizeRequests()
			.antMatchers("/users/list").hasRole(MemberRole.USER)
			.antMatchers("/**").permitAll();
		
  		http
			.formLogin()
			.loginPage("/users/login")
			.defaultSuccessUrl("/")
			.failureUrl("/users/login");

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
	  auth.userDetailsService(securityMemberService).passwordEncoder(passwordEncoder());
	}
}
