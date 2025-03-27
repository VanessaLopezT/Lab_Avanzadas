package servicios;

import BaseDatos.AuxiliarBD;
import DAO.CursoProfesorDAO;
import modelo.Curso;
import modelo.Profesor;
import modelo.CursoProfesor;
import Factory.DAOFactory;
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

        CursoProfesor nuevaAsignacion = DAOFactory.crearCursoProfesor(profesor, anio, semestre, curso);

        try (Connection conexion = AuxiliarBD.conectar()) {
            CursoProfesorDAO cursoProfesorDAO = new CursoProfesorDAO(conexion);
            cursoProfesorDAO.guardarCursoProfesorBD(conexion, nuevaAsignacion);
            AuxiliarBD.mostrarDatosBD_CURSO_PROFESOR();
        }
    }
}
