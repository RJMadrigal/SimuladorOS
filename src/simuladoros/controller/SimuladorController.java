package simuladoros.controller;

import simuladoros.model.Proceso;
import simuladoros.model.CPU;
import simuladoros.model.Memoria;
import simuladoros.model.DispositivoEntradaSalida;
import java.util.ArrayList;

public class SimuladorController {
    
    private ArrayList<Proceso> procesos;
    private CPU cpu;
    private Memoria memoria;
    private DispositivoEntradaSalida dispositivos;
    
    public SimuladorController() {
        procesos = new ArrayList<>();
        cpu = new CPU();
        memoria = new Memoria();
        dispositivos = new DispositivoEntradaSalida();
    }
    
    public void agregarProceso(Proceso p) {
        procesos.add(p);
        actualizarRecursos(p);
    }
    
    private void actualizarRecursos(Proceso p) {
        cpu.actualizarUso(p.getCpuUso());
        memoria.actualizarUso(p.getMemoria());
        dispositivos.actualizarUso(10); // usar 10% para dispositivo por cada proceso
    }

    public ArrayList<Proceso> getProcesos() {
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
