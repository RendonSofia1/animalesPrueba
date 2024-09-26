<%-- 
    Document   : registro_animal
    Created on : 25-sep-2024, 16:17:31
    Author     : rendo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registro Animal</h1>
        <form action="${pageContext.request.contextPath}/animal" method="POST">
                ID <br>
                <input type="number" name="txt_id"><br>
                COLOR <br>
                <input type="text" name="txt_color"><br>
                ESPECIE <br>
                <input type="text" name="txt_especie"><br>
                TIPO_ANIMAL <br>
                <input type="text" name="txt_tipo_animal"><br>
                TIPO_ALIMENTO <br>
                <input type="text" name="txt_tipo_alimento"><br>
                PESO <br>
                <input type="number" step="0.01" name="txt_peso"><br>
                HABITAD <br>
                <input type="text" name="txt_habitad"><br> 
                ALTURA <br>
                <input type="text" name="txt_altura"><br>
                <input type="submit" name="accion" value="Agregar">
            </form>
                <a href="/animales_Servlet/animal">Cargar Animales</a>
    </body>
</html>

