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
public class CursosInscritos implements Serializable{
    private List<Inscripcion> listado= new ArrayList<Inscripcion>();
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
    
     public void guardainformacion(Inscripcion inscripcion){
    try {
        FileOutputStream archivo= new FileOutputStream("inscripciones.bin");
        ObjectOutputStream escritura=new ObjectOutputStream(archivo);
        
        escritura.writeObject(inscripcion);
        System.out.print("\nguardainformacion-->Objeto inscripcion anadido con exito");
        escritura.close();
    } catch (IOException error){
        error.printStackTrace(System.out);
    }
    }
    
    public void cargarDatos() {
    try {
        FileInputStream archivo = new FileInputStream("inscripciones.bin");
        ObjectInputStream lectura = new ObjectInputStream(archivo);
        
        Inscripcion inscripcionRecuperada = (Inscripcion) lectura.readObject();
        
        System.out.println("\ncargarDatos-->Objeto inscripcion leido con exito: ");
        System.out.println(inscripcionRecuperada); // Esto imprimirá la información del objeto
        
        lectura.close();
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error al leer el archivo:");
        e.printStackTrace();
    }
}

    
}
