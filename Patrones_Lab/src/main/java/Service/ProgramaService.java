package Service;

import BaseDatos.AuxiliarBD;
import BaseDatos.DatabaseConnection;
import DAO.ProgramaDAO;
import Fabrica_Objetos.FabricaDAO;
import modelo.Programa;
import modelo.Facultad;
import Fabrica_Objetos.FabricaModelo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class ProgramaService {
    public void guardarPrograma(int idPrograma, String nombre, double duracion, Date registro, Facultad facultad) throws SQLException {
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del programa no puede estar vacío.");
        }

        if (facultad == null) {
            throw new IllegalArgumentException("No existe una facultad con ese ID en la base de datos.");
        }

        Programa nuevoPrograma = FabricaModelo.crearPrograma(idPrograma, nombre, duracion, registro, facultad);

        Connection conexion = DatabaseConnection.getInstance().getConnection();
            ProgramaDAO programaDAO = FabricaDAO.crearProgramaDAO();
            programaDAO.guardarProgramaBD(conexion, nuevoPrograma);
            AuxiliarBD.mostrarDatosBD_PROGRAMA();
        
    }
    
    public Programa obtenerProgramaPorID(int idPrograma) throws SQLException{ 
        Connection conexion = DatabaseConnection.getInstance().getConnection();
            ProgramaDAO programaDAO = FabricaDAO.crearProgramaDAO();
        return programaDAO.obtenerProgramaPorID(idPrograma);
        
    }     
}

