<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<html>
  <%@include file="head.jsp" %>
  
  <body>
    <div class="contenedor-login-register">

      <div class="contenedor-item">
        <div class="contenedor-carta">
          <div class="carta">
            <div class="logo-formulario">
		<img href="index.html" src="./imagenes/logo.png"/>
            </div>
              <form class="formulario" method="post" action="LoginUserServlet">
              <h1>Inicia sesión</h1>
              <div class="contenedor-item">
                <input class="item" type="email" name="correo" placeholder="Correo electrónico" required />
              </div>
              <div class="contenedor-item">
                <input class="item" type="password" name="password" placeholder="Contraseña" required/>
              </div>
            <button id="boton-iniciar-registrar" type="submit"><a>Iniciar sesión</a></button>
            </form>

            <div class="carta-footer">
              <div>
                ¿Aún no tienes una cuenta?<a href="/GetUniversidadesServlet?type=0">¡Regístrate!</a>
              </div>
              <div>
                <a href="recuperar-contrasena.jsp">He olvidado mi contraseña</a>
                <% 
                if(request.getParameter("respuesta") != null) {
                    int r = Integer.parseInt(request.getParameter("respuesta"));
                    if(r == 0){ 
                    
                        %>
                        <p style="color: green">Usuario registrado correctamente</p>
                        <%
                    } else if(r == 1){
                        
                        %>
                        <p style="color: red">Correo o contraseña incorretos</p>
                        <%      
                    }
                    else if(r == 2){
                        
                        %>
                        <p style="color: green">Contraseña cambiada correctamente</p>
                        <%      
                    }
                }
              %>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
