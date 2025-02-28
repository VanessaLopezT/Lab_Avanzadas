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
public class InscripcionesPersona implements Serializable, Servicios{
    private static final long serialVersionUID=1L; // Para mantener compatibilidad en serialización
    
    private List<Persona> listado= new ArrayList<Persona>();
 
    
    
    public void inscribir(Persona persona){
        listado.add(persona);
    }
    public void eliminar(Persona persona){
        listado.removeIf(p -> p.getID()==persona.getID());
    }
    public void actualizar(Persona persona){
        for (int i=0; i<listado.size(); i++){
            if (listado.get(i).getID()==persona.getID()){
                listado.set(i, persona);
                return;
            }
        }
    }
    
    
    
    public void guardainformacion() {
    try {
        FileOutputStream archivo = new FileOutputStream("personas.bin");
        ObjectOutputStream escritura = new ObjectOutputStream(archivo);
        
        escritura.writeObject(listado); // Guardar lista completa en binario
        System.out.print("\nguardainformacion--> Lista de Persona aniadida con exito.");
        escritura.close();
    } catch (IOException error) {
        error.printStackTrace(System.out);
    }
}

    public void cargarDatos() {
    try {
        FileInputStream archivo = new FileInputStream("personas.bin");
        ObjectInputStream lectura = new ObjectInputStream(archivo);
        
        List<Persona> listaPersonasRecuperadas = (List<Persona>) lectura.readObject();
        
        System.out.println("\ncargarDatos--> Lista de Persona leida con exito:");
        for (Persona persona : listaPersonasRecuperadas) {
            System.out.println(persona); // Imprimir cada objeto Persona
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
    List<String> personas = new ArrayList<>();
    for (Persona persona : listado) {
        personas.add(persona.toString() + "\n"); // Agrega un salto de línea al final de cada persona
    }
    return personas;
}



    
}
