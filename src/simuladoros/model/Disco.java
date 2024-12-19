package simuladoros.model;


public class Disco {
    private double uso;

    public Disco() {
        this.uso = 0;
    }

    public double getUso() {
        return uso;
    }

    public void setUso(double uso) {
        if (uso >= 0 && uso <= 100) {
            this.uso = uso;
        }
    }

    public void actualizarUso(int cantidad) {
        this.uso = Math.min(this.uso + cantidad, 100);
    }

    public void liberarUso(int cantidad) {
        this.uso = Math.max(this.uso - cantidad, 0);
    }
}