package com.sndhand.web.requestBased;

import com.sndhand.web.componentBased.ContainerBean;
import com.sndhand.web.core.Group;
import com.sndhand.web.core.Shop;
import com.sndhand.web.core.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Servlet which handles registration for users and shops.
 * 
 */
@WebServlet(name = "RegServlet", urlPatterns = {"/requestBased/register/*"})
public class RegServlet extends HttpServlet {
    
    @Inject
    private ContainerBean container;
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if(action.equals("register")) {
            User u = new User.Builder(
                      request.getParameter("username")
                    , request.getParameter("password"))
                    .build();

            Group g = new Group();
            g.setGroupName("REGUSER");
            g.setUserName(request.getParameter("username"));

            if (!container.getContainer().getUsers().getAll().contains(u)) {
                container.getContainer().getUsers().add(u);
                container.getContainer().getGroups().add(g);
                response.sendRedirect("../index.xhtml");
            } else {
                response.sendRedirect("registerError.jspx");
            }
        } else if (action.equals("shop")) {
            Shop.Builder builder = new Shop.Builder(
                      request.getParameter("name")
                    , Double.valueOf(request.getParameter("latitude"))
                    , Double.valueOf(request.getParameter("longitude")));
            
            String   ownername    = request.getParameter("ownername");
            String   location     = request.getParameter("location");
            String   description  = request.getParameter("description");
            String   organization = request.getParameter("organization");
            String[] beneficiary  = request.getParameterValues("beneficiary");
            String   tradition    = request.getParameter("tradition");
            
            if (location != null && location.length() > 0) {
                builder.location(location);
            }
            if (description != null && description.length() > 0) {
                builder.description(description);
            }
            if (organization != null && organization.length() > 0) {
                builder.organizationType(Shop.OrganizationType.valueOf(organization));
            }
            if (beneficiary != null) {
                List<Shop.Beneficiary> b = new ArrayList<Shop.Beneficiary>();
                
                for (String s : beneficiary) {
                    b.add(Shop.Beneficiary.valueOf(s));
                }
                
                builder.beneficiaries(b);
            }
            if (tradition != null && tradition.length() > 0) {
                builder.tradition(Shop.Tradition.valueOf(tradition));
            }
            
            Shop s = builder.build();
            
            for (User u : container.getContainer().getUsers().getAll()) {
                if (u.getName().equals(ownername)) {
                    for (Group p : container.getContainer().getGroups().getGroupByUser(u.getName())) {
                        p.setGroupName("OWNER");
                        container.getContainer().getGroups().update(p);
                    }
                    
                    s.addOwner(u);
                }
            }
            
            container.getContainer().getShops().add(s);
            response.sendRedirect("../index.xhtml");
        }
    }
   
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
}
