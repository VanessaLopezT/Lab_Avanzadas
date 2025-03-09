package servicios;

import BaseDatos.ConexionBD;
import DAO.FacultadDAO;
import modelo.Facultad;
import java.sql.Connection;
import java.sql.SQLException;

public class FacultadService {

    public Facultad obtenerFacultadPorID(int idFacultad) throws SQLException {
        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            FacultadDAO facultadDAO = new FacultadDAO(conexion);
            return facultadDAO.obtenerFacultadPorID(idFacultad);
        }
    }
}
