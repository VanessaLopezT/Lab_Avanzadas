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
public class CursosInscritos {
    private List<Inscripcion> listado= new ArrayList<Inscripcion>();
    private static final long serialVersionUID=1L; // Para mantener compatibilidad en serialización
    
    private static final String archivo="personas.dat"; // Nombre del archivo binario

    
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
        System.out.print("Objeto añadido con exito");
        escritura.close();
    } catch (IOException error){
        error.printStackTrace(System.out);
    }
    }
    
    public void cargarDatos(){};
    
}
