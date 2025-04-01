package Fabrica_Objetos;

import DAO.*;

/**
 * Fábrica de DAOs para garantizar la reutilización de instancias y evitar ciclos infinitos.
 * @author VANESA
 */
public class FabricaDAO {
    private static CursoDAO cursoDAO;
    private static ProgramaDAO programaDAO;
    private static FacultadDAO facultadDAO;
    private static PersonaDAO personaDAO;
    private static InscripcionesPersonaDAO inscripcionesPersonaDAO;
    private static CursoProfesorDAO cursoProfesorDAO;
    private static CursoProfesoresDAO cursoProfesoresDAO;
    private static EstudianteDAO estudianteDAO;
    private static InscripcionDAO inscripcionDAO;
    private static CursosInscritosDAO cursosInscritosDAO;
    private static ProfesorDAO profesorDAO;

    public static PersonaDAO crearPersonaDAO() {
        if (personaDAO == null) {
            personaDAO = new PersonaDAO();
        }
        return personaDAO;
    }
    
    public static CursoDAO crearCursoDAO() {
        if (cursoDAO == null) {
            cursoDAO = new CursoDAO();
        }
        return cursoDAO;
    }

    public static ProgramaDAO crearProgramaDAO() {
        if (programaDAO == null) {
            programaDAO = new ProgramaDAO();
        }
        return programaDAO;
    }
    
    public static InscripcionesPersonaDAO crearInscripcionesPersonaDAO() {
        if (inscripcionesPersonaDAO == null) {
            inscripcionesPersonaDAO = new InscripcionesPersonaDAO();
        }
        return inscripcionesPersonaDAO;
    }

    public static CursoProfesorDAO crearCursoProfesorDAO() {
        if (cursoProfesorDAO == null) {
            cursoProfesorDAO = new CursoProfesorDAO();
        }
        return cursoProfesorDAO;
    }
    
    public static CursoProfesoresDAO crearCursoProfesoresDAO() {
        if (cursoProfesoresDAO == null) {
            cursoProfesoresDAO = new CursoProfesoresDAO();
        }
        return cursoProfesoresDAO;
    }
    
    public static EstudianteDAO crearEstudianteDAO() {
        if (estudianteDAO == null) {
            estudianteDAO = new EstudianteDAO();
        }
        return estudianteDAO;
    }

    public static FacultadDAO crearFacultadDAO() {
        if (facultadDAO == null) {
            facultadDAO = new FacultadDAO();
        }
        return facultadDAO;
    }

    public static InscripcionDAO crearInscripcionDAO() {
        if (inscripcionDAO == null) {
            inscripcionDAO = new InscripcionDAO();
        }
        return inscripcionDAO;
    }
    
    public static CursosInscritosDAO crearCursosInscritosDAO() {
        if (cursosInscritosDAO == null) {
            cursosInscritosDAO = new CursosInscritosDAO();
        }
        return cursosInscritosDAO;
    }

    public static ProfesorDAO crearProfesorDAO() {
        if (profesorDAO == null) {
            profesorDAO = new ProfesorDAO();
        }
        return profesorDAO;
    }
}