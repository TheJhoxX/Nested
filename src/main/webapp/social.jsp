<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="es">
  <%@include file="head.jsp" %>

 <body>

	<%@include file="cabecera-web.jsp" %>

	<div class="secciones-nests">
		<input  placeholder="Buscar un usuario" class="buscador-nests" type="search" />
		<a class="seccion" href="/SocialServlet"><h3 id="seccion-seleccionada">Descubrir</h3></a>
		<a class="seccion" href="/MostrarSolicitudesServlet"><h3>Solicitudes</h3></a>
		<a class="seccion" href="/MostrarAmigosServlet"><h3>Mis contactos</h3></a>
    </div>


	<div class="contenedor-slider">
			
		<div class="slider">
			
			<div class="slides">
				<% 
                                    // Obtén el ArrayList de usuarios del request
                                    ArrayList<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios");

                                    if (usuarios != null) {
                                        for (int i = 0; i < usuarios.size(); i++) {
                                            Usuario usuario = usuarios.get(i);
                                    %>
                                    <div class="slide" id="slide<%= i %>">
					<a class="boton-slider-siguiente" href="#slide<%= i == 0 ? usuarios.size() - 1 : i - 1 %>"><span class="material-symbols-outlined icono-cambio-slider">navigate_before</span></a>
					<div class="slide-info">
						<a class="perfil-slide" style="background-image: url('imagenCabeceraServlet?id=<%=usuario.getId()%>')" href="MostrarPerfilPublicoServlet?idPublico=<%= usuario.getId()%>"></a>
						<div class="contenedor-vertical"> 
							<h1 class="informacion-slider"><%= usuario.getUniversidad()%></h1>
							<h2 class="informacion-slider"><%= usuario.getGrado()%></h2>
							<h1 class="informacion-slider"><%= usuario.getArroba()%></h1>
							<a style="text-decoration: none" class="boton-solicitar" href="EnviaSolicitudServlet?idSolicitado=<%= usuario.getId() %>"><h2>ENVIAR UNA SOLICITUD</h2></a>
                                                        
						</div>
					</div>
					<a class="boton-slider-siguiente" href="#slide<%= i == usuarios.size()-1 ? 0 : i+1 %>"><span class="material-symbols-outlined icono-cambio-slider">navigate_next</span></a>
                                        
                                    </div>
                                <%      }
                                    } %>
                        </div>
                </div>
        </div>

 </body>
