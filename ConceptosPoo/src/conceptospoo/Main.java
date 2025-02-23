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
         
     Persona persona1= new Persona(1123498175,"Vanessa","Lopez","yeimy@gmail.com"); 
     Persona persona2=new Persona(1123401852,"yohaN","MartineZ","Yohanhernandez@gmail.com");
     Persona persona3=new Persona(1123401852,"Johan","Hernandez","johanhernandez@gmail.com");
     
     Profesor profesor1= new Profesor(5789,"Miguel","Ramirez","migue.Ramirez@unillanos.edu.co","Catedratico");
     Profesor profesor2= new Profesor(9176,"Angela","Perez","Ange.Perez@unillanos.edu.co","Catedratico");
     
     Persona decano1=new Persona(7392,"Juan","Herrera","JuanH@unillanos.edu.co");
     Persona decano2=new Persona(7123,"Roberta","Perez","RobePerez@unillanos.edu.co");
     
     Facultad facultad1=new Facultad(1234,"Facultad de ciencias basicas e ingenieria",decano1);
     Facultad facultad2=new Facultad(5678,"Facultad de licienciatura",decano2);
     
     Date date1=new Date(116,5,3);
     Date date2=new Date(123,2,5);
     Programa programa1=new Programa(603,"Ingenieria de sistemas",10,date1,facultad1);
     Programa programa2=new Programa(601,"Licienciatura en matematicas",10,date2,facultad2);
     
     Curso curso1=new Curso(238,"Etica",programa1,true);
     Curso curso2=new Curso(912,"Algoritmia",programa2,true);
     Curso curso3=new Curso(872,"Algoritmia",programa2,true);
     
     CursoProfesor cursoprofesor1=new CursoProfesor(profesor1,2025,7,curso1);
     CursoProfesor cursoprofesor2=new CursoProfesor(profesor2,2025,5,curso2);
     
     Estudiante estudiante1=new Estudiante(160004713,programa1,true,3.7,1123498175,"Yeimy Vanessa","Lopez Terreros","yeimy.lopez@unillanos.edu.co");
     Estudiante estudiante2=new Estudiante(160004748,programa2,true,3.1,1123981625,"MhaiKol Sneider","Gutierrez Beltran","Maicol.Gutierrez@unillanos.edu.co");
     Estudiante estudiante3=new Estudiante(160004748,programa2,true,3.1,1123981625,"Maicol Sneider","Guerrero Beltran","Maicol.Guerrero@unillanos.edu.co");
 
     Inscripcion inscripcion1=new Inscripcion(curso1,2022,3,estudiante1);
     Inscripcion inscripcion2=new Inscripcion(curso2,2023,5,estudiante2);
     Inscripcion inscripcion3=new Inscripcion(curso3,2023,5,estudiante3);
     
        

     
     
     System.out.print("\nGUARDAR Y CARGAR DE INSCRIPCIONES PERSONA \n");
     InscripcionesPersona inscripcionespersona= new InscripcionesPersona();
     inscripcionespersona.guardainformacion(persona1);
     inscripcionespersona.cargarDatos();
     inscripcionespersona.guardainformacion(persona2);
     inscripcionespersona.cargarDatos();
     
     
     
     
     System.out.print("\n\n\nGUARDAR Y CARGAR DE CURSOS PROFESORES \n");
     
     CursoProfesores cursoprofesores=new CursoProfesores();
     cursoprofesores.inscribir(cursoprofesor1);
     cursoprofesores.inscribir(cursoprofesor2);
     cursoprofesores.guardainformacion(cursoprofesor1);
     cursoprofesores.cargarDatos();
     cursoprofesores.guardainformacion(cursoprofesor2);
     cursoprofesores.cargarDatos();
     
     
     
     
     System.out.print("\n\n\nGUARDAR Y CARGAR DE CURSOS INSCRITOS \n");
     CursosInscritos cursosinscritos=new CursosInscritos();
     cursosinscritos.inscribirCurso(inscripcion1);
     cursosinscritos.inscribirCurso(inscripcion2);
     cursosinscritos.guardainformacion(inscripcion1);
     cursosinscritos.cargarDatos();
     cursosinscritos.guardainformacion(inscripcion2);
     cursosinscritos.cargarDatos();
     
     
     
     
     System.out.print("\n\n\nINSCRIBIR, ELIMINAR, ACTUALIZAR \n");
     
     inscripcionespersona.inscribir(persona1);
     inscripcionespersona.inscribir(persona2);
     System.out.print("\nLista de inscritos en InscripcionesPersona: \n");
     System.out.println(String.join("", inscripcionespersona.ImprimirListado()));

     inscripcionespersona.actualizar(persona3);
     System.out.print("Lista actualizando inscritos en InscripcionesPersona: \n");
      System.out.println(String.join("", inscripcionespersona.ImprimirListado()));
     
     inscripcionespersona.eliminar(persona1);
     System.out.print("Lista elimando inscritos en InscripcionesPersona: \n");
     System.out.println(String.join("", inscripcionespersona.ImprimirListado()));

     
  

     
     
     System.out.print("\n\n\nMETODOS DE SERVICIOS EN CURSOS_INSCRITOS Y  CURSOS_PROFESORES\n");
     
     System.out.print("\nListado de cursos inscritos: ");
     System.out.println(String.join("", cursosinscritos.ImprimirListado()));
     System.out.print("\nImprimir posicion cero de cursos inscritos: \n"+cursosinscritos.ImprimirPosicion(0));
     System.out.print("\n\nImprimir cantidad actual de cursos inscritos: "+cursosinscritos.cantidadActual());
     System.out.print("\n\nListado de curso profesores: ");
     System.out.println(String.join("", cursoprofesores.ImprimirListado()));
     System.out.print("\nImprimir posicion cero de curso profesores: \n"+cursoprofesores.ImprimirPosicion(0));
     System.out.print("\n\nImprimir cantidad actual de curso profesores: "+cursoprofesores.cantidadActual());
     
    }
}
