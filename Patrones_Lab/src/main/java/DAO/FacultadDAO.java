/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import BaseDatos.AuxiliarBD;
import Factory.FabricaObjetos;
import interfaz.InscripcionApp;
import modelo.Facultad;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;


public class FacultadDAO {
    
    private Connection conexion;
   
    public FacultadDAO() {
        this.conexion = AuxiliarBD.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
    }
    InscripcionApp inscripcionApp=new InscripcionApp();
     PersonaDAO personaDAO=new PersonaDAO();
    
       public void guardarFacultadBD(Connection conexion, Facultad facultad) throws SQLException {

    String sql = "MERGE INTO facultad KEY(id) VALUES (?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, facultad.getID());
        pstmt.setString(2, facultad.getNombre());
        pstmt.setInt(3, facultad.getDecano().getID());
        pstmt.executeUpdate();
    }
}   
      
       
    public Facultad obtenerFacultadPorID(int idFacultad) {
    Connection conexion = AuxiliarBD.getInstance().getConnection();
        String sql = "SELECT * FROM facultad WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idFacultad);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return FabricaObjetos.crearFacultad(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        personaDAO.obtenerPersonaPorID(rs.getInt("decano_id")) // Recuperar también al decano
                    );
                }
            }
        
    } catch (SQLException e) {
        inscripcionApp.mostrarError("Error al buscar la facultad: " + e.getMessage());
    }
    return null;
}
       
}
