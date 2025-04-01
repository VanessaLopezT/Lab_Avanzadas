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
public interface Observador {
    // Método que se llamará cuando el objeto observable notifique un cambio 
      void actualizar(List<Curso> cursos);
}
