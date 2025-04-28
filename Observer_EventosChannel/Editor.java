/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer_EventosChannel;

/**
 *
 * @author VANESA
 */
public class Editor {
    private GestorEventos_ObservableConcreto gestorEventos;
    private String archivo;
    
    public Editor(){
    gestorEventos=new GestorEventos_ObservableConcreto();}
    
    
    public void AbrirArchivo(String ruta){
        this.archivo=ruta;
        gestorEventos.notificar("abrir", archivo);
    }
    
    public void guardarArchivo(){
    gestorEventos.notificar("guardar", archivo);}
    

    public GestorEventos_ObservableConcreto gestorEventos(){
    return gestorEventos;
    }
}
