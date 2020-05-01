<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu - Trofi</title>
</head>
<body>
	<h1>Ordenes Aceptadas</h1>
	
	<table border="1" width="100%">
		<tr>
		 <td> ID</td>
		 <td> DIRECCION DE ENTREGA </td>
		 <td> DETALLES </td>
		 <td> ESTADO DE LA ORDEN </td>
		 <td>ACCIONES</td>
		</tr>
		<c:forEach var="orden" items="${lista}">
			<tr>
				<td><c:out value="${orden.id_orden}"/></td>
				<td><c:out value="${orden.direccion_cliente}"/></td>
				<td><a href="muestra_orden?action=verDetalles&id_orden=<c:out value="${orden.id_orden}" />">Ver Detalles</a></td>
				<td><c:out value="${orden.estado_orden}"/></td>
				<td><a href="muestra_orden?action=cambiarEstado&id_orden=<c:out value="${orden.id_orden}" />">Cambiar Estado</a></td>
			</tr>
		</c:forEach>
	</table>
	<table>
		<tr>
			<td><a href="muestra_orden?action=verOrdenes" >Ver Ordenes </a> </td>
		</tr>
	</table>
	
</body>
</html>