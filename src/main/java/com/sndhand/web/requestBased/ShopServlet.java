package com.sndhand.web.requestBased;

import com.sndhand.web.componentBased.ContainerBean;
import com.sndhand.web.core.Comment;
import com.sndhand.web.core.Shop;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Servlet that handles requests for shops. 
 * 
 */
@WebServlet(name = "ShopServlet", urlPatterns = {"/requestBased/shop/*"})
public class ShopServlet extends HttpServlet {
    
    @Inject
    ContainerBean container;
    
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
        
        String shop = request.getParameter("shop");
        if (shop != null) {
            Shop s = container.getContainer().getShops().find(Integer.parseInt(request.getParameter("shop")));
            if (!s.getBeneficiaries().contains(Shop.Beneficiary.None)) {
                request.setAttribute("beneficiaries", s.getBeneficiaries());
            }
            if (!s.getOrganizationType().equals(Shop.OrganizationType.None)){
                request.setAttribute("organizationType", s.getOrganizationType());
            }
            if (!s.getTradition().equals(Shop.Tradition.None)) {
                request.setAttribute("tradition", s.getTradition());
            }
                
            request.setAttribute("comments", s.getComments());
            request.setAttribute("SHOP",    s);
            request.getRequestDispatcher("shop.jspx").forward(request , response);
        }
        
        String action = request.getParameter("action");
        if (action != null && action.equals("comment")) {
            String  text    = request.getParameter("text");
            String  auth    = request.getParameter("writer");
            Byte    rating  = Byte.valueOf(request.getParameter("ratingInputField"));
            Integer shopId = Integer.valueOf(request.getParameter("shopId"));
            
            // Add comment to shop
            Shop s = container.getContainer().getShops().find(shopId);
            s.getComments().add(new Comment.Builder(auth, text).rating(rating).build());
            container.getContainer().getShops().update(s);
            request.setAttribute("comments", s.getComments());
            request.setAttribute("SHOP", s);
            request.getRequestDispatcher("shop.jspx").forward(request , response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
