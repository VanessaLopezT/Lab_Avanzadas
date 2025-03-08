/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import modelo.Profesor;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author VANESA
 */
public class ProfesorDAO {
        private Connection conexion;

    public ProfesorDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
      public void guardarProfesorBD(Connection conexion, Profesor profesor) throws SQLException {
    String sqlPersona = "MERGE INTO persona KEY(id) VALUES (?, ?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlPersona)) {
        pstmt.setInt(1, profesor.getID());
        pstmt.setString(2, profesor.getNombres());
        pstmt.setString(3, profesor.getApellidos());
        pstmt.setString(4, profesor.getEmail());
        pstmt.executeUpdate();
    }

    // Luego guardar en la tabla PROFESOR
    String sqlProfesor = "MERGE INTO profesor KEY(id) VALUES (?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlProfesor)) {
        pstmt.setInt(1, profesor.getID());
        pstmt.setString(2, profesor.getTipoContrato());
        pstmt.executeUpdate();
    }
}
}
