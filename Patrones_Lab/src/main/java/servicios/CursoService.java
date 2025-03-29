package servicios;

import BaseDatos.AuxiliarBD;
import DAO.CursoDAO;
import modelo.Curso;
import modelo.Programa;
import Factory.FabricaObjetos;
import java.sql.Connection;
import java.sql.SQLException;

public class CursoService {
    public void guardarCurso(int idCurso, String nombre, Programa programa, boolean activo) throws SQLException {
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso no puede estar vacío.");
        }

        if (programa == null) {
            throw new IllegalArgumentException("No existe un programa con ese ID en la base de datos.");
        }

        Curso nuevoCurso = FabricaObjetos.crearCurso(idCurso, nombre, programa, activo);

        Connection conexion = AuxiliarBD.getInstance().getConnection();
            CursoDAO cursoDAO = new CursoDAO();
            cursoDAO.guardarCursoBD(conexion, nuevoCurso);
            AuxiliarBD.mostrarDatosBD_CURSO();
        
    }
    
    
    public Curso obtenerCursoPorID(int idCurso){
    Connection conexion = AuxiliarBD.getInstance().getConnection();
            CursoDAO cursoDAO = new CursoDAO();
    return cursoDAO.obtenerCursoPorID(idCurso);
    }
}

    
    

