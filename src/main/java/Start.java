import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Start {

    private JFrame window;

    public Start() {
        this.window = new JFrame("Calculator");
        window.setSize(336, 500);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new Panel());
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Start();
            }
        });
    }
}
