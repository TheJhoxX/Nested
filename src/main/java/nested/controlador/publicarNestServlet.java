package nested.controlador;
import java.io.*;
import nested.modelo.*;
import nested.persistencia.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.servlet.annotation.*;
import javax.json.*;

@MultipartConfig
public class publicarNestServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request,HttpServletResponse response) 
    throws ServletException, IOException {
        //Toma los parámetros de la peticion
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Nest nest = new Nest();
        Usuario user = (Usuario)session.getAttribute("user");
        
        /*Comienzo a extraer datos para la publicacion*/
        nest.setIdOrganizador(user.getId());
        if(request.getParameter("publico").equals("true")){
            nest.setPublico(true);
        }
        else {
            nest.setPublico(false);
        }
        nest.setNombre(request.getParameter("nombre"));
        nest.setDescripcion(request.getParameter("descripcion"));
                
        /*Hacer conversiones a la fecha para que pueda ser pasada a Timestamp y
        ser almacenada en la BD*/
        LocalDateTime fecha = LocalDateTime.parse(request.getParameter("fechaInicio"));
        Instant instant = fecha.atZone(ZoneId.systemDefault()).toInstant();
        Timestamp timestamp = Timestamp.from(instant);
        nest.setFechaRealizacion(timestamp);
        
        fecha = LocalDateTime.parse(request.getParameter("fechaFin"));
        instant = fecha.atZone(ZoneId.systemDefault()).toInstant();
        timestamp = Timestamp.from(instant);

        nest.setFechaFinalizacion(timestamp);
        

        
        nest.setLimitePersonas(Integer.valueOf(request.getParameter("limitePersonas")));
        nest.setUbicacion(request.getParameter("ubicacion"));
        System.out.println(nest.getUbicacion());
       

        /* 
        Aunque no se haya adjuntado nada, algunos navegadores en lugar de 
        devolver un null devuelven un fichero vacío, es por esto que tengo
        que distinguir de esta forma
        */
        Part imagenFondo = request.getPart("imagenFondo");
        if (imagenFondo != null && imagenFondo.getSubmittedFileName() != null && !imagenFondo.getSubmittedFileName().isEmpty()) {
            // Procesar la imagen
            nest.setImagenFondo(imagenFondo);
        } else {
            // La imagen no se adjuntó
            nest.setImagenFondo(null);
            System.out.println("SE HA PILLADO LA IMAGEN COMO NULL");
        }


        
        
        
        
        //Guardar el nuevo Nest en la peticion
        request.setAttribute("nest", nest);

        int id = NestBD.insert(nest, getServletContext());
        ParticipacionBD.insertarParticipacion(user.getId(), id);
        
        //Sacar los invitados fijos
        String invitadosJson = request.getParameter("invitados");
        JsonReader reader = Json.createReader(new StringReader(invitadosJson));
        JsonArray invitadosArray = reader.readArray();
        if (invitadosArray.size() > 0) {
            for (int i = 0; i<invitadosArray.size(); i++){
                String arroba = invitadosArray.getString(i);
                int idInvitado = UsuarioDB.getIdUsuario(arroba);
                ParticipacionBD.insertarParticipacion(idInvitado, id);
            }
        }
        

        }
    
        protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
            doPost(request, response);
        }
    
}
