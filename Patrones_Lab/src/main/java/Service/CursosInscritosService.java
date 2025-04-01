package Service;

import DAO.CursosInscritosDAO;
import modelo.Inscripcion;

import java.sql.SQLException;

public class CursosInscritosService {

    public void inscribirEnCursos(Inscripcion inscripcion) throws SQLException {
        if (inscripcion == null) {
            throw new IllegalArgumentException("La inscripción no puede ser nula.");
        }

        CursosInscritosDAO.inscribir(inscripcion);
    }

    public void guardarInscripcion(Inscripcion inscripcion) throws SQLException {
        if (inscripcion == null) {
            throw new IllegalArgumentException("La inscripción no puede ser nula.");
        }

        CursosInscritosDAO.guardarDatosBD(inscripcion);
    }

    public void buscarInscripcion(int inscripcionID, int estudianteID) throws SQLException {
        CursosInscritosDAO.buscarInscripcionEnCursosInscritos(inscripcionID, estudianteID);
    }

    public void eliminarInscripcion(int inscripcionID, int estudianteID) throws SQLException {
        CursosInscritosDAO.Eliminar(inscripcionID, estudianteID);
    }

    public void actualizarInscripcion(int inscripcionID, int estudianteID, int nuevoCursoID) throws SQLException {
        CursosInscritosDAO.Actualizar(inscripcionID, estudianteID, nuevoCursoID);
    }
}
