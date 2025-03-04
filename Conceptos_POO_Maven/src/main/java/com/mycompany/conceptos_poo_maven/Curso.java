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
/**
/**
 *
 * @author Estudiante_MCA
 */
public class Curso implements Serializable{
    private Integer ID;
    private String NombreCurso;
    private Programa programa;
    private boolean activo;

    public Curso(Integer ID, String NombreCurso, Programa programa, boolean activo) {
        this.ID = ID;
        this.NombreCurso = NombreCurso;
        this.programa = programa;
        this.activo = activo;
    }
    
public void guardarCursoBD(Connection conexion) {
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
        pstmt.setInt(1, this.ID);
        pstmt.setString(2, this.NombreCurso);
        pstmt.setInt(3, this.programa.getID());
        pstmt.setBoolean(4, this.activo);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
}





    @Override
    public String toString() {
        return "Curso{" + "ID=" + ID + ", NombreCurso=" + NombreCurso + ", programa=" + programa + ", activo=" + activo + '}';
    }


    public Integer getID() {
        return ID;
    }

    public String getNombreCurso() {
        return NombreCurso;
    }

    public void setNombreCurso(String NombreCurso) {
        this.NombreCurso = NombreCurso;
    }

    
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
