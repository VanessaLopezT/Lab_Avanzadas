/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import BaseDatos.AuxiliarBD;
import Factory.FabricaObjetos;
import interfaz.InscripcionApp;
import modelo.Estudiante;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author VANESA
 */
public class EstudianteDAO {
    
    private Connection conexion;
    public EstudianteDAO() {
        this.conexion = AuxiliarBD.getInstance().getConnection(); // ✅ Usa la misma conexión siempre
    }

    InscripcionApp inscripcionApp=new InscripcionApp();
    
    
    
    public void guardarEstudianteBD(Connection conexion, Estudiante estudiante) throws SQLException {
    String sqlPersona = "MERGE INTO persona KEY(id) VALUES (?, ?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlPersona)) {
        pstmt.setInt(1, estudiante.getID());
        pstmt.setString(2, estudiante.getNombres());
        pstmt.setString(3, estudiante.getApellidos());
        pstmt.setString(4, estudiante.getEmail());
        pstmt.executeUpdate();
    }

    // 🔹 Luego guardar en ESTUDIANTE
    String sqlEstudiante = "MERGE INTO estudiante KEY(id) VALUES (?, ?, ?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlEstudiante)) {
        pstmt.setInt(1, estudiante.getID());
        pstmt.setInt(2, estudiante.getCodigo());
        pstmt.setInt(3, estudiante.getPrograma().getID());
        pstmt.setBoolean(4, estudiante.isActivo());
        pstmt.setDouble(5, estudiante.getPromedio());
        pstmt.executeUpdate();
    }
 }
    
    
    public Estudiante obtenerEstudiantePorID(int idEstudiante) {
    Connection conexion = AuxiliarBD.getInstance().getConnection();
        // Crear la tabla si no existe
        String sqlEstudiante = "CREATE TABLE IF NOT EXISTS estudiante (" +
                "id INT PRIMARY KEY, " +
                "codigo INT, " +
                "programa_id INT, " +
                "activo BOOLEAN, " +
                "promedio DOUBLE, " +
                "FOREIGN KEY (id) REFERENCES persona(id), " +
                "FOREIGN KEY (programa_id) REFERENCES programa(id));";

        try (Statement stmt = conexion.createStatement()) {
            stmt.execute(sqlEstudiante);
        }catch (SQLException e) {
        inscripcionApp.mostrarError("Error al crear tabla: " + e.getMessage());
    }
        // Consulta para obtener el estudiante con sus datos personales
        String sql = "SELECT e.id, e.codigo, e.programa_id, e.activo, e.promedio, " +
                     "p.nombres, p.apellidos, p.email " +
                     "FROM estudiante e " +
                     "JOIN persona p ON e.id = p.id " +
                     "WHERE e.id = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idEstudiante);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProgramaDAO programaDAO=new ProgramaDAO();
                    return FabricaObjetos.crearEstudiante(
                        rs.getInt("codigo"),
                         
                        programaDAO.obtenerProgramaPorID(rs.getInt("programa_id")),
                        rs.getBoolean("activo"),
                        rs.getDouble("promedio"),
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email")
                    );
                }
            }
        
    } catch (SQLException e) {
        inscripcionApp.mostrarError("Error al buscar el estudiante: " + e.getMessage());
    }
    return null;
}

    
    
}

    


