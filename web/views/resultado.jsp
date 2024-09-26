<%-- 
    Document   : resultado
    Created on : 25-sep-2024, 17:54:12
    Author     : rendo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"> 
        <title>Resultado del Registro</title> 
    </head>
    <body>
        <h2>Resultado</h2> 
        <p><%= request.getAttribute("mensaje")%></p> 
        <a href="views/registro_animal.jsp">Regresar al formulario</a> 
    </body>
</html>