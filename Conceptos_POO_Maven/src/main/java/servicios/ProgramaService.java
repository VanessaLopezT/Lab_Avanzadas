package servicios;

import BaseDatos.ConexionBD;
import DAO.ProgramaDAO;
import modelo.Programa;
import java.sql.Connection;
import java.sql.SQLException;

public class ProgramaService {
 
    public Programa obtenerProgramaPorID(int idPrograma) throws SQLException {
        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            ProgramaDAO programaDAO = new ProgramaDAO(conexion);
            return programaDAO.obtenerProgramaPorID(idPrograma);
        }
    }
}
