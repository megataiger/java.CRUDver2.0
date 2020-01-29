package servlets;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    public CharsetFilter() {
        System.out.println("Request response encoder Filter object has been created");
    }

    public void doFilter
            (ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }
}