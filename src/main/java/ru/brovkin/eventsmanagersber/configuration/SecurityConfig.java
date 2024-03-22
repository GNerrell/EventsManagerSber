package ru.brovkin.eventsmanagersber.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.brovkin.eventsmanagersber.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/event/authorize/registration").permitAll()
                    .antMatchers("/event/authorize/creator/**").hasRole("CREATOR")
                    .antMatchers("/event/authorize/user/**").hasAnyRole("COMMON_USER", "CREATOR")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/event/authorize/login")
                    .defaultSuccessUrl("/event/authorize/home", true)
                    .failureUrl("/event/authorize/bad_login")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .permitAll();
        }
}
