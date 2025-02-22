/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conceptospoo;

import java.io.Serializable;

/**
 *
 * @author Estudiante_MCA
 */
public class Facultad implements Serializable{
    private double ID;
    private String nombre;
    private Persona decano;

    public Facultad(double ID, String nombre, Persona decano) {
        this.ID = ID;
        this.nombre = nombre;
        this.decano = decano;
    }

    @Override
    public String toString() {
        return "Facultad{" + "ID=" + ID + ", nombre=" + nombre + ", decano=" + decano.toString() + '}';
    }

    public double getID() {
        return ID;
    }

    public void setID(double ID) {
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
