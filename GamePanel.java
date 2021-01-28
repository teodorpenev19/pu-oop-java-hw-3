import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements MouseListener {
    public Pawn turtle1 = new Pawn();
    public Pawn turtle2 = new Pawn();
    public Pawn[] player1 = new Pawn[4];
    public Pawn[] player2 = new Pawn[4];
    public Square[][] table = new Square[5][5];
    public int squareSize = 80;
    private int turn;
    private int mouseGet = -1;
    private int winner = 0;
    public double mouseXchange;
    public double mouseYchange;

    public GamePanel() {
        turtle1.index = getTurtle1();
        turtle1.x = turtle1.index * squareSize + 120 ;
        turtle1.y = 280;
        turtle2.index = getTurtle2(turtle1.index);
        turtle2.x = turtle2.index * squareSize + 120 ;
        turtle2.y = 280;

        super.setDoubleBuffered(true);
        addMouseListener(this);
        turn = 1;

        for(int i = 0; i < 4; i++) {
            player1[i] = new Pawn();
            player2[i] = new Pawn();
        }
        player1[0].setCoordinates(120,120);
        player1[1].setCoordinates(440,120);
        player1[2].setCoordinates(200,440);
        player1[3].setCoordinates(360, 440);
        player2[0].setCoordinates(200,120);
        player2[1].setCoordinates(360,120);
        player2[2].setCoordinates(120,440);
        player2[3].setCoordinates(440,440);

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                table[i][j] = new Square(100 + j * squareSize,100 + i * squareSize,squareSize);
            }
        }

        for(int i = 0; i < 4; i++) {
            table[(player1[i].y - 100) / squareSize][(player1[i].x - 100) / squareSize].isEmpty = false;
            table[(player2[i].y - 100) / squareSize][(player2[i].x - 100) / squareSize].isEmpty = false;
            player1[i].setLastCoordinates();
            player2[i].setLastCoordinates();
        }

        turtle1.row = (turtle2.y - 100) / squareSize;
        turtle1.column = (turtle1.x - 100) / squareSize;
        turtle2.row = (turtle2.y - 100) / squareSize;
        turtle2.column = (turtle2.x - 100) / squareSize;
        table[turtle1.row][turtle1.column].isEmpty = false;
        table[turtle2.row][turtle2.column].isEmpty = false;
        table[turtle1.row][turtle1.column].isThereTurtle = true;
        table[turtle2.row][turtle2.column].isThereTurtle = true;

        table[0][0].color = Color.RED;
        table[0][4].color = Color.RED;
        table[4][1].color = Color.RED;
        table[4][3].color = Color.RED;

        table[0][1].color = Color.GRAY;
        table[0][3].color = Color.GRAY;
        table[4][0].color = Color.GRAY;
        table[4][4].color = Color.GRAY;

        table[1][0].color = Color.LIGHT_GRAY;
        table[1][1].color = Color.LIGHT_GRAY;
        table[1][3].color = Color.LIGHT_GRAY;
        table[1][4].color = Color.LIGHT_GRAY;
        table[3][0].color = Color.LIGHT_GRAY;
        table[3][1].color = Color.LIGHT_GRAY;
        table[3][3].color = Color.LIGHT_GRAY;
        table[3][4].color = Color.LIGHT_GRAY;

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.WHITE);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 600, 600);
        g2d.fillRect(100, 100, 400, 400);

        g2d.setColor(Color.BLACK);
        Font font = new Font("Tahoma", Font.BOLD, 32);
        g2d.setFont(font);
        g2d.drawString("Turn : ", 100,50);
        if(turn == 1) {
            g2d.setColor(Color.RED);
            g2d.drawString("RED team!",200, 50);
        }
        if(turn == 2) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("BLACK team!",200, 50);
        }

        Color player1Color = new Color(223,164,110);
        Color player2Color = new Color(0,0,0);

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                g2d.setColor(table[i][j].color);
                g2d.fillRect(table[i][j].x,table[i][j].y,table[i][j].size,table[i][j].size);
            }
        }

        g2d.setColor(Color.GRAY);
        g2d.fillOval(280,280,squareSize/2,squareSize/2);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(280,280,squareSize/2,squareSize/2);

        g2d.setColor(Color.BLACK);
        for (int i = 100; i < 500; i = i + squareSize){
            for(int j = 100; j < 500; j = j + squareSize) {
                g2d.drawRect(i , j , squareSize, squareSize);
            }
        }

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(4));
        if(turtle1.isAlive)g2d.drawOval(turtle1.x,turtle1.y, squareSize/2, squareSize/2);
        if(turtle2.isAlive)g2d.drawOval(turtle2.x,turtle2.y, squareSize/2, squareSize/2);

        if(mouseGet == -1){
            for(int i = 0; i < 4; i++) {
                player1[i].changeToLastCoodrinates();
                player2[i].changeToLastCoodrinates();
            }
        }

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();

        if(turn == 1 && mouseGet != -1) {
            player1[mouseGet].setCoordinates(mouseLocation.x - (int)mouseXchange, mouseLocation.y - (int)mouseYchange);
            g2d.setColor(player2Color);

            for(int i = 0; i < 4; i++) {
                if (player2[i].isAlive) {
                    g2d.fillOval(player2[i].x, player2[i].y, squareSize / 2, squareSize / 2);
                }
            }

            g2d.setColor(player1Color);
            for(int i = 0; i < 4; i++) {
                if(player1[i].isAlive) {
                    g2d.fillOval(player1[i].x,player1[i].y,squareSize/2,squareSize/2);
                }
            }
        }

        if(turn == 2 && mouseGet != -1){
            player2[mouseGet].setCoordinates(mouseLocation.x - (int)mouseXchange, mouseLocation.y - (int)mouseYchange);
            g2d.setColor(player1Color);
            for(int i = 0; i < 4; i++) {
                if(player1[i].isAlive) {
                    g2d.fillOval(player1[i].x,player1[i].y,squareSize/2,squareSize/2);
                }
            }
            g2d.setColor(player2Color);
            for(int i = 0; i < 4; i++) {
                if(player2[i].isAlive) {
                    g2d.fillOval(player2[i].x,player2[i].y,squareSize/2,squareSize/2);
                }
            }
        }

        if(mouseGet == -1) {
            g2d.setColor(player1Color);
            for(int i = 0; i < 4; i++) {
                if(player1[i].isAlive) {
                    g2d.fillOval(player1[i].x,player1[i].y,squareSize/2,squareSize/2);
                }
            }
            g2d.setColor(player2Color);
            for(int i = 0; i < 4; i++) {
                if(player2[i].isAlive) {
                    g2d.fillOval(player2[i].x,player2[i].y,squareSize/2,squareSize/2);
                }
            }
        }

        if(winner == 1) {
            font = new Font("Tahoma", Font.BOLD, 20);
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(180, 180, 240, 240);

            g2d.setColor(Color.RED);
            g2d.drawString("RED team win", 230,230);
        }
        if(winner == 2) {
            font = new Font("Tahoma", Font.BOLD, 20);
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(180, 180, 240, 240);

            g2d.setColor(Color.BLACK);
            g2d.drawString("BLACK team win", 230,230);
        }

        repaint();
    }

    public int getTurtle1() {
        Random randomNumber = new Random();
        int randomIndex1 = randomNumber.nextInt(4);
        int turtle1Column = 0;
        if(randomIndex1 == 0){
            turtle1Column = 0;
        }
        if(randomIndex1 == 1){
            turtle1Column = 1;
        }
        if(randomIndex1 == 2){
            turtle1Column = 3;
        }
        if(randomIndex1 == 3){
            turtle1Column = 4;
        }

        return  turtle1Column;

    }

    public int getTurtle2(int turtle1) {
        Random randomNumber;
        int randomIndex2;
        int turtle2Column = 0;
        do {
            randomNumber = new Random();
            randomIndex2 = randomNumber.nextInt(4);
            if(randomIndex2 == 0){
                turtle2Column = 0;
            }
            if(randomIndex2 == 1){
                turtle2Column = 1;
            }
            if(randomIndex2 == 2){
                turtle2Column = 3;
            }
            if(randomIndex2 == 3){
                turtle2Column = 4;
            }
        }while(turtle2Column == turtle1);

        return turtle2Column;
    }




    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(winner != 0){
            System.exit(1);
        }
            if(mouseGet == -1) {
                if(turn == 1) {
                    for(int i = 0; i < 4; i++){
                        if(player1[i].isAlive == false)continue;
                        if(e.getX() >= player1[i].x && e.getX() <= player1[i].x + squareSize/2 && e.getY() >= player1[i].y && e.getY() <= player1[i].y+squareSize/2) {
                            mouseGet = i;
                            player1[i].setLastCoordinates();
                            player1[i].setCoordinates(e.getY() - squareSize / 4,e.getX() - squareSize / 4);
                            break;
                        }
                        else {
                            mouseGet = -1;
                        }
                    }
                }
                else if(turn == 2) {
                    for(int i = 0; i < 4; i++){
                        if(player2[i].isAlive == false)continue;
                        if(e.getX() >= player2[i].x && e.getX() <= player2[i].x + squareSize/2 && e.getY() >= player2[i].y && e.getY() <= player2[i].y+squareSize/2) {
                            mouseGet = i;
                            player2[i].setLastCoordinates();
                            player2[i].setCoordinates(e.getY() - squareSize / 4,e.getX() - squareSize / 4);
                            break;
                        }
                        else {
                            mouseGet = -1;
                        }
                    }
                }
            }
            else {
                if(turn == 1) {
                    if(e.getX() >= 100 && e.getY() >= 100 & e.getX() <= 500 && e.getY() <= 500) {
                        int column = (e.getX()-100) /squareSize;
                        int row = (e.getY()-100) / squareSize;
                        int columnPlayer = (player1[mouseGet].getLastX()-100) /squareSize;
                        int rowPlayer = (player1[mouseGet].getLastY()-100) / squareSize;
                        if(table[row][column].isEmpty == false) {
                            if (table[row][column].isThereTurtle) {
                                if (turtle1.row == row && turtle1.column == column && turtle1.isAlive) {
                                    player1[mouseGet].isAlive = false;
                                    turtle1.isAlive = false;
                                    table[rowPlayer][columnPlayer].isEmpty = true;
                                    table[turtle1.row][turtle1.column].isEmpty = true;
                                    table[turtle1.row][turtle1.column].isThereTurtle = false;
                                    turn = 2;
                                }
                                if (turtle2.row == row && turtle2.column == column && turtle2.isAlive) {
                                    player1[mouseGet].isAlive = false;
                                    turtle2.isAlive = false;
                                    table[rowPlayer][columnPlayer].isEmpty = true;
                                    table[turtle2.row][turtle2.column].isEmpty = true;
                                    table[turtle2.row][turtle2.column].isThereTurtle = false;
                                    turn = 2;
                                }
                                mouseGet = -1;
                            }
                            else {
                                player1[mouseGet].changeToLastCoodrinates();
                                mouseGet = -1;
                            }
                        }

                        else {
                            columnPlayer = (player1[mouseGet].getLastX()-100) /squareSize;
                            rowPlayer = (player1[mouseGet].getLastY()-100) / squareSize;
                            int distanceToNewSquare = Math.abs(row - rowPlayer) + Math.abs(column - columnPlayer);
                            if(distanceToNewSquare == 1)
                            {
                                player1[mouseGet].setCoordinates(100 + column * 80 + squareSize/4, 100 + row * 80 + squareSize/4);
                                turn = 2;
                                table[row][column].isEmpty = false;
                                table[rowPlayer][columnPlayer].isEmpty = true;
                                player1[mouseGet].setLastCoordinates();
                                mouseGet = -1;
                                if(row == 2 && column == 2) {
                                    winner = 1;
                                }
                            }
                            else {
                                player1[mouseGet].changeToLastCoodrinates();
                                mouseGet = -1;
                            }
                        }
                    }
                }
                else if(turn == 2) {
                    if(e.getX() >= 100 && e.getY() >= 100 & e.getX() <= 500 && e.getY() <= 500) {
                        int column = (e.getX()-100) / squareSize;
                        int row = (e.getY()-100) / squareSize;
                        int columnPlayer = (player2[mouseGet].getLastX()-100) /squareSize;
                        int rowPlayer = (player2[mouseGet].getLastY()-100) / squareSize;
                        if(table[row][column].isEmpty == false) {
                            if (table[row][column].isThereTurtle) {
                                if (turtle1.row == row && turtle1.column == column && turtle1.isAlive) {
                                    player2[mouseGet].isAlive = false;
                                    turtle1.isAlive = false;
                                    table[rowPlayer][columnPlayer].isEmpty = true;
                                    table[turtle1.row][turtle1.column].isEmpty = true;
                                    table[turtle1.row][turtle1.column].isThereTurtle = false;
                                    turn = 1;
                                }
                                if (turtle2.row == row && turtle2.column == column && turtle2.isAlive) {
                                    player2[mouseGet].isAlive = false;
                                    turtle2.isAlive = false;
                                    table[rowPlayer][columnPlayer].isEmpty = true;
                                    table[turtle2.row][turtle2.column].isEmpty = true;
                                    table[turtle2.row][turtle2.column].isThereTurtle = false;
                                    turn = 1;
                                }
                                mouseGet = -1;
                            }
                            else {
                                player2[mouseGet].changeToLastCoodrinates();
                                mouseGet = -1;
                            }
                        }

                        else {
                            columnPlayer = (player2[mouseGet].getLastX()-100) /squareSize;
                            rowPlayer = (player2[mouseGet].getLastY()-100) / squareSize;
                            int distanceToNewSquare = Math.abs(row - rowPlayer) + Math.abs(column - columnPlayer);
                            if(distanceToNewSquare == 1) {
                                player2[mouseGet].setCoordinates(100 + column * 80 + squareSize/4, 100 + row * 80 + squareSize/4);
                                turn = 1;
                                table[row][column].isEmpty = false;
                                table[rowPlayer][columnPlayer].isEmpty = true;
                                player2[mouseGet].setLastCoordinates();
                                mouseGet = -1;
                                if(row == 2 && column == 2) {
                                    winner = 2;
                                }
                            }
                            else {
                                player2[mouseGet].changeToLastCoodrinates();
                                mouseGet = -1;
                            }

                        }
                    }
                }
            }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        mouseXchange = mouseLocation.getX() - e.getX();
        mouseYchange = mouseLocation.getY() - e.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
