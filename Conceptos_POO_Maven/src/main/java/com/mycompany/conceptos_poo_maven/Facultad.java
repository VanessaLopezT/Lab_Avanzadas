/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conceptos_poo_maven;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.io.Serializable;

public class Facultad implements Serializable{
    private int ID;
    private String nombre;
    private Persona decano;

    public Facultad(int ID, String nombre, Persona decano) {
        this.ID = ID;
        this.nombre = nombre;
        this.decano = decano;
    }
    
   public void guardarFacultadBD(Connection conexion) throws SQLException {

    String sql = "MERGE INTO facultad KEY(id) VALUES (?, ?, ?);";
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, this.ID);
        pstmt.setString(2, this.nombre);
        pstmt.setInt(3, this.decano.getID());
        pstmt.executeUpdate();
    }
}

    @Override
    public String toString() {
        return "Facultad{" + "ID=" + ID + ", nombre=" + nombre + ", decano=" + decano.toString() + '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Persona getDecano() {
        return decano;
    }

    public void setDecano(Persona decano) {
        this.decano = decano;
    }
    
    
}
