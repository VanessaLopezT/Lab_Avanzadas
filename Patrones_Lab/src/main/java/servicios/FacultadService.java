package servicios;

import BaseDatos.AuxiliarBD;
import DAO.FacultadDAO;
import modelo.Facultad;
import modelo.Persona;
import Factory.FabricaObjetos;
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

        Facultad nuevaFacultad = FabricaObjetos.crearFacultad(idFacultad, nombreFacultad, decano);

        Connection conexion = AuxiliarBD.getInstance().getConnection();
            FacultadDAO facultadDAO = new FacultadDAO();
            facultadDAO.guardarFacultadBD(conexion, nuevaFacultad);
            AuxiliarBD.mostrarDatosBD_FACULTAD();
        }
    
    
    public Facultad obtenerFacultadPorID(int idFacultad) throws SQLException {
     Connection conexion = AuxiliarBD.getInstance().getConnection();
            FacultadDAO facultadDAO = new FacultadDAO();
            return facultadDAO.obtenerFacultadPorID(idFacultad); 
    }
}
