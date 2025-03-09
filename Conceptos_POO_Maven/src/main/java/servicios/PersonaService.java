/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;
import BaseDatos.ConexionBD;
import DAO.PersonaDAO;
import modelo.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaService {
    public void guardarPersona(int id, String nombres, String apellidos, String email) throws SQLException {
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos deben estar completos.");
        }

        Persona nuevaPersona = new Persona(id, nombres, apellidos, email);
        
        try (Connection conexion = ConexionBD.conectar()) {
            
            PersonaDAO personaDAO = new PersonaDAO(conexion);
            personaDAO.guardarPersonaBD(conexion, nuevaPersona);
            ConexionBD.mostrarDatosBD_PERSONA();
        }
    }
    
    public boolean eliminarPersona(int idPersona) throws SQLException {
        String sqlEliminar = "DELETE FROM inscripciones_personas WHERE persona_id = ?";
        
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmtEliminar = conexion.prepareStatement(sqlEliminar)) {
             
            stmtEliminar.setInt(1, idPersona);
            int filasAfectadas = stmtEliminar.executeUpdate();
            
            if (filasAfectadas > 0) {
                ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
                return true;
            }
        }
        return false; // Si no se eliminó ninguna fila, retornamos falso.
    }
    
    public boolean actualizarPersona(int idPersona) throws SQLException {
        String sqlBuscar = "SELECT * FROM PERSONA WHERE ID = ?";
        String sqlActualizar = "UPDATE inscripciones_personas SET nombres = ?, apellidos = ?, email = ? WHERE persona_id = ?";
        
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmtBuscar = conexion.prepareStatement(sqlBuscar);
             PreparedStatement stmtActualizar = conexion.prepareStatement(sqlActualizar)) {
            
            // Buscar la persona en la base de datos
            stmtBuscar.setInt(1, idPersona);
            ResultSet rs = stmtBuscar.executeQuery();
            if (!rs.next()) {
                return false; // La persona no existe
            }
            
            // Obtener datos de la persona
            String nombres = rs.getString("NOMBRES");
            String apellidos = rs.getString("APELLIDOS");
            String email = rs.getString("EMAIL");

            // Ejecutar actualización
            stmtActualizar.setString(1, nombres);
            stmtActualizar.setString(2, apellidos);
            stmtActualizar.setString(3, email);
            stmtActualizar.setInt(4, idPersona);

            int filasAfectadas = stmtActualizar.executeUpdate();
            ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
            return filasAfectadas > 0;
        }
    }
    
    public boolean existePersona(int idPersona) throws SQLException {
        String sql = "SELECT COUNT(*) FROM PERSONA WHERE ID = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    public boolean inscribirPersona(int idPersona) throws SQLException {
        String sqlBuscar = "SELECT * FROM PERSONA WHERE ID = ?";
        String sqlInsertar = "INSERT INTO inscripciones_personas (persona_id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmtBuscar = conexion.prepareStatement(sqlBuscar);
             PreparedStatement stmtInsertar = conexion.prepareStatement(sqlInsertar)) {

            // Buscar la persona en la base de datos
            stmtBuscar.setInt(1, idPersona);
            ResultSet rs = stmtBuscar.executeQuery();
            if (!rs.next()) {
                return false; // La persona no existe
            }

            // Obtener datos de la persona
            String nombres = rs.getString("NOMBRES");
            String apellidos = rs.getString("APELLIDOS");
            String email = rs.getString("EMAIL");

            // Insertar en inscripciones_personas
            stmtInsertar.setInt(1, idPersona);
            stmtInsertar.setString(2, nombres);
            stmtInsertar.setString(3, apellidos);
            stmtInsertar.setString(4, email);   
            int filasAfectadas = stmtInsertar.executeUpdate();
            ConexionBD.mostrarDatosBD_INSCRIPCIONES_PERSONAS();
            return filasAfectadas > 0;
        }
    }
    
    public Persona obtenerPersonaPorID(int idPersona) throws SQLException {
        try (Connection conexion = ConexionBD.conectar()) {
            PersonaDAO personaDAO = new PersonaDAO(conexion);
            return personaDAO.obtenerPersonaPorID(idPersona);
        }
    }
    
}


