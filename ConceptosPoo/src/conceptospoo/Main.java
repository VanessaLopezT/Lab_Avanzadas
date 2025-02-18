/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conceptospoo;
import java.util.Date;
       
/**
 *
 * @author Estudiante_MCA
 */
public class Main {
    public static void main(String[] args){
     Persona persona1= new Persona(4713,"Vanessa","Lopez","yeimy.lopez@unillanos.edu.co"); 
     Persona persona2=new Persona(4744,"Johan","Hernandez","johan.hernandez.basallo@unillanos.edu.co");
     Profesor profesor1= new Profesor(5789,"Miguel","Ramirez","migue.Ramirez@unillanos.edu.co","Catedratico");
     Persona decano1=new Persona(7392,"Juan","Herrera","JuanH@unillanos.edu.co");
     Facultad facultad1=new Facultad(1234,"Facultad de ciencias basicas e ingenieria",decano1);
     Date date=new Date(116,5,3);
     Programa programa1=new Programa(603,"Ingenieria de sistemas",10,date,facultad1);
     boolean active=true;
     Curso curso1=new Curso(238,programa1,active);
     CursoProfesor cursoprofesor1=new CursoProfesor(profesor1,2025,7,curso1);
     
     
     
     InscripcionesPersona inscripciones= new InscripcionesPersona();
     inscripciones.inscribir(persona1);
     inscripciones.inscribir(persona2);
     
     inscripciones.guardainformacion(persona1);
     inscripciones.guardainformacion(persona2);
     
     inscripciones.cargarDatos();
     System.out.print("\n");
     CursoProfesores cursoprofesores=new CursoProfesores();
     cursoprofesores.inscribir(cursoprofesor1);
     cursoprofesores.cargarDatos();
     
     
    }
}
