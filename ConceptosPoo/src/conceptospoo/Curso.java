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
public class Curso implements Serializable{
    private Integer ID;
    private Programa programa;
    private boolean activo;

    public Curso(Integer ID, Programa programa, boolean activo) {
        this.ID = ID;
        this.programa = programa;
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Curso{" + "ID=" + ID + ", programa=" + programa.toString() + ", activo=" + activo + '}';
    }

    public Integer getID() {
        return ID;
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
