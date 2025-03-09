package servicios;

import BaseDatos.ConexionBD;
import DAO.PersonaDAO;
import modelo.Persona;

import java.sql.Connection;
import java.sql.SQLException;

public class PersonaService {

    public void guardarPersona(int id, String nombres, String apellidos, String email) throws SQLException {
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos deben estar completos.");
        }

        Persona nuevaPersona = new Persona(id, nombres, apellidos, email);
        try (Connection conexion = ConexionBD.conectar()) {
            new PersonaDAO(conexion).guardarPersonaBD(conexion,nuevaPersona);
        }
    }

    public void eliminarPersona(int idPersona) throws SQLException {
    try (Connection conexion = ConexionBD.conectar()) {
        new PersonaDAO(conexion).eliminarPersona(idPersona);
    }
}

public void actualizarPersona(int idPersona) throws SQLException {
    try (Connection conexion = ConexionBD.conectar()) {
        new PersonaDAO(conexion).actualizarPersona(idPersona);
    }
}

public boolean existePersona(int idPersona) throws SQLException {
    try (Connection conexion = ConexionBD.conectar()) {
        return new PersonaDAO(conexion).existePersona(idPersona);
    }
}

public void inscribirPersona(int idPersona) throws SQLException {
    try (Connection conexion = ConexionBD.conectar()) {
        new PersonaDAO(conexion).inscribirPersona(idPersona);
    }
}


    public Persona obtenerPersonaPorID(int idPersona) throws SQLException {
        try (Connection conexion = ConexionBD.conectar()) {
            return new PersonaDAO(conexion).obtenerPersonaPorID(idPersona);
        }
    }
    
    
    
}
