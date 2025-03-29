/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import BaseDatos.AuxiliarBD;
import Factory.FabricaObjetos;
import interfaz.InscripcionApp;
import modelo.Programa;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.Facultad;


public class ProgramaDAO {

    private Connection conexion;
    
    
    public ProgramaDAO() {
        this.conexion = AuxiliarBD.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
    }
    
    InscripcionApp inscripcionApp=new InscripcionApp();
    FacultadDAO facultadDAO=new FacultadDAO();
    
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
    
     
   public Programa obtenerProgramaPorID(int idPrograma) {
    Connection conexion = AuxiliarBD.getInstance().getConnection();
        String sql = "SELECT * FROM programa WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPrograma);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return FabricaObjetos.crearPrograma(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("duracion"),
                        rs.getDate("registro"),
                        facultadDAO.obtenerFacultadPorID(rs.getInt("facultad_id")) // Obtener la facultad asociada
                    );
                }
            }
        
    } catch (SQLException e) {
        inscripcionApp.mostrarError("Error al buscar el programa: " + e.getMessage());
    }
    return null;
}
   
   
}
