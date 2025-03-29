package servicios;

import BaseDatos.AuxiliarBD;
import DAO.InscripcionDAO;
import modelo.Curso;
import modelo.Estudiante;
import modelo.Inscripcion;
import Factory.FabricaObjetos;
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

        Inscripcion nuevaInscripcion = FabricaObjetos.crearInscripcion(curso, año, semestre, estudiante);

        Connection conexion = AuxiliarBD.getInstance().getConnection();
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            InscripcionDAO inscripcionDAO = new InscripcionDAO();
            inscripcionDAO.guardarInscripcionBD(conexion, nuevaInscripcion);
            AuxiliarBD.mostrarDatosBD_INSCRIPCION();
        
    }
    
    public Inscripcion obtenerInscripcionPorCursoYEstudiante(int cursoID, int estudianteID) throws SQLException {
    Connection conexion =AuxiliarBD.getInstance().getConnection();
         InscripcionDAO inscripcionDAO = new InscripcionDAO();
        return inscripcionDAO.obtenerInscripcionPorCursoYEstudiante(cursoID, estudianteID);
         
    }
}
