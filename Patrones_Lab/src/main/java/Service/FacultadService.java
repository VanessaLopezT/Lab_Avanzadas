package Service;

import BaseDatos.AuxiliarBD;
import BaseDatos.DatabaseConnection;
import DAO.FacultadDAO;
import Fabrica_Objetos.FabricaDAO;
import modelo.Facultad;
import modelo.Persona;
import Fabrica_Objetos.FabricaModelo;
import java.sql.Connection;
import java.sql.SQLException;

public class FacultadService {
   
    public void guardarFacultad(int idFacultad, String nombreFacultad, Persona decano) throws SQLException {
        if (nombreFacultad.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la facultad no puede estar vacío.");
        }

        if (decano == null) {
            throw new IllegalArgumentException("No existe una persona con ese ID en la base de datos.");
        }

        Facultad nuevaFacultad = FabricaModelo.crearFacultad(idFacultad, nombreFacultad, decano);

        Connection conexion = DatabaseConnection.getInstance().getConnection();
            FacultadDAO facultadDAO =FabricaDAO.crearFacultadDAO();
            facultadDAO.guardarFacultadBD(conexion, nuevaFacultad);
            AuxiliarBD.mostrarDatosBD_FACULTAD();
        }
    
    
    public Facultad obtenerFacultadPorID(int idFacultad) throws SQLException {
     Connection conexion = DatabaseConnection.getInstance().getConnection();
            FacultadDAO facultadDAO =FabricaDAO.crearFacultadDAO();
            return facultadDAO.obtenerFacultadPorID(idFacultad); 
    }
}
