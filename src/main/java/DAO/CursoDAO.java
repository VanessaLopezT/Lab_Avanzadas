/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Fabrica_Objetos.FabricaModelo;
import BaseDatos.DatabaseConnection;
import Fabrica_Objetos.FabricaDAO;
import ObserverPattern.Observable;
import ObserverPattern.Observador;
import modelo.Curso;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Facultad;
import modelo.Persona;
import modelo.Programa;



public class CursoDAO implements Observable{
    private List<Observador> observadores;  // Lista de observadores
    private List<Curso> cursos = new ArrayList<>();
    
    
    private Connection conexion;

    public CursoDAO() {
        this.conexion = DatabaseConnection.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
        this.observadores = new ArrayList<>();
        this.cursos = new ArrayList<>();
        
         // Simula algunos datos si la base de datos está vacía
        if (cursos == null || cursos.isEmpty()) {
            // Crear un curso simulado
            Persona decano = new Persona(7392, "Juan", "Herrera", "JuanH@unillanos.edu.co");
            Facultad facultad = new Facultad(1234, "Facultad de ciencias basicas e ingenieria", decano);
            Date fechaRegistro = new Date(123, 2, 5);  // Fecha de registro (simulada)
            Programa programa = new Programa(601, "Licenciatura en matemáticas", 10, fechaRegistro, facultad);
            Curso curso = new Curso(1, "Curso de Java", programa, true);
            
            cursos.add(curso);  // Agregar el curso simulado a la lista
            System.out.println("Curso creado: " + curso.getNombreCurso());
       }
    }
    
    // Implementación de agregarObservador()
    @Override
    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    // Implementación de eliminarObservador()
    @Override
    public void eliminarObservador(Observador o) {
        observadores.remove(o);
    }

    @Override
public void notificarObservadores(List<Curso> cursos) {
    System.out.println("Notificando a los observadores...");
    for (Observador observador : observadores) {
        observador.actualizar(cursos);
    }
}
    
    public void guardarCursoBD(Connection conexion, Curso curso) {
        if (conexion == null) {
            System.out.println("❌ Error: La conexión a la base de datos es nula.");
            return;
        }

        String sql = "MERGE INTO curso AS c " +
                     "USING (VALUES (?, ?, ?, ?)) AS vals (id, nombre, programa_id, activo) " +
                     "ON c.id = vals.id " +
                     "WHEN MATCHED THEN UPDATE SET c.nombre = vals.nombre, c.programa_id = vals.programa_id, c.activo = vals.activo " +
                     "WHEN NOT MATCHED THEN INSERT (id, nombre, programa_id, activo) VALUES (vals.id, vals.nombre, vals.programa_id, vals.activo);";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, curso.getID());
            pstmt.setString(2, curso.getNombreCurso());
            pstmt.setInt(3, curso.getPrograma().getID());
            pstmt.setBoolean(4, curso.isActivo());
            pstmt.executeUpdate();
            
             cursos.add(curso);  // Agregar el curso a la lista
             notificarObservadores(cursos);  // Notificar a los observadores que un curso ha sido agregado
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Curso> obtenerTodosLosCursos() {
    cursos.clear();  // Limpiar la lista antes de llenarla con los datos de la BD
    String sql = "SELECT * FROM curso";
    Connection conexion = DatabaseConnection.getInstance().getConnection();
    if (conexion == null) {
    System.out.println("❌ Error: La conexión a la base de datos es nula.");
    return new ArrayList<>(); // Regresa una lista vacía si no hay conexión
}
    try (
            
         PreparedStatement pstmt = conexion.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        System.out.println("Ejecutando consulta para obtener cursos...");
        if (!rs.next()) {
        System.out.println("No se encontraron cursos en la base de datos.");
        return new ArrayList<>();
        }

        while (rs.next()) {
            int idCurso = rs.getInt("id");
            String nombreCurso = rs.getString("nombre");
            int programaId = rs.getInt("programa_id");
            boolean activo = rs.getBoolean("activo");

            System.out.println("Curso encontrado: ID = " + idCurso + ", Nombre = " + nombreCurso);

            // Obtener programa
            var programa = FabricaDAO.crearProgramaDAO().obtenerProgramaPorID(programaId);
            
            Curso curso = FabricaModelo.crearCurso(idCurso, nombreCurso, programa, activo);
            cursos.add(curso);
        }
        
    } catch (SQLException e) {
        System.err.println("Error al obtener los cursos: " + e.getMessage());
    }

    // Mostrar la cantidad de cursos obtenidos
    System.out.println("Total de cursos obtenidos: " + cursos.size());

    return new ArrayList<>(cursos);  // Devolver una copia de la lista para evitar modificaciones externas
}


    public Curso obtenerCursoPorID(int idCurso) {
            Connection conexion = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM curso WHERE id = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, idCurso);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return FabricaModelo.crearCurso(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            FabricaDAO.crearProgramaDAO().obtenerProgramaPorID(rs.getInt("programa_id")),
                            rs.getBoolean("activo")
                        );
                    }
                }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar el curso: " + e.getMessage());
        }
        return null;
    }
    
    

}

