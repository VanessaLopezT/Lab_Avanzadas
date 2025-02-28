package conceptospoo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author VANESA
 */
public class GestorUniversidad {
    //Listas que contendrán TODOS los objetos creados. SOLO PARA PASAR LA INFORMACION.NO modificables 
    private final List<Persona> personasGestor = new ArrayList<>();
    private final List<Profesor> profesoresGestor = new ArrayList<>();
    private final List<Persona> decanosGestor = new ArrayList<>();
    private final List<Estudiante> estudiantesGestor = new ArrayList<>();
    private final List<Facultad> facultadesGestor = new ArrayList<>();
    private final List<Programa> programasGestor = new ArrayList<>();
    private final List<Curso> cursosGestor = new ArrayList<>();
    private final List<CursoProfesor> cursoProfesorGestor = new ArrayList<>();
    private final List<Inscripcion> inscripcionesGestor = new ArrayList<>();
 
    
    private List<CursosInscritos> cursosInscritosGestor = new ArrayList<>();
    
    public void inicializarDatosTotales(){
     
     inicializarDatos_Personas_Decanos();
     inicializarDatos_Facultad_Programa();
     inicializarDatos_Profesores_Estudiantes();
     inicializarDatos_Cursos_CursosProfesores();
     inicializarDatos_Inscripciones();
   
    }
    
    public void inicializarDatos_Personas_Decanos(){
     personasGestor.add(new Persona(1123498175,"Vanessa","Lopez","yeimy@gmail.com"));
     personasGestor.add(new Persona(1123401852,"Johan","Fabio","AndreFa@gmail.com"));
     personasGestor.add(new Persona(1123401852,"Johan","Hernandez","johanhernandez@gmail.com"));
     
     decanosGestor.add(new Persona(7392,"Juan","Herrera","JuanH@unillanos.edu.co"));
     decanosGestor.add(new Persona(7123,"Roberta","Perez","RobePerez@unillanos.edu.co"));
     
    }
    
    public void inicializarDatos_Profesores_Estudiantes(){
     profesoresGestor.add(new Profesor(5789,"Miguel","Ramirez","migue.Ramirez@unillanos.edu.co","Catedratico"));
     profesoresGestor.add(new Profesor(9176,"Angela","Perez","Ange.Perez@unillanos.edu.co","Catedratico"));
     
     estudiantesGestor.add(new Estudiante(160004713,programasGestor.get(0),true,3.7,1123498175,"Yeimy Vanessa","Lopez Terreros","yeimy.lopez@unillanos.edu.co"));
     estudiantesGestor.add(new Estudiante(160004748,programasGestor.get(1),true,3.1,1123981625,"MhaiKol Sneider","Gutierrez Beltran","Maicol.Gutierrez@unillanos.edu.co"));
     estudiantesGestor.add(new Estudiante(160004748,programasGestor.get(1),true,3.1,1123981625,"Maicol Sneider","Guerrero Beltran","Maicol.Guerrero@unillanos.edu.co"));
         
    }
    
    public void inicializarDatos_Facultad_Programa(){
     facultadesGestor.add(new Facultad(1234,"Facultad de ciencias basicas e ingenieria",decanosGestor.get(0)));
     facultadesGestor.add(new Facultad(5678,"Facultad de licienciatura",decanosGestor.get(1)));
     
     Date date1=new Date(116,5,3);
     Date date2=new Date(123,2,5);
     programasGestor.add(new Programa(603,"Ingenieria de sistemas",10,date1,facultadesGestor.get(0)));
     programasGestor.add(new Programa(601,"Licienciatura en matematicas",10,date2,facultadesGestor.get(1)));
     
    }
    
    public void inicializarDatos_Cursos_CursosProfesores(){
     cursosGestor.add(new Curso(238,"Etica",programasGestor.get(0),true));  
     cursosGestor.add(new Curso(912,"Algoritmia",programasGestor.get(1),true));
     cursosGestor.add(new Curso(872,"Ciencias",programasGestor.get(1),true));
     
     cursoProfesorGestor.add(new CursoProfesor(profesoresGestor.get(0),2025,7,cursosGestor.get(0)));
     cursoProfesorGestor.add(new CursoProfesor(profesoresGestor.get(1),2025,5,cursosGestor.get(1)));
     
    }
    
     public void inicializarDatos_Inscripciones(){
     inscripcionesGestor.add(new Inscripcion(cursosGestor.get(0),2022,3,estudiantesGestor.get(0)));
     inscripcionesGestor.add(new Inscripcion(cursosGestor.get(1),2021,4,estudiantesGestor.get(1)));
     inscripcionesGestor.add(new Inscripcion(cursosGestor.get(2),2023,5,estudiantesGestor.get(2)));
     
    }
    
     
    public void mostrarOperaciones(){
    //Listas MODIFICABLES que seran convertidas a binario
     InscripcionesPersona inscripcionespersona= new InscripcionesPersona();
     CursoProfesores cursoprofesores=new CursoProfesores();
     CursosInscritos cursosinscritos=new CursosInscritos();
     
     System.out.print("\nGUARDAR Y CARGAR DE INSCRIPCIONES PERSONA, CURSOS PROFESORES Y CURSOS INSCRITOS \n");
     inscripcionespersona.inscribir(personasGestor.get(0));
     inscripcionespersona.inscribir(personasGestor.get(1));
     inscripcionespersona.inscribir(personasGestor.get(2));
     inscripcionespersona.guardainformacion();
     inscripcionespersona.cargarDatos();

     cursoprofesores.inscribir(cursoProfesorGestor.get(0));
     cursoprofesores.inscribir(cursoProfesorGestor.get(1));
     cursoprofesores.guardainformacion();
     cursoprofesores.cargarDatos();
      
     cursosinscritos.inscribirCurso(inscripcionesGestor.get(0));
     cursosinscritos.inscribirCurso(inscripcionesGestor.get(1));
     cursosinscritos.inscribirCurso(inscripcionesGestor.get(2));
     cursosinscritos.guardainformacion();
     cursosinscritos.cargarDatos();
 
     
     
     
     
     System.out.print("\n\n\nINSCRIBIR, ELIMINAR, ACTUALIZAR \n");
     
     System.out.print("\nLista de inscritos en InscripcionesPersona: \n");
     System.out.println(String.join("", inscripcionespersona.ImprimirListado()));

     inscripcionespersona.actualizar(personasGestor.get(2));
     System.out.print("Lista actualizando inscritos en InscripcionesPersona: \n");
      System.out.println(String.join("", inscripcionespersona.ImprimirListado()));
     
     inscripcionespersona.eliminar(personasGestor.get(2));
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
