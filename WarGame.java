import javax.swing.*;

public class WarGame extends JFrame {

    public WarGame() {
        GamePanel game = new GamePanel();
        super.setTitle("War of three armies.");
        super.setSize(600, 600);
        super.setResizable(false);
        super.add(game);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);

    }


    public static void main(String[] args){
        new WarGame();
    }
}
