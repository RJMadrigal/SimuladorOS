package simuladoros.model;

public class Proceso {
    
    private final String id;
    private int tiempoEjecucion;
    private int memoria;
    private int cpuUso;
    private int disco;
    
    public Proceso(String id, int tiempoEjecucion, int memoria, int cpuUso, int disco) {
        this.id = id;
        this.tiempoEjecucion = tiempoEjecucion;
        this.memoria = memoria;
        this.cpuUso = cpuUso;
        this.disco = disco;
    }

    public String getId() {
        return id;
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public int getMemoria() {
        return memoria;
    }

    public int getCpuUso() {
        return cpuUso;
    }

    public int getDisco() {
        return disco;
    }

    public void setTiempoEjecucion(int tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

    public void setCpuUso(int cpuUso) {
        this.cpuUso = cpuUso;
    }

    public void setDisco(int disco) {
        this.disco = cpuUso;
    }
}

