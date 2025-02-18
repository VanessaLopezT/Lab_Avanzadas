/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conceptospoo;

/**
 *
 * @author Estudiante_MCA
 */
public class Profesor extends Persona{
    protected String TipoContrato;

    public Profesor(double ID, String nombres, String apellidos, String email,String TipoContrato) {
        super(ID, nombres, apellidos, email);
        this.TipoContrato = TipoContrato;
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

    public double getID() {
        return ID;
    }

    public void setID(double ID) {
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
