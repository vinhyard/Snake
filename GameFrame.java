import javax.swing.JFrame;

public class GameFrame extends JFrame{
    GameFrame() {

        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        //take jframe and fit in all components added to frame
        this.pack();
        this.setVisible(true);
        // window set to middle of computer
        this.setLocationRelativeTo(null);
    }
}
