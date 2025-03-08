/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import modelo.Estudiante;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author VANESA
 */
public class EstudianteDAO {
     private Connection conexion;

    public EstudianteDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void guardarEstudianteBD(Connection conexion, Estudiante estudiante) throws SQLException {
    String sqlPersona = "MERGE INTO persona KEY(id) VALUES (?, ?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlPersona)) {
        pstmt.setInt(1, estudiante.getID());
        pstmt.setString(2, estudiante.getNombres());
        pstmt.setString(3, estudiante.getApellidos());
        pstmt.setString(4, estudiante.getEmail());
        pstmt.executeUpdate();
    }

    // 🔹 Luego guardar en ESTUDIANTE
    String sqlEstudiante = "MERGE INTO estudiante KEY(id) VALUES (?, ?, ?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlEstudiante)) {
        pstmt.setInt(1, estudiante.getID());
        pstmt.setInt(2, estudiante.getCodigo());
        pstmt.setInt(3, estudiante.getPrograma().getID());
        pstmt.setBoolean(4, estudiante.isActivo());
        pstmt.setDouble(5, estudiante.getPromedio());
        pstmt.executeUpdate();
    }
}

    

}
