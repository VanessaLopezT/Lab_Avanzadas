/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import BaseDatos.ConexionBD;
import modelo.Inscripcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author VANESA
 */
public class CursosInscritosDAO {

    public static void inscribir(Inscripcion inscripcion) {
        String sql = "INSERT INTO cursos_inscritos (inscripcion_id, estudiante_id) VALUES (?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, inscripcion.getCurso().getID());
            stmt.setInt(2, inscripcion.getEstudiante().getID());

            int filasAfectadas = stmt.executeUpdate();
            System.out.println("inscribiendo");
            ConexionBD.mostrarDatosBD_CURSOS_INSCRITOS();
            if (filasAfectadas < 0) {
                System.out.println("No se pudo inscribir al estudiante.");
            }
        } catch (SQLException e) {
            System.err.println("Error al inscribir al estudiante: " + e.getMessage());
        }
    }
    
    public static void guardarDatosBD(Inscripcion inscripcion) {
        String sql = "INSERT INTO cursos_inscritos (inscripcion_id) VALUES (?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, inscripcion.getCurso().getID());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al guardar la inscripción: " + e.getMessage());
        }
    }

    public static void buscarInscripcionEnCursosInscritos(int inscripcionID, int estudianteID) {
        String sql = "SELECT * FROM CURSOS_INSCRITOS WHERE INSCRIPCION_ID = ? AND ESTUDIANTE_ID = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, inscripcionID);
            pstmt.setInt(2, estudianteID);

            System.out.println("Ejecutando consulta: " + pstmt.toString());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Inscripción encontrada en CURSOS_INSCRITOS:");
                System.out.println("ID: " + rs.getInt("ID") +
                        " | INSCRIPCION_ID: " + rs.getInt("INSCRIPCION_ID") +
                        " | ESTUDIANTE_ID: " + rs.getInt("ESTUDIANTE_ID"));
            } else {
                System.out.println("No se encontró la inscripción en CURSOS_INSCRITOS.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Eliminar(int inscripcionID, int estudianteID) {
        String sqlBuscar = "SELECT * FROM CURSOS_INSCRITOS WHERE INSCRIPCION_ID = ? AND ESTUDIANTE_ID = ?";
        String sqlEliminar = "DELETE FROM CURSOS_INSCRITOS WHERE ID = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
             PreparedStatement pstmtEliminar = conexion.prepareStatement(sqlEliminar)) {

            pstmtBuscar.setInt(1, inscripcionID);
            pstmtBuscar.setInt(2, estudianteID);
            ResultSet rs = pstmtBuscar.executeQuery();

            if (rs.next()) {
                int idRegistro = rs.getInt("ID");

                pstmtEliminar.setInt(1, idRegistro);
                int filasAfectadas = pstmtEliminar.executeUpdate();
                System.out.println("elimando");
                ConexionBD.mostrarDatosBD_CURSOS_INSCRITOS();
                if (filasAfectadas < 0) {
                    
                    System.out.println("No se pudo eliminar la inscripción.");
                }
            } else {
                System.out.println("No se encontró la inscripción en CURSOS_INSCRITOS.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Actualizar(int inscripcionID, int estudianteID, int nuevoCursoID) {
        String sqlBuscar = "SELECT * FROM CURSOS_INSCRITOS WHERE INSCRIPCION_ID = ? AND ESTUDIANTE_ID = ?";
        String sqlActualizar = "UPDATE CURSOS_INSCRITOS SET INSCRIPCION_ID = ? WHERE ID = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
             PreparedStatement pstmtActualizar = conexion.prepareStatement(sqlActualizar)) {

            pstmtBuscar.setInt(1, inscripcionID);
            pstmtBuscar.setInt(2, estudianteID);
            ResultSet rs = pstmtBuscar.executeQuery();

            if (rs.next()) {
                int idRegistro = rs.getInt("ID");

                pstmtActualizar.setInt(1, nuevoCursoID);
                pstmtActualizar.setInt(2, idRegistro);

                int filasAfectadas = pstmtActualizar.executeUpdate();
                System.out.println("actualizando");
                ConexionBD.mostrarDatosBD_CURSOS_INSCRITOS();
                if (filasAfectadas > 0) {
                    System.out.println("Inscripción actualizada con el nuevo curso (ID: " + nuevoCursoID + ").");
                } else {
                    System.out.println("No se pudo actualizar la inscripción.");
                }
            } else {
                System.out.println("No se encontró la inscripción en CURSOS_INSCRITOS.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}
