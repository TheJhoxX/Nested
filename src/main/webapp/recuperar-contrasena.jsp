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
              <form action="RecuperarContrasenaServlet" method="post" id="formulario" class="formulario">
              <h1>Recuperar su contraseña</h1>
              <div class="contenedor-item">
                <input class="item" type="email" name="correo" placeholder="Correo electrónico de tu cuenta" required/>
              </div>
              <div class="contenedor-item">
                  <input class="item" type="password" name="pswd" id="pswd" placeholder="Nueva contraseña" required/>
              </div>
              <div class="contenedor-item">
                <input class="item" type="password" name="cpswd" id="cpswd" placeholder="Repita la nueva contraseña" required/>
              </div>
              <button id="boton-iniciar-registrar" type="submit"><a>Recuperar contraseña</a></button>
                <%
                    if(request.getParameter("respuesta") != null) {
                        int r = Integer.parseInt(request.getParameter("respuesta"));
                        if(r == 1){ 
                    
                %>
                        <p style="color: red">El correo no está asociado a una cuenta existente.</p>
                <%
                        }
}
                %>
            </form>
          </div>
        </div>
      </div>
    </div>
        <script type="text/javascript" src="scripts/scriptPassword.js"></script>
  </body>
</html>


