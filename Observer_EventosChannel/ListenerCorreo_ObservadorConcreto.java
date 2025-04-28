/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer_EventosChannel;

/**
 *
 * @author VANESA
 */
public class ListenerCorreo_ObservadorConcreto implements Suscriptor_Observador{
    private String correo;
    private String mensaje;

    public ListenerCorreo_ObservadorConcreto(String correo, String mensaje) {
        this.correo = correo;
        this.mensaje = mensaje;
    }
    
    @Override
    public void actualizar(String nombreArchivo) {
        System.out.println("Enviando correo a "+ correo + ": " + String.format(mensaje, nombreArchivo));}
    
    
}
