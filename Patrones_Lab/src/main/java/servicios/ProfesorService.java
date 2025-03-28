package servicios;

import BaseDatos.AuxiliarBD;
import DAO.ProfesorDAO;
import modelo.Profesor;

import java.sql.Connection;
import java.sql.SQLException;

public class ProfesorService {
    public void guardarProfesor(int id, String nombres, String apellidos, String email, String tipoContrato) throws SQLException {
        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || tipoContrato.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos deben estar completos.");
        }

        Profesor nuevoProfesor = new Profesor(id, nombres, apellidos, email, tipoContrato);

        Connection conexion = AuxiliarBD.getInstance().getConnection();
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            if (conexion == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos.");
            }
            ProfesorDAO profesorDAO = new ProfesorDAO();
            profesorDAO.guardarProfesorBD(conexion, nuevoProfesor);
            AuxiliarBD.mostrarDatosBD_PROFESOR();
        
    }
    
    public Profesor obtenerProfesorPorID(int idProfesor){
    Connection conexion = AuxiliarBD.getInstance().getConnection();
    ProfesorDAO profesorDAO = new ProfesorDAO();     
        return profesorDAO.obtenerProfesorPorID(idProfesor);
        }
    
}

