/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import modelo.Inscripcion;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;



public class InscripcionDAO {

    private Connection conexion;

    public InscripcionDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
   public void guardarInscripcionBD(Connection conexion, Inscripcion inscripcion) throws SQLException {
    String verificarSQL = "SELECT COUNT(*) FROM inscripcion WHERE curso_id = ? AND estudiante_id = ? AND anio = ? AND semestre = ?";

    // Verificar si la inscripción ya existe
   String insertarSQL = "INSERT INTO inscripcion (curso_id, estudiante_id, anio, semestre) VALUES (?, ?, ?, ?);";

    try (PreparedStatement verificarStmt = conexion.prepareStatement(verificarSQL)) {
        verificarStmt.setInt(1, inscripcion.getCurso().getID());
        verificarStmt.setInt(2, inscripcion.getEstudiante().getID());
        verificarStmt.setInt(3, inscripcion.getAño());
        verificarStmt.setInt(4, inscripcion.getSemestre());

        ResultSet rs = verificarStmt.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
        return;
        }

        // Si la inscripción no existe, la insertamos
        try (PreparedStatement insertarStmt = conexion.prepareStatement(insertarSQL)) {
            insertarStmt.setInt(1, inscripcion.getCurso().getID());
            insertarStmt.setInt(2, inscripcion.getEstudiante().getID());
            insertarStmt.setInt(3, inscripcion.getAño());
            insertarStmt.setInt(4, inscripcion.getSemestre());
            insertarStmt.executeUpdate();
        }
    } catch (SQLException e) {
        System.err.println("Error en guardarInscripcionBD: " + e.getMessage());
    }
}
  
    
}