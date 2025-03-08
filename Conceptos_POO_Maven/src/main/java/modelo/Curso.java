/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.sql.Connection;
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
