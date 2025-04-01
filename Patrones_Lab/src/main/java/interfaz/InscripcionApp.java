package interfaz;

import javax.swing.*;


public class InscripcionApp extends JFrame {
    
    
    public InscripcionApp() {
      
    }
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Método main para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InscripcionApp().setVisible(true));
    }
}
