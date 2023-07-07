package nested.controlador;

import com.google.common.collect.ListMultimap;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.time.LocalDateTime;
import javax.servlet.RequestDispatcher;
import nested.persistencia.NestBD;
import nested.persistencia.ParticipacionBD;
import nested.modelo.Nest;
import nested.modelo.Usuario;
import nested.persistencia.AmistadDB;

/**
 *
 * @author thejhoxx
 */
@WebServlet(name = "ServletSeccionNest", urlPatterns = {"/ServletSeccionNest"})
public class ServletSeccionNest extends HttpServlet {

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
        HttpSession session = request.getSession();
        Usuario user = (Usuario) session.getAttribute("user");
        ArrayList<Nest> listadoNestsProximos = new ArrayList<Nest>();
        ArrayList<Integer> listadoParticipantes = new ArrayList<Integer>();
        
        ArrayList<Nest> nestsBD = NestBD.getNests();
        ArrayList<Usuario> amigosBD = AmistadDB.obtenerAmigosUsuario(user.getId());
        ListMultimap<Integer, Usuario> multiMapParticipantes= ParticipacionBD.ObtenerParticipantesTotales();
        
        int seccion = Integer.valueOf(request.getParameter("tipo"));
        
        /*En caso de ser la seccion 2 solo se muestran los Nests a los que
        el usuario no esté ya apuntado*/
        

        for (int i = 0; i<nestsBD.size(); i++){
            if (condiciones(nestsBD.get(i),user,seccion) == true){
                Nest nest = nestsBD.get(i);
                listadoNestsProximos.add(nest);
                listadoParticipantes.add(ParticipacionBD.contarParticipantes(nest.getIdentificador()));
            }
        }
        
        
        
        
        request.setAttribute("listadoNestsProximos", listadoNestsProximos);
        request.setAttribute("listadoParticipantes", listadoParticipantes);
        request.setAttribute("listadoAmigos", amigosBD);
        request.setAttribute("multiMapParticipantes", multiMapParticipantes );
        
        String url = "";
        if (seccion == 1){
            url = "/nests-ahora-mismo.jsp";
        }
        if (seccion == 2){
            url = "/nests.jsp";
        }
        if (seccion == 3){
            url = "/nests-terminados.jsp";
        }
        if (seccion == 4){
            url = "/mis-nests.jsp";
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
        
        
    }
    

    
    private boolean condiciones (Nest nest, Usuario user, int seccion){
        
        boolean condicionesCumplidas = false;
        
        if (seccion == 1){
            if ((LocalDateTime.now().isAfter(nest.getFechaDeRealizacion().toLocalDateTime())) &&
                (LocalDateTime.now().isBefore(nest.getFechaDeFinalizacion().toLocalDateTime()))){
                    if ((nest.getPublico()==false)){
                         if ( (AmistadDB.sonAmigos(user.getId(),nest.getIdOrganizador())) || (user.getId() == nest.getIdOrganizador()) ){
                            condicionesCumplidas = true;
                        }
                        else {
                            //Si el evento es privado y no son amigos no puede verlo
                            condicionesCumplidas = false;
                        }
                    }
                    else {
                        //Si el evento es publico puede verlo todo el mundo
                        condicionesCumplidas = true;
                    }
            }
            else {
                condicionesCumplidas = false;
            }
        }
        
        if (seccion == 2){
            if ((LocalDateTime.now().isBefore(nest.getFechaDeRealizacion().toLocalDateTime())) && 
                (ParticipacionBD.consultarParticipacion(user.getId(), nest.getIdentificador()) == false)){
                if ((nest.getPublico()==false)){
                    if ( (AmistadDB.sonAmigos(user.getId(),nest.getIdOrganizador())) || (user.getId() == nest.getIdOrganizador()) ){
                        condicionesCumplidas = true;
                    }
                    else {
                        //Si el evento es privado y no son amigos no puede verlo
                        condicionesCumplidas = false;
                    }
                }
                else {
                    //Si el evento es publico puede verlo todo el mundo
                    condicionesCumplidas = true;
                }
            }
            else {
                condicionesCumplidas = false;
            }
        }
        
        if (seccion == 3){
            if ((LocalDateTime.now().isAfter(nest.getFechaDeFinalizacion().toLocalDateTime())) &&
                (LocalDateTime.now().isBefore(nest.getFechaDeFinalizacion().toLocalDateTime().plusDays(90))) &&
                (!ParticipacionBD.consultarVotado(user.getId(), nest.getIdentificador())) &&
                (ParticipacionBD.consultarParticipacion(user.getId(), nest.getIdentificador()) == true)){
                    if ((nest.getPublico()==false)){
                        if ( (AmistadDB.sonAmigos(user.getId(),nest.getIdOrganizador())) || (user.getId() == nest.getIdOrganizador()) ){
                            condicionesCumplidas = true;
                        }
                        else {
                            //Si el evento es privado y no son amigos no puede verlo
                            condicionesCumplidas = false;
                        }
                    }
                    else {
                        //Si el evento es publico puede verlo todo el mundo
                        condicionesCumplidas = true;
                    }
            }
            else {
                condicionesCumplidas = false;
            }
        }
        
        if (seccion == 4){
            if ((LocalDateTime.now().isBefore(nest.getFechaDeRealizacion().toLocalDateTime())) && 
                (ParticipacionBD.consultarParticipacion(user.getId(), nest.getIdentificador()) == true)){
                    if ((nest.getPublico()==false)){
                         if ( (AmistadDB.sonAmigos(user.getId(),nest.getIdOrganizador())) || (user.getId() == nest.getIdOrganizador()) ){
                            condicionesCumplidas = true;
                        }
                        else {
                            //Si el evento es privado y no son amigos no puede verlo
                            condicionesCumplidas = false;
                        }
                    }
                    else {
                        //Si el evento es publico puede verlo todo el mundo
                        condicionesCumplidas = true;
                    }
            }
            else {
                condicionesCumplidas = false;
            }
        }
        
        return condicionesCumplidas;
        
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
