<%-- 
    Document   : actualizar_animal
    Created on : 25-sep-2024, 19:30:35
    Author     : rendo
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="Config.ConnectionBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualizar animal</title>
    </head>
    <body>
        <h2>Actualizar animal</h2> 
        <%
            String id = request.getParameter("id");
            String color = "";
            String especie = "";
            String tipo_animal = "";
            String tipo_alimento = "";
            Double peso = null;
            String habitad = "";
            String altura = "";

            ConnectionBD conexion = new ConnectionBD();
            Connection connection = conexion.getConnectionBD();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {

                // Consulta para obtener los datos del usuario por ID 
                String sql = "SELECT color, especie, tipo_animal, tipo_alimento, peso, habitad, altura"
                        + " FROM animal WHERE id LIKE ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, id);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    color = resultSet.getString("color");
                    especie = resultSet.getString("especie");
                    tipo_animal = resultSet.getString("tipo_animal");
                    tipo_alimento = resultSet.getString("tipo_alimento");
                    peso = resultSet.getDouble("peso");
                    habitad = resultSet.getString("habitad");
                    altura = resultSet.getString("altura");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %> 

        <!-- Formulario con los datos del usuario para actualizar --> 
        <form id="formActualizarAnimal"> 
            ID <br>
            <input disabled type="number" id="txt_id" value="<%= id%>"><br>
            COLOR <br>
            <input type="text" id="txt_color" value="<%= color%>"><br>
            ESPECIE <br>
            <input type="text" id="txt_especie" value="<%= especie%>"><br>
            TIPO_ANIMAL <br>
            <input type="text" id="txt_tipo_animal" value="<%= tipo_animal%>"><br>
            TIPO_ALIMENTO <br>
            <input type="text" id="txt_tipo_alimento" value="<%= tipo_alimento%>"><br>
            PESO <br>
            <input type="number" step="0.01" id="txt_peso" value="<%= peso%>"><br>
            HABITAD <br>
            <input type="text" id="txt_habitad" value="<%= habitad%>"><br> 
            ALTURA <br>
            <input type="text" id="txt_altura" value="<%= altura%>"><br>

            <input type="button" value="Actualizar" onclick="actualizarAnimal()"> 
        </form> 
        <a href="/animales_Servlet/animal"></a>
        <div id="resultado"></div> 
        <script>
            function actualizarAnimal() {
                const id = document.getElementById("txt_id").value;
                const color = document.getElementById("txt_color").value;
                const especie = document.getElementById("txt_especie").value;
                const tipo_animal = document.getElementById("txt_tipo_animal").value;
                const tipo_alimento = document.getElementById("txt_tipo_alimento").value;
                const peso = document.getElementById("txt_peso").value;
                const habitad = document.getElementById("txt_habitad").value;
                const altura = document.getElementById("txt_altura").value;

                const datos = {
                    id: id,
                    color: color,
                    especie: especie,
                    tipo_animal: tipo_animal,
                    tipo_alimento: tipo_alimento,
                    peso: peso,
                    habitad: habitad,
                    altura: altura
                };

                fetch(`${pageContext.request.contextPath}/animal?id=` + id, {
                    method: "PUT",
                    body: JSON.stringify(datos), 
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                })
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("resultado").innerText = data;
                        })
                        .catch(error => {
                            document.getElementById("resultado").innerText = "Error al actualizar animal.";
                            console.error('Error:', error);
                        });
            }

        </script> 
    </body>
</html>
