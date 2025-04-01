/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import BaseDatos.DatabaseConnection;
import Fabrica_Objetos.FabricaDAO;
import Fabrica_Objetos.FabricaModelo;
import interfaz.InscripcionApp;
import modelo.Inscripcion;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;


public class InscripcionDAO {

    private Connection conexion;
    InscripcionApp inscripcionApp=FabricaModelo.crearInscripcionApp();
    
    public InscripcionDAO() {
        this.conexion = DatabaseConnection.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
    }
    
    private  EstudianteDAO estudianteDAO=FabricaDAO.crearEstudianteDAO();
    private CursoDAO cursoDAO=FabricaDAO.crearCursoDAO();
    
   public void guardarInscripcionBD(Connection conexion, Inscripcion inscripcion) throws SQLException {
    String verificarSQL = "SELECT COUNT(*) FROM inscripcion WHERE curso_id = ? AND estudiante_id = ? AND anio = ? AND semestre = ?";

    // Verificar si la inscripción ya existe
   String insertarSQL = "INSERT INTO inscripcion (curso_id, estudiante_id, anio, semestre) VALUES (?, ?, ?, ?);";

    try (PreparedStatement verificarStmt = conexion.prepareStatement(verificarSQL)) {
        verificarStmt.setInt(1, inscripcion.getCurso().getID());
        verificarStmt.setInt(2, inscripcion.getEstudiante().getID());
        verificarStmt.setInt(3, inscripcion.getAño());
        verificarStmt.setInt(4, inscripcion.getSemestre());

        ResultSet rs = verificarStmt.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
        return;
        }

        // Si la inscripción no existe, la insertamos
        try (PreparedStatement insertarStmt = conexion.prepareStatement(insertarSQL)) {
            insertarStmt.setInt(1, inscripcion.getCurso().getID());
            insertarStmt.setInt(2, inscripcion.getEstudiante().getID());
            insertarStmt.setInt(3, inscripcion.getAño());
            insertarStmt.setInt(4, inscripcion.getSemestre());
            insertarStmt.executeUpdate();
        }
    } catch (SQLException e) {
        System.err.println("Error en guardarInscripcionBD: " + e.getMessage());
    }
}
  
   
   public Inscripcion obtenerInscripcionPorCursoYEstudiante(int cursoID, int estudianteID) {
    Connection conexion = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT * FROM inscripcion WHERE curso_id = ? AND estudiante_id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, cursoID);
            pstmt.setInt(2, estudianteID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return FabricaModelo.crearInscripcion(
                        cursoDAO.obtenerCursoPorID(rs.getInt("curso_id")),
                        rs.getInt("anio"),
                        rs.getInt("semestre"),
                        estudianteDAO.obtenerEstudiantePorID(rs.getInt("estudiante_id"))
                    );
                }
            }
        
    } catch (SQLException e) {
        inscripcionApp.mostrarError("Error al buscar la inscripción: " + e.getMessage());
    }
    return null;
}

    
}