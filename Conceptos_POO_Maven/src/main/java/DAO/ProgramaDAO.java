/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import modelo.Programa;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;


public class ProgramaDAO {

    private Connection conexion;

    public ProgramaDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
     public void guardarProgramaBD(Connection conexion, Programa programa) throws SQLException {
    String sql = "MERGE INTO programa KEY(id) VALUES (?, ?, ?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setDouble(1, programa.getID());
        pstmt.setString(2, programa.getNombre());
        pstmt.setDouble(3, programa.getDuracion());
        pstmt.setDate(4, new java.sql.Date(programa.getRegistro().getTime()));
        pstmt.setInt(5, programa.getFacultad().getID());
        pstmt.executeUpdate();
    }
}

}
