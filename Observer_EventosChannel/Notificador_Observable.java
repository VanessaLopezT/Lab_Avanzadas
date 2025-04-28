/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observer_EventosChannel;

/**
 *
 * @author VANESA
 */
public interface Notificador_Observable {
    void suscribir(String tipoEvento, Suscriptor_Observador suscriptor);
    void desuscribir(String tipoEvento, Suscriptor_Observador suscriptor);
    void notificar(String tipoEvento, String data);
}
