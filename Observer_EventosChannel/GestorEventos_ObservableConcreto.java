/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer_EventosChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author VANESA
 */
public class GestorEventos_ObservableConcreto implements Notificador_Observable{
    private HashMap<String, List<Suscriptor_Observador>> suscriptores=new HashMap<>();
    @Override
    public void suscribir(String tipoEvento, Suscriptor_Observador suscriptor) {
        suscriptores.computeIfAbsent(tipoEvento, k -> new ArrayList<>()).add(suscriptor);
    }

    @Override
    public void desuscribir(String tipoEvento, Suscriptor_Observador suscriptor) {
        List<Suscriptor_Observador> listaSuscriptores=suscriptores.get(tipoEvento);
        if (listaSuscriptores!=null){
            listaSuscriptores.remove(suscriptor);
    }   
        
    }

    @Override
    public void notificar(String tipoEvento, String data) {
         List<Suscriptor_Observador> listaSuscriptores = suscriptores.get(tipoEvento);
        if (listaSuscriptores != null) {
            for (Suscriptor_Observador suscriptor : listaSuscriptores) {
                suscriptor.actualizar(data);
            }
        }
    }
    
}
