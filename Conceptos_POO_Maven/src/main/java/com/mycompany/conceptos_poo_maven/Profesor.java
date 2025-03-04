/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conceptos_poo_maven;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*; 
/**
 *
 * @author Estudiante_MCA
 */
public class Profesor extends Persona{
    protected String TipoContrato;

    public Profesor(int ID, String nombres, String apellidos, String email,String TipoContrato) {
        super(ID, nombres, apellidos, email);
        this.TipoContrato = TipoContrato;
    }
    public void guardarProfesorBD(Connection conexion) throws SQLException {
    String sqlPersona = "MERGE INTO persona KEY(id) VALUES (?, ?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlPersona)) {
        pstmt.setInt(1, this.ID);
        pstmt.setString(2, this.nombres);
        pstmt.setString(3, this.apellidos);
        pstmt.setString(4, this.email);
        pstmt.executeUpdate();
    }

    // Luego guardar en la tabla PROFESOR
    String sqlProfesor = "MERGE INTO profesor KEY(id) VALUES (?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlProfesor)) {
        pstmt.setInt(1, this.ID);
        pstmt.setString(2, this.TipoContrato);
        pstmt.executeUpdate();
    }
}

    @Override
    public String toString() {
        return "Profesor{" + "TipoContrato=" + TipoContrato + '}';
    }

    public String getTipoContrato() {
        return TipoContrato;
    }

    public void setTipoContrato(String TipoContrato) {
        this.TipoContrato = TipoContrato;
    }

    @Override
    public int getID() {
        return ID;
    }
    @Override
    public void setID(int ID) {
        this.ID = ID;
    }
    @Override
    public String getNombres() {
        return nombres;
    }
    @Override
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    @Override
    public String getApellidos() {
        return apellidos;
    }
    @Override
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    
    
}
