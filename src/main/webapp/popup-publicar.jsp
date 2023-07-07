
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <div id="popup-publicar" class="overlay">
		<div class="popup">

			<div class="cabecera-popup">
				<h2>PUBLICA TU NEST</h2>
				<a class="cierre" href="#">
					<span class="material-symbols-outlined cierre-popup">close</span>
				</a>
			</div>
			
			<div class="contenedor-item">

				<form action="servletNests"  method="post" class="formulario" enctype="multipart/form-data" id="formulario-publicar">

					<div class="contenedor-vertical">
						
						<input type="text" class="item-popup-publicar" name="nombre" placeholder="Nombre del Evento" required/>
						<input type="text" class="item-popup-publicar" name="descripcion" placeholder="Descripción" required/>
						<div class="contenedor-item">
                                                        <div class="contenedor-vertical">
                                                            <div class="contenedor-item">
                                                                <p style="margin-left: 1rem">Fecha y hora de inicio: </p>
                                                                <input type="datetime-local" class="item-popup-publicar" id="fechaInicio" name="fechaInicio" required/>
                                                            </div>
                                                            <div class="contenedor-item">
                                                                <p style="margin-left: 1rem">Fecha y hora de finalización: </p>
                                                                <input type="datetime-local" class="item-popup-publicar" id="fechaFin" name="fechaFin" required/>
                                                            </div>
                                                            
                                                        </div>
                                                </div>
                                                    <div id="map" class="item-popup-publicar" style="width: 100%; height: 20vh; border-radius: 20px">
                                                    
                                                </div>
						<div class="contenedor-item">
                                                    <input type="number" class="item-popup-publicar" name="limitePersonas" placeholder="Número de personas" required/>
                                                    <div class="contenedor-item">
                                                        <p>Evento público: </p>
                                                        <input type="checkbox" id="publico" name="publico" value="true"/>
                                                    </div>
                                                        
						</div>	
                                                <%  
                                                    ArrayList<Usuario> listadoAmigos = (ArrayList<Usuario>) request.getAttribute("listadoAmigos");
                                                    if (listadoAmigos.size() > 0){
                                                %>
                                                    
                                                <p style="margin-bottom: 0">Añade un amigo al Nest haciendo click en él<p/>
                                                        <div id="contenedor-listado-participantes">
                                                            <div class="listado-participantes">
                                                            <%  
                                                                    for (int i = 0; i<listadoAmigos.size(); i++){ 
                                                                         Usuario amigo = listadoAmigos.get(i);
                                                            %>

                                                                        <div class="invitacion">
                                                                            <a class="perfil" href="MostrarPerfilPublicoServlet?idPublico=<%= amigo.getId() %>" style="background-image: url('imagenCabeceraServlet?id=<%= amigo.getId() %>'); background-size: cover;  background-position: center;  background-repeat: no-repeat;"></a>
                                                                                <p><%= amigo.getArroba() %></p>
                                                                        </div>
                                                                <%  }%>
                                                            
                                                            </div>
                                                        </div>
                                                <%  }   %>
                                                

						<div id="contenedor-imagen-publicar">
							<label for="adjuntar-imagen-publicar"><span class="material-symbols-outlined">upload</span>Agrega una imagen para tu Nest</label>
							<input type="file" name="imagenFondo" id="adjuntar-imagen-publicar"/>
						</div>
						<div class="contenedor-item">
							<input type="submit" id="boton-publicar"  value="PUBLICAR NEST"/>
						</div>
							
					</div>	

				</form>
			</div>
		</div>
	</div>
        <script src="scripts/scriptNests.js"></script>

    </body>
</html>
