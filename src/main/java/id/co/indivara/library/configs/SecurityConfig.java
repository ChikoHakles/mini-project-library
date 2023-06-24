package id.co.indivara.library.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "LIBRARIAN", "READER")
                .antMatchers(HttpMethod.POST, "/api/books").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.DELETE, "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.POST, "/api/readers").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.PUT, "/api/readers/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.DELETE, "/api/readers/**").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.POST, "/api/wishlists").hasAnyRole("LIBRARIAN", "READER")
                .antMatchers(HttpMethod.POST, "/api/borrows").hasAnyRole("LIBRARIAN")
                .antMatchers(HttpMethod.POST, "/api/returns").hasAnyRole("LIBRARIAN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ADMIN")
                .and()
                .withUser("librarian").password("{noop}librarian").roles("LIBRARIAN")
                .and()
                .withUser("reader").password("{noop}reader").roles("READER");
    }
}