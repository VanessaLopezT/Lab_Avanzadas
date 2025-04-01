package Service;

import BaseDatos.DatabaseConnection;
import DAO.PersonaDAO;
import Fabrica_Objetos.FabricaDAO;
import Fabrica_Objetos.FabricaModelo;
import modelo.Persona;

import java.sql.Connection;
import java.sql.SQLException;

public class PersonaService {

    public void guardarPersona(int id, String nombres, String apellidos, String email) throws SQLException {
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos deben estar completos.");
        }

        Persona nuevaPersona = FabricaModelo.crearPersona(id, nombres, apellidos, email);
        Connection conexion = DatabaseConnection.getInstance().getConnection();
            FabricaDAO.crearPersonaDAO().guardarPersonaBD(conexion,nuevaPersona);
        
    }

    public void eliminarPersona(int idPersona) throws SQLException {
    Connection conexion = DatabaseConnection.getInstance().getConnection();
        FabricaDAO.crearPersonaDAO().eliminarPersona(idPersona);
    
}

public void actualizarPersona(int idPersona) throws SQLException {
    Connection conexion = DatabaseConnection.getInstance().getConnection();
        FabricaDAO.crearPersonaDAO().actualizarPersona(idPersona);
}

public boolean existePersona(int idPersona) throws SQLException {
    Connection conexion = DatabaseConnection.getInstance().getConnection();
        return FabricaDAO.crearPersonaDAO().existePersona(idPersona);
    
}

public void inscribirPersona(int idPersona) throws SQLException {
    Connection conexion = DatabaseConnection.getInstance().getConnection();
        FabricaDAO.crearPersonaDAO().inscribirPersona(idPersona);
    
}


    public Persona obtenerPersonaPorID(int idPersona) throws SQLException {
    Connection conexion = DatabaseConnection.getInstance().getConnection();
        PersonaDAO personaDAO = FabricaDAO.crearPersonaDAO();     
        return personaDAO.obtenerPersonaPorID(idPersona);
         
    } 
    
    
}
