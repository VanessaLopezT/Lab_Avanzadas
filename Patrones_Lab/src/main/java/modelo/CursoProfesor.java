/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.io.Serializable;

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
