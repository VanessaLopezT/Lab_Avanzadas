package interfaz;

import Fabrica_Objetos.FabricaService;
import ObserverPattern.Observador;
import Service.CursoService;
import Service.ProgramaService;
import modelo.Curso;
import modelo.Programa;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentanaCurso extends JFrame implements Observador {
    private CursoTableModel cursoTableModel;
    private JTable tablaCursos;
    private final CursoService cursoService = FabricaService.crearCursoService();
    private final ProgramaService programaService = FabricaService.crearProgramaService();
    private final JTextArea outputArea;

    public VentanaCurso() {
       
        setTitle("Ventana Curso");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);

        // Usar CursoTableModel en lugar de DefaultTableModel
        cursoTableModel = new CursoTableModel(new ArrayList<>());
        tablaCursos = new JTable(cursoTableModel);
        
        JScrollPane scrollPane = new JScrollPane(tablaCursos);
        
       cursoService.cargarCursosss();
        
        
        JPanel panel = new JPanel(new GridLayout(6, 2));
        JTextField idCurso = new JTextField();
        JTextField nombreCurso = new JTextField();
        JTextField idPrograma = new JTextField("603");
        JCheckBox activoCurso = new JCheckBox("Activo", true);
        JButton btnGuardarCurso = new JButton("Guardar Curso");

        btnGuardarCurso.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idCurso.getText().trim());
                String nombre = nombreCurso.getText().trim();
                int idProgramaVal = Integer.parseInt(idPrograma.getText().trim());
                boolean activo = activoCurso.isSelected();

                // Validaciones
                if (nombre.isEmpty()) {
                    mostrarError("Error: El nombre del curso no puede estar vacío.");
                    return;
                }

                Programa programa = programaService.obtenerProgramaPorID(idProgramaVal);
                if (programa == null) {
                    mostrarError("Error: No existe un programa con ese ID en la base de datos.");
                    return;
                }

                // Llamar al servicio para guardar el curso
                cursoService.guardarCursoss(id, nombre, programa, activo);

                // Mostrar en el área de salida que el curso se guardó
                outputArea.append("Curso guardado: " + id + "\n");

                // Aquí es donde debes actualizar la lista de cursos en la tabla
                List<Curso> cursos = cursoService.obtenerTodosLosCursos();  // Obtener los cursos actualizados
                if (cursos == null || cursos.isEmpty()) {
                    mostrarError("No se encontraron cursos actualizados.");
                } else {
                    cursoTableModel.actualizarCursos(cursos);  // Actualizar la tabla
                    outputArea.append("Cursos actualizados correctamente.\n");
                }
            } catch (NumberFormatException ex) {
                mostrarError("Error: Los ID deben ser números enteros.");
            } catch (SQLException ex) {
                mostrarError("Error al guardar el curso: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("ID Curso:"));
        panel.add(idCurso);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreCurso);
        panel.add(new JLabel("ID Programa:"));
        panel.add(idPrograma);
        panel.add(new JLabel("Activo:"));
        panel.add(activoCurso);
        panel.add(btnGuardarCurso);
        panel.add(new JScrollPane(outputArea));
        panel.add(scrollPane);  // Esta línea agrega la tabla al panel
        add(panel);
        cursoService.agregarObservador(this);
        setVisible(true); 
        
    }

    private void mostrarError(String mensaje) {
        outputArea.append("ERROR: " + mensaje + "\n");
    }

    @Override
public void actualizar(List<Curso> cursos) {
    if (cursos == null || cursos.isEmpty()) {
        outputArea.append("Error: No se recibieron cursos.\n");
    } else {
        System.out.println("Actualización de cursos recibida con " + cursos.size() + " cursos.");
        cursoTableModel.actualizarCursos(cursos);  // Este es el método que actualiza la tabla
        outputArea.append("Cursos actualizados correctamente.\n");
    }
}
}
