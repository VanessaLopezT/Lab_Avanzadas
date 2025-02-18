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
public class InscripcionesPersona implements Serializable{
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
    
    public void guardainformacion(Persona persona){
    try {
        FileOutputStream archivo= new FileOutputStream("personas.bin");
        ObjectOutputStream escritura=new ObjectOutputStream(archivo);
        
        escritura.writeObject(persona);
        System.out.print("Objeto añadido con exito");
        escritura.close();
    } catch (IOException error){
        error.printStackTrace(System.out);
    }
    }
    

    
    public void cargarDatos(){
    
    
}

    
}
