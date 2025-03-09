package servicios;

import DAO.CursoProfesoresDAO;
import modelo.CursoProfesor;
import modelo.Curso;
import modelo.Profesor;
import java.sql.SQLException;

public class CursoProfesoresService {

    private final CursoProfesoresDAO cursoProfesoresDAO = new CursoProfesoresDAO();

    public boolean inscribirCursoProfesor(CursoProfesor cursoProfesor) throws SQLException {
        if (cursoProfesor == null) {
            throw new IllegalArgumentException("La asignación no puede ser nula.");
        }
        return cursoProfesoresDAO.inscribir(cursoProfesor);
    }

    public boolean eliminarCursoProfesor(int profesorID, int cursoID, int anio, int semestre) throws SQLException {
        return cursoProfesoresDAO.eliminar(profesorID, cursoID, anio, semestre);
    }

    public boolean actualizarCursoProfesor(int profesorID, int cursoID, int nuevoAnio, int nuevoSemestre) throws SQLException {
        return cursoProfesoresDAO.actualizar(profesorID, cursoID, nuevoAnio, nuevoSemestre);
    }

    public CursoProfesor buscarCursoProfesor(int profesorID, int cursoID) throws SQLException {
        return cursoProfesoresDAO.buscarCursoProfesor(profesorID, cursoID);
    }
}
