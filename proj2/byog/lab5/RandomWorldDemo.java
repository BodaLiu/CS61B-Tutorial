package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world that contains RANDOM tiles.
 */
public class RandomWorldDemo {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;

    private static final long SEED = 287312;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     * @param tiles
     */
    public static void fillWithRandomTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = randomTile();
            }
        }
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(7);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.FLOOR;
            case 3: return Tileset.WATER;
            case 4: return Tileset.GRASS;
            case 5: return Tileset.MOUNTAIN;
            case 6: return Tileset.TREE;
            case 7: return Tileset.SAND;
            default: return Tileset.NOTHING;
        }
    }
    public static class pos {
        public int X;
        public int Y;
        pos(int X,int Y){
            this.X = X;
            this.Y = Y;
        }
    }

    //return the position of the top right neighbor of certain hexagon
    private static pos topLeftNeighbor(pos p,int size){
        int x = p.X - (2 * size - 1);
        int y = p.Y + size;
        pos result = new pos(x, y);
        return result;
    }

    private static pos bottomLeftNeighbor(pos p,int size){
        int x = p.X - (2 * size - 1);
        int y = p.Y - size;
        pos result = new pos(x, y);
        return result;
    }
    private static void addLength(TETile[][] tiles, int posX, int posY, int length, int maxSize, TETile T){
          int x = (maxSize - length)/2 + 1;
          /**for (; x<= (maxSize - length)/2; x++){
              tiles[posX + x][posY] = Tileset.NOTHING;
          }*/
          for(; x <= (maxSize + length)/2; x++){
              tiles[posX + x][posY] = T;
          }
          /**for(; x <= maxSize; x++){
              tiles[posX + x][posY] = Tileset.NOTHING;
          }*/
    }
    public static void addHexagon(TETile[][] tiles, pos p, int size){
         int maxSize = size + (size - 1) * 2;
         TETile T = randomTile();
         for(int i = 0; i < size ; i++){
             addLength(tiles, p.X, p.Y - i, size + 2 * i, maxSize, T);
         }
         for(int i = 0; i < size ; i++){
             addLength(tiles, p.X, p.Y - size - i, maxSize - 2 * i, maxSize, T);
         }
    }

    //print N hexagons
    public static void addNHexagon(TETile[][] tiles, pos p, int size, int num){
        pos temp = new pos(p.X, p.Y);
        for(int i = 1; i <= num; i++){
            addHexagon(tiles, temp, size);
            temp.Y -= 2 * size;
        }
    }
    public static void main(String[] args) {
        //initialize
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                randomTiles[x][y] = Tileset.NOTHING;
            }
        }
        //add hexagon
        pos position1 = new pos(70,40);
        addNHexagon(randomTiles, position1,3, 3);
        pos position2 = topLeftNeighbor(position1, 3);
        addNHexagon(randomTiles, position2, 3, 4);
        pos position3 = topLeftNeighbor(position2, 3);
        addNHexagon(randomTiles, position3, 3, 5);
        pos position4 = bottomLeftNeighbor(position3, 3);
        addNHexagon(randomTiles, position4,3, 4);
        pos position5 = bottomLeftNeighbor(position4, 3);
        addNHexagon(randomTiles, position5, 3, 3);

        ter.renderFrame(randomTiles);

    }


}
