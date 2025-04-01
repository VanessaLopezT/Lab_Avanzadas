/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ObserverPattern;

import java.util.List;
import modelo.Curso;

/**
 *
 * @author VANESA
 */
public interface Observable {
    void agregarObservador(Observador o); //Agrega un observador a la lista.
    void eliminarObservador(Observador o); //Elimina un observador de la lista.
    void notificarObservadores(List<Curso> cursos); //Notifica a todos los observadores que ha ocurrido un cambio.
}
