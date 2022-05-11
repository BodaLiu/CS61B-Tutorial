package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    public pos p1;
    public pos p2;
    private int num = 0;
    public ArrayList<pos> WallPos;
    public static Room[] rooms = new Room[40];
    public static int SizeOfRooms = 0;
    public Room(pos p1, pos p2){
        int smallerY;
        int biggerY;
        if (p1.Y < p2.Y) {
            smallerY = p1.Y;
            biggerY = p2.Y;
        }else {
            smallerY = p2.Y;
            biggerY = p1.Y;
        }
        int smallerX;
        int biggerX;
        if (p1.X <= p2.X) {
            smallerX = p1.X;
            biggerX = p2.X;
        } else{
            smallerX = p2.X;
            biggerX = p1.X;
        }
        this.p1 = new pos(smallerX, smallerY);
        this.p2 = new pos(biggerX, biggerY);
        WallPos = new ArrayList<>();
    }
    private void addWall(TETile[][] tiles){
        for (int times = 0; p1.X + times <= p2.X; times++){
            tiles[p1.X + times][p1.Y] = Tileset.WALL;
            if(times != 0 && p1.X + times != p2.X){
                pos p = new pos(p1.X + times, p1.Y);
                WallPos.add(p);
            }
        }
        for (int times = 0; p1.X + times <= p2.X; times++){
            tiles[p1.X + times][p2.Y] = Tileset.WALL;
            if(times != 0 && p1.X + times != p2.X){
                pos p = new pos(p1.X + times, p2.Y);
                WallPos.add(p);
            }
        }
        for (int times = 0; p1.Y + times <= p2.Y; times++){
            tiles[p1.X][p1.Y + times] = Tileset.WALL;
            if(times != 0 && p1.Y + times != p2.Y) {
                pos p = new pos(p1.X, p1.Y + times);
                WallPos.add(p);
            }
        }
        for (int times = 0; p1.Y + times <= p2.Y; times++){
            tiles[p2.X][p1.Y + times] = Tileset.WALL;
            if(times != 0 && p1.Y + times != p2.Y) {
                pos p = new pos(p2.X, p1.Y + times);
                WallPos.add(p);
            }
        }

    }
    //something wrong with random
    public pos getRandomBrick(int randomNumber){
        int randomX = randomNumber % WallPos.size();
        return WallPos.get(randomX);
    }
    public boolean addRoom(TETile[][] tiles){
        //check
        if(!(addThings.checkAddLine(tiles, p1, p2, 1) && addThings.checkAddLine(tiles, p1, p2, 0))){
            return false;
        }
        //add walls
        if (p1.Y == p2.Y || p1.X == p2.X) {
            return false;}
        this.addWall(tiles);

        //fill the room with floor
        pos leftP = new pos(p1.X + 1, p2.Y - 1);
        pos rightP = new pos(p2.X - 1, p2.Y - 1);
        while(leftP.Y > p1.Y){
            addThings.addStraightXLine(tiles, leftP, rightP, Tileset.FLOOR);
            leftP.Y --;
            rightP.Y --;
        }
        return true;
    }
    private static int getDistanceSquare(Room r1, Room r2){
        int distanceSquare = (r1.p2.X - r2.p1.X) * (r1.p2.X - r2.p1.X) + (r1.p2.Y - r2.p1.Y) * (r1.p2.Y - r2.p1.Y);
        return distanceSquare;
    }

    public static void sortRooms(){
     Room[] sortedRoom = new Room[40];
     //get smallestXI
     int smallestXI = 0;
     int smallestX = 100;
     for(int i = 0; i < SizeOfRooms; i++){
         if(smallestX > rooms[i].p1.X){
             smallestX = rooms[i].p1.X;
             smallestXI = i;
         }
     }
     sortedRoom[0] = rooms[smallestXI];
        sortedRoom[0].num = 0;
     rooms[smallestXI] = null;

     for(int i = 0; i < SizeOfRooms - 1; i++) {
         int smallestDj = 0;
         int smallestD = 10000;
         for(int j = 0; j < SizeOfRooms; j++) {
             if(rooms[j] == null){
                 continue;
             }
            if(getDistanceSquare(sortedRoom[i], rooms[j]) < smallestD){
                smallestDj = j;
                smallestD = getDistanceSquare(sortedRoom[i], rooms[j]);
            }
         }
         sortedRoom[i+1] = rooms[smallestDj];
         sortedRoom[i+1].num = i + 1;
         rooms[smallestDj] = null;
     }
         rooms = sortedRoom;
    }

    public void addInRoom(){
        rooms[SizeOfRooms] = this;
        num = SizeOfRooms;
        SizeOfRooms++;
    }
    public static void printRoom(int i){
        System.out.println("rooms[" + i + "].p1 is");
        System.out.print(Room.rooms[i].p1.X);
        System.out.print(' ');
        System.out.print(Room.rooms[i].p1.Y);
        System.out.println();
        System.out.println("rooms[" + i + "].p2 is");
        System.out.print(Room.rooms[i].p2.X);
        System.out.print(' ');
        System.out.print(Room.rooms[i].p2.Y);
        System.out.println();
        System.out.println(Room.rooms[i].num);
    }
    public static void deleteStraightXLine(TETile[][] tiles, pos p1, pos p2) {
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
            if(tiles[smallerX + times][p1.Y] != Tileset.WAYWALL) {
                tiles[smallerX + times][p1.Y] = Tileset.NOTHING;
            }
        }
    }
    public static void deleteStraightYLine(TETile[][] tiles, pos p1, pos p2) {
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
            if(tiles[p1.X][smallerY + times] != Tileset.WAYWALL) {
                tiles[p1.X][smallerY + times] = Tileset.NOTHING;
            }
        }
    }

    private static void deleteLine(TETile[][] tiles, pos p1, pos p2, int way){
        pos middlePoint;
        /*if(!checkAddLine(tiles, p1, p2, way)){
            return false;
        }*/
        if(way == 1){
            middlePoint = new pos(p1.X, p2.Y);
            deleteStraightXLine(tiles, p2, middlePoint);
            deleteStraightYLine(tiles, p1, middlePoint);
        }
        else{
            middlePoint = new pos(p2.X, p1.Y);
            deleteStraightXLine(tiles, p1, middlePoint);
            deleteStraightYLine(tiles, p2, middlePoint);
        }
    }
    public void deleteRoom(TETile[][] tiles){
        deleteLine(tiles,p1,p2,0);
        deleteLine(tiles,p1,p2,1);
        pos leftP = new pos(p1.X + 1, p2.Y - 1);
        pos rightP = new pos(p2.X - 1, p2.Y - 1);
        while(leftP.Y > p1.Y){
            deleteStraightXLine(tiles, leftP, rightP);
            leftP.Y --;
            rightP.Y --;
        }
        for(int i = 0; i + this.num < SizeOfRooms - 1; i++){
            rooms[i + this.num] = rooms[i + this.num + 1];
            rooms[i+this.num].num = i + this.num;
        }
        rooms[SizeOfRooms - 1] = null;
        SizeOfRooms--;
    }

    //test
    public static void main(String[] args) {
        pos position7 = new pos(20, 20);
        pos position8 = new pos(23, 23);
        Room room4 = new Room(position7, position8);
        room4.addInRoom();

        pos position1 = new pos(5, 5);
        pos position2 = new pos(8, 8);
        Room room1 = new Room(position1, position2);
        room1.addInRoom();

        pos position3 = new pos(13, 13);
        pos position4 = new pos(10, 10);
        Room room2 = new Room(position3, position4);
        room2.addInRoom();

        pos position5 = new pos(1, 1);
        pos position6 = new pos(4, 4);
        Room room3 = new Room(position5, position6);
        room3.addInRoom();


        sortRooms();

        for(int i = 0; i < SizeOfRooms; i++){
            System.out.println(rooms[i].p1.X);
        }
       // System.out.println(rooms[2].p1.X);
    }
}
