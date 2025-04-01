package Service;

import BaseDatos.AuxiliarBD;
import BaseDatos.DatabaseConnection;
import DAO.EstudianteDAO;
import Fabrica_Objetos.FabricaDAO;
import modelo.Estudiante;
import modelo.Programa;
import Fabrica_Objetos.FabricaModelo;
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

        Estudiante nuevoEstudiante = FabricaModelo.crearEstudiante(codigo, programa, activo, promedio, id, nombres, apellidos, email);

        Connection conexion = DatabaseConnection.getInstance().getConnection();
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            EstudianteDAO estudianteDAO =FabricaDAO.crearEstudianteDAO();
            estudianteDAO.guardarEstudianteBD(conexion, nuevoEstudiante);
            AuxiliarBD.mostrarDatosBD_ESTUDIANTE();
        
    }
    
    public Estudiante obtenerEstudiantePorID(int idEstudiante) {    
           Connection conexion = DatabaseConnection.getInstance().getConnection();
            EstudianteDAO estudianteDAO =FabricaDAO.crearEstudianteDAO(); 
        return estudianteDAO.obtenerEstudiantePorID(idEstudiante);
        
    }
}
