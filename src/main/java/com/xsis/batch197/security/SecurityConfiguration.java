package com.xsis.batch197.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.xsis.batch197.model.XAddressBookModel;
import com.xsis.batch197.repository.XAddressBookRepo;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserPrincipalDetailsService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers("/bootstrap/dist/css/**",
					"/bootstrap/dist/js/**",
					"/bootstrap/dist/fonts/**",
					"/font-awesome/css/**",
					"/font-awesome/fonts/**",
					"/font-awesome/less/**",
					"/font-awesome/scss/**",
					"/nprogress/**",
					"/nprogress/support/**",
					"/iCheck/skins/flat/**",
					"/iCheck/**",
					"/bootstrap-progressbar/css/**",
					"/bootstrap-progressbar/less/**",
					"/bootstrap-progressbar/scss/**",
					"/bootstrap-progressbar/**",
					"/bootstrap-daterangepicker/**",
					"/bootstrap-datetimepicker/build/css/**",
					"/bootstrap-datetimeepicker/build/js/**",
					"/build/css/**",
					"/build/js/**",
					"/build/images/**",
					"/jquery/dist/**",		
					"/jquery/**",		
					"/fastclick/lib/**",
					"/skycons/**",
					"/moment/**",
					"/select2/dist/css/**",
					"/select2/dist/js/**",
					"/animate.css/**",
					"/api/user/fp-count/**",
					"/login","/forgot-password","/check-email","/select-role","/session-expired").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.loginProcessingUrl("/login").failureHandler(failureHandler())
			.defaultSuccessUrl("/select-role")
			.usernameParameter("username").passwordParameter("password")
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(2).expiredUrl("/session-expired");
			//.successHandler(authSuccessHandler).failureHandler(authFailureHandler);
			//.rememberMe().rememberMeParameter("remember-me")
			//.tokenValiditySeconds(600).key("RahasiaDong!!").and().exceptionHandling().accessDeniedPage("/access-denied")
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(encoderPassword());
		daoAuthenticationProvider.setUserDetailsService(this.userService);

		return daoAuthenticationProvider;
	}

	@Bean
	public PasswordEncoder encoderPassword() {
		return new BCryptPasswordEncoder();
	}
	
	private AuthenticationSuccessHandler successHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse, Authentication authentication)
					throws IOException, ServletException {
				httpServletResponse.getWriter().append("OK");
				httpServletResponse.setStatus(200);
			}
		};
	}
	
	private AuthenticationFailureHandler failureHandler() {
		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse, AuthenticationException e)
					throws IOException, ServletException {
				String username = httpServletRequest.getParameter("username");
				Integer count = 0;
				
				httpServletResponse.getWriter().append("{\"Username\":\""+ username+"\",\"count\":\""+ count+"\"}");
				httpServletResponse.setStatus(401);
			}
		};
	}
	
	private AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
					AccessDeniedException e) throws IOException, ServletException {
				httpServletResponse.getWriter().append("Access denied");
				httpServletResponse.setStatus(403);
			}
		};
	}
	
	private AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
					AuthenticationException e) throws IOException, ServletException {
				httpServletResponse.getWriter().append("Not authenticated");
				httpServletResponse.setStatus(401);
			}
		};
	}
}
