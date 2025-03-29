/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Factory;
import java.util.Date;
import modelo.Curso;
import modelo.CursoProfesor;
import modelo.Estudiante;
import modelo.Facultad;
import modelo.Inscripcion;
import modelo.Persona;
import modelo.Profesor;
import modelo.Programa;

/**
 *
 * @author VANESA
 */
public class FabricaObjetos {
    
     public static Curso crearCurso(Integer ID, String NombreCurso, Programa programa, boolean activo) {
        return new Curso(ID, NombreCurso, programa, activo);
    }
     
    public static CursoProfesor crearCursoProfesor(Profesor profesor, Integer anio, Integer semestre, Curso curso) {
        return new CursoProfesor(profesor, anio, semestre, curso);
    }
    
    public static Estudiante crearEstudiante(int codigo, Programa programa, boolean activo, double promedio, int ID, String nombres, String apellidos, String email) {
        return new Estudiante(codigo, programa, activo, promedio, ID,nombres, apellidos, email);
    }
    
    public static Facultad crearFacultad(int ID, String nombre, Persona decano) {
        return new Facultad(ID, nombre, decano);
    }
    
    public static Inscripcion crearInscripcion(Curso curso, Integer año, Integer semestre, Estudiante estudiante) {
        return new Inscripcion(curso, año, semestre, estudiante);
    }
    
    public static Persona crearPersona(int ID, String nombres, String apellidos, String email) {
        return new Persona(ID, nombres, apellidos, email);
    }
    
    public static Profesor crearProfesor(int ID, String nombres, String apellidos, String email, String TipoContrato) {
        return new Profesor(ID, nombres, apellidos, email, TipoContrato);
    }
    
    public static Programa crearPrograma(int ID, String nombre, double duracion, Date registro, Facultad facultad) {
        return new Programa(ID, nombre, duracion, registro, facultad);
    }
     
    
}
 
    