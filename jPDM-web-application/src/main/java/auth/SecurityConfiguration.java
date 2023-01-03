package auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	      auth
//	          .inMemoryAuthentication()
//	              .withUser("user").password("password").roles("USER").and()
//	              .withUser("admin").password("password").roles("USER", "ADMIN");
	  }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        .antMatcher("/**")
        .authorizeRequests()
            .anyRequest().hasRole("ADMIN")
            .and()
        .httpBasic();
		return http.build();
	}

//	@Bean
// 	public UserDetailsService userDetailsService() {
// 		UserDetails user = User.withDefaultPasswordEncoder()
// 			.username("user")
// 			.password("password")
// 			.roles("USER")
// 			.build();
// 		return new InMemoryUserDetailsManager(user);
// 	}
}