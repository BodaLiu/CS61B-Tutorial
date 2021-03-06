package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class addThings {
    public static Random RANDOM;
    public addThings(long Seed){
        RANDOM = new Random(Seed);
    }
    private static boolean checkXLine(TETile[][] tiles, pos p1, pos p2){
        int smallerX;
        int biggerX;
        if (p1.X <= p2.X) {
            smallerX = p1.X;
            biggerX = p2.X;
        } else {
            smallerX = p2.X;
            biggerX = p1.X;
        }
        for (int times = 1; smallerX + times < biggerX; times++){
            if(tiles[smallerX + times][p1.Y] != Tileset.NOTHING){
                return false;
            }
        }
        return true;
    }
    private static boolean checkXLine(TETile[][] tiles, pos p1, pos p2, TETile T){
        int smallerX;
        int biggerX;
        if (p1.X <= p2.X) {
            smallerX = p1.X;
            biggerX = p2.X;
        } else {
            smallerX = p2.X;
            biggerX = p1.X;
        }
        for (int times = 1; smallerX + times < biggerX; times++){
            if(tiles[smallerX + times][p1.Y] == T){
                return false;
            }
        }
        return true;
    }
    private static boolean checkYLine(TETile[][] tiles, pos p1, pos p2){
        int smallerY;
        int biggerY;
        if (p1.Y <= p2.Y) {
            smallerY = p1.Y;
            biggerY = p2.Y;
        } else {
            smallerY = p2.Y;
            biggerY = p1.Y;
        }
        for (int times = 1; smallerY + times < biggerY; times++){
            if(tiles[p1.X][smallerY + times] != Tileset.NOTHING){
                return false;
            }
        }
        return true;
    }
    private static boolean checkYLine(TETile[][] tiles, pos p1, pos p2, TETile T){
        int smallerY;
        int biggerY;
        if (p1.Y <= p2.Y) {
            smallerY = p1.Y;
            biggerY = p2.Y;
        } else {
            smallerY = p2.Y;
            biggerY = p1.Y;
        }
        for (int times = 1; smallerY + times < biggerY; times++){
            if(tiles[p1.X][smallerY + times] == T){
                return false;
            }
        }
        return true;
    }
    //check if adding this line will cause overlapping
    public static boolean checkAddLine(TETile[][] tiles, pos p1, pos p2, int way) {
        if(p1.X == p2.X || p1.Y == p2.Y){
            return checkXLine(tiles, p2, p1) && checkYLine(tiles, p1, p2);
        }
        pos middlePoint;
        if (way == 1) {
            middlePoint = new pos(p1.X, p2.Y);
            return checkXLine(tiles, p2, middlePoint) && checkYLine(tiles, p1, middlePoint) && tiles[p1.X][p2.Y] == Tileset.NOTHING;
        }
        if (way == 0) {
            middlePoint = new pos(p2.X, p1.Y);
            return checkXLine(tiles, p1, middlePoint) && checkYLine(tiles, p2, middlePoint) && tiles[p2.X][p1.Y] == Tileset.NOTHING;
        }
        return true;
    }
    static boolean checkAddLine(TETile[][] tiles, pos p1, pos p2, int way, TETile T) {
        if(p1.X == p2.X || p1.Y == p2.Y){
            return checkXLine(tiles, p2, p1, T) && checkYLine(tiles, p1, p2, T);
        }
        pos middlePoint;
        if (way == 1) {
            middlePoint = new pos(p1.X, p2.Y);
            return checkXLine(tiles, p2, middlePoint, T) && checkYLine(tiles, p1, middlePoint, T) && tiles[p1.X][p2.Y] != T;
        }
        if (way == 0) {
            middlePoint = new pos(p2.X, p1.Y);
            return checkXLine(tiles, p1, middlePoint, T) && checkYLine(tiles, p2, middlePoint, T) && tiles[p2.X][p1.Y] != T;
        }
        return true;
    }
    static boolean checkContent(TETile[][] tiles, pos p1,pos p2){
        pos leftP = new pos(p1.X + 1, p2.Y - 1);
        pos rightP = new pos(p2.X - 1, p2.Y - 1);
        while(leftP.Y > p1.Y){
            if(!addThings.checkXLine(tiles, leftP, rightP)){
                return false;
            }
            leftP.Y --;
            rightP.Y --;
        }
        return true;
    }
    public static void addStraightXLine(TETile[][] tiles, pos p1, pos p2, TETile T) {
        if (p1.Y != p2.Y) {
            throw new RuntimeException("addStraightXline method must have two position with same Y!");
        }
        int smallerX;
        int biggerX;
        if (p1.X <= p2.X) {
            smallerX = p1.X;
            biggerX = p2.X;
        } else {
            smallerX = p2.X;
            biggerX = p1.X;
        }
        for (int times = 0; smallerX + times <= biggerX; times++){
            tiles[smallerX + times][p1.Y] = T;
        }
    }
    public static void addStraightYLine(TETile[][] tiles, pos p1, pos p2, TETile T) {
        if (p1.X != p2.X) {
            throw new RuntimeException("addStraightXline method must have two position with same Y!");
        }
        int smallerY;
        int biggerY;
        if (p1.Y <= p2.Y) {
            smallerY = p1.Y;
            biggerY = p2.Y;
        } else {
            smallerY = p2.Y;
            biggerY = p1.Y;
        }
        for (int times = 0; smallerY + times <= biggerY; times++){
            tiles[p1.X][smallerY + times] = T;
        }
    }
    // If way == 0, we get a "L"line, else we get a "??" line
    public static void addLine(TETile[][] tiles, pos p1, pos p2, TETile T, int way){
        pos middlePoint;
        /*if(!checkAddLine(tiles, p1, p2, way)){
            return false;
        }*/
        if(way == 1){
            middlePoint = new pos(p1.X, p2.Y);
            addStraightXLine(tiles, p2, middlePoint, T);
            addStraightYLine(tiles, p1, middlePoint, T);
        }
        else{
            middlePoint = new pos(p2.X, p1.Y);
            addStraightXLine(tiles, p1, middlePoint, T);
            addStraightYLine(tiles, p2, middlePoint, T);
        }
    }


    private static boolean addHallway(TETile[][] tiles, pos brick1, pos brick2, int way){
        //add floor
        pos middlePoint;
        if(!addThings.checkAddLine(tiles, brick1, brick2, way)){
            return false;
        }
        addThings.addLine(tiles, brick1, brick2, Tileset.FLOOR, way);

        //another way to add floor
        /*
        if(way == 1){
            middlePoint = new pos(brick1.X,  brick2.Y);
            addThings.addStraightXLine(tiles, brick2, middlePoint, Tileset.FLOOR);
            addThings.addStraightYLine(tiles, brick1, middlePoint, Tileset.FLOOR);
        }
        else{
            middlePoint = new pos( brick2.X, brick1.Y);
            addThings.addStraightXLine(tiles, brick1, middlePoint, Tileset.FLOOR);
            addThings.addStraightYLine(tiles,  brick2, middlePoint, Tileset.FLOOR);
        }*/


        //get nearest brick

        pos[] brick1Neighbors = new pos[2];
        pos[] brick2Neighbors = new pos[2];
        if(tiles[brick1.X][brick1.Y + 1] == Tileset.WALL || tiles[brick1.X][brick1.Y + 1] == Tileset.WAYWALL){
            brick1Neighbors[0] = new pos(brick1.X, brick1.Y + 1);
            brick1Neighbors[1] = new pos(brick1.X, brick1.Y - 1);
        }
        else if(tiles[brick1.X + 1][brick1.Y] == Tileset.WALL || tiles[brick1.X + 1][brick1.Y] == Tileset.WAYWALL){
            brick1Neighbors[0] = new pos(brick1.X + 1, brick1.Y);
            brick1Neighbors[1] = new pos(brick1.X - 1, brick1.Y);
        }

        if(tiles[brick2.X][brick2.Y + 1] == Tileset.WALL || tiles[brick2.X][brick2.Y + 1] == Tileset.WAYWALL){
            brick2Neighbors[0] = new pos(brick2.X, brick2.Y + 1);
            brick2Neighbors[1] = new pos(brick2.X, brick2.Y - 1);
        }
        else if(tiles[brick2.X + 1][brick2.Y] == Tileset.WALL || tiles[brick2.X + 1][brick2.Y] == Tileset.WAYWALL){
            brick2Neighbors[0] = new pos(brick2.X + 1, brick2.Y);
            brick2Neighbors[1] = new pos(brick2.X - 1, brick2.Y);
        }

        //add wall for the hallway
        if(checkAddLine(tiles, brick1Neighbors[0], brick2Neighbors[0], way, Tileset.FLOOR)){
            addThings.addLine(tiles, brick1Neighbors[0], brick2Neighbors[0], Tileset.WAYWALL, way);
            addThings.addLine(tiles, brick1Neighbors[1], brick2Neighbors[1], Tileset.WAYWALL, way);
        }
        else{
            addThings.addLine(tiles, brick1Neighbors[0], brick2Neighbors[1], Tileset.WAYWALL, way);
            addThings.addLine(tiles, brick1Neighbors[1], brick2Neighbors[0], Tileset.WAYWALL, way);
        }
/*
        //check

            System.out.println("brick1Neighbors[0] is");
            System.out.print(brick1Neighbors[0].X);
            System.out.print(' ');
            System.out.print(brick1Neighbors[0].Y);
            System.out.println();
            System.out.println("brick1Neighbors[1] is");
            System.out.print(brick1Neighbors[1].X);
            System.out.print(' ');
            System.out.print(brick1Neighbors[1].Y);
            System.out.println();

            System.out.println();
            System.out.println("brick2Neighbors[0] is");
            System.out.print(brick2Neighbors[0].X);
            System.out.print(' ');
            System.out.print(brick2Neighbors[0].Y);
            System.out.println();
            System.out.println("brick2Neighbors[1] is");
            System.out.print(brick2Neighbors[1].X);
            System.out.print(' ');
            System.out.print(brick2Neighbors[1].Y);
            System.out.println();

*/
        return true;
    }

    public boolean addRandomHallway(TETile[][] ourTiles, Room room1, Room room2){
        pos brick2;
        pos brick1;
        int randomWay;
        int i = 0;
        do {
            if(i >= 200){
                return false;
            }
            int randomNumber1 = RANDOM.nextInt(1000);
            brick1 = room1.getRandomBrick(randomNumber1);
            int randomNumber2 = RANDOM.nextInt(1000);
            brick2 = room2.getRandomBrick(randomNumber2);
            randomWay = RANDOM.nextInt(2);
            i++;
        }
        while (!addHallway(ourTiles, brick1, brick2, randomWay));

        return true;
    }
    public boolean addRandomHallwayRecursion(TETile[][] ourTiles, Room[] rooms, int j, int delta){
        pos brick2;
        pos brick1;
        int randomWay;
        int i = 0;
        if(j - delta < 0){
            rooms[j].deleteRoom(ourTiles);
            return false;
        }
        do {
            if(i == 500){
                break;
            }
            int randomNumber1 = RANDOM.nextInt(1000);
            brick1 = rooms[j].getRandomBrick(randomNumber1);
            int randomNumber2 = RANDOM.nextInt(1000);
            brick2 = rooms[j - delta].getRandomBrick(randomNumber2);
            randomWay = RANDOM.nextInt(2);
            i++;
        }
        while (!addHallway(ourTiles, brick1, brick2, randomWay));
        if(i < 500){
            return true;
        }
        return addRandomHallwayRecursion(ourTiles, rooms, j, delta + 1);
    }
    public static Room addRandomRoom(TETile[][] tiles){
        Room room;
        do{
        int x1 = RANDOM.nextInt(80);
        int y1 = RANDOM.nextInt(37);
        int length = RANDOM.nextInt(7);
        int height = RANDOM.nextInt(7);
        pos p1 = new pos(x1, y1+5);
        pos p2 = new pos(x1 + 2 + length, y1 + 7 + height);
        room= new Room(p1, p2);
        }while(!room.addRoom(tiles));
        room.addInRoom();
        return room;
    }
    public static void addRandomRooms(TETile[][] tiles){
        int ran = RANDOM.nextInt(10);
        for(int i = 1; i <= 25 + ran; i++){
           addRandomRoom(tiles);
        }
    }


    public static class Door {
        pos doorPos;
        public Door(TETile[][] tiles){
            int randomNumber ;
            int num ;
            do{
                randomNumber = RANDOM.nextInt(100);
                num = RANDOM.nextInt(Room.SizeOfRooms);
                doorPos = Room.rooms[num].getRandomBrick(randomNumber);
            }while(tiles[doorPos.X][doorPos.Y] != Tileset.WALL);
            tiles[doorPos.X][doorPos.Y] = Tileset.LOCKED_DOOR;
        }
    }


    public static class player {
        pos p;
        public player(TETile[][] tiles) {
            int num = RANDOM.nextInt(Room.SizeOfRooms);
            int randomX = RANDOM.nextInt(Room.rooms[num].p2.X - Room.rooms[num].p1.X - 1);
            int randomY = RANDOM.nextInt(Room.rooms[num].p2.Y - Room.rooms[num].p1.Y - 1);
            tiles[Room.rooms[num].p1.X + randomX + 1][Room.rooms[num].p1.Y + randomY + 1] = Tileset.PLAYER;
            p = new pos(Room.rooms[num].p1.X + randomX + 1, Room.rooms[num].p1.Y + randomY + 1);
        }
        void up(TETile[][] tiles){
            if(tiles[p.X][p.Y + 1] != Tileset.WALL && tiles[p.X][p.Y + 1] != Tileset.WAYWALL) {
                tiles[p.X][p.Y] = Tileset.FLOOR;
                tiles[p.X][p.Y + 1] = Tileset.PLAYER;
                p.Y ++;
            }
        }

        void down(TETile[][] tiles){
            if(tiles[p.X][p.Y - 1] != Tileset.WALL && tiles[p.X][p.Y - 1] != Tileset.WAYWALL) {
                tiles[p.X][p.Y] = Tileset.FLOOR;
                tiles[p.X][p.Y - 1] = Tileset.PLAYER;
                p.Y --;
            }
        }

        void right(TETile[][] tiles){
            if(tiles[p.X + 1][p.Y] != Tileset.WALL && tiles[p.X + 1][p.Y] != Tileset.WAYWALL) {
                tiles[p.X][p.Y] = Tileset.FLOOR;
                tiles[p.X + 1][p.Y] = Tileset.PLAYER;
                p.X ++;
            }
        }

        void left(TETile[][] tiles){
            if(tiles[p.X - 1][p.Y] != Tileset.WALL && tiles[p.X - 1][p.Y] != Tileset.WAYWALL) {
                tiles[p.X][p.Y] = Tileset.FLOOR;
                tiles[p.X - 1][p.Y] = Tileset.PLAYER;
                p.X --;
            }
        }
    }


    //test method
    public static void main(String[] args) {
        System.out.println();
        //initialize
        int WIDTH = 100;
        int HEIGHT = 50;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] ourTiles = new TETile[WIDTH][HEIGHT];
        addThings add = new addThings(11381495);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                ourTiles[x][y] = Tileset.NOTHING;
            }
        }
        for(int i = 1; i <= 15; i++){
        add.addRandomRoom(ourTiles);
        }

        Room.sortRooms();
        for(int i = 1; i < Room.SizeOfRooms ; i++){
            if(!add.addRandomHallwayRecursion(ourTiles, Room.rooms, i, 1)){
                i--;
            }
        }

        for(int i = 0; i < Room.SizeOfRooms ; i++){
            Room.printRoom(i);
        }

        ter.renderFrame(ourTiles);
    }
}

