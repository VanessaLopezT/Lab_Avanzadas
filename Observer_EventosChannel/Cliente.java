/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer_EventosChannel;

/**
 *
 * @author VANESA
 */
public class Cliente {
    public static void main(String[] args) {
        Editor editor=new Editor();
        
        ListenerLog_ObservadorConcreto listenerLog=new ListenerLog_ObservadorConcreto("Alguien ha abierto el archivo: %s");
        ListenerCorreo_ObservadorConcreto listenerCorreo=new ListenerCorreo_ObservadorConcreto("vanne@example.com", "Alguien ha guardado el archivo: %s");
        
        editor.gestorEventos().suscribir("abrir", listenerLog);
        editor.gestorEventos().suscribir("guardar", listenerCorreo);
        
        // Realizar acciones que activarán los eventos
        editor.AbrirArchivo("archivo.txt");
        editor.guardarArchivo();
    }
    
}
