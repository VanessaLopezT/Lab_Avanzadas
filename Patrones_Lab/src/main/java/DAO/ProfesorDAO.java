/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import BaseDatos.AuxiliarBD;
import Factory.FabricaObjetos;
import interfaz.InscripcionApp;
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

    public ProfesorDAO() {
        this.conexion = AuxiliarBD.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
    }
    
    InscripcionApp inscripcionApp=new InscripcionApp();
    
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
      
    public Profesor obtenerProfesorPorID(int idProfesor) {
    Connection conexion = AuxiliarBD.getInstance().getConnection();
        // 🔹 Consulta combinando profesor y persona para obtener toda la información
        String sql = "SELECT p.id, pe.nombres, pe.apellidos, pe.email, p.tipo_contrato " +
                     "FROM profesor p " +
                     "JOIN persona pe ON p.id = pe.id " +
                     "WHERE p.id = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idProfesor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return FabricaObjetos.crearProfesor(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("tipo_contrato")
                    );
                }
            }
        
    } catch (SQLException e) {
        inscripcionApp.mostrarError("Error al buscar el profesor: " + e.getMessage());
    }
    return null;
}

}
