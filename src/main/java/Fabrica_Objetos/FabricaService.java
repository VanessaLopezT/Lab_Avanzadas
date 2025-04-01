/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica_Objetos;

import Service.CursoProfesorService;
import Service.CursoProfesoresService;
import Service.CursoService;
import Service.CursosInscritosService;
import Service.EstudianteService;
import Service.FacultadService;
import Service.InscripcionService;
import Service.PersonaService;
import Service.ProfesorService;
import Service.ProgramaService;


/**
 *
 * @author VANESA
 */
public class FabricaService {

    public static PersonaService crearPersonaService() {
        return new PersonaService();
    }
    
    public static CursoService crearCursoService() {
        return new CursoService();
    }
    
    public static CursosInscritosService crearCursosInscritosService() {
        return new CursosInscritosService();
    }
    
    public static CursoProfesorService crearCursoProfesorService() {
        return new CursoProfesorService();
    }

    public static EstudianteService crearEstudianteService() {
        return new EstudianteService();
    }

    public static FacultadService crearFacultadService() {
        return new FacultadService();
    }

    public static InscripcionService crearInscripcionService() {
        return new InscripcionService();
    }

    public static ProfesorService crearProfesorService() {
        return new ProfesorService();
    }
    
    public static CursoProfesoresService crearCursoProfesoresService() {
        return new CursoProfesoresService();
    }

    public static ProgramaService crearProgramaService() {
        return new ProgramaService();
    }

}

