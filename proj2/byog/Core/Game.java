package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    private addThings.player player;

    private addThings.Door door;
    private boolean checkWin(){
        if(player.p.equals(door.doorPos)) {
            int WIDTH = 40;
            int HEIGHT = 40;
            Font font = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setXscale(0, WIDTH);
            StdDraw.setYscale(0, HEIGHT);
            StdDraw.clear(Color.BLACK);
            String s = "You win!";
            StdDraw.text(20, 30, s);
            StdDraw.show();
            return true;
        }
        return false;
    }
    private static void drawFrame(){
        int WIDTH = 40;
        int HEIGHT = 40;
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        String s = "CS61B: THE GAME";
        StdDraw.text(20, 30, s);
        Font font2 = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font2);
        String s1 = "NEW GAME (N)";
        String s2 = "LOAD GAME (L)";
        String s3 = "QUIT GAME (Q)";
        StdDraw.text(20,20,s1);
        StdDraw.text(20,18,s2);
        StdDraw.text(20,16,s3);
        StdDraw.show();
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawFrame();
        long seed = 0;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            if (StdDraw.nextKeyTyped() == 'n') {
                StdDraw.clear(Color.BLACK);
                StringBuilder s3 = new StringBuilder("Input a seed:");
                StdDraw.text(20,20, s3.toString());
                StdDraw.show();
                while(true){
                    if (!StdDraw.hasNextKeyTyped()) {
                        continue;
                    }
                    char a = StdDraw.nextKeyTyped();
                    if(a == 's'){
                        break;
                    }
                    if(a - '0' >= 0 && a - '0' <= 9){
                        s3.append(a);
                        StdDraw.clear(Color.BLACK);
                        StdDraw.text(20, 20 ,s3.toString());
                        StdDraw.show();
                        seed = seed * 10 + a - '0';
                    }

                }
            }
            break;
        }

        //replace creatworld
        addThings add = new addThings(seed);
        int WIDTH = 100;
        int HEIGHT = 50;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] ourTiles = new TETile[WIDTH][HEIGHT];
        ter.renderFrame(creatWorld(seed, ourTiles));
        while(true){
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char type = StdDraw.nextKeyTyped();
            if(type == 'w'){
               player.up(ourTiles);
               ter.renderFrame(ourTiles);
               if(checkWin()){
                   break;
               }
            }
            if(type == 'a'){
                player.left(ourTiles);
                ter.renderFrame(ourTiles);
                if(checkWin()){
                    break;
                }
            }
            if(type == 's'){
                player.down(ourTiles);
                ter.renderFrame(ourTiles);
                if(checkWin()){
                    break;
                }
            }
            if(type == 'd'){
                player.right(ourTiles);
                ter.renderFrame(ourTiles);
                if(checkWin()){
                    break;
                }
            }
        }

    }
    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    private TETile[][] creatWorld(long seed, TETile[][] ourTiles){
        addThings add = new addThings(seed);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                ourTiles[x][y] = Tileset.NOTHING;
            }
        }
        addThings.addRandomRooms(ourTiles);
        Room.sortRooms();
        for(int i = 1; i < Room.SizeOfRooms ; i++){
            if(!add.addRandomHallwayRecursion(ourTiles, Room.rooms, i, 1)){
                i--;
            }
        }

        door = new addThings.Door(ourTiles);
        player = new addThings.player(ourTiles);

        return ourTiles;
    }

        public TETile[][] playWithInputString(String input) {
            // TODO: Fill out this method to run the game using the input passed in,
            // and return a 2D tile representation of the world that would have been
            // drawn if the same inputs had been given to playWithKeyboard().


            long seed = Long.parseLong(input.replaceAll("[^0-9]", ""));

            addThings add = new addThings(seed);
            //TETile[][] finalWorldFrame = null;
            int WIDTH = 100;
            int HEIGHT = 50;
            TERenderer ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            TETile[][] ourTiles = new TETile[WIDTH][HEIGHT];
            for (int x = 0; x < WIDTH; x += 1) {
                for (int y = 0; y < HEIGHT; y += 1) {
                    ourTiles[x][y] = Tileset.NOTHING;
                }
            }
            addThings.addRandomRooms(ourTiles);
            Room.sortRooms();
            for(int i = 1; i < Room.SizeOfRooms ; i++){
                if(!add.addRandomHallwayRecursion(ourTiles, Room.rooms, i, 1)){
                    i--;
                }
            }

            door = new addThings.Door(ourTiles);

            ter.renderFrame(ourTiles);
            return ourTiles;
        }
    // test code
    public static void main(String[] args) {
            Game game = new Game();
            game.playWithKeyboard();
    }
}
