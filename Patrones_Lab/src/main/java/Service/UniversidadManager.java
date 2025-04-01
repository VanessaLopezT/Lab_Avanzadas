package Service;

import BaseDatos.AuxiliarBD;
import modelo.CursoProfesor;
import BaseDatos.DatabaseConnection;
import modelo.Inscripcion;
import modelo.Profesor;
import modelo.Programa;
import modelo.Persona;
import modelo.Facultad;
import modelo.Estudiante;
import modelo.Curso;
import DAO.CursoDAO;
import DAO.CursoProfesorDAO;
import DAO.CursoProfesoresDAO;
import DAO.CursosInscritosDAO;
import Fabrica_Objetos.FabricaModelo;
import DAO.EstudianteDAO;
import DAO.FacultadDAO;
import DAO.InscripcionDAO;
import DAO.InscripcionesPersonaDAO;
import DAO.PersonaDAO;
import DAO.ProfesorDAO;
import DAO.ProgramaDAO;
import Fabrica_Objetos.FabricaDAO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import modelo.CursoProfesores;
import modelo.CursosInscritos;
import modelo.InscripcionesPersona;

public class UniversidadManager {
    // Listas de almacenamiento en memoria
    private final List<Persona> personasGestor = new ArrayList<>();
    private final List<Profesor> profesoresGestor = new ArrayList<>();
    private final List<Persona> decanosGestor = new ArrayList<>();
    private final List<Estudiante> estudiantesGestor = new ArrayList<>();
    private final List<Facultad> facultadesGestor = new ArrayList<>();
    private final List<Programa> programasGestor = new ArrayList<>();
    private final List<Curso> cursosGestor = new ArrayList<>();
    private final List<CursoProfesor> cursoProfesorGestor = new ArrayList<>();
    private final List<Inscripcion> inscripcionesGestor = new ArrayList<>();

    public final CursosInscritos cursosInscritos = FabricaModelo.crearCursosInscritos();
    public final InscripcionesPersona inscripcionesPersona = FabricaModelo.crearInscripcionesPersona();
    public final CursoProfesores cursoProfesores = FabricaModelo.crearCursoProfesores();
    CursoProfesoresDAO cursoProfesoresDAO=FabricaDAO.crearCursoProfesoresDAO();
    CursosInscritosDAO cursosInscritosDAO=FabricaDAO.crearCursosInscritosDAO(); 
    InscripcionesPersonaDAO inscripcionesPersonaDAO=FabricaDAO.crearInscripcionesPersonaDAO();
    
    public void inicializarDatosTotales() {
        inicializarDatos_Personas_Decanos();
        inicializarDatos_Facultad_Programa();
        inicializarDatos_Profesores_Estudiantes();
        inicializarDatos_Cursos_CursosProfesores();
        inicializarDatos_Inscripciones();
        
    }

    public void guardar_en_BD() throws SQLException {
        Connection conexion = DatabaseConnection.getInstance().getConnection();

            System.out.println("\n Guardando datos por defecto en la base de datos...");
            
            
            PersonaDAO personaDAO=FabricaDAO.crearPersonaDAO();    
            for (Persona persona : personasGestor) personaDAO.guardarPersonaBD(conexion, persona);
            
            ProfesorDAO profesorDAO=FabricaDAO.crearProfesorDAO();
            for (Profesor profesor : profesoresGestor) profesorDAO.guardarProfesorBD(conexion, profesor);
            for (Persona decanos : decanosGestor) personaDAO.guardarPersonaBD(conexion, decanos);
            
            FacultadDAO facultadDAO = FabricaDAO.crearFacultadDAO();
            for (Facultad facultad : facultadesGestor) facultadDAO.guardarFacultadBD(conexion, facultad);

            ProgramaDAO programaDAO = FabricaDAO.crearProgramaDAO();
            for (Programa programa : programasGestor) programaDAO.guardarProgramaBD(conexion, programa);
            
            EstudianteDAO estudianteDAO = FabricaDAO.crearEstudianteDAO();
            for (Estudiante estudiante : estudiantesGestor) estudianteDAO.guardarEstudianteBD(conexion, estudiante);

            CursoDAO cursoDAO = FabricaDAO.crearCursoDAO();
            for (Curso curso : cursosGestor) {cursoDAO.guardarCursoBD(conexion, curso);}
            
            CursoProfesorDAO cursoProfesorDAO=FabricaDAO.crearCursoProfesorDAO();
            for (CursoProfesor cursoProfesor : cursoProfesorGestor) cursoProfesorDAO.guardarCursoProfesorBD(conexion, cursoProfesor);
            
            InscripcionDAO inscripcionDAO= FabricaDAO.crearInscripcionDAO();
            for (Inscripcion inscripcion : inscripcionesGestor) inscripcionDAO.guardarInscripcionBD(conexion,inscripcion);
            
            
            for (Persona persona : personasGestor) {inscripcionesPersona.inscribir(persona);  
            }

   }
    
    public void eliminarDuplicadosInscripcionPersonas() {
    String eliminarDuplicados = 
        "DELETE FROM INSCRIPCIONES_PERSONAS WHERE ID NOT IN (" +
        "    SELECT MIN(ID) FROM INSCRIPCIONES_PERSONAS " +
        "    GROUP BY PERSONA_ID, NOMBRES, APELLIDOS, EMAIL" +
        ");";
    Connection conexion = DatabaseConnection.getInstance().getConnection();
    try (
         Statement stmt = conexion.createStatement()) {
        
        stmt.executeUpdate(eliminarDuplicados);
        
    } catch (SQLException e) {
        System.err.println("❌ Error al eliminar duplicados en INSCRIPCIONES_PERSONAS: " + e.getMessage());
    }
}
    
    public void eliminarDuplicadosBD() {
        Connection conexion = DatabaseConnection.getInstance().getConnection();
    try (
         java.sql.Statement stmt = conexion.createStatement()) {

        // 🟢 Eliminar duplicados en INSCRIPCION (deja el de menor ID)
        String eliminarDuplicadosInscripcion =
            "DELETE FROM INSCRIPCION WHERE ID NOT IN (" +
            "    SELECT MIN(ID) FROM INSCRIPCION " +
            "    GROUP BY CURSO_ID, ESTUDIANTE_ID, ANIO, SEMESTRE" +
            ");";
        stmt.executeUpdate(eliminarDuplicadosInscripcion);
       
      
        // 🟢 Eliminar duplicados en CURSO_PROFESORES
        String eliminarDuplicadosCursoProfesores =
            "DELETE FROM CURSO_PROFESORES WHERE ID NOT IN (" +
            "    SELECT MIN(ID) FROM CURSO_PROFESORES " +
            "    GROUP BY PROFESOR_ID, CURSO_ID" +
            ");";
        stmt.executeUpdate(eliminarDuplicadosCursoProfesores);
      
        // 🟢 Eliminar duplicados en CURSOS_INSCRITOS
        String eliminarDuplicadosCursosInscritos =
            "DELETE FROM CURSOS_INSCRITOS WHERE ID NOT IN (" +
            "    SELECT MIN(ID) FROM CURSOS_INSCRITOS " +
            "    GROUP BY INSCRIPCION_ID, ESTUDIANTE_ID" +
            ");";
        stmt.executeUpdate(eliminarDuplicadosCursosInscritos);
      
    } catch (SQLException e) {
        System.err.println("❌ Error al eliminar duplicados: " + e.getMessage());
    }
}


    public void inicializarDatos_Personas_Decanos() {
        
        personasGestor.add(FabricaModelo.crearPersona(1123498175, "Vanessa", "Lopez", "yeimy@gmail.com"));
        personasGestor.add(FabricaModelo.crearPersona(1123401852, "Johan", "Fabio", "AndreFa@gmail.com"));
        personasGestor.add(FabricaModelo.crearPersona(1123401852, "Johan", "Hernandez", "johanhernandez@gmail.com"));

        decanosGestor.add(FabricaModelo.crearPersona(7392, "Juan", "Herrera", "JuanH@unillanos.edu.co"));
        decanosGestor.add(FabricaModelo.crearPersona(7123, "Roberta", "Perez", "RobePerez@unillanos.edu.co"));
        personasGestor.add(decanosGestor.get(0));
        personasGestor.add(decanosGestor.get(1));
    }

    public void inicializarDatos_Profesores_Estudiantes() {
        profesoresGestor.add(FabricaModelo.crearProfesor(5789, "Miguel", "Ramirez", "migue.Ramirez@unillanos.edu.co", "Catedratico"));
        profesoresGestor.add(FabricaModelo.crearProfesor(9176, "Angela", "Perez", "Ange.Perez@unillanos.edu.co", "Catedratico"));
        estudiantesGestor.add(FabricaModelo.crearEstudiante(160004713, programasGestor.get(0), true, 3.7, 1123498175, "Yeimy Vanessa", "Lopez Terreros", "yeimy.lopez@unillanos.edu.co"));
        estudiantesGestor.add(FabricaModelo.crearEstudiante(160004748, programasGestor.get(1), true, 3.1, 1123981625, "Maicol Sneider", "Guerrero Beltran", "Maicol.Guerrero@unillanos.edu.co"));
    }

    public void inicializarDatos_Facultad_Programa() {
        facultadesGestor.add(FabricaModelo.crearFacultad(1234, "Facultad de ciencias basicas e ingenieria", decanosGestor.get(0)));
        facultadesGestor.add(FabricaModelo.crearFacultad(5678, "Facultad de licienciatura", decanosGestor.get(1)));

        programasGestor.add(FabricaModelo.crearPrograma(603, "Ingenieria de sistemas", 10, new Date(116, 5, 3), facultadesGestor.get(0)));
        programasGestor.add(FabricaModelo.crearPrograma(601, "Licienciatura en matematicas", 10, new Date(123, 2, 5), facultadesGestor.get(1)));
    }

    public void inicializarDatos_Cursos_CursosProfesores() {
        cursosGestor.add(FabricaModelo.crearCurso(238, "Etica", programasGestor.get(0), true));
        cursosGestor.add(FabricaModelo.crearCurso(912, "Algoritmia", programasGestor.get(1), true));
        cursosGestor.add(FabricaModelo.crearCurso(872, "Ciencias", programasGestor.get(1), true));

        cursoProfesorGestor.add(FabricaModelo.crearCursoProfesor(profesoresGestor.get(0), 2025, 7, cursosGestor.get(0)));
        cursoProfesorGestor.add(FabricaModelo.crearCursoProfesor(profesoresGestor.get(1), 2025, 5, cursosGestor.get(1)));
    }

    public void inicializarDatos_Inscripciones() {
        inscripcionesGestor.add(FabricaModelo.crearInscripcion(cursosGestor.get(0), 2022, 3, estudiantesGestor.get(0)));
        inscripcionesGestor.add(FabricaModelo.crearInscripcion(cursosGestor.get(1), 2021, 4, estudiantesGestor.get(1)));
        inscripcionesGestor.add(FabricaModelo.crearInscripcion(cursosGestor.get(2), 2023, 5, estudiantesGestor.get(1)));
    }
    
 
  public void mostrarColumnasTabla(String nombreTabla) throws SQLException {
    Connection conexion = DatabaseConnection.getInstance().getConnection();
        DatabaseMetaData metaData = conexion.getMetaData();
        ResultSet columnas = metaData.getColumns(null, null, nombreTabla.toUpperCase(), null);

        System.out.println("Columnas de la tabla " + nombreTabla + ":");
        while (columnas.next()) {
            String nombreColumna = columnas.getString("COLUMN_NAME");
            String tipoDato = columnas.getString("TYPE_NAME");
            System.out.println("- " + nombreColumna + " (" + tipoDato + ")");
        }
        columnas.close();
   
}

public void OperacionesCursos_Inscritos(){
    
        System.out.println("\nBuscando inscripcion...");
        cursosInscritosDAO.buscarInscripcionEnCursosInscritos(238, 1123498175);
        
        System.out.println("\nANTES de agregar nueva inscripción:");
        AuxiliarBD.mostrarDatos_CursosInscritos();
         Inscripcion inscripcion = FabricaModelo.crearInscripcion(cursosGestor.get(0), 2024, 1, estudiantesGestor.get(0));
        System.out.println("\nDESPUÉS de agregar nueva inscripción:");
        cursosInscritosDAO.inscribir(inscripcion);
        AuxiliarBD.mostrarDatos_CursosInscritos();
        
        System.out.println("\nANTES de actualizar inscripción:");
        AuxiliarBD.mostrarDatos_CursosInscritos();

        inscripcion.setAño(1998);
        inscripcion.setSemestre(9);
        cursosInscritosDAO.Actualizar(238, 1123498175, 872); 
        
        System.out.println("\nDESPUÉS de actualizar inscripción:");
        AuxiliarBD.mostrarDatos_CursosInscritos();
    
        System.out.println("\nANTES de eliminar inscripción:");
        AuxiliarBD.mostrarDatos_CursosInscritos();
        cursosInscritosDAO.Eliminar(238, 1123498175);
        System.out.println("\nDESPUÉS de eliminar inscripción:");
        AuxiliarBD.mostrarDatos_CursosInscritos();
    

    // Guardar datos en archivo binario
    System.out.println("\nGuardando inscripciones en archivo binario...");
    cursosInscritos.guardainformacion();

    //  Cargar datos desde archivo binario
    System.out.println("\n Cargando inscripciones desde archivo binario...");
    cursosInscritos.cargarDatos();
 

}


public void OperacionesCursos_Profesores(){
    
        System.out.println("\nBuscando CursoProfesor...");
        cursoProfesoresDAO.buscarCursoProfesor(profesoresGestor.get(0).getID(),cursosGestor.get(0).getID());
        
        System.out.println("\n ANTES de agregar nueva inscripción:");
        AuxiliarBD.mostrarDatos_cursoProfesores();
        CursoProfesor cursoprofesor=FabricaModelo.crearCursoProfesor(profesoresGestor.get(0),2005,6,cursosGestor.get(1));
        cursoProfesoresDAO.inscribir(cursoprofesor);
       System.out.println("\n DESPUÉS de agregar nueva inscripción:");
       AuxiliarBD.mostrarDatos_cursoProfesores();
    
        System.out.println("\n ANTES de actualizar inscripción:");
        AuxiliarBD.mostrarDatos_cursoProfesores();
        cursoProfesoresDAO.actualizar(5789, 238, 2009, 4);
        System.out.println("\n DESPUÉS de actualizar Profesor-Curso:");
        AuxiliarBD.mostrarDatos_cursoProfesores();

        System.out.println("\n ANTES de eliminar inscripción:");
        AuxiliarBD.mostrarDatos_cursoProfesores();
  
        System.out.println("\n DESPUÉS de eliminar inscripción:");
        cursoProfesoresDAO.eliminar(5789, 238, 2009, 4);
        AuxiliarBD.mostrarDatos_cursoProfesores();
    //  Guardar datos en archivo binario
    System.out.println("\n Guardando inscripciones en archivo binario...");
    cursoProfesores.guardainformacion();

    //  Cargar datos desde archivo binario
    System.out.println("\n Cargando inscripciones desde archivo binario...");
    cursoProfesores.cargarDatos();

   
 

}


public void OperacionesInscripciones_Personas() throws SQLException {
    mostrarColumnasTabla("inscripciones_personas");

    System.out.println("\n Buscando inscripciones_personas");
    inscripcionesPersona.buscarPersonaInscrita(1123498175);
      System.out.println("\n Guardando inscripciones en archivo binario...");
     inscripcionesPersonaDAO.guardarEnArchivo();

    System.out.println("\n Cargando inscripciones desde archivo binario...");
    inscripcionesPersonaDAO.cargarDesdeArchivo();
    System.out.println("\n ANTES de agregar nueva inscripción:");
    AuxiliarBD.mostrarDatos_inscripcionesPersonas();

    // Persona que queremos inscribir
    int personaId = 873123233;
    Persona nuevapersona = FabricaModelo.crearPersona(personaId, "Helena", "Suarez", "HeleSua@gmail.com");

    // 🔍 Verificar si la persona ya está en la tabla PERSONA antes de inscribirla
    if (!personaExisteEnBD(personaId)) {
        System.out.println("\n Persona NO encontrada en la tabla PERSONA. Se añadirá primero.");
        agregarPersonaABaseDeDatos(nuevapersona);
    } else {
        System.out.println("\n Persona ya existe en la tabla PERSONA.");
    }

    // 📌 Ahora sí, inscribir a la persona
    inscripcionesPersona.inscribir(nuevapersona);

    System.out.println("\n DESPUÉS de agregar nueva inscripción:");
    AuxiliarBD.mostrarDatos_inscripcionesPersonas();

    // 🔄 Actualizar inscripción
    nuevapersona.setNombres("Alejandra");
    System.out.println("\nANTES de actualizar inscripción:");
    AuxiliarBD.mostrarDatos_inscripcionesPersonas();

    inscripcionesPersona.actualizarPersona(personaId, "Jessica", "Lorena", "JesiLoore@gmail.com");

    System.out.println("\nDESPUÉS de actualizar inscripción:");
    AuxiliarBD.mostrarDatos_inscripcionesPersonas();
  System.out.println("\nGuardando inscripciones en archivo binario...");
    inscripcionesPersonaDAO.guardarEnArchivo();

    System.out.println("\n Cargando inscripciones desde archivo binario...");
    inscripcionesPersonaDAO.cargarDesdeArchivo();
    // ❌ Eliminar inscripción
    System.out.println("\nANTES de eliminar inscripción:");
    AuxiliarBD.mostrarDatos_inscripcionesPersonas();

    inscripcionesPersona.eliminarPersona(personaId);

    System.out.println("\nDESPUÉS de eliminar inscripción:");
    AuxiliarBD.mostrarDatos_inscripcionesPersonas();

    // 💾 Guardar y cargar datos binarios
    System.out.println("\nGuardando inscripciones en archivo binario...");
    inscripcionesPersonaDAO.guardarEnArchivo();

    System.out.println("\nCargando inscripciones desde archivo binario...");
    inscripcionesPersonaDAO.cargarDesdeArchivo();
}


// ✅ Método para verificar si la persona ya existe en la tabla PERSONA
public boolean personaExisteEnBD(int personaId) {
    String query = "SELECT COUNT(*) FROM PERSONA WHERE ID = ?";
    Connection conexion = DatabaseConnection.getInstance().getConnection();
    try (
         PreparedStatement stmt = conexion.prepareStatement(query)) {
        
        stmt.setInt(1, personaId);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next() && rs.getInt(1) > 0) {
            return true; // ✅ La persona sí existe
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // ❌ No existe
}

// 🆕 Método para agregar una persona a la tabla PERSONA
public void agregarPersonaABaseDeDatos(Persona persona) {
    String query = "INSERT INTO PERSONA (ID, NOMBRES, APELLIDOS, EMAIL) VALUES (?, ?, ?, ?)";
    Connection conexion = DatabaseConnection.getInstance().getConnection();
    try (
         PreparedStatement stmt = conexion.prepareStatement(query)) {
        
        stmt.setInt(1, persona.getID());
        stmt.setString(2, persona.getNombres());
        stmt.setString(3, persona.getApellidos());
        stmt.setString(4, persona.getEmail());
        
        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("Persona añadida a la base de datos correctamente.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void MostrarOperacionesLista_MostrarBinarios(){ 
    System.out.println("\nGuardando inscripcionespersonas en archivo binario...");
    inscripcionesPersonaDAO.guardarEnArchivo();

    System.out.println("\nCargando inscripcionespersonas desde archivo binario...");
    inscripcionesPersonaDAO.cargarDesdeArchivo();
    
}

  public void mostrarOperaciones() throws SQLException {
     eliminarDuplicadosBD();
     eliminarDuplicadosInscripcionPersonas();
     OperacionesCursos_Profesores();
     OperacionesInscripciones_Personas();
     OperacionesCursos_Profesores();
       
}
     
}
