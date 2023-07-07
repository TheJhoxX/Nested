
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package nested.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import nested.modelo.Usuario;
import nested.persistencia.EstudiosDB;
import nested.persistencia.UsuarioDB;


/**
 *
 * @author Jacobo M
 */
@WebServlet(name = "RegisterUserServlet", urlPatterns = {"/RegisterUserServlet"})
public class RegisterUserServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            Usuario user = new Usuario();
            request.setCharacterEncoding("UTF-8");
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String correo = request.getParameter("correo");
            String arroba = request.getParameter("arroba");
            String auxFecha = request.getParameter("fechaNacimiento");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaNacimiento = LocalDate.parse(auxFecha, formatter);
            Period edad = Period.between(fechaNacimiento, LocalDate.now());
            String sexo = request.getParameter("sexo");
            String universidad = request.getParameter("universidad");
            String grado = request.getParameter("grado");
            String contrasena = request.getParameter("contrasena");
            user.setNombre(nombre);
            user.setApellidos(apellidos);
            user.setCorreo(correo);
            user.setArroba(arroba);
            user.setFechaNacimiento(fechaNacimiento);
            user.setSexo(sexo);
            user.setUniversidad(universidad);
            user.setGrado(grado);
            user.setContrasena(contrasena);
            String url;
            ArrayList<String> universidades = EstudiosDB.getUniversidades();
            request.setAttribute("universidades", universidades);
            if(universidad == null){
                url = "/register.jsp?respuesta=3";
            }else if(sexo == null){
                url="/register.jsp?respuesta=4";
            }else if(UsuarioDB.loggedCorreo(user.getCorreo())){
                url = "/register.jsp?respuesta=1";
            }else if(UsuarioDB.loggedArroba(user.getArroba())){
                url = "/register.jsp?respuesta=2";
            }else if(edad.getYears()< 18){
                url = "/register.jsp?respuesta=5";
            }else{
                int id = UsuarioDB.insert(user, request.getServletContext());
                user.setId(id);
                url = "/login.jsp?respuesta=0";
            } 
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
            Logger.getLogger(RegisterUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterUserServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegisterUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RegisterUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
