/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conceptos_poo_maven;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*; 
/**
 *
 * @author Estudiante_MCA
 */
public class Estudiante extends Persona{
    private int codigo;
    private Programa programa;
    private boolean activo;
    private double promedio;

    public Estudiante(int codigo, Programa programa, boolean activo, double promedio, int ID, String nombres, String apellidos, String email) {
        super(ID, nombres, apellidos, email);
        this.codigo = codigo;
        this.programa = programa;
        this.activo = activo;
        this.promedio = promedio;
    }
public void guardarEstudianteBD(Connection conexion) throws SQLException {
    String sqlPersona = "MERGE INTO persona KEY(id) VALUES (?, ?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlPersona)) {
        pstmt.setInt(1, this.ID);
        pstmt.setString(2, this.nombres);
        pstmt.setString(3, this.apellidos);
        pstmt.setString(4, this.email);
        pstmt.executeUpdate();
    }

    // 🔹 Luego guardar en ESTUDIANTE
    String sqlEstudiante = "MERGE INTO estudiante KEY(id) VALUES (?, ?, ?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sqlEstudiante)) {
        pstmt.setInt(1, this.ID);
        pstmt.setInt(2, this.codigo);
        pstmt.setInt(3, this.programa.getID());
        pstmt.setBoolean(4, this.activo);
        pstmt.setDouble(5, this.promedio);
        pstmt.executeUpdate();
    }
}

    @Override
    public String toString() {
        return "Estudiante{" + "codigo=" + codigo + ", programa=" + programa.toString() + ", activo=" + activo + ", promedio=" + promedio + '}';
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
