/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Config.ConnectionBD;
import Model.AnimalModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rendo
 */
@WebServlet("/animal")
public class Animal extends HttpServlet {

    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Animal</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Animal at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Se ejecuta el doGet");
        ConnectionBD conexion = new ConnectionBD();
        List<AnimalModel> listaAnimales = new ArrayList<>();
        String sql = "SELECT id, color, especie, tipo_animal, "
                + "tipo_alimento, peso, habitad, altura FROM animal";

        try {
            conn = conexion.getConnectionBD();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Itera sobre los resultados y crea objetos UsuarioModel
            while (rs.next()) {
                AnimalModel animal = new AnimalModel();
                int idFinal = Integer.parseInt(rs.getString("id"));
                animal.setId(idFinal);
                animal.setColor(rs.getString("color"));
                animal.setEspecie(rs.getString("especie"));
                animal.setTipo_animal(rs.getString("tipo_animal"));
                animal.setTipo_alimento(rs.getString("tipo_alimento"));
                Double pesoFinal = Double.parseDouble(rs.getString("peso"));
                animal.setPeso(pesoFinal);
                animal.setHabitad(rs.getString("habitad"));
                animal.setAltura(rs.getString("altura"));
                listaAnimales.add(animal);
            }

            // Pasa la lista de usuarios al JSP
            request.setAttribute("animales", listaAnimales);
            request.getRequestDispatcher("/views/mostrar_animales.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los usuarios" + e);
        } finally {
            // Close resources
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        ConnectionBD conexion = new ConnectionBD();
        // Obtener los parámetros del formulario 
        String id = request.getParameter("txt_id");
        String color = request.getParameter("txt_color");
        String especie = request.getParameter("txt_especie");
        String tipo_animal = request.getParameter("txt_tipo_animal");
        String tipo_alimento = request.getParameter("txt_tipo_alimento");
        String peso = request.getParameter("txt_peso");
        String habitad = request.getParameter("txt_habitad");
        String altura = request.getParameter("txt_altura");

        int idFinal = Integer.parseInt(id);
        Double pesoFinal = Double.parseDouble(peso);

        try {
            // Crear la consulta SQL para insertar el usuario 
            String sql = "INSERT INTO animal (id, color, especie, tipo_animal, "
                    + "tipo_alimento, peso, habitad, altura) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idFinal);
            ps.setString(2, color);
            ps.setString(3, especie);
            ps.setString(4, tipo_animal);
            ps.setString(5, tipo_alimento);
            ps.setDouble(6, pesoFinal);
            ps.setString(7, habitad);
            ps.setString(8, altura);

            // Ejecutar la consulta 
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                // Si se insertó correctamente, redirigir al usuario a una página de éxito 
                request.setAttribute("mensaje", "Animal registrado con éxito!");
                request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
            } else {
                // Si falló, redirigir a una página de error 
                request.setAttribute("mensaje", "Error al registrar animal.");
                request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error: " + e.getMessage());
            request.getRequestDispatcher("/views/resultado.jsp").forward(request, response);
        } finally {
            // Cerrar los recursos 
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        System.out.println("se ejecuta doDelete");

        String id = request.getParameter("id");
        // Validate input
        if (id == null || id.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Invalid request
            System.out.println("invalid request");
            return;
        }

        String sql = "DELETE FROM animal WHERE id like ?";

        try {
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK); // Eliminar exitoso 
                System.out.println("se eliminó");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // No se encontró el usuario 
                System.out.println("se ejecuta doDelete");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Error del servidor 
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        System.out.println("doPut Ejecutandose");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String id = null;
        String color = null;
        String especie = null;
        String tipo_animal = null;
        String tipo_alimento = null;
        String pesoString = null;
        String habitad = null;
        String altura = null;

        try {
            // Leer el cuerpo de la solicitud
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Convertir el cuerpo de la solicitud en una cadena JSON
            String jsonData = sb.toString();

            // Eliminar los caracteres innecesarios para extraer los datos manualmente
            jsonData = jsonData.replace("{", "").replace("}", "").replace("\"", "");

            // Dividir los datos en pares clave-valor
            StringTokenizer tokenizer = new StringTokenizer(jsonData, ",");

            while (tokenizer.hasMoreTokens()) {
                String pair = tokenizer.nextToken();
                String[] keyValue = pair.split(":");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                // Asignar los valores manualmente
                switch (key) {
                    case "id":
                        id = value;
                        break;
                    case "color":
                        color = value;
                        break;
                    case "especie":
                        especie = value;
                        break;
                    case "tipo_animal":
                        tipo_animal = value;
                        break;
                    case "tipo_alimento":
                        tipo_alimento = value;
                        break;
                    case "peso":
                        pesoString = value;
                        break;
                    case "habitad":
                        habitad = value;
                        break;
                    case "altura":
                        altura = value;
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error parsing JSON.");
            return;
        }

        if (id == null || id.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Id not found in request body");
            return;
        }

        try {
            conn = conexion.getConnectionBD();
            String sql = "UPDATE animal SET color = ?, especie = ?, tipo_animal = ?, tipo_alimento = ?, peso = ?, habitad = ?, altura = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, color);
            ps.setString(2, especie);
            ps.setString(3, tipo_animal);
            ps.setString(4, tipo_alimento);
            ps.setString(5, pesoString);
            ps.setString(6, habitad);
            ps.setString(7, altura);
            ps.setString(8, id);

            // Ejecutar la consulta de actualización
            ps.executeUpdate();
            System.out.println("Animal actualizado exitosamente.");
            response.getWriter().write("Animal actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error al actualizar el animal." + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
