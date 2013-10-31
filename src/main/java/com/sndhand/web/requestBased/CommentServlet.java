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
 * A servlet for handling the page for posting comments
 * 
 */
@WebServlet(name = "CommentServlet", urlPatterns = {"/requestBased/shopComment/*"})
public class CommentServlet extends HttpServlet {

    private int shopID;
    
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
       
        String store = request.getParameter("shop");
        if (store != null && store.length() > 0) {
            shopID = Integer.valueOf(store);
            
            request.getRequestDispatcher("shopComment.jspx").forward(request , response);
        }
        
        String action = request.getParameter("action");
        if (action != null && action.equals("comment")) {
            String text     = request.getParameter("text");
            String auth     = request.getUserPrincipal().getName();
            String ratings  = request.getParameter("ratingInputField");
            
            Byte rating = ratings == null ? 0 : Byte.valueOf(ratings);
            
            // Add comment to store
            Shop s = container.getContainer().getShops().find(shopID);
            s.addComment(new Comment.Builder(auth, text).rating(rating).build());
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
