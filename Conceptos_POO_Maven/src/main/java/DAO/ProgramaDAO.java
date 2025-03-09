/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import modelo.Programa;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.Facultad;


public class ProgramaDAO {

    private Connection conexion;
    FacultadDAO facultadDAO=new FacultadDAO(conexion);
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
     public Programa obtenerProgramaPorID(int idPrograma) throws SQLException {
        String sql = "SELECT * FROM programa WHERE id = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPrograma);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Facultad facultad = facultadDAO.obtenerFacultadPorID(rs.getInt("facultad_id")); // Asumimos que este método existe
                    
                    return DAOFactory.crearPrograma(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("duracion"),
                        rs.getDate("registro"),
                        facultad
                    );
                }
            }
        }
        return null; // Retorna null si el programa no existe
    }
}
