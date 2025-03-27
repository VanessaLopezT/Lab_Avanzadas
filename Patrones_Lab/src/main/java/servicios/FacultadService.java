package servicios;

import BaseDatos.AuxiliarBD;
import DAO.FacultadDAO;
import modelo.Facultad;
import modelo.Persona;
import Factory.DAOFactory;
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

        Facultad nuevaFacultad = DAOFactory.crearFacultad(idFacultad, nombreFacultad, decano);

        try (Connection conexion = AuxiliarBD.conectar()) {
            FacultadDAO facultadDAO = new FacultadDAO(conexion);
            facultadDAO.guardarFacultadBD(conexion, nuevaFacultad);
            AuxiliarBD.mostrarDatosBD_FACULTAD();
        }
    }
}
