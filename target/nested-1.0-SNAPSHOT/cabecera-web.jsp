
<%@page import="nested.modelo.*,javax.servlet.http.HttpServletRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <%  Usuario user = (Usuario) session.getAttribute("user");    %>
        <div class="cabecera-nests">
            <a class="logo" href="index.jsp"></a>
		<div class="titulo-seccion">
			<h1>NESTS</h1>
		</div>
                
            <a class="perfil" href="/GetUniversidadesServlet?type=1" style="background-image: url('imagenCabeceraServlet?id=<%=user.getId()%>'); background-size: cover;  background-position: center;  background-repeat: no-repeat;"></a>

        </div>
</html>
