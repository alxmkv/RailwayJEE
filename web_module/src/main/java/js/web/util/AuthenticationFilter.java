package js.web.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Markov
 */
@WebFilter("/app/*")
public class AuthenticationFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		String requestedPage = "";
		// 1. Check if user is logged in
		if (((HttpServletRequest) req).getSession().getAttribute("login") == null) {
			((HttpServletResponse) resp)
					.sendRedirect(((HttpServletRequest) req).getContextPath()
							+ "/index.jsf");
		} else {
			String requestURI = ((HttpServletRequest) req).getRequestURI();
			System.out.println("Request URI: " + requestURI);
			if (requestURI.startsWith("/web_module/app/")
					&& requestURI.endsWith(".jsf")) {
				requestedPage = requestURI.substring(
						requestURI.lastIndexOf("/") + 1,
						requestURI.lastIndexOf(".jsf"));
				// 2. Check if user requests existing resources
				// if (!"main".equals(requestedPage)
				// && !"tickets".equals(requestedPage)
				// && !"trains".equals(requestedPage)
				// && !"addtrain".equals(requestedPage)
				// && !"stations".equals(requestedPage)
				// && !"addstation".equals(requestedPage)
				// && !"users".equals(requestedPage)
				// && !"passengers".equals(requestedPage)
				// && !"error".equals(requestedPage)) {
				// ((HttpServletResponse) resp)
				// .sendRedirect(((HttpServletRequest) req)
				// .getContextPath() + "/app/error.jsf");
				// }
				// 3. Redirect to error page if an administrator resource is
				// requested by a simple user
				if (((Integer) ((HttpServletRequest) req).getSession()
						.getAttribute("type")).compareTo(2) == 0
						&& ("trains".equals(requestedPage)
								|| "stations".equals(requestedPage)
								|| "users".equals(requestedPage) || "passengers"
									.equals(requestedPage))) {
					System.out.println("Unauthorized access to: "
							+ requestedPage);
					((HttpServletResponse) resp)
							.sendRedirect(((HttpServletRequest) req)
									.getContextPath() + "/app/error.jsf");
				}
			}
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
}