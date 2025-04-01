package Service;

import BaseDatos.AuxiliarBD;
import BaseDatos.DatabaseConnection;
import DAO.CursoDAO;
import Fabrica_Objetos.FabricaDAO;
import modelo.Curso;
import modelo.Programa;
import Fabrica_Objetos.FabricaModelo;
import ObserverPattern.Observable;
import ObserverPattern.Observador;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

   public class CursoService implements Observable{
    private CursoDAO cursoDAO;
    private List<Curso> cursos;
    private List<Observador> observadores;  // Lista de observadores para notificar cambios

    public CursoService() {
        // Crear CursoDAO usando la fábrica
        this.cursoDAO = FabricaDAO.crearCursoDAO();
        this.cursos = cursoDAO.obtenerTodosLosCursos();  // Cargar los cursos inicialmente
        this.observadores = new ArrayList<>();  // Inicializar lista de observadores
    }
    
    private boolean cargandoCursos = false;

public void cargarCursosss() {
    if (cargandoCursos) return; // Evita llamadas repetidas
    cargandoCursos = true;

    System.out.println("Ejecutando cargarCursos()...");
    List<Curso> cursos = obtenerCursos();
    actualizarCursos(cursos);  

    cargandoCursos = false;
}

    // Método para agregar un observador
    @Override
    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    // Método para eliminar un observador
    @Override
    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);
    }

    // Método para notificar a los observadores sobre cambios en los cursos
    @Override
    public void notificarObservadores(List<Curso> cursos) {
        for (Observador observador : observadores) {
            observador.actualizar(cursos);  // Llamar al método actualizar de cada observador
        }
    }

    // Método para actualizar los cursos y notificar a los observadores
    public void actualizarCursos(List<Curso> cursos) {
        for (Observador observador : observadores) {
            observador.actualizar(cursos);
        }
    }

    // Método para mostrar los cursos en consola (o puede ser en otra parte de la UI)
    public void mostrarCursos() {
        for (Curso curso : cursos) {
            System.out.println(curso.getNombreCurso());
        }
    }

    public List<Curso> obtenerCursos() {
    System.out.println("Cursos cargados: " + cursos.size());  // Verificar la cantidad de cursos obtenidos
    return cursos;
}

    // Método para guardar un curso en la base de datos
    public void guardarCursoss(int idCurso, String nombre, Programa programa, boolean activo) throws SQLException {
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso no puede estar vacío.");
        }

        if (programa == null) {
            throw new IllegalArgumentException("No existe un programa con ese ID en la base de datos.");
        }

        Curso nuevoCurso = FabricaModelo.crearCurso(idCurso, nombre, programa, activo);
        
        // Guardar el curso en la base de datos
        Connection conexion = DatabaseConnection.getInstance().getConnection();
        cursoDAO.guardarCursoBD(conexion, nuevoCurso);
        // Obtener la lista de cursos actualizada
        List<Curso> cursosActualizados = cursoDAO.obtenerTodosLosCursos(); // Método que obtiene todos los cursos
        // Notificar a los observadores (en este caso a VentanaCurso)
        notificarObservadores(cursosActualizados);}

    // Obtener un curso por su ID
    public Curso obtenerCursoPorID(int idCurso) {
        return cursoDAO.obtenerCursoPorID(idCurso);
    }
    
    public List<Curso> obtenerTodosLosCursos() {
    // Este método debe devolver todos los cursos desde la base de datos
    return cursoDAO.obtenerTodosLosCursos();  
}

}


    
    

