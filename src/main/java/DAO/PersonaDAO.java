/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import BaseDatos.AuxiliarBD;
import BaseDatos.DatabaseConnection;
import Fabrica_Objetos.FabricaModelo;
import interfaz.InscripcionApp;
import modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author VANESA
 */
public class PersonaDAO {
    
    private Connection conexion;

    public PersonaDAO() {
        this.conexion = DatabaseConnection.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
    }
    
    InscripcionApp inscripcionApp=FabricaModelo.crearInscripcionApp();

        
public void guardarPersonaBD(Connection conexion, Persona persona) throws SQLException { 
    String sqlPersona = "MERGE INTO persona KEY(id) VALUES (?, ?, ?, ?);";
    
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlPersona)) {
        pstmt.setInt(1, persona.getID());
        pstmt.setString(2, persona.getNombres());
        pstmt.setString(3, persona.getApellidos());
        pstmt.setString(4, persona.getEmail()); 
        pstmt.executeUpdate();
        AuxiliarBD.mostrarDatosBD_PERSONA();
    }
}
    
    public void eliminarPersona(int idPersona) throws SQLException {
    String sql = "DELETE FROM inscripciones_personas WHERE persona_id = ?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idPersona);
        stmt.executeUpdate();
        AuxiliarBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
    }
}


   public void actualizarPersona(int idPersona) throws SQLException {
    String sqlBuscar = "SELECT * FROM PERSONA WHERE ID = ?";
    String sqlActualizar = "UPDATE inscripciones_personas SET nombres = ?, apellidos = ?, email = ? WHERE persona_id = ?";
    try (PreparedStatement stmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement stmtActualizar = conexion.prepareStatement(sqlActualizar)) {
        
        stmtBuscar.setInt(1, idPersona);
        ResultSet rs = stmtBuscar.executeQuery();
        if (!rs.next()) return;

        stmtActualizar.setString(1, rs.getString("NOMBRES"));
        stmtActualizar.setString(2, rs.getString("APELLIDOS"));
        stmtActualizar.setString(3, rs.getString("EMAIL"));
        stmtActualizar.setInt(4, idPersona);
        stmtActualizar.executeUpdate();
        
        // Se llama a mostrarDatos sin depender de un return booleano
        AuxiliarBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
    }
}


    public boolean existePersona(int idPersona) throws SQLException {
        String sql = "SELECT COUNT(*) FROM PERSONA WHERE ID = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPersona);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void inscribirPersona(int idPersona) throws SQLException {
    String sqlBuscar = "SELECT * FROM PERSONA WHERE ID = ?";
    String sqlInsertar = "INSERT INTO inscripciones_personas (persona_id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement stmtInsertar = conexion.prepareStatement(sqlInsertar)) {

        stmtBuscar.setInt(1, idPersona);
        ResultSet rs = stmtBuscar.executeQuery();
        if (!rs.next()) return;

        stmtInsertar.setInt(1, idPersona);
        stmtInsertar.setString(2, rs.getString("NOMBRES"));
        stmtInsertar.setString(3, rs.getString("APELLIDOS"));
        stmtInsertar.setString(4, rs.getString("EMAIL"));
        stmtInsertar.executeUpdate();
        AuxiliarBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
    }
}


    public Persona obtenerPersonaPorID(int idPersona) {
   Connection conexion = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM persona WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPersona);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return FabricaModelo.crearPersona(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                    );
                }
            }
    } catch (SQLException e) {
        inscripcionApp.mostrarError("Error al buscar la persona: " + e.getMessage());
    }
    return null;
}

}
