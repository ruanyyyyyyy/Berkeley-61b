package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;


import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the left bottom is (0,0) and the top right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < n) {
            sb.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return sb.toString();
    }

    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);

        //draw the GUI
        if (!gameOver) {
            Font smallfont = new Font("Arial", Font.BOLD, 20);
            StdDraw.setFont(smallfont);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.textLeft(1, height-1, "Round: "+round);
            StdDraw.text(width/2, height-1, playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(width-1, height-1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height-2, width, height-2);
        }


        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(width/2, height/2, s);
        StdDraw.show();


    }

    public void flashSequence(String letters) {
        for(int i = 0; i < letters.length(); i += 1) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        drawFrame("");
        char keyc;
        StringBuilder sb = new StringBuilder();
        while(sb.length()< n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            keyc = StdDraw.nextKeyTyped();
            sb.append(keyc);
            drawFrame(sb.toString());
        }
        StdDraw.pause(500);
        return sb.toString();
    }

    public void startGame() {
        gameOver = false;
        playerTurn = false;
        round = 1;

        while (!gameOver) {
            String randomS = generateRandomString(round);
            drawFrame("Round" + round + " Good luck!");
            StdDraw.pause(500);
            flashSequence(randomS);
            playerTurn = true;
            String randomPlayer = solicitNCharsInput(round);
            if (!randomS.equals(randomPlayer)) {
                gameOver = true;
                flashSequence("Game Over! You made it to round:" + round);
            }
            round += 1;
            playerTurn = false;
        }

    }

}
