/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer_mensaje;

/**
 *
 * @author VANESA
 */
public class Cliente {
    public static void main(String[] args) {
        ObservableConcreto observable=new ObservableConcreto();
        
        ObservadorConcreto Observador1=new ObservadorConcreto("Observador 1");
        ObservadorConcreto Observador2=new ObservadorConcreto("Observador 2");
        
        observable.agregarObservador(Observador1);
        observable.agregarObservador(Observador2);
        
        observable.cambiarEstado("Mensaje para los observadores");
        observable.cambiarEstado("Nuevo mensaje para los observadores");
        
        observable.eliminarObservador(Observador2);
        observable.cambiarEstado("Mensaje para observadores restantes");
    }
    
}
