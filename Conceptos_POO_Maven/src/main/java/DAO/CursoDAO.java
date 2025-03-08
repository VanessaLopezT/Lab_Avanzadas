/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import modelo.Curso;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;


public class CursoDAO {

    private Connection conexion;

    public CursoDAO(Connection conexion) {
        this.conexion = conexion;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

}

