package servicios;

import DAO.InscripcionesPersonaDAO;
import modelo.Persona;
import java.sql.Connection;
import java.sql.SQLException;
import BaseDatos.ConexionBD;

public class InscripcionesPersonaService {
    private final InscripcionesPersonaDAO inscripcionesPersonaDAO = new InscripcionesPersonaDAO();

    public boolean inscribirPersona(Persona persona) throws SQLException {
        if (persona == null) {
            throw new IllegalArgumentException("La persona no puede ser nula.");
        }

        return inscripcionesPersonaDAO.inscribir(persona);
    }

    public boolean eliminarPersonaInscrita(int personaID) throws SQLException {
        return inscripcionesPersonaDAO.eliminarPersona(personaID);
    }

    public boolean actualizarInscripcion(int personaID, String nuevoNombre, String nuevoApellido, String nuevoEmail) throws SQLException {
        return inscripcionesPersonaDAO.actualizarPersona(personaID, nuevoNombre, nuevoApellido, nuevoEmail);
    }

    public Persona buscarPersonaInscrita(int personaID) throws SQLException {
        return inscripcionesPersonaDAO.buscarPersonaInscrita(personaID);
    }
}
