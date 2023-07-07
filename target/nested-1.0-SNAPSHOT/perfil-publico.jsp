<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="es">
  <%@include file="head.jsp" %>
  <body>
    <!--Cabecera común a todas las secciones-->
    <%@include file="cabecera-web.jsp" %>
    <% Usuario publicUser = (Usuario) request.getAttribute("publicUser"); %>

    <div class="contenedor-perfil">
      <form class="formulario-perfil">
        <div class="contenedor-item">
          <div class="contenedor-vertical" >
            <input class="item-popup-publicar" type="" value="<%= publicUser.getNombre() %>" readonly/> 
            <input class="item-popup-publicar" type="" value="<%= publicUser.getApellidos() %>"" readonly/>
            <input
              class="item-popup-publicar" type="email" value="<%= publicUser.getCorreo() %>"" readonly/>
			<input class="item-popup-publicar" type="" value="<%= publicUser.getArroba() %>"" readonly/>
          </div>
          <div id="perfil-publico" class="foto-perfil" style="background-image: url('imagenCabeceraServlet?id=<%=publicUser.getId()%>')"></div>
        </div>

        <div class="contenedor-item">
          <input
            class="item-popup-publicar" type="" value="<%= publicUser.getFechaNacimiento() %>"" readonly/>
          <input class="item-popup-publicar" value="<%= publicUser.getSexo() %>"" readonly/>
        </div>
        <input class="item-popup-publicar" value="<%= publicUser.getUniversidad() %>"" readonly/>
        <input class="item-popup-publicar" value="<%= publicUser.getGrado() %>"" readonly />
        <h3>Reputación: <%= publicUser.getReputacion() %></h3>

      </form>
    </div>
  </body>
</html>

