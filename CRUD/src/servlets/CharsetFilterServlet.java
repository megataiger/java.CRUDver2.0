package servlets;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilterServlet implements Filter {
    private FilterConfig filterConfig = null;

    public CharsetFilterServlet()
    {
        System.out.println("Request response encoder Filter object has been created");
    }

    public void init(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        request.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }

    public void destroy() {
        this.filterConfig = null;
    }
}