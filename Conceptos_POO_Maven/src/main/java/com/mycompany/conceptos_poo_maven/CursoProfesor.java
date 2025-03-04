/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conceptos_poo_maven;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*; 
/**
 *
 * @author Estudiante_MCA
 */
public class CursoProfesor implements Serializable{
    private Profesor profesor;
    private Integer anio;
    private Integer semestre;
    private Curso curso;

    public CursoProfesor(Profesor profesor, Integer anio, Integer semestre, Curso curso) {
        this.profesor = profesor;
        this.anio = anio;
        this.semestre = semestre;
        this.curso = curso;
    }
    
public void guardarCursoProfesorBD(Connection conexion) {
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
        pstmt.setInt(1, this.profesor.getID()); // Obtener ID del profesor
        pstmt.setInt(2, this.curso.getID());    // Obtener ID del curso
        pstmt.setInt(3, this.anio);
        pstmt.setInt(4, this.semestre);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    @Override
    public String toString() {
        return "CursoProfesor{" + "profesor=" + profesor.toString() + ", anio=" + anio + ", semestre=" + semestre + ", curso=" + curso.toString() + '}';
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Integer getAño() {
        return anio;
    }

    public void setAño(Integer año) {
        this.anio = año;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    
}
