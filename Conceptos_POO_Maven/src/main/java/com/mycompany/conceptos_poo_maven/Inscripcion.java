/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conceptos_poo_maven;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;    
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
/**
 *
 * @author Estudiante_MCA
 */
public class Inscripcion implements Serializable {
    private Curso curso;
    private Integer año;
    private Integer semestre;
    private Estudiante estudiante;

    public Inscripcion(Curso curso, Integer año, Integer semestre, Estudiante estudiante) {
        this.curso = curso;
        this.año = año;
        this.semestre = semestre;
        this.estudiante = estudiante;
    }

   public void guardarInscripcionBD(Connection conexion) throws SQLException {

    // Verificar si la inscripción ya existe
    String verificarSQL = "SELECT COUNT(*) FROM inscripciones WHERE curso_id = ? AND estudiante_id = ? AND semestre = ?";
    String insertarSQL = "INSERT INTO inscripciones (curso_id, estudiante_id, semestre) VALUES (?, ?, ?)";

    try (PreparedStatement verificarStmt = conexion.prepareStatement(verificarSQL)) {
        verificarStmt.setInt(1, this.curso.getID());
        verificarStmt.setInt(2, this.estudiante.getID());
        verificarStmt.setInt(3, this.semestre);

        ResultSet rs = verificarStmt.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
        return;
        }

        // Si la inscripción no existe, la insertamos
        try (PreparedStatement insertarStmt = conexion.prepareStatement(insertarSQL)) {
            insertarStmt.setInt(1, this.curso.getID());
            insertarStmt.setInt(2, this.estudiante.getID());
            insertarStmt.setInt(3, this.semestre);
            insertarStmt.executeUpdate();
        }
    } catch (SQLException e) {
        System.err.println("❌ Error en guardarInscripcionBD: " + e.getMessage());
    }
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscripcion that = (Inscripcion) o;
        return Objects.equals(curso, that.curso) &&
               Objects.equals(estudiante, that.estudiante) &&
               Objects.equals(año, that.año) &&
               Objects.equals(semestre, that.semestre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curso, estudiante, año, semestre);
    }

    @Override
    public String toString() {
        return "Inscripcion{" + "curso=" + curso.toString() + ", anio=" + año + ", semestre=" + semestre + ", estudiante=" + estudiante.toString() + '}';
    }


    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
        
    
}
