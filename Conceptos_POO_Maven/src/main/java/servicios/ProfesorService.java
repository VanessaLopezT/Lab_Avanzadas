package servicios;

import BaseDatos.ConexionBD;
import DAO.ProfesorDAO;
import modelo.Profesor;

import java.sql.Connection;
import java.sql.SQLException;

public class ProfesorService {

    public void guardarProfesor(int id, String nombres, String apellidos, String email, String tipoContrato) throws SQLException {
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || tipoContrato.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos deben estar completos.");
        }

        Profesor nuevoProfesor = new Profesor(id, nombres, apellidos, email, tipoContrato);

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            ProfesorDAO profesorDAO = new ProfesorDAO(conexion);
            profesorDAO.guardarProfesorBD(conexion, nuevoProfesor);
            ConexionBD.mostrarDatosBD_PROFESOR();
        }
    }
}

