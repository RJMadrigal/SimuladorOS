package simuladoros.controller;

import simuladoros.model.Proceso;
import simuladoros.model.CPU;
import simuladoros.model.Memoria;
import simuladoros.model.DispositivoEntradaSalida;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import simuladoros.model.Disco;
import simuladoros.view.MainView;

public class SimuladorController {

    private Queue<Proceso> procesos;
    private CPU cpu;
    private Memoria memoria;
    private DispositivoEntradaSalida dispositivos;
    private Disco disco;
    private MainView vista;

    public SimuladorController(MainView vista) {
        this.vista = vista;
        procesos = new LinkedList<>();
        cpu = new CPU();
        memoria = new Memoria(vista);
        disco = new Disco();
        dispositivos = new DispositivoEntradaSalida();
    }

    public synchronized void agregarProceso(Proceso p) {
        p.setEstado(Proceso.Estado.LISTO);
        procesos.add(p);
        memoria.agregarProceso(p);  // Gestiona el proceso en memoria o swap
        actualizarRecursos(p);
    }

    private synchronized void actualizarRecursos(Proceso p) {
    if (memoria.getUso() + p.getMemoria() <= 100) {
        // Si hay suficiente memoria, el proceso se mantiene en memoria
        cpu.actualizarUso(p.getCpuUso());
        memoria.actualizarUso(p.getMemoria());
        dispositivos.actualizarUso(10);
        p.setEstado(Proceso.Estado.LISTO);
    } else {
        // Si no hay suficiente memoria, mover el proceso al disco
        if (disco.getUso() + p.getDisco() <= 100) {
            p.setEstado(Proceso.Estado.BLOQUEADO);
            disco.actualizarUso(p.getDisco());
            vista.mostrarMensaje("Proceso " + p.getId() + " movido al disco por falta de memoria.");
        } else {
            // Si tampoco hay suficiente espacio en el disco, bloquear el proceso
            p.setEstado(Proceso.Estado.BLOQUEADO);
            vista.mostrarMensaje("Proceso " + p.getId() + " bloqueado por falta de recursos.");
        }
    }
}

    public synchronized void eliminarProceso() {
        Proceso p = procesos.poll();
        if (p != null) {
            liberarRecursos(p);
            p.setEstado(Proceso.Estado.TERMINADO);
            vista.mostrarMensaje("Proceso terminado: " + p.getId() + " | Estado: " + p.getEstado());
        }
    }

    private synchronized void liberarRecursos(Proceso p) {
        if (disco.getUso() >= p.getDisco()) {
            disco.liberarUso(p.getDisco());
            vista.mostrarMensaje("Espacio en disco liberado para el proceso " + p.getId());

        }
        cpu.actualizarUso(-p.getCpuUso());
        memoria.actualizarUso(-p.getMemoria());
        dispositivos.actualizarUso(-10);
    }

    public void generarProcesoAleatorio() {
        Random rand = new Random();
        String id = "P" + (procesos.size() + 1);
        int tiempoEjecucion = rand.nextInt(5) + 1;
        int memoria = rand.nextInt(50) + 1;
        int cpuUso = rand.nextInt(50) + 1;
        int disco = memoria;
        Proceso p = new Proceso(id, tiempoEjecucion, memoria, cpuUso, disco);
        agregarProceso(p);
    }

    public void ingresarProcesoManual(String id, int tiempoEjecucion, int memoria, int cpuUso) {
        Proceso p = new Proceso(id, tiempoEjecucion, memoria, cpuUso, memoria);
        agregarProceso(p);
    }

    public void simularProcesos() {
        new Thread(() -> {
            while (!procesos.isEmpty()) {
                Proceso p = procesos.peek();
                if (p != null) {
                    try {
                        p.setEstado(Proceso.Estado.LISTO);
                        vista.mostrarMensaje("Proceso en estado: " + p.getEstado() + " | " + p.getId());

                        p.setEstado(Proceso.Estado.EJECUTANDO);
                        vista.mostrarMensaje("Ejecutando proceso: " + p.getId() + " | Estado: " + p.getEstado());

                        for (int i = 0; i < p.getTiempoEjecucion(); i++) {
                            // Simula el uso del CPU por segundo
                            Thread.sleep(1000);
                            int tiempoRestante = p.getTiempoEjecucion() - (i + 1);
                            vista.mostrarMensaje("Tiempo restante para " + p.getId() + ": " + tiempoRestante + "s");
                        }

                        p.setEstado(Proceso.Estado.TERMINADO);
                        vista.mostrarMensaje("Proceso terminado: " + p.getId() + " | Estado: " + p.getEstado());

                        eliminarProceso();

                        // Revisar si hay procesos bloqueados que puedan ser restaurados
                        for (Proceso procesoEnSwap : memoria.procesosSwap) {
                            memoria.restaurarDesdeSwap(procesoEnSwap);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public Disco getDisco() {
        return disco;
    }

    public Queue<Proceso> getProcesos() {
        return procesos;
    }

    public CPU getCpu() {
        return cpu;
    }

    public Memoria getMemoria() {
        return memoria;
    }

    public DispositivoEntradaSalida getDispositivos() {
        return dispositivos;
    }
}
