package auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@ComponentScan("auth")
@ComponentScan("test.mocks.org")
@ComponentScan("model.service")
@EnableWebSecurity
public class SecurityConfiguration {
	private static final String ROLE_ADMIN = "ADMIN";
	private static final String ROLE_DB_ADMIN = "DBA";

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		
		http
        .authorizeRequests()
        .antMatchers("/admin/**")
        .hasRole("ADMIN")
        .antMatchers("/javax.faces.resource/**")
        .hasRole("ADMIN")
        .antMatchers("/login*")
        .permitAll()
        .anyRequest()
        .anonymous();
		
//        .authenticated()
//	      .and()
		
		  http
	      .formLogin()
	      .defaultSuccessUrl("/admin");
//	      .loginPage("/login")
//	      .loginProcessingUrl("/perform_login")
//	      .defaultSuccessUrl("/homepage.html", true)
//	      .failureUrl("/login?error=true")
//	      .failureHandler(authFailureHandler())
//	      .and()
//	      .logout()
//	      .logoutUrl("/perform_logout")
//	      .deleteCookies("JSESSIONID")
//	      .logoutSuccessHandler(logoutHandler());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user1 = User.withUsername("user").password(passwordEncoder().encode("user")).roles("USER").build();
//		UserDetails user2 = User.withUsername("user2").password(passwordEncoder().encode("user2")).roles("USER")
//				.build();
//		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(user1, user2, admin);
//	}

	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
		return new AuthFailureHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutHandler() {
		return new AuthLogoutHandler();
	}
	
	private HttpSecurity configureChain(HttpSecurity http) {
		return http;
	}
}