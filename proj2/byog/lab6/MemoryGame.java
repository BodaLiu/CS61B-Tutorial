package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;// It should be modified
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

        long seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
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
        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder s = new StringBuilder();
        while(s.length() < n){
            s.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return s.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(20,20,s);
        Font font2 = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font2);
        StdDraw.textLeft(1,height - 1, "Round" + round);
        StdDraw.text(20,height - 1, playerTurn ? "Type!" : "Watch!");
        StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
        StdDraw.line(0,height - 2, width, height - 2);

        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for(int i = 0; i < letters.length(); i++){
            StdDraw.clear();
            StdDraw.show();
            StdDraw.pause(500);
            this.drawFrame(String.valueOf(letters.charAt(i)));
            StdDraw.pause(1000);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder s = new StringBuilder();
        drawFrame(" ");
        while (s.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
        char key = StdDraw.nextKeyTyped();
        s.append(key);
        String a = s.toString();
        this.drawFrame(a);
    }
        StdDraw.pause(500);
        return s.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Game loop
        round = 1;
        while (true) {
            String a = "Round:";
            a += String.valueOf(round);
            playerTurn = false;
            this.drawFrame(a);
            StdDraw.pause(1000);
            String answer = this.generateRandomString(round);
            flashSequence(answer);
            playerTurn = true;
            String player = this.solicitNCharsInput(round);
            if(!answer.equals(player)){
                break;
            }
            StdDraw.pause(1000);
            round++;
        }
        String b = "Game Over! You made it to round:";
        b += String.valueOf(round);
        this.drawFrame(b);
    }

}
