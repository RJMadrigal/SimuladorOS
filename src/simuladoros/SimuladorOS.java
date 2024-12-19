package simuladoros;

import simuladoros.view.MainView;

public class SimuladorOS 
{
    public static void main(String[] args) 
    {
        java.awt.EventQueue.invokeLater(() -> {
            new MainView().setVisible(true);
        });
    }
}
