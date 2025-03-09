/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import modelo.InscripcionesPersona;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.Persona;
import BaseDatos.ConexionBD;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author VANESA
 */
public class InscripcionesPersonaDAO {
      private static final List<Persona> listado = new ArrayList<>();
      private Connection conexion;


    public boolean inscribir(Persona persona) {
        String sqlBuscar = "SELECT * FROM inscripciones_personas WHERE persona_id = ?";
        String sqlInsertar = "INSERT INTO inscripciones_personas (persona_id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
             PreparedStatement pstmtInsertar = conexion.prepareStatement(sqlInsertar)) {

            // Verificar si la persona ya está inscrita
            pstmtBuscar.setInt(1, persona.getID());
            ResultSet rs = pstmtBuscar.executeQuery();
            if (rs.next()) {
                return false; // Ya está inscrita
            }

            // Insertar en la base de datos
            pstmtInsertar.setInt(1, persona.getID());
            pstmtInsertar.setString(2, persona.getNombres());
            pstmtInsertar.setString(3, persona.getApellidos());
            pstmtInsertar.setString(4, persona.getEmail());

            return pstmtInsertar.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al inscribir persona: " + e.getMessage());
            return false;
        }
    }

    public Persona buscarPersonaInscrita(int personaID) {
        String sql = "SELECT * FROM inscripciones_personas WHERE persona_id = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, personaID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return DAOFactory.crearPersona(
                    rs.getInt("persona_id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar persona: " + e.getMessage());
        }
        return null;
    }

    public boolean eliminarPersona(int personaID) {
        String sql = "DELETE FROM inscripciones_personas WHERE persona_id = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, personaID);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar persona: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarPersona(int personaID, String nuevoNombre, String nuevoApellido, String nuevoEmail) {
        String sql = "UPDATE inscripciones_personas SET nombres = ?, apellidos = ?, email = ? WHERE persona_id = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevoApellido);
            pstmt.setString(3, nuevoEmail);
            pstmt.setInt(4, personaID);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar persona: " + e.getMessage());
            return false;
        }
    }

    public List<Persona> obtenerTodasLasInscripciones() {
        List<Persona> listado = new ArrayList<>();
        String sql = "SELECT * FROM inscripciones_personas";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listado.add(DAOFactory.crearPersona(
                    rs.getInt("persona_id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener datos: " + e.getMessage());
        }
        return listado;
    }
    
     public void guardarEnArchivo() {
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM inscripciones_personas");
         ResultSet rs = stmt.executeQuery()) {
        
        listado.clear(); // Limpiar listado antes de agregar nuevos datos
        
        while (rs.next()) {
            Persona persona = new Persona(
                rs.getInt("persona_id"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("email")
            );
            listado.add(persona);
        }

        System.out.println("\nDatos cargados desde la base de datos en el listado.");
        
    } catch (SQLException e) {
        System.err.println("Error al obtener datos de la base de datos: " + e.getMessage());
        return; // Si hay error, no intenta guardar en el archivo
    }

    try (FileOutputStream archivo = new FileOutputStream("personas.bin");
         ObjectOutputStream escritura = new ObjectOutputStream(archivo)) {
        
        escritura.writeObject(listado);
        System.out.print("\nguardainformacion --> Lista de Persona añadida con éxito en archivo binario.");
        
    } catch (IOException error) {
        error.printStackTrace(System.out);
    }
}


    public void cargarDesdeArchivo() {
    try (FileInputStream archivo = new FileInputStream("personas.bin");
         ObjectInputStream lectura = new ObjectInputStream(archivo)) {
        
        List<Persona> listaRecuperada = (List<Persona>) lectura.readObject();
        
        listado.clear();
        listado.addAll(listaRecuperada);

        System.out.println("\ncargarDatos --> Lista de Persona leída con éxito desde el archivo binario:");
        for (Persona persona : listado) {
            System.out.println(persona);
        }

    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error al leer el archivo:");
        e.printStackTrace();
    }
}

    
}
