/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nested.controlador;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import nested.modelo.Usuario;
import nested.persistencia.EstudiosDB;
import nested.persistencia.UsuarioDB;

/**
 *
 * @author zen1xx
 */
@MultipartConfig
public class ModificarPerfilServlet extends HttpServlet{
    
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
            throws ServletException, IOException, ParseException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()){
            request.setCharacterEncoding("UTF-8");
            HttpSession session = request.getSession();
            Usuario user = (Usuario) session.getAttribute("user");
            ArrayList<String> universidades = EstudiosDB.getUniversidades();
            request.setAttribute("universidades", universidades);
            String nombre = null;
            String apellidos = null;
            String arroba = null;
            String auxFecha = null;
            //Part foto = request.getPart("foto");
            String sexo = null;
            String universidad = null;
            String grado = null;
            
            Collection<Part> parts = request.getParts();
            for(Part part : parts){
                String name = part.getName();
                if (name.equals("nombre")) {
                    nombre = request.getParameter(name);
                } else if (name.equals("apellidos")) {
                    apellidos = request.getParameter(name);
                } else if (name.equals("arroba")) {
                    arroba = request.getParameter(name);
                } else if (name.equals("fechaNacimiento")) {
                    auxFecha = request.getParameter(name);
                } else if (name.equals("sexo")) {
                    sexo = request.getParameter(name);
                } else if (name.equals("universidad")) {
                    universidad = request.getParameter(name);
                }else if (name.equals("grado")) {
                    grado = request.getParameter(name);
                }
            }
            
            Part pfp = request.getPart("foto");
            
            if(pfp.getSize()>0){
                user.setPfp(pfp);
            }else{
                user.setPfp(null);
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaNacimiento = LocalDate.parse(auxFecha, formatter);
            user.setNombre(nombre);
            user.setApellidos(apellidos);
            user.setArroba(arroba);
            user.setFechaNacimiento(fechaNacimiento);
            user.setSexo(sexo);
            user.setUniversidad(universidad);
            user.setGrado(grado);
            String url;
            UsuarioDB.update(user);
           
            url ="/perfil.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }
    
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ModificarPerfilServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ModificarPerfilServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ModificarPerfilServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ModificarPerfilServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
