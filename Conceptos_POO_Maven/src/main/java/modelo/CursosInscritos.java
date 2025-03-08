package modelo;

import BaseDatos.ConexionBD;
import servicios.Servicios;
import modelo.Inscripcion;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CursosInscritos implements Serializable, Servicios {
    private static final long serialVersionUID = 1L;
    private List<Inscripcion> listado = new ArrayList<>();
    
  public void inscribir(Inscripcion inscripcion) {
    listado.add(inscripcion);   
    String sql = "INSERT INTO cursos_inscritos (inscripcion_id, estudiante_id) VALUES (?, ?)";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, inscripcion.getCurso().getID()); 
        stmt.setInt(2, inscripcion.getEstudiante().getID());

        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas < 0) {
            System.out.println("No se pudo inscribir al estudiante.");
        }
    } catch (SQLException e) {
        System.err.println("Error al inscribir al estudiante: " + e.getMessage());
    }
}


  public void guardarDatosBD(Inscripcion inscripcion) {
    String sql = "INSERT INTO cursos_inscritos (inscripcion_id) VALUES (?);";
    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

        pstmt.setInt(1, inscripcion.getCurso().getID()); 

        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Error al guardar la inscripción: " + e.getMessage());
    }
}

public void buscarInscripcionEnCursosInscritos(int inscripcionID, int estudianteID) {
    String sql = "SELECT * FROM CURSOS_INSCRITOS WHERE INSCRIPCION_ID = ? AND ESTUDIANTE_ID = ?;";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, inscripcionID);  
        pstmt.setInt(2, estudianteID);

        System.out.println("Ejecutando consulta: " + pstmt.toString());

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            System.out.println("Inscripción encontrada en CURSOS_INSCRITOS:");
            System.out.println("ID: " + rs.getInt("ID") +
                               " | INSCRIPCION_ID: " + rs.getInt("INSCRIPCION_ID") +
                               " | ESTUDIANTE_ID: " + rs.getInt("ESTUDIANTE_ID"));
        } else {
            System.out.println("No se encontró la inscripción en CURSOS_INSCRITOS.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


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

   public void Eliminar(int inscripcionID, int estudianteID) {
    
    String sqlBuscar = "SELECT * FROM CURSOS_INSCRITOS WHERE INSCRIPCION_ID = ? AND ESTUDIANTE_ID = ?;";
    String sqlEliminar = "DELETE FROM CURSOS_INSCRITOS WHERE ID = ?;";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement pstmtEliminar = conexion.prepareStatement(sqlEliminar)) {

        // 🔍 Buscar la inscripción
        pstmtBuscar.setInt(1, inscripcionID);
        pstmtBuscar.setInt(2, estudianteID);
        ResultSet rs = pstmtBuscar.executeQuery();

        if (rs.next()) {
            
            int idRegistro = rs.getInt("ID");
            System.out.println("Inscripción encontrada en CURSOS_INSCRITOS:");
            System.out.println("ID: " + idRegistro +
                               " | INSCRIPCION_ID: " + rs.getInt("INSCRIPCION_ID") +
                               " | ESTUDIANTE_ID: " + rs.getInt("ESTUDIANTE_ID"));

            // 🗑 Eliminar la inscripción
            pstmtEliminar.setInt(1, idRegistro);

            int filasAfectadas = pstmtEliminar.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Inscripción eliminada correctamente.");
            } else {
                System.out.println("No se pudo eliminar la inscripción.");
            }

        } else {
            System.out.println("No se encontró la inscripción en CURSOS_INSCRITOS.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void Actualizar(int inscripcionID, int estudianteID, int nuevoCursoID) {
    String sqlBuscar = "SELECT * FROM CURSOS_INSCRITOS WHERE INSCRIPCION_ID = ? AND ESTUDIANTE_ID = ?;";
    String sqlActualizar = "UPDATE CURSOS_INSCRITOS SET INSCRIPCION_ID = ? WHERE ID = ?;";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement pstmtActualizar = conexion.prepareStatement(sqlActualizar)) {

        // 🔍 Buscar la inscripción
        pstmtBuscar.setInt(1, inscripcionID);
        pstmtBuscar.setInt(2, estudianteID);
        ResultSet rs = pstmtBuscar.executeQuery();

        if (rs.next()) {
            int idRegistro = rs.getInt("ID");
            System.out.println("Inscripción encontrada en CURSOS_INSCRITOS:");
            System.out.println("ID: " + idRegistro +
                               " | INSCRIPCION_ID: " + rs.getInt("INSCRIPCION_ID") +
                               " | ESTUDIANTE_ID: " + rs.getInt("ESTUDIANTE_ID"));

            // ✏ Actualizar con el nuevo curso
            pstmtActualizar.setInt(1, nuevoCursoID);
            pstmtActualizar.setInt(2, idRegistro);

            int filasAfectadas = pstmtActualizar.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Inscripción actualizada con el nuevo curso (ID: " + nuevoCursoID + ").");
            } else {
                System.out.println("No se pudo actualizar la inscripción.");
            }

        } else {
            System.out.println("No se encontró la inscripción en CURSOS_INSCRITOS.");
        }

    } catch (SQLException e) {
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


   

