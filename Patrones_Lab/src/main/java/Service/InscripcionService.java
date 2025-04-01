package Service;

import BaseDatos.AuxiliarBD;
import BaseDatos.DatabaseConnection;
import DAO.InscripcionDAO;
import Fabrica_Objetos.FabricaDAO;
import modelo.Curso;
import modelo.Estudiante;
import modelo.Inscripcion;
import Fabrica_Objetos.FabricaModelo;
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

        Inscripcion nuevaInscripcion = FabricaModelo.crearInscripcion(curso, año, semestre, estudiante);

        Connection conexion = DatabaseConnection.getInstance().getConnection();
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            InscripcionDAO inscripcionDAO =FabricaDAO.crearInscripcionDAO();
            inscripcionDAO.guardarInscripcionBD(conexion, nuevaInscripcion);
            AuxiliarBD.mostrarDatosBD_INSCRIPCION();
        
    }
    
    public Inscripcion obtenerInscripcionPorCursoYEstudiante(int cursoID, int estudianteID) throws SQLException {
    Connection conexion =DatabaseConnection.getInstance().getConnection();
         InscripcionDAO inscripcionDAO =FabricaDAO.crearInscripcionDAO();
        return inscripcionDAO.obtenerInscripcionPorCursoYEstudiante(cursoID, estudianteID);
         
    }
}
