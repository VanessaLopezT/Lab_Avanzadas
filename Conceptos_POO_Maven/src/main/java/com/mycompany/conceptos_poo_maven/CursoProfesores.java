/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conceptos_poo_maven;

import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Estudiante_MCA
 */
    public class CursoProfesores implements Serializable, Servicios{
    private static final long serialVersionUID=1L; // Para mantener compatibilidad en serialización
    private List<CursoProfesor> listado=new ArrayList<CursoProfesor>();
    
    
    @Override
    public String toString() {
        return "CursoProfesores{" + "listado=" + listado + '}';
    }
    public void buscarCursoProfesor(int profesorID, int cursoID) {
        String sql = "SELECT * FROM curso_profesores WHERE profesor_id = ? AND curso_id = ?;";
        
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, profesorID);
            pstmt.setInt(2, cursoID);
      
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("✅ Profesor encontrado en el curso:" +
                        " | ID: " + rs.getInt("ID") +
                        " | PROFESOR_ID: " + rs.getInt("PROFESOR_ID") +
                        " | CURSO_ID: " + rs.getInt("CURSO_ID") +
                        " | AÑO: " + rs.getInt("ANIO") +
                        " | SEMESTRE: " + rs.getInt("SEMESTRE"));
            } else {
                System.out.println("⚠ No se encontró el profesor en el curso para ese año y semestre.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
public void inscribir(CursoProfesor cursoProfesor) {
    String sqlBuscar = "SELECT * FROM curso_profesores WHERE profesor_id = ? AND curso_id = ? AND anio = ? AND semestre = ?";
    String sqlInsertar = "INSERT INTO curso_profesores (profesor_id, curso_id, anio, semestre) VALUES (?, ?, ?, ?)";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement pstmtInsertar = conexion.prepareStatement(sqlInsertar)) {

        pstmtBuscar.setInt(1, cursoProfesor.getProfesor().getID());
        pstmtBuscar.setInt(2, cursoProfesor.getCurso().getID());
        pstmtBuscar.setInt(3, cursoProfesor.getAño());
        pstmtBuscar.setInt(4, cursoProfesor.getSemestre());

        ResultSet rs = pstmtBuscar.executeQuery();
        if (rs.next()) {
            return;
        }

        pstmtInsertar.setInt(1, cursoProfesor.getProfesor().getID());
        pstmtInsertar.setInt(2, cursoProfesor.getCurso().getID());
        pstmtInsertar.setInt(3, cursoProfesor.getAño());
        pstmtInsertar.setInt(4, cursoProfesor.getSemestre());

        int filasAfectadas = pstmtInsertar.executeUpdate();
        if (filasAfectadas > 0) {
            listado.add(cursoProfesor); // Se agrega a la lista
            System.out.println("✅ Profesor inscrito correctamente.");
        } else {
            System.out.println("⚠ No se pudo inscribir al profesor.");
        }
    } catch (SQLException e) {
        System.err.println("❌ Error al inscribir al profesor: " + e.getMessage());
    }
}

public void eliminar(int profesorID, int cursoID, int anio, int semestre) {
    String sql = "DELETE FROM curso_profesores WHERE profesor_id = ? AND curso_id = ? AND anio = ? AND semestre = ?";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmt = conexion.prepareStatement(sql)) {

        pstmt.setInt(1, profesorID);
        pstmt.setInt(2, cursoID);
        pstmt.setInt(3, anio);
        pstmt.setInt(4, semestre);

        int filasAfectadas = pstmt.executeUpdate();
        if (filasAfectadas > 0) {
            // Eliminar de la lista
            listado.removeIf(cp -> cp.getProfesor().getID() == profesorID &&
                                   cp.getCurso().getID() == cursoID &&
                                   cp.getAño() == anio &&
                                   cp.getSemestre() == semestre);

            System.out.println("Profesor eliminado correctamente.");
        } else {
            System.out.println("No se encontró el profesor en el curso.");
        }
    } catch (SQLException e) {
        System.err.println("Error al eliminar al profesor: " + e.getMessage());
    }
}

public void actualizar(int profesorID, int cursoID, int nuevoAnio, int nuevoSemestre) {
    String sqlBuscar = "SELECT * FROM curso_profesores WHERE profesor_id = ? AND curso_id = ?";
    String sqlActualizar = "UPDATE curso_profesores SET anio = ?, semestre = ? WHERE profesor_id = ? AND curso_id = ?";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement pstmtBuscar = conexion.prepareStatement(sqlBuscar);
         PreparedStatement pstmtActualizar = conexion.prepareStatement(sqlActualizar)) {

        pstmtBuscar.setInt(1, profesorID);
        pstmtBuscar.setInt(2, cursoID);
        ResultSet rs = pstmtBuscar.executeQuery();

        if (rs.next()) {
            System.out.println("✅ Registro encontrado, actualizando...");

            pstmtActualizar.setInt(1, nuevoAnio);
            pstmtActualizar.setInt(2, nuevoSemestre);
            pstmtActualizar.setInt(3, profesorID);
            pstmtActualizar.setInt(4, cursoID);

            int filasAfectadas = pstmtActualizar.executeUpdate();

            if (filasAfectadas > 0) {
                // Modificar en la lista
                for (CursoProfesor cp : listado) {
                    if (cp.getProfesor().getID() == profesorID && cp.getCurso().getID() == cursoID) {
                        cp.setAño(nuevoAnio);
                        cp.setSemestre(nuevoSemestre);
                        break;
                    }
                }
                System.out.println("✅ Actualización exitosa.");
            } else {
                System.out.println("⚠ No se pudo actualizar el registro.");
            }
        } else {
            System.out.println("⚠ No se encontró el profesor en el curso.");
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al actualizar: " + e.getMessage());
    }
}






 public void guardarDatosBD(CursoProfesor cursoProfesor) {
        String sql = "INSERT INTO curso_profesores (profesor_id, curso_id, anio, semestre) VALUES (?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE anio = VALUES(anio), semestre = VALUES(semestre);";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setInt(1, cursoProfesor.getProfesor().getID());
            pstmt.setInt(2, cursoProfesor.getCurso().getID());
            pstmt.setInt(3, cursoProfesor.getAño());
            pstmt.setInt(4, cursoProfesor.getSemestre());
            
            pstmt.executeUpdate();
            System.out.println("✅ CursoProfesor guardado en la base de datos.");
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar CursoProfesor: " + e.getMessage());
        }
    }
    
    public void cargarDatosBD() {
        listado.clear();
        String sql = "SELECT * FROM curso_profesores;";
        
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int profesorID = rs.getInt("profesor_id");
                int cursoID = rs.getInt("curso_id");
                int anio = rs.getInt("anio");
                int semestre = rs.getInt("semestre");
                
                Profesor profesor = obtenerProfesorPorID(profesorID, conexion);
                Curso curso = obtenerCursoPorID(cursoID, conexion);
                
                if (profesor != null && curso != null) {
                    CursoProfesor cursoProfesor = new CursoProfesor(profesor, anio, semestre, curso);
                    listado.add(cursoProfesor);
                } else {
                    System.out.println("⚠️ Error: Profesor o curso no encontrados.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Profesor obtenerProfesorPorID(int profesorID, Connection conexion) throws SQLException {
    String sql = "SELECT p.id, per.nombres, per.apellidos, per.email, p.tipo_contrato " +
                 "FROM profesor p " +
                 "JOIN persona per ON p.id = per.id " +
                 "WHERE p.id = ?;";

    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, profesorID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new Profesor(
                rs.getInt("id"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("email"),
                rs.getString("tipo_contrato")
            );
        }
    }
    return null;
}

  private Curso obtenerCursoPorID(int cursoID, Connection conexion) throws SQLException {
    String sql = "SELECT c.id, c.nombre, c.activo, p.id AS programa_id, p.nombre AS programa_nombre, " +
                 "p.duracion, p.registro, f.id AS facultad_id, f.nombre AS facultad_nombre " +
                 "FROM curso c " +
                 "JOIN programa p ON c.programa_id = p.id " +
                 "JOIN facultad f ON p.facultad_id = f.id " +
                 "WHERE c.id = ?;";

    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, cursoID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Facultad facultad = new Facultad(rs.getInt("facultad_id"), rs.getString("facultad_nombre"), null);
            Programa programa = new Programa(rs.getInt("programa_id"), rs.getString("programa_nombre"), 
                                             rs.getDouble("duracion"), rs.getDate("registro"), facultad);
            return new Curso(rs.getInt("id"), rs.getString("nombre"), programa, rs.getBoolean("activo"));
        }
    }
    return null;
}


    
    public void guardainformacion() {
    try {
        FileOutputStream archivo = new FileOutputStream("CursoProfesor.bin");
        ObjectOutputStream escritura = new ObjectOutputStream(archivo);
        
        escritura.writeObject(listado); // Guardar lista completa en binario
        System.out.print("\nguardainformacion--> Lista de CursoProfesor aniadida con exito.");
        escritura.close();
    } catch (IOException error) {
        error.printStackTrace(System.out);
    }
}

    public void cargarDatos() {
    try (FileInputStream archivo = new FileInputStream("CursoProfesor.bin");
         ObjectInputStream lectura = new ObjectInputStream(archivo)) {
        
        List<CursoProfesor> listaCursoProfesorRecuperada = (List<CursoProfesor>) lectura.readObject();
        
        System.out.println("\ncargarDatos--> Lista de CursoProfesor leída con éxito:");
        for (CursoProfesor cursoProfesor : listaCursoProfesorRecuperada) {
            if (cursoProfesor.getCurso() != null && cursoProfesor.getProfesor() != null) {
                System.out.println(cursoProfesor);
            }
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
        List<String> cursos = new ArrayList<>();
        for (CursoProfesor cursoProfesor : listado) {
            cursos.add("\n"+cursoProfesor.toString());
        }
        return cursos;
    }
    

}

    
    

