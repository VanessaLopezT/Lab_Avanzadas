/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Factory.FabricaObjetos;
import modelo.CursoProfesor;
import modelo.Profesor;
import modelo.Curso;
import BaseDatos.AuxiliarBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Facultad;
import modelo.Programa;
/**
 *
 * @author VANESA
 */
public class CursoProfesoresDAO {
    
    
    
    public CursoProfesor buscarCursoProfesor(int profesorID, int cursoID) {
        String sql = "SELECT * FROM curso_profesores WHERE profesor_id = ? AND curso_id = ?;";
        Connection conexion = AuxiliarBD.getInstance().getConnection();
        try (
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, profesorID);
            pstmt.setInt(2, cursoID);
      
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return FabricaObjetos.crearCursoProfesor(
                    obtenerProfesorPorID(profesorID, conexion),
                    rs.getInt("anio"),
                    rs.getInt("semestre"),
                    obtenerCursoPorID(cursoID, conexion)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void inscribir(CursoProfesor cursoProfesor) {
    String sqlBuscar = "SELECT * FROM curso_profesores WHERE profesor_id = ? AND curso_id = ? AND anio = ? AND semestre = ?";
    String sqlInsertar = "INSERT INTO curso_profesores (profesor_id, curso_id, anio, semestre) VALUES (?, ?, ?, ?)";
    Connection conexion = AuxiliarBD.getInstance().getConnection();
    try (
         PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement pstmtInsertar = conexion.prepareStatement(sqlInsertar)) {

        pstmtBuscar.setInt(1, cursoProfesor.getProfesor().getID());
        pstmtBuscar.setInt(2, cursoProfesor.getCurso().getID());
        pstmtBuscar.setInt(3, cursoProfesor.getAño());
        pstmtBuscar.setInt(4, cursoProfesor.getSemestre());

        ResultSet rs = pstmtBuscar.executeQuery();
        if (rs.next()) {
            System.out.println("⚠️ La asignación ya existe.");
            return;
        }

        pstmtInsertar.setInt(1, cursoProfesor.getProfesor().getID());
        pstmtInsertar.setInt(2, cursoProfesor.getCurso().getID());
        pstmtInsertar.setInt(3, cursoProfesor.getAño());
        pstmtInsertar.setInt(4, cursoProfesor.getSemestre());

        int filasAfectadas = pstmtInsertar.executeUpdate();
        if (filasAfectadas > 0) {
            AuxiliarBD.mostrarDatosBD_CURSO_PROFESORES();
            System.out.println("✅ Profesor asignado al curso correctamente.");
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al inscribir al profesor: " + e.getMessage());
    }
}

public void eliminar(int profesorID, int cursoID, int anio, int semestre) {
    String sql = "DELETE FROM curso_profesores WHERE profesor_id = ? AND curso_id = ? AND anio = ? AND semestre = ?";
    Connection conexion = AuxiliarBD.getInstance().getConnection();
    try (
         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

        pstmt.setInt(1, profesorID);
        pstmt.setInt(2, cursoID);
        pstmt.setInt(3, anio);
        pstmt.setInt(4, semestre);

        int filasAfectadas = pstmt.executeUpdate();
        if (filasAfectadas > 0) {
            AuxiliarBD.mostrarDatosBD_CURSO_PROFESORES();
            System.out.println("✅ Asignación eliminada correctamente.");
        } else {
            System.out.println("⚠️ No se encontró la asignación a eliminar.");
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al eliminar al profesor: " + e.getMessage());
    }
}

public void actualizar(int profesorID, int cursoID, int nuevoAnio, int nuevoSemestre) {
    String sqlActualizar = "UPDATE curso_profesores SET anio = ?, semestre = ? WHERE profesor_id = ? AND curso_id = ?";
    Connection conexion = AuxiliarBD.getInstance().getConnection();
    try (
         PreparedStatement pstmtActualizar = conexion.prepareStatement(sqlActualizar)) {

        pstmtActualizar.setInt(1, nuevoAnio);
        pstmtActualizar.setInt(2, nuevoSemestre);
        pstmtActualizar.setInt(3, profesorID);
        pstmtActualizar.setInt(4, cursoID);

        int filasAfectadas = pstmtActualizar.executeUpdate();
        if (filasAfectadas > 0) {
            AuxiliarBD.mostrarDatosBD_CURSO_PROFESORES();
            System.out.println("✅ Asignación actualizada correctamente.");
        } else {
            System.out.println("⚠️ No existe una asignación para actualizar.");
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al actualizar la asignación: " + e.getMessage());
    }
}


    public List<CursoProfesor> cargarDatosBD() {
        List<CursoProfesor> listado = new ArrayList<>();
        String sql = "SELECT * FROM curso_profesores;";
        Connection conexion = AuxiliarBD.getInstance().getConnection();
        try (
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int profesorID = rs.getInt("profesor_id");
                int cursoID = rs.getInt("curso_id");
                int anio = rs.getInt("anio");
                int semestre = rs.getInt("semestre");

                Profesor profesor = obtenerProfesorPorID(profesorID, conexion);
                Curso curso = obtenerCursoPorID(cursoID, conexion);

                if (profesor != null && curso != null) {
                    listado.add(FabricaObjetos.crearCursoProfesor(profesor, anio, semestre, curso));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al cargar los datos: " + e.getMessage());
        }
        return listado;
    }

  
    
     private Profesor obtenerProfesorPorID(int profesorID, Connection conexion) throws SQLException {
    String sql = "SELECT p.id, per.nombres, per.apellidos, per.email, p.tipo_contrato " +
                 "FROM profesor p " +
                 "JOIN persona per ON p.id = per.id " +
                 "WHERE p.id = ?;";

    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, profesorID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return FabricaObjetos.crearProfesor(
                rs.getInt("id"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("email"),
                rs.getString("tipo_contrato")
            );
        }
    }
    return null;
}
    
   private Curso obtenerCursoPorID(int cursoID, Connection conexion) throws SQLException {
    String sql = "SELECT c.id, c.nombre, c.activo, p.id AS programa_id, p.nombre AS programa_nombre, " +
                 "p.duracion, p.registro, f.id AS facultad_id, f.nombre AS facultad_nombre " +
                 "FROM curso c " +
                 "JOIN programa p ON c.programa_id = p.id " +
                 "JOIN facultad f ON p.facultad_id = f.id " +
                 "WHERE c.id = ?;";

    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, cursoID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Facultad facultad = FabricaObjetos.crearFacultad(rs.getInt("facultad_id"), rs.getString("facultad_nombre"), null);
            Programa programa = FabricaObjetos.crearPrograma(rs.getInt("programa_id"), rs.getString("programa_nombre"), 
                                             rs.getDouble("duracion"), rs.getDate("registro"), facultad);
            return FabricaObjetos.crearCurso(rs.getInt("id"), rs.getString("nombre"), programa, rs.getBoolean("activo"));
        }
    }
    return null;
}
   
   
   
}
