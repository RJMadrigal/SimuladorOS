package simuladoros.controller;

import simuladoros.model.Proceso;
import simuladoros.model.CPU;
import simuladoros.model.Memoria;
import simuladoros.model.DispositivoEntradaSalida;
import java.util.Queue;
import java.util.LinkedList;
import simuladoros.model.Disco;

public class SimuladorController {
    
    private Queue<Proceso> procesos;
    private CPU cpu;
    private Memoria memoria;
    private DispositivoEntradaSalida dispositivos;
    private Disco disco;
    
    public SimuladorController() {
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
