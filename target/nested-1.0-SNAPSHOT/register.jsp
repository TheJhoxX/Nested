<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="es">
  <%@include file="head.jsp" %>
  
  <body>
    <div class="contenedor-login-register">
      <div class="contenedor-carta">
        <div class="carta">
          <div class="logo-formulario">
            <img src="imagenes/logo.png" alt="logo" />
          </div>
            
          <form class="formulario" method="post" action="RegisterUserServlet" accept-charset="UTF-8">
            <h1>Regístrate</h1>
            <div class="contenedor-item">
              <input class="item" name="nombre" placeholder="Nombre" required/>
              <input class="item" name="apellidos" placeholder="Apellidos" required/>
            </div> 
            <input class="item" type="email" name="correo" placeholder="Correo electrónico" required/>

            <input class="item" name="arroba" placeholder="Nombre de usuario" required/>
            
            <div class="contenedor-item">
              <input class="item" type="date" name="fechaNacimiento" placeholder="Fecha de nacimiento" required/>
              <select class="item" name="sexo" placeholder="Seleccione su sexo">
                  <option disabled selected>Seleccione su sexo</option>
                  <option value="Hombre">Hombre</option>
                  <option value="Mujer">Mujer</option>
                  <option value="Otro">Otro</option>
              </select>
            </div>
            
            <select class="item" name="universidad">
                <option disabled selected>Selccione su Universidad</option>
                <%
                    ArrayList<String> unis = (ArrayList<String>) request.getAttribute("universidades");
                    for(int i = 0; i < unis.size(); i++){
                %>
                    <option><%=unis.get(i) %></option>
                <%
                    }
                %>
            </select>
            
            <input class="item" name="grado" placeholder="Grado" required/>
            
            <input class="item" type="password" name="contrasena" placeholder="Contraseña" required/>
  
            <button id="boton-iniciar-registrar" name="boton-registrar" type="submit"><a>Registrar</a></button>
            <div class="carta-footer">
              <div>
                ¿Ya dispones de una cuenta?<a href="login.jsp">¡Inicia sesión!</a>
              </div>
              <% 
                if(request.getParameter("respuesta") != null) {
                    int r = Integer.parseInt(request.getParameter("respuesta"));
                    if(r == 1){ 
                    
                        %>
                        <p style="color: red">Este correo ya está asociado a una cuenta.</p>
                        <%
                    } else if(r == 2){
                        
                        %>
                        <p style="color: red">Nombre de usuario no disponible.</p>
                        <%      
                    }else if(r == 3){
                        
                        %>
                        <p style="color: red">Seleccione su universidad.</p>
                        <%
                    }else if(r == 4){

                        %>
                        <p style="color: red">Seleccione su sexo.</p>
                        <%
                    }else if(r == 5){

                        %>
                        <p style="color: red">Los usuarios deben ser mayores de edad.</p>
                        <%
                    }
                }
              %>
            </div>
          </form>
        </div>
      </div>
    </div>
  </body>
</html>
