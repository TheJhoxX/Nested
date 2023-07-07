<%@page import="java.util.List, java.util.ArrayList"%>
<%@page import="com.google.common.collect.ListMultimap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
    
    <%@include file="head.jsp" %>
    

  <body>

      <%@include file="cabecera-web.jsp" %>

	<div class="secciones-nests">
      <a class="seccion" href="/ServletSeccionNest?tipo=1"><h3 >Ahora mismo</h3></a>
      <a class="seccion" href="/ServletSeccionNest?tipo=2"><h3 id="seccion-seleccionada">Proximamente</h3></a>
      <a class="seccion" href="/ServletSeccionNest?tipo=3"><h3>Terminado</h3></a>
      <a class="seccion" href="/ServletSeccionNest?tipo=4"><h3>Mis nests</h3></a>
    </div>
	
    <div class="contenedor-buscador-publicar">
		<div id="buscador-nests">
			<input  placeholder="Buscar un nest" class="buscador-nests" type="search" />
		</div>
		<div id="publicar-nest">
			<a href="#popup-publicar">+Nests</a>
		</div>
    </div>
      
    <%
        ArrayList<Nest> listadoNestsProximos = (ArrayList<Nest>) request.getAttribute("listadoNestsProximos");
        ArrayList<Integer> listadoParticipantes = (ArrayList<Integer>) request.getAttribute("listadoParticipantes");
        ListMultimap<Integer, Usuario> multiMapParticipantes= (ListMultimap<Integer, Usuario>) request.getAttribute("multiMapParticipantes");
      
    %>
    <div class="contenedor-nests">
  
        <% 
            String candado = "";
            if (listadoNestsProximos.size() == 0) { 
            %>
            <h1 style="margin: 1rem" >NO HAY NINGÚN NEST DISPONIBLE AL QUE APUNTARSE</h1>
        <% } else { 
                
                for (int i = 0; i<listadoNestsProximos.size(); i++){
                    Nest nest = listadoNestsProximos.get(i);
                    int participantes = listadoParticipantes.get(i);

                    if (nest.getPublico() == true){
                        candado = "lock_open";
                    }
                    else {
                        candado = "lock";
                    }
                       
            
        %>    
                    <div class="bg-img-nest" style="background-image: url('imagenFondoNestServlet?id=<%= nest.getIdentificador()%>'); background-size: cover; background-position: center; background-repeat: no-repeat;">
                        <div class="nest">
				<h1 class="nombre-nest"><%= nest.getNombre() %> </h1>
				<div class="usuario-organizador">
                                <a class="perfil" href="/MostrarPerfilPublicoServlet?idPublico=<%= nest.getIdOrganizador() %>" style="background-image: url('imagenCabeceraServlet?id=<%= nest.getIdOrganizador() %>'); background-size: cover;  background-position: center;  background-repeat: no-repeat;"></a>				</div>
				<div class="participar">
                                    <a href="#popupInscripcion-<%= listadoParticipantes.get(i)%>/<%= nest.getLimitePersonas() %>"><span class="material-symbols-outlined icono-participar">shift</span></a>
                                    <h2> <%= participantes %>/<%= nest.getLimitePersonas() %> </h2>
				</div>
			</div>
			<div class="nest-footer">
				<span class="material-symbols-outlined candado"><%= candado %></span>
                               <a href="#popup-participantes-<%= nest.getNombre() %>" >
                                <h3>+ PARTICIPANTES</h3>
                              </a>
                                
                                
				<a href="#popup-informacion-<%= nest.getNombre() %>"><h3>+INFORMACIÓN</h3></a>		
			</div>
                    </div>
                        
                       
                    <form action="ServletInscripcion?opcion=1" method="post" id="popupInscripcion-<%= listadoParticipantes.get(i)%>/<%= nest.getLimitePersonas() %>" class="overlay">
                        <div class="popup">
                            <div class="cabecera-popup">
                                    <h2><%=nest.getNombre()%></h2>
                                   <input type="hidden" name="idNest" value="<%= nest.getIdentificador() %>">
                                    <a class="cierre" href="#">
                                            <span class="material-symbols-outlined cierre-popup">close</span>
                                    </a>
                            </div>
                            <div class="contenedor-item">
                                    <p>¿Quieres apuntarte a este Nest?</p>
                                    <button type="submit" class="boton-aceptar" href=\"#\"><span class="material-symbols-outlined">done</span></button>
                                    <a class="boton-rechazar" href="#"><span class="material-symbols-outlined">close</span></a>
                            </div>
                        </div>
                    </form>
                    
                    <div id="popup-informacion-<%= nest.getNombre() %>" class="overlay">
                        
                        <div class="popup">
                            <div class="cabecera-popup">

                                <h2><%= nest.getNombre() %></h2>
                                <a class="cierre" href="#">
                                    <span class="material-symbols-outlined cierre-popup">close</span>
                                </a>

                            </div>

                            <div class="contenedor-vertical">
                                <div class="contenedor-item">
                                    
                                    <div id="map:<%= nest.getUbicacion() %>:<%= i %>" style="width: 60%; height: 20vh; border-radius: 20px; margin: 1rem"></div>
                                    <p id="display-name:<%= nest.getUbicacion() %>" style="width: 40%; margin: 1rem"><%= nest.getUbicacion() %></p>
                                        
                                    	
                                </div>
                                <div class="descripcion-nest">
                                        <p>
                                            <%= nest.getDescripcion() %>
                                        </p>
                                        
                                        <p class="texto-fecha">Hora de comienzo: </p>
                                        <p class="hora-nest"><%= nest.getFechaDeRealizacion()%></p>
                                        <p class="texto-fecha">Hora de finalización: </p>
                                        <p class="hora-nest" style="background-color: #3b3b3b;"><%= nest.getFechaDeFinalizacion() %></p>
                                        
                                </div>
                            </div>
                        </div>	
                    
                    </div>
                     
                                        
                    <%  
                        List<Usuario> listaParticipantesNest = multiMapParticipantes.get(nest.getIdentificador()); 
                        if (listaParticipantesNest.size() > 0){
                        %>                   
                    <div id="popup-participantes-<%= nest.getNombre() %>" class="overlay">
                        
                        <div class="popup">
                            <div class="cabecera-popup">
				<h2>PARTICIPANTES</h2>
				<a class="cierre" href="#">
					<span class="material-symbols-outlined cierre-popup">close</span>
				</a>
                            </div>


                                    <input type="search" class="item-popup-publicar" placeholder="Busca participantes"/>
                                    <div class="listado-participantes" style="margin: 1rem">
                                        
                                        
                                        <div class="listado-participantes">
                                             <%  
                                                    for (int j = 0; j<listaParticipantesNest.size(); j++){ 
                                                         Usuario participante = listaParticipantesNest.get(j);
                                                            %>
                                            
                                                <div class="participante">
                                                            <a class="perfil" href="MostrarPerfilPublicoServlet?idPublico=<%= participante.getId() %>" style=" background-image: url('imagenCabeceraServlet?id=<%= participante.getId() %>"); background-size: cover;  background-position: center;  background-repeat: no-repeat;"></a>
                                                                <p><%= participante.getArroba()%></p>
                                                        </div>
                                                     <%  }%>    

                                        </div>
                                    </div>
                               
                            
                        </div>	
                    
                    </div>
                 <%  }  
                        else{
                             
                        %>
                        <div id="popup-participantes-<%= nest.getNombre() %>" class="overlay">
                        
                        <div class="popup">
                            <div class="cabecera-popup">
				<h2>PARTICIPANTES</h2>
                                
				<a class="cierre" href="#">
					<span class="material-symbols-outlined cierre-popup">close</span>
				</a>
                            </div>
                            
                                 <h3 style="margin: 1rem"> No hay participantes </h3>
                                
                           
                </div>            
              </div>                     
        
        <%          }
                }
            }
        %>    
        
        
        <%@include file="popup-publicar.jsp"%>
    
    
  </body>
</html>
