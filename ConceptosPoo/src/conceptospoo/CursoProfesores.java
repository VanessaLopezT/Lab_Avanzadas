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
public class CursoProfesores {
    private static final long serialVersionUID=1L; // Para mantener compatibilidad en serialización
    
    private List<CursoProfesor> listado=new ArrayList<CursoProfesor>();
    
    private static final String archivo="personas.dat"; // Nombre del archivo binario

    @Override
    public String toString() {
        return "CursoProfesores{" + "listado=" + listado + '}';
    }
     
    public void inscribir(CursoProfesor cursoProfesor){
        listado.add(cursoProfesor);
    }   
    public void guardainformacion(CursoProfesor cursoProfesor){
    try {
        FileOutputStream archivo= new FileOutputStream("CursosProfesores.bin");
        ObjectOutputStream escritura=new ObjectOutputStream(archivo);
        
        escritura.writeObject(cursoProfesor);
        System.out.print("\nObjeto añadido con exito");
        escritura.close();
    } catch (IOException error){
        error.printStackTrace(System.out);
    }
    }
    
    public void cargarDatos(){};

    
    
}
