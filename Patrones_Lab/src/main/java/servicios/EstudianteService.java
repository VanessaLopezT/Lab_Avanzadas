package servicios;

import BaseDatos.AuxiliarBD;
import DAO.EstudianteDAO;
import modelo.Estudiante;
import modelo.Programa;
import Factory.FabricaObjetos;
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

        Estudiante nuevoEstudiante = FabricaObjetos.crearEstudiante(codigo, programa, activo, promedio, id, nombres, apellidos, email);

        Connection conexion = AuxiliarBD.getInstance().getConnection();
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            estudianteDAO.guardarEstudianteBD(conexion, nuevoEstudiante);
            AuxiliarBD.mostrarDatosBD_ESTUDIANTE();
        
    }
    
    public Estudiante obtenerEstudiantePorID(int idEstudiante) {    
           Connection conexion = AuxiliarBD.getInstance().getConnection();
            EstudianteDAO estudianteDAO = new EstudianteDAO(); 
        return estudianteDAO.obtenerEstudiantePorID(idEstudiante);
        
    }
}
