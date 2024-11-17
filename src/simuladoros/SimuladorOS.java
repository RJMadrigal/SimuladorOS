package simuladoros;

import simuladoros.view.MainView;

/**
 * Clase principal del proyecto SimuladorOS.
 */
public class SimuladorOS {
    public static void main(String[] args) {
        // Ejecutar la ventana principal en el hilo de eventos de Swing
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Crear y mostrar la ventana principal del simulador
                new MainView().setVisible(true);
            }
        });
    }
}