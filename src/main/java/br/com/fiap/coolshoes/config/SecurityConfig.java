package br.com.fiap.coolshoes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.fiap.coolshoes.security.JwtAuthenticationEntrypoint;
import br.com.fiap.coolshoes.security.JwtRequestFilter;
import br.com.fiap.coolshoes.security.JwtUserDetailService;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

  private final JwtAuthenticationEntrypoint jwtAuthenticationEntrypoint;
  private final JwtUserDetailService jwtUserDetailService;
  private final JwtRequestFilter jwtRequestFilter;
  private final PasswordEncoder passwordEncoder;

  @Bean
  @Override
  public AuthenticationManager authenticationManager() throws Exception{
      return super.authenticationManager();
  }

  public SecurityConfig(JwtUserDetailService jwtUserDetailService, JwtAuthenticationEntrypoint jwtAuthenticationEntrypoint,
  JwtRequestFilter jwtRequestFilter,
  PasswordEncoder passwordEncoder){
    this.jwtUserDetailService = jwtUserDetailService;
    this.jwtAuthenticationEntrypoint = jwtAuthenticationEntrypoint;
    this.jwtRequestFilter = jwtRequestFilter;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(jwtUserDetailService)
        .passwordEncoder(passwordEncoder);
    // auth.inMemoryAuthentication()
    //     .withUser("admin").password("{noop}admin").roles("ADMIN", "USER")
    //     .and()
    //     .withUser("user").password("{noop}user").roles("USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception{
    http
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/user/***").permitAll()
        // .antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntrypoint)
          .and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .csrf().disable()
        .formLogin().disable();
  }

  @Override
  public void configure(WebSecurity web) throws Exception{
    web.ignoring()
        .antMatchers(
          "/v3/api-docs",
          "/configuration/ui",
          "/swagger-resources/**",
          "/configuration/security",
          "/swagger-ui/**",
          "/webjars/**"
        );
  }
  
}
