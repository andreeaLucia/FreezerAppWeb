package freezer;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(
          new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/loginUser", "/addUser","/addCoinAndPercent");
        web.ignoring().antMatchers(HttpMethod.OPTIONS);
//        web.ignoring().antMatchers(HttpMethod.GET);
//        web.ignoring().antMatchers(HttpMethod.POST);
    }
}
