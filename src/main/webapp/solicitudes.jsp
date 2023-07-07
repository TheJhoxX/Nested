<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<%@include file="head.jsp" %>
<body>
    <%@include file="cabecera-web.jsp" %>

    <div class="secciones-nests">
        <input  placeholder="Buscar un usuario" class="buscador-nests" type="search" />
	<a class="seccion" href="/SocialServlet"><h3>Descubrir</h3></a>
	<a class="seccion" href="/MostrarSolicitudesServlet"><h3 id="seccion-seleccionada">Solicitudes</h3></a>
	<a class="seccion" href="/MostrarAmigosServlet"><h3>Mis contactos</h3></a>
    </div>

    <section class="contenedor-solicitudes"> 
        <%
            ArrayList<Usuario> usuarios = (ArrayList<Usuario>)request.getAttribute("solicitudes");
            Usuario aux;
            for(int i = 0; i < usuarios.size(); i++){
                aux = usuarios.get(i);
        %>
                <form class="solicitud">
                    <div class="foto-y-universidad">
                        <img class="foto-perfil-solicitud" src="imagenCabeceraServlet?id=<%= aux.getId() %>">
                        <div class="datos-perfil-universidad">
                            <div class="texto-datos">
                                <h3><%=aux.getUniversidad() %></h3>
                            </div>
                            <div class="texto-datos">
                                <h4><%=aux.getGrado() %></h4>
                            </div>
                            <div class="texto-datos">
                                <h4><%=aux.getArroba() %></h4>
                            </div>
                        </div>
                    </div>
                    <div class="nombre-apellidos">
                        <h3><%=aux.getNombre()+" "+aux.getApellidos() %></h3>
                    </div>
                    <div class="aceptar-rechazar">
                        <button type="submit" class="boton-aceptar" onclick="window.location.href='/AceptarRechazarSolicitudServlet?type=1&soli=<%=aux.getId()%>'"><span class="material-symbols-outlined">done</span></button>
                        <button type="submit" class="boton-rechazar" onclick="window.location.href='/AceptarRechazarSolicitudServlet?type=0&soli=<%=aux.getId()%>'"><span class="material-symbols-outlined">close</span></button>

                    </div>
                </form>
        <%
            }
        %>
    </section>
</body>
</html>
