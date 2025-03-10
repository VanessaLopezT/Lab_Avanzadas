/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
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
