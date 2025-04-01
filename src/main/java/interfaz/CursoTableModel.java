/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Curso;

/**
 *
 * @author VANESA
 */
public class CursoTableModel extends AbstractTableModel {
    private List<Curso> cursos = new ArrayList<>();
    private String[] columnNames = {"ID", "NombreCurso", "Programa", "Activo"};

    // Constructor que recibe la lista de cursos
    public CursoTableModel(List<Curso> cursos) {
        this.cursos = cursos;
    }

    // Método para actualizar los cursos y notificar a la tabla que se debe redibujar
    public void actualizarCursos(List<Curso> cursos) {
    if (cursos == null) {
        System.out.println("ERROR: Lista de cursos está vacía o nula.");
    }
    System.out.println("Actualizando cursos en el modelo...");
    this.cursos = cursos;
    fireTableDataChanged();  // Actualiza la tabla
    
    }

    @Override
    public int getRowCount() {
        return cursos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Curso curso = cursos.get(rowIndex);
        switch (columnIndex) {
            case 0: return curso.getID();
            case 1: return curso.getNombreCurso();
            case 2: return curso.getPrograma().getNombre();
            case 3: return curso.isActivo() ? "Sí" : "No";
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
