/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer_mensaje;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VANESA
 */
public class ObservableConcreto implements Observable{
    private List<Observador> observadores=new ArrayList<>();
    private String mensaje;
    
    @Override
    public void agregarObservador(Observador observador) {
        observadores.add(observador);}

    @Override
    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);}

    @Override
    public void notificarObservadores() {
        for (Observador observador: observadores){
            observador.actualizar(mensaje);
         }
    }
    
    public void cambiarEstado(String mensaje){
        this.mensaje=mensaje;
        notificarObservadores();
    }
    
}
