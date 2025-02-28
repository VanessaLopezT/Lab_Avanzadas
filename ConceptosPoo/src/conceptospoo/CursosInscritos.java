/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conceptospoo;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.io.Serializable;
     
/**
 *
 * @author Estudiante_MCA
 */
public class CursosInscritos implements Serializable, Servicios{
    private List<Inscripcion> listado= new ArrayList<>();
    private static final long serialVersionUID=1L; // Para mantener compatibilidad en serialización
    
    
    
    @Override
    public String toString() {
        return "CursosInscritos{" + "listado=" + listado + '}';
    }
    
    
    public void inscribirCurso(Inscripcion inscripcion){
        listado.add(inscripcion);
    }
    public void eliminar(Inscripcion inscripcion){
        listado.removeIf(i -> i.getCurso()==inscripcion.getCurso());
    }
    public void actualizar(Inscripcion inscripcion){
        for (int i=0; i<listado.size(); i++){
            if (listado.get(i).getCurso()==inscripcion.getCurso()){
                listado.set(i, inscripcion);
                return;
            }
        }
    }
    
     public void guardainformacion() {
    try {
        FileOutputStream archivo = new FileOutputStream("inscripciones.bin");
        ObjectOutputStream escritura = new ObjectOutputStream(archivo);
        
        escritura.writeObject(listado); // Guardar lista completa en binario
        System.out.print("\nguardainformacion--> Lista de Inscripciones aniadida con exito.");
        escritura.close();
    } catch (IOException error) {
        error.printStackTrace(System.out);
    }
}

    public void cargarDatos() {
    try {
        FileInputStream archivo = new FileInputStream("inscripciones.bin");
        ObjectInputStream lectura = new ObjectInputStream(archivo);
        
        List<Inscripcion> listaInscripcionesRecuperada = (List<Inscripcion>) lectura.readObject();
        
        System.out.println("\ncargarDatos--> Lista de Inscripciones leida con exito:");
        for (Inscripcion inscripcion : listaInscripcionesRecuperada) {
            System.out.println(inscripcion); // Imprimir cada objeto Inscripcion
        }
        
        lectura.close();
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error al leer el archivo:");
        e.printStackTrace();
    }
}
 
    
    @Override
    public String ImprimirPosicion(int posicion) {
        return (posicion >= 0 && posicion < listado.size()) ? listado.get(posicion).toString() : "Posición fuera de rango";
    }

    @Override
    public int cantidadActual() {
        return listado.size();
    }
    
    @Override
    public List<String> ImprimirListado() {
        List<String> inscripciones = new ArrayList<>();
        for (Inscripcion inscripcion : listado) {
            inscripciones.add("\n"+inscripcion.toString());
        }
        return inscripciones;
    }

    
}
