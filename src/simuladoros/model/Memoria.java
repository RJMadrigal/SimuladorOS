package simuladoros.model;

import java.util.LinkedList;
import java.util.Queue;
import simuladoros.view.MainView;

public class Memoria {

    private int capacidad;
    private int uso;
    private Queue<Proceso> procesosEnMemoria;
    public Queue<Proceso> procesosSwap; // Procesos en swap (en el disco)
    private MainView vista;
    

    public Memoria(MainView vista) {
        this.capacidad = 100;  // Capacidad total de memoria en porcentaje
        this.uso = 0;  // Uso actual de la memoria
        this.procesosEnMemoria = new LinkedList<>();
        this.procesosSwap = new LinkedList<>();
        this.vista = vista;
    }

    public int getUso() {
        return uso;
    }

    public void actualizarUso(int cantidad) {
        this.uso += cantidad;
    }

    public void agregarProceso(Proceso p) {
        if (this.uso + p.getMemoria() <= capacidad) {
            // Hay suficiente memoria, agregar el proceso directamente a la memoria
            procesosEnMemoria.add(p);
           // actualizarUso(p.getMemoria());
        } else {
            // No hay suficiente memoria, se necesita swap
            swap(p);
        }
    }

    public void swap(Proceso p) {
        // Mueve el proceso a disco (swap)
        procesosSwap.add(p);
        p.setEstado(Proceso.Estado.BLOQUEADO);
        vista.mostrarMensaje("Proceso " + p.getId() + " movido al disco debido a falta de memoria (swap).");
    }

    public void restaurarDesdeSwap(Proceso p) {
        // Restaura un proceso desde el swap (del disco) a memoria
        if (this.uso + p.getMemoria() <= capacidad) {
            procesosSwap.remove(p);
            procesosEnMemoria.add(p);
            actualizarUso(p.getMemoria());
            p.setEstado(Proceso.Estado.LISTO);
            vista.mostrarMensaje("Proceso " + p.getId() + " devuelto desde el disco hacia memoria.");
        } else {
            vista.mostrarMensaje("No hay suficiente espacio para restaurar el proceso " + p.getId());
        }
    }
}