/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Factory.FabricaObjetos;
import BaseDatos.AuxiliarBD;
import modelo.Curso;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import modelo.Programa;


public class CursoDAO {

    private Connection conexion;

    public CursoDAO() {
        this.conexion = AuxiliarBD.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
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
    
    public Curso obtenerCursoPorID(int idCurso) {
            Connection conexion = AuxiliarBD.getInstance().getConnection();
            String sql = "SELECT * FROM curso WHERE id = ?";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, idCurso);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return FabricaObjetos.crearCurso(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            new ProgramaDAO().obtenerProgramaPorID(rs.getInt("programa_id")),
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

