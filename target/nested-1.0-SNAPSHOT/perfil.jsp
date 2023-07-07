
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="es">
  <%@include file="head.jsp" %>
  <body>
    <!--Cabecera común a todas las secciones-->
    
    <%@include file="cabecera-web.jsp" %>

    <div class="contenedor-perfil">
       
      <form class="formulario-perfil"  method="post" action="ModificarPerfilServlet" enctype="multipart/form-data" accept-charset="UTF-8">
        <div class="contenedor-item">
          <div class="contenedor-vertical" >
              <input class="item-popup-publicar" name="nombre" patter="\w+(\s+\w+)?" value="<%= user.getNombre() %>" required/>
              <input class="item-popup-publicar" name="apellidos" patter="\w+(\s+\w+)?" value="<%= user.getApellidos()%>" required/>
              <input class="item-popup-publicar" type="email" name="correo" value="<%= user.getCorreo() %>" readonly/>
              <input class="item-popup-publicar" name="arroba" value="<%= user.getArroba()%>" required />
          </div>
            <div class="foto-perfil" style="background-image: url('imagenCabeceraServlet?id=<%=user.getId()%>')" onclick="document.getElementById('input-foto').click();">
		<input type="file" name="foto" id="input-foto" style="display: none;" onchange="loadImage(event);"/>
            </div>
          <script>
		function loadImage(event) {
			const file = event.target.files[0];
			if (!file) return;

			const reader = new FileReader();
			reader.onload = function() {
				document.querySelector('.foto-perfil').style.backgroundImage = 'url(' + reader.result + ')';
			};
			reader.readAsDataURL(file);
		}
	</script>
        </div>

        <div class="contenedor-item">
          <input class="item-popup-publicar" type="date" name="fechaNacimiento" value="<%= user.getFechaNacimiento()%>" required />
          <select class="item-popup-publicar" name="sexo" value=<%= user.getSexo()%>>
                  <option selected><%= user.getSexo()%></option>
                  <option value="Hombre">Hombre</option>
                  <option value="Mujer">Mujer</option>
                  <option value="Otro">Otro</option>
          </select>        
        </div>
        <select class="item-popup-publicar" name="universidad" value=<%= user.getUniversidad()%> />
           <option value="<%= user.getUniversidad()%>" selected><%= user.getUniversidad()%></option>
           <%
                    ArrayList<String> unis = (ArrayList<String>) request.getAttribute("universidades");
                    for(int i = 0; i < unis.size(); i++){
                %>
                    <option><%=unis.get(i) %></option>
                <%
                    }
                %>
        </select>
        <input class="item-popup-publicar" name="grado" value="<%= user.getGrado()%>" required />

        <div class="perfil-footer">
            <button type="reset" id="boton-cancelar-cambios"><a href="/GetUniversidadesServlet?type=1"/>Cancelar</button>
          <button type="submit" id="boton-guardar-cambios">Guardar cambios</button>
        </div>
      </form>
    </div>
  </body>
</html>
