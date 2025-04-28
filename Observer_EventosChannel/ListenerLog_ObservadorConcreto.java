/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer_EventosChannel;

import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author VANESA
 */
public class ListenerLog_ObservadorConcreto implements Suscriptor_Observador{
    private String mensaje;

    public ListenerLog_ObservadorConcreto(String mensaje) {
        this.mensaje = mensaje;
    }

    
    @Override
    public void actualizar(String nombreArchivo) {
       try(FileWriter writer=new FileWriter("log.txt",true)){
           writer.write(String.format(mensaje, nombreArchivo)+"\n");
       } catch (IOException e) {
            e.printStackTrace();
       }
    }
    
}
