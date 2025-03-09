package servicios;

import BaseDatos.ConexionBD;
import DAO.EstudianteDAO;
import modelo.Estudiante;
import modelo.Programa;
import DAO.DAOFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class EstudianteService {

       
    public void guardarEstudiante(int id, int codigo, String nombres, String apellidos, String email, double promedio, Programa programa, boolean activo) throws SQLException {
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos deben estar completos.");
        }

        if (programa == null) {
            throw new IllegalArgumentException("No existe un programa con ese ID en la base de datos.");
        }

        Estudiante nuevoEstudiante = DAOFactory.crearEstudiante(codigo, programa, activo, promedio, id, nombres, apellidos, email);

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            EstudianteDAO estudianteDAO = new EstudianteDAO(conexion);
            estudianteDAO.guardarEstudianteBD(conexion, nuevoEstudiante);
            ConexionBD.mostrarDatosBD_ESTUDIANTE();
        }
    }
    
    public Estudiante obtenerEstudiantePorID(int idEstudiante) throws SQLException {
        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            EstudianteDAO estudianteDAO = new EstudianteDAO(conexion);
            return estudianteDAO.obtenerEstudiantePorID(idEstudiante);
        }
    }
}
