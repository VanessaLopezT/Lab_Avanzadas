/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import BaseDatos.DatabaseConnection;
import Fabrica_Objetos.FabricaDAO;
import Fabrica_Objetos.FabricaModelo;
import interfaz.InscripcionApp;
import modelo.Programa;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.Facultad;


public class ProgramaDAO {

    private Connection conexion;
    
    
    public ProgramaDAO() {
        this.conexion = DatabaseConnection.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
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
    Connection conexion = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM programa WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPrograma);
            try (ResultSet rs = pstmt.executeQuery()) {
                FacultadDAO facultadDAO=FabricaDAO.crearFacultadDAO();
                if (rs.next()) {
                    return FabricaModelo.crearPrograma(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("duracion"),
                        rs.getDate("registro"),
                        facultadDAO.obtenerFacultadPorID(rs.getInt("facultad_id")) // Obtener la facultad asociada
                    );
                }
            }
        
    } 
    return null;
}
   
   
}
