<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="es">
<%@include file="head.jsp" %>
<body>
    <%@include file="cabecera-web.jsp" %>  

	<div class="secciones-nests">
		<input  placeholder="Buscar un usuario" class="buscador-nests" type="search" />
		<a class="seccion" href="/SocialServlet"><h3>Descubrir</h3></a>
		<a class="seccion" href="/MostrarSolicitudesServlet"><h3>Solicitudes</h3></a>
		<a class="seccion" href="/MostrarAmigosServlet"><h3 id="seccion-seleccionada">Mis contactos</h3></a>
    </div>
      <%
        ArrayList<Usuario> amigos = (ArrayList<Usuario>)request.getAttribute("amigos");
       %>
    <section class="contenedor-amigos"> 
        <%
           for (int i=0; i<amigos.size();i++){
                Usuario amigo=amigos.get(i);
            
           %>
        <div class="amigo">
            <a href="MostrarPerfilPublicoServlet?idPublico=<%= amigo.getId() %>">
            <div class="foto-y-universidad">
		<img class="foto-perfil-solicitud" src="imagenCabeceraServlet?id=<%= amigo.getId() %>"/>
                <div class="datos-perfil-universidad">
                    <div class="texto-datos">
                        <h3> <%= amigo.getUniversidad() %> </h3>
                    </div>
                    <div class="texto-datos">
                        <h4><%= amigo.getGrado() %></h4>
                    </div>
                    <div class="texto-datos">
			<h4><%= amigo.getArroba() %></h4>
                    </div>
                </div>
            </div>
            <div class="nombre-apellidos">
                <h3><%= amigo.getNombre() + " " + amigo.getApellidos() %></h3>
            </div>
        </div>
        
       <%
           }
       %>
        
    </section>
</body>
</html>
