package servicios;

import BaseDatos.AuxiliarBD;
import DAO.InscripcionDAO;
import modelo.Curso;
import modelo.Estudiante;
import modelo.Inscripcion;
import Factory.DAOFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class InscripcionService {

    public void guardarInscripcion(Curso curso, int año, int semestre, Estudiante estudiante) throws SQLException {
        if (semestre < 1 || semestre > 2) {
            throw new IllegalArgumentException("El semestre debe ser 1 o 2.");
        }

        if (curso == null) {
            throw new IllegalArgumentException("No existe un curso con ese ID en la base de datos.");
        }

        if (estudiante == null) {
            throw new IllegalArgumentException("No existe un estudiante con ese ID en la base de datos.");
        }

        Inscripcion nuevaInscripcion = DAOFactory.crearInscripcion(curso, año, semestre, estudiante);

        try (Connection conexion = AuxiliarBD.conectar()) {
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            InscripcionDAO inscripcionDAO = new InscripcionDAO(conexion);
            inscripcionDAO.guardarInscripcionBD(conexion, nuevaInscripcion);
            AuxiliarBD.mostrarDatosBD_INSCRIPCION();
        }
    }
}
