/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conceptospoo;
import java.io.*;
import java.io.Serializable;
   
/**
 *
 * @author Estudiante_MCA
 */
public class CursoProfesor implements Serializable{
    private Profesor profesor;
    private Integer año;
    private Integer semestre;
    private Curso curso;

    public CursoProfesor(Profesor profesor, Integer año, Integer semestre, Curso curso) {
        this.profesor = profesor;
        this.año = año;
        this.semestre = semestre;
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "CursoProfesor{" + "profesor=" + profesor.toString() + ", a\u00f1o=" + año + ", semestre=" + semestre + ", curso=" + curso.toString() + '}';
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
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
