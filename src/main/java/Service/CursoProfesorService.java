package Service;

import BaseDatos.AuxiliarBD;
import BaseDatos.DatabaseConnection;
import DAO.CursoProfesorDAO;
import Fabrica_Objetos.FabricaDAO;
import modelo.Curso;
import modelo.Profesor;
import modelo.CursoProfesor;
import Fabrica_Objetos.FabricaModelo;
import java.sql.Connection;
import java.sql.SQLException;

public class CursoProfesorService {

    public void asignarProfesorACurso
        (Curso curso, int anio, int semestre, Profesor profesor) throws SQLException {
        if (semestre < 1 || semestre > 2) {
            throw new IllegalArgumentException("El semestre debe ser 1 o 2.");
        }

        if (curso == null) {
            throw new IllegalArgumentException("No existe un curso con ese ID en la base de datos.");
        }

        if (profesor == null) {
            throw new IllegalArgumentException("No existe un profesor con ese ID en la base de datos.");
        }

        CursoProfesor nuevaAsignacion = FabricaModelo.crearCursoProfesor(profesor, anio, semestre, curso);
        Connection conexion = DatabaseConnection.getInstance().getConnection();
            CursoProfesorDAO cursoProfesorDAO =FabricaDAO.crearCursoProfesorDAO();
            cursoProfesorDAO.guardarCursoProfesorBD(conexion, nuevaAsignacion);
            AuxiliarBD.mostrarDatosBD_CURSO_PROFESOR();
        
    }
}
