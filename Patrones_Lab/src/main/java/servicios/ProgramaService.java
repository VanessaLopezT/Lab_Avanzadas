package servicios;

import BaseDatos.AuxiliarBD;
import DAO.ProgramaDAO;
import modelo.Programa;
import modelo.Facultad;
import Factory.DAOFactory;
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

        Programa nuevoPrograma = DAOFactory.crearPrograma(idPrograma, nombre, duracion, registro, facultad);

        try (Connection conexion = AuxiliarBD.conectar()) {
            ProgramaDAO programaDAO = new ProgramaDAO(conexion);
            programaDAO.guardarProgramaBD(conexion, nuevoPrograma);
            AuxiliarBD.mostrarDatosBD_PROGRAMA();
        }
    }
}

