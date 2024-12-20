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
        memoria = new Memoria();
        disco = new Disco();
        dispositivos = new DispositivoEntradaSalida();
    }
    
    public void agregarProceso(Proceso p) {
        procesos.add(p);
        actualizarRecursos(p);
    }
    
    private void actualizarRecursos(Proceso p) {
        if(memoria.getUso() + p.getMemoria() > 100){
            // Si no hay suficiente memoria, mover proceso al disco (swap)
            disco.actualizarUso(p.getMemoria());
        }
        else{
        cpu.actualizarUso(p.getCpuUso());
        memoria.actualizarUso(p.getMemoria());
        dispositivos.actualizarUso(10); // usar 10% para dispositivo por cada proceso
        }
    }
    
    public void eliminarProceso(){
        Proceso p = procesos.poll();
        if(p != null){
            liberarRecursos(p);
        }
    }
    
    private void liberarRecursos(Proceso p) {
        if (disco.getUso() >= p.getMemoria()) {
            // Si el proceso estaba en el disco, liberar espacio en el disco
            disco.liberarUso(p.getMemoria());
        } else {
            // Liberar recursos normalmente
            cpu.actualizarUso(-p.getCpuUso());
            memoria.actualizarUso(-p.getMemoria());
            dispositivos.actualizarUso(-10); // liberar 10% para dispositivo por cada proceso
        }
    }
    
    public void generarProcesoAleatorio() {
        Random rand = new Random();
        String id = "PROCESS" + (procesos.size() + 1);
        int tiempoEjecucion = rand.nextInt(10) + 1; // tiempo de ejecución entre 1 y 10
        int memoria = rand.nextInt(50) + 1; // memoria entre 1 y 50
        int cpuUso = rand.nextInt(50) + 1; // uso de CPU entre 1 y 50
        int disco = rand.nextInt(50) + 1; // uso de disco entre 1 y 50
        Proceso p = new Proceso(id, tiempoEjecucion, memoria, cpuUso, disco);
        agregarProceso(p);
    }

    public void ingresarProcesoManual(String id, int tiempoEjecucion, int memoria, int cpuUso, int disco) {
        Proceso p = new Proceso(id, tiempoEjecucion, memoria, cpuUso, disco);
        agregarProceso(p);
    }

    public void simularProcesos() {
        new Thread(() -> {
            while (!procesos.isEmpty()) {
                Proceso p = procesos.peek();
                if (p != null) {
                    try {
                        vista.mostrarMensaje("Ejecutando proceso: " + p.getId());
                        Thread.sleep(p.getTiempoEjecucion() * 1000); // simula el tiempo de ejecución
                        eliminarProceso();
                        vista.mostrarMensaje("Proceso terminado: " + p.getId());
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
