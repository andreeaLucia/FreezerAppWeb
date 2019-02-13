package freezer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import freezer.service.FreezerService;

public class AuthorizationFilter extends GenericFilterBean {

	private FreezerService freezerService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		if (freezerService == null) {
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			freezerService = webApplicationContext.getBean(FreezerService.class);
		}

		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String authorizationHeader = httpServletRequest.getHeader("X-Authorization");

			if (freezerService.verifyTokenJava(authorizationHeader)
					&& checkIsAdminAndPath(httpServletRequest.getRequestURI(), authorizationHeader)) {
				filterChain.doFilter(request, response);
			} else {
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
				httpServletResponse.setContentType("text/plain; charset=UTF-8");
				httpServletResponse.getWriter().write("Bad credentials");
				httpServletResponse.getWriter().flush();
			}
		}
	}

	private boolean checkIsAdminAndPath(String requestURI, String authorizationHeader) {
		List<String> adminPaths = new ArrayList<String>();

//		list.add("/getAllRemindersForAuser");
		adminPaths.add("/getAllReminders");
//		list.add("/addReminder");
		adminPaths.add("/deleteReminder");
//		list.add("/addReminder");
//		list.add("/updateReminder");

		if (!adminPaths.contains(requestURI)) {
			return true;
		} else {
			return freezerService.isAdmin(authorizationHeader);
		}
	}

	private boolean checkHeader(String token) {

		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build(); // Reusable verifier instance
			DecodedJWT jwt = verifier.verify(token);

		} catch (UnsupportedEncodingException exception) {
			// UTF-8 encoding not supported
			return false;
		} catch (JWTVerificationException exception) {
			// Invalid signature/claims
			return false;
		}
		return true;
	}

}
