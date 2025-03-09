/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import BaseDatos.ConexionBD;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.io.Serializable;
import DAO.InscripcionesPersonaDAO;
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

    private final InscripcionesPersonaDAO dao = new InscripcionesPersonaDAO();

    public boolean inscribir(Persona persona) {
        boolean resultado = dao.inscribir(persona);
        if (resultado) {
            listado.add(persona);
        } 
        return resultado;
    }

    public Persona buscarPersonaInscrita(int personaID) {
        return dao.buscarPersonaInscrita(personaID);
    }

    public boolean eliminarPersona(int personaID) {
        boolean resultado = dao.eliminarPersona(personaID);
        if (resultado) {
            listado.removeIf(p -> p.getID() == personaID);
            System.out.println("Persona eliminada correctamente.");
        } else {
            System.out.println("No se pudo eliminar la persona.");
        }
        return resultado;
    }

    public boolean actualizarPersona(int personaID, String nuevoNombre, String nuevoApellido, String nuevoEmail) {
        return dao.actualizarPersona(personaID, nuevoNombre, nuevoApellido, nuevoEmail);
    }

    public void cargarDesdeBD() {
        listado.addAll(dao.obtenerTodasLasInscripciones());
        System.out.println("Datos cargados desde la BD.");
    }


   
   
}
