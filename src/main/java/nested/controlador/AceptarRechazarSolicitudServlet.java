/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nested.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nested.modelo.Usuario;
import nested.persistencia.AmistadDB;
import nested.persistencia.SolicitudDB;
import nested.persistencia.UsuarioDB;

/**
 *
 * @author Jacobo M
 */
public class AceptarRechazarSolicitudServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            Usuario actual = (Usuario)session.getAttribute("user");
            String url = "/solicitides.jsp";
            String auxType = (String)request.getParameter("type");
            System.out.println("Entro al servlet");
            int type = Integer.parseInt(auxType);
            String auxId = request.getParameter("soli");
            int soli = Integer.parseInt(auxId);
            ArrayList<Integer> ids = new ArrayList<>();
            ArrayList<Usuario> users = new ArrayList<>();
            SolicitudDB.deleteSolicitud(soli, actual.getId());
            ids = SolicitudDB.getSolicitudesById(actual.getId());
            for(int i =0; i<ids.size();i++){
                users.add(UsuarioDB.selectUserID(ids.get(i)));
            }
            if(type == 1){
                AmistadDB.doubleInsert(soli, actual.getId());
            }
            request.setAttribute("solicitudes", users);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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