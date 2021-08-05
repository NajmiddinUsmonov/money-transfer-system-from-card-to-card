package uz.usmonov.appclicksystems.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.usmonov.appclicksystems.Security.JwtFilter;
import uz.usmonov.appclicksystems.MyAuthService.MyAuthService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    MyAuthService myAuthService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf ().disable ()
                .authorizeRequests ()
                .antMatchers ( "/api/login" ).permitAll ()
                .anyRequest ()
                .authenticated ();

        /* JwtFilter works before UsernamePassword.....*/
        http.addFilterBefore ( jwtFilter, UsernamePasswordAuthenticationFilter.class );

        /* Unless Token in Session is deleted, you can enter again without token  */
        http.sessionManagement ().sessionCreationPolicy ( SessionCreationPolicy.STATELESS );
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager ();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder ();
    }
}
