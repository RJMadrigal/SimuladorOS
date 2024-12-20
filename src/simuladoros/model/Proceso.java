package simuladoros.model;

public class Proceso {

    private final String id;
    private int tiempoEjecucion;
    private int memoria;
    private int cpuUso;
    private int disco;
    private Estado estado;  

    // Enum para los cinco estados
    public enum Estado {
        NUEVO, LISTO, EJECUTANDO, BLOQUEADO, TERMINADO
    }

    public Proceso(String id, int tiempoEjecucion, int memoria, int cpuUso, int disco) {
        this.id = id;
        this.tiempoEjecucion = tiempoEjecucion;
        this.memoria = memoria;
        this.cpuUso = cpuUso;
        this.disco = disco;
        this.estado = Estado.NUEVO;  // Estado inicial
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
