package modelo;

import Service.Servicios;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.io.Serializable;



public class CursosInscritos implements Serializable, Servicios {
    private static final long serialVersionUID = 1L;
    private List<Inscripcion> listado = new ArrayList<>();
    

    public void guardainformacion() {
        try (FileOutputStream archivo = new FileOutputStream("inscripciones.bin");
             ObjectOutputStream escritura = new ObjectOutputStream(archivo)) {
            escritura.writeObject(listado);
            System.out.print("\nguardainformacion--> Lista de Inscripciones añadida con éxito.");
        } catch (IOException error) {
            error.printStackTrace(System.out);
        }
    }

    public void cargarDatos() {
        try (FileInputStream archivo = new FileInputStream("inscripciones.bin");
             ObjectInputStream lectura = new ObjectInputStream(archivo)) {
            List<Inscripcion> listaRecuperada = (List<Inscripcion>) lectura.readObject();
            listado.clear();
            listado.addAll(listaRecuperada);
            System.out.println("\ncargarDatos--> Lista de Inscripciones leída con éxito:");
            for (Inscripcion inscripcion : listaRecuperada) {
                System.out.println(inscripcion);
            }
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
            inscripciones.add("\n" + inscripcion.toString());
        }
        return inscripciones;
    }
    
    
}


   

