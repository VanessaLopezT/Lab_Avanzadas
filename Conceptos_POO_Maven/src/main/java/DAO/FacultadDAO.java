/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import modelo.Facultad;
import modelo.Persona;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;


public class FacultadDAO {

    private Connection conexion;
    PersonaDAO personaDAO=new PersonaDAO(conexion);
    
    public FacultadDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
       public void guardarFacultadBD(Connection conexion, Facultad facultad) throws SQLException {

    String sql = "MERGE INTO facultad KEY(id) VALUES (?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, facultad.getID());
        pstmt.setString(2, facultad.getNombre());
        pstmt.setInt(3, facultad.getDecano().getID());
        pstmt.executeUpdate();
    }
}   
       public Facultad obtenerFacultadPorID(int idFacultad) throws SQLException {
        String sql = "SELECT * FROM facultad WHERE id = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idFacultad);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Persona decano = personaDAO.obtenerPersonaPorID(rs.getInt("decano_id")); // Asumimos que este método existe
                    
                    return DAOFactory.crearFacultad(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        decano
                    );
                }
            }
        }
        return null; // Retorna null si la facultad no existe
    }

       
       
       
}
