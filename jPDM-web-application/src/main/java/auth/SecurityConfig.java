package auth;

//@Configuration
//@ComponentScan("auth")
//@ComponentScan("test.mocks.org")
//@ComponentScan("model.service")
//@EnableWebSecurity
public class SecurityConfig {
	/*
	@Autowired
	private UserAuthenticationProvider customAuthenticationProvider;
	
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

	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
		return new AuthFailureHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutHandler() {
		return new AuthLogoutHandler();
	}
	*/
}