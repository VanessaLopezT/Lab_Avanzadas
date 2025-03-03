/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conceptos_poo_maven;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
    
/**
 *
 * @author Estudiante_MCA
 */
public class InscripcionesPersona implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final List<Persona> listado = new ArrayList<>();

    public void inscribir(Persona persona) {
    String sqlBorrar = "DELECT * FROM inscripciones_personas";
    String sqlBuscar = "SELECT * FROM inscripciones_personas WHERE persona_id = ?";
    String sqlInsertar = "INSERT INTO inscripciones_personas (persona_id, nombres, apellidos, email) VALUES (?, ?, ?, ?)";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement pstmtInsertar = conexion.prepareStatement(sqlInsertar)) {

        // Verificar si la persona ya está inscrita
        pstmtBuscar.setInt(1, persona.getID());
        ResultSet rs = pstmtBuscar.executeQuery();
        if (rs.next()) {
            return;
        }

        // Insertar si no está inscrita
        pstmtInsertar.setInt(1, persona.getID());
        pstmtInsertar.setString(2, persona.getNombres());
        pstmtInsertar.setString(3, persona.getApellidos());
        pstmtInsertar.setString(4, persona.getEmail());

        int filasAfectadas = pstmtInsertar.executeUpdate();
        if (filasAfectadas > 0) {
            listado.add(persona); // Se agrega a la lista
            System.out.println("✅ Persona inscrita correctamente.");
        } else {
            System.out.println("⚠ No se pudo inscribir a la persona.");
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al inscribir a la persona: " + e.getMessage());
    }
}

public void buscarPersonaInscrita(int personaID) {
    System.out.print("Buscando persona...");
    String sql = "SELECT * FROM INSCRIPCIONES_PERSONAS WHERE PERSONA_ID = ?;";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        
        pstmt.setInt(1, personaID);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            System.out.println("✅ Persona inscrita encontrada:" +
                    " | ID: " + rs.getInt("ID") +
                    " | PERSONA_ID: " + rs.getInt("PERSONA_ID") +
                    " | NOMBRES: " + rs.getString("NOMBRES") +
                    " | APELLIDOS: " + rs.getString("APELLIDOS") +
                    " | EMAIL: " + rs.getString("EMAIL"));
        } else {
            System.out.println("⚠ No se encontró a la persona en INSCRIPCIONES_PERSONAS.");
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al buscar persona en INSCRIPCIONES_PERSONAS: " + e.getMessage());
    }
}

public void eliminarPersona(int personaID) {
    String sqlBuscar = "SELECT * FROM persona WHERE ID = ?;";
    String sqlEliminar = "DELETE FROM persona WHERE ID = ?;";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement pstmtEliminar = conexion.prepareStatement(sqlEliminar)) {

        // 🔍 Buscar la persona en la base de datos
        pstmtBuscar.setInt(1, personaID);
        ResultSet rs = pstmtBuscar.executeQuery();

        if (rs.next()) {
            int idRegistro = rs.getInt("ID");
            System.out.println("✅ Persona encontrada en la base de datos:");
            System.out.println("ID: " + idRegistro +
                               " | NOMBRES: " + rs.getString("NOMBRES") +
                               " | APELLIDOS: " + rs.getString("APELLIDOS") +
                               " | EMAIL: " + rs.getString("EMAIL"));

            // 🗑 Eliminar la persona
            pstmtEliminar.setInt(1, idRegistro);

            int filasAfectadas = pstmtEliminar.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Persona eliminada correctamente.");
                listado.removeIf(p -> p.getID() == personaID); // También la eliminamos de la lista en memoria
            } else {
                System.out.println("⚠ No se pudo eliminar la persona.");
            }
        } else {
            System.out.println("⚠ No se encontró la persona en la base de datos.");
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al eliminar la persona: " + e.getMessage());
    }
}


   public void actualizarPersona(int personaID, String nuevoNombre, String nuevoApellido, String nuevoEmail) {
    String sqlBuscar = "SELECT * FROM INSCRIPCIONES_PERSONAS WHERE PERSONA_ID = ?;";
    String sqlActualizar = "UPDATE INSCRIPCIONES_PERSONAS SET NOMBRES = ?, APELLIDOS = ?, EMAIL = ? WHERE PERSONA_ID = ?;";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement pstmtActualizar = conexion.prepareStatement(sqlActualizar)) {

        // 🔍 Buscar la persona en la tabla INSCRIPCIONES_PERSONAS
        pstmtBuscar.setInt(1, personaID);
        ResultSet rs = pstmtBuscar.executeQuery();

        if (rs.next()) {
            System.out.println("✅ Persona encontrada en INSCRIPCIONES_PERSONAS:");
            System.out.println("ID: " + rs.getInt("PERSONA_ID") +
                               " | NOMBRES: " + rs.getString("NOMBRES") +
                               " | APELLIDOS: " + rs.getString("APELLIDOS") +
                               " | EMAIL: " + rs.getString("EMAIL"));

            // ✏ Actualizar los datos de la persona en la tabla
            pstmtActualizar.setString(1, nuevoNombre);
            pstmtActualizar.setString(2, nuevoApellido);
            pstmtActualizar.setString(3, nuevoEmail);
            pstmtActualizar.setInt(4, personaID);

            int filasAfectadas = pstmtActualizar.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Datos de la persona actualizados correctamente.");
            } else {
                System.out.println("⚠ No se pudo actualizar la persona.");
            }
        } else {
            System.out.println("⚠ No se encontró la persona en INSCRIPCIONES_PERSONAS.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public boolean guardarDatosBD(Persona persona) {
        String sql = "INSERT INTO persona (id, nombres, apellidos, email) VALUES (?, ?, ?, ?);";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, persona.getID());
            pstmt.setString(2, persona.getNombres());
            pstmt.setString(3, persona.getApellidos());
            pstmt.setString(4, persona.getEmail());
            int filasAfectadas = pstmt.executeUpdate();
            
            return filasAfectadas > 0; // Retorna true si se insertó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   
    
   public void guardainformacion() {
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

        System.out.println("\n🔄 Datos cargados desde la base de datos en el listado.");
        
    } catch (SQLException e) {
        System.err.println("❌ Error al obtener datos de la base de datos: " + e.getMessage());
        return; // Si hay error, no intenta guardar en el archivo
    }

    try (FileOutputStream archivo = new FileOutputStream("personas.bin");
         ObjectOutputStream escritura = new ObjectOutputStream(archivo)) {
        
        escritura.writeObject(listado);
        System.out.print("\n💾 guardainformacion --> Lista de Persona añadida con éxito en archivo binario.");
        
    } catch (IOException error) {
        error.printStackTrace(System.out);
    }
}


    public void cargarDatos() {
    try (FileInputStream archivo = new FileInputStream("personas.bin");
         ObjectInputStream lectura = new ObjectInputStream(archivo)) {
        
        List<Persona> listaRecuperada = (List<Persona>) lectura.readObject();
        
        listado.clear();
        listado.addAll(listaRecuperada);

        System.out.println("\n📂 cargarDatos --> Lista de Persona leída con éxito desde el archivo binario:");
        for (Persona persona : listado) {
            System.out.println(persona);
        }

    } catch (IOException | ClassNotFoundException e) {
        System.out.println("❌ Error al leer el archivo:");
        e.printStackTrace();
    }
}


    
}
