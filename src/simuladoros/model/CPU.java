package simuladoros.model;

public class CPU {
    private double uso;
    
    public CPU() {
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
}
