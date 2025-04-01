package interfaz;

import ObserverPattern.Observador;
import Service.CursoService;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.Curso;
import java.util.List;


public class EstudianteDetalle extends JFrame implements Observador {

    private CursoTableModel cursoTableModel;
    private final JTextArea outputArea;
    private JTable tablaCursos;
    private final CursoService cursoService;

    public EstudianteDetalle() {
        cursoService = new CursoService();

        // Crear el modelo de la tabla usando CursoTableModel
        cursoTableModel = new CursoTableModel(new ArrayList<>());
        tablaCursos = new JTable(cursoTableModel);

        // Hacer que EstudianteDetalle sea un observador del cursoService
        cursoService.agregarObservador(this);

        // Configurar la ventana
        setTitle("Sistema de Inscripciones");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Cursos", crearPanelCursos());
        tabbedPane.addTab("Docentes", crearPanelDocentes());

        // Área de salida (JTextArea) para mensajes o errores
        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);

        // Menú para abrir las ventanas secundarias
        JMenuBar menuBar = new JMenuBar();
        JMenu menuVentanas = new JMenu("Ventanas");
        JMenuItem menuEstudiante = new JMenuItem("Ventana Estudiante");
        JMenuItem menuDocente = new JMenuItem("Ventana Docente");
        JMenuItem menuCurso = new JMenuItem("Ventana Curso");

        // Agregar acciones a los ítems del menú
        menuEstudiante.addActionListener(e -> abrirVentanaEstudiante());
        menuDocente.addActionListener(e -> abrirVentanaDocente());
        menuCurso.addActionListener(e -> abrirVentanaCurso());

        menuVentanas.add(menuEstudiante);
        menuVentanas.add(menuDocente);
        menuVentanas.add(menuCurso);
        menuBar.add(menuVentanas);
        setJMenuBar(menuBar);

        // Agregar los componentes a la ventana principal
        add(tabbedPane, BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);
    }

    // Crear el panel de cursos (con JTable para mostrar en tiempo real)
    private JPanel crearPanelCursos() {
        JPanel panel = new JPanel();

        // Ya no es necesario crear el modelo de la tabla aquí, se hace en el constructor
        panel.add(new JScrollPane(tablaCursos));

        return panel;
    }

    // Crear el panel de docentes (con JTable para mostrar en tiempo real)
    private JPanel crearPanelDocentes() {
        JPanel panel = new JPanel();
        JTable table = new JTable(); // Aquí deberías configurar tu JTable con datos reales
        panel.add(new JScrollPane(table));
        return panel;
    }

    // Métodos para abrir las ventanas secundarias
    private void abrirVentanaEstudiante() {
        VentanaEstudiante ventanaEstudiante = new VentanaEstudiante();
        ventanaEstudiante.setVisible(true);
    }

    private void abrirVentanaDocente() {
        VentanaDocente ventanaDocente = new VentanaDocente();
        ventanaDocente.setVisible(true);
    }

    private void abrirVentanaCurso() {
        VentanaCurso ventanaCurso = new VentanaCurso();
        ventanaCurso.setVisible(true);
    }

    // Método de actualización del observador, se llama cuando se actualizan los cursos
    @Override
    public void actualizar(List<Curso> cursos) {
        if (cursos == null || cursos.isEmpty()) {
            outputArea.append("No se recibieron cursos. Verifique la conexión o la carga de datos.\n");
        } else {
            System.out.println("Actualización de cursos recibida con " + cursos.size() + " cursos.");
            cursoTableModel.actualizarCursos(cursos);
            outputArea.append("Cursos actualizados correctamente.\n");
        }
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EstudianteDetalle ventanaPrincipal = new EstudianteDetalle();
            ventanaPrincipal.setVisible(true);
        });
    }
}
