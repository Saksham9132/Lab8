package filters;

import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import models.User;

/**
 *
 * @author Saksham
 */
public class AdminFilter implements Filter {
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        
        String email = (String)session.getAttribute("email");
       
      
        UserDB userdb = new UserDB();
        User user = userdb.get(email);
        //check if the roleId is 1 and then redirects to admin page with a message
        if(user.getRole().getRoleId() == 1 ){
            session.setAttribute("message", "Successfully logged in with Admin profile! ");
            chain.doFilter(request, response);
            return;
        }
        
        //check if user roleId is not 1 then redirect it to notes page with a warning
        else {
            session.setAttribute("message", "Can not access Admin page!");
            httpResponse.sendRedirect("notes");
            return;
        }
    }
    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        
    }

}
