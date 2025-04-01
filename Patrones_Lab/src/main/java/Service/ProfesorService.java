package Service;

import BaseDatos.AuxiliarBD;
import BaseDatos.DatabaseConnection;
import DAO.ProfesorDAO;
import Fabrica_Objetos.FabricaDAO;
import Fabrica_Objetos.FabricaModelo;
import modelo.Profesor;

import java.sql.Connection;
import java.sql.SQLException;

public class ProfesorService {
    public void guardarProfesor(int id, String nombres, String apellidos, String email, String tipoContrato) throws SQLException {
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || tipoContrato.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos deben estar completos.");
        }

        Profesor nuevoProfesor = FabricaModelo.crearProfesor(id, nombres, apellidos, email, tipoContrato);

        Connection conexion = DatabaseConnection.getInstance().getConnection();
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            ProfesorDAO profesorDAO =FabricaDAO.crearProfesorDAO();
            profesorDAO.guardarProfesorBD(conexion, nuevoProfesor);
            AuxiliarBD.mostrarDatosBD_PROFESOR();
        
    }
    
    public Profesor obtenerProfesorPorID(int idProfesor){
    Connection conexion = DatabaseConnection.getInstance().getConnection();
    ProfesorDAO profesorDAO = FabricaDAO.crearProfesorDAO();     
        return profesorDAO.obtenerProfesorPorID(idProfesor);
        }
    
}

