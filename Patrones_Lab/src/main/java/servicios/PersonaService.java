package servicios;

import BaseDatos.AuxiliarBD;
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
        Connection conexion = AuxiliarBD.getInstance().getConnection();
            new PersonaDAO().guardarPersonaBD(conexion,nuevaPersona);
        
    }

    public void eliminarPersona(int idPersona) throws SQLException {
    Connection conexion = AuxiliarBD.getInstance().getConnection();
        new PersonaDAO().eliminarPersona(idPersona);
    
}

public void actualizarPersona(int idPersona) throws SQLException {
    Connection conexion = AuxiliarBD.getInstance().getConnection();
        new PersonaDAO().actualizarPersona(idPersona);
}

public boolean existePersona(int idPersona) throws SQLException {
    Connection conexion = AuxiliarBD.getInstance().getConnection();
        return new PersonaDAO().existePersona(idPersona);
    
}

public void inscribirPersona(int idPersona) throws SQLException {
    Connection conexion = AuxiliarBD.getInstance().getConnection();
        new PersonaDAO().inscribirPersona(idPersona);
    
}


    public Persona obtenerPersonaPorID(int idPersona) throws SQLException {
    Connection conexion = AuxiliarBD.getInstance().getConnection();
        PersonaDAO personaDAO = new PersonaDAO();     
        return personaDAO.obtenerPersonaPorID(idPersona);
         
    } 
    
    
}
