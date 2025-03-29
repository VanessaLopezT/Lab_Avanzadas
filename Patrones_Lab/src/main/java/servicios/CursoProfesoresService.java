package servicios;

import DAO.CursoProfesoresDAO;
import modelo.CursoProfesor;
import java.sql.SQLException;

public class CursoProfesoresService {

    private final CursoProfesoresDAO cursoProfesoresDAO = new CursoProfesoresDAO();

    public void inscribirCursoProfesor(CursoProfesor cursoProfesor) throws SQLException {
        if (cursoProfesor == null) {
            throw new IllegalArgumentException("La asignación no puede ser nula.");
        }
        cursoProfesoresDAO.inscribir(cursoProfesor);
    }

    public void eliminarCursoProfesor(int profesorID, int cursoID, int anio, int semestre) throws SQLException {
        cursoProfesoresDAO.eliminar(profesorID, cursoID, anio, semestre);
    }

    public void actualizarCursoProfesor(int profesorID, int cursoID, int nuevoAnio, int nuevoSemestre) throws SQLException {
        cursoProfesoresDAO.actualizar(profesorID, cursoID, nuevoAnio, nuevoSemestre);
    }

    public CursoProfesor buscarCursoProfesor(int profesorID, int cursoID) throws SQLException {
        return cursoProfesoresDAO.buscarCursoProfesor(profesorID, cursoID);
    }
}