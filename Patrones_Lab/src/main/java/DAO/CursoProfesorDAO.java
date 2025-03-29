/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import BaseDatos.AuxiliarBD;
import modelo.CursoProfesor;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author VANESA
 */
public class CursoProfesorDAO {
    
     private Connection conexion;

    public CursoProfesorDAO() {
        this.conexion = AuxiliarBD.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
    }
    
    public void guardarCursoProfesorBD(Connection conexion, CursoProfesor cursoProfesor) {
    if (conexion == null) {
        System.out.println("❌ Error: La conexión a la base de datos es nula.");
        return;
    }

    String sql = "MERGE INTO curso_profesor AS cp " +
                 "USING (VALUES (?, ?, ?, ?)) AS vals (profesor_id, curso_id, anio, semestre) " +
                 "ON cp.profesor_id = vals.profesor_id AND cp.curso_id = vals.curso_id " +
                 "WHEN MATCHED THEN UPDATE SET cp.anio = vals.anio, cp.semestre = vals.semestre " +
                 "WHEN NOT MATCHED THEN INSERT (profesor_id, curso_id, anio, semestre) " +
                 "VALUES (vals.profesor_id, vals.curso_id, vals.anio, vals.semestre);";

    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, cursoProfesor.getProfesor().getID()); // Obtener ID del profesor
        pstmt.setInt(2, cursoProfesor.getCurso().getID());    // Obtener ID del curso
        pstmt.setInt(3, cursoProfesor.getAño());
        pstmt.setInt(4, cursoProfesor.getSemestre());
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
