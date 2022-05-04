import java.util.Scanner;
/**
 * @author lxg
 * @description 字符串分割
 * @date 2021/9/26
 */
public class Solution {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while(input.hasNextLine()){
            String s = input.nextLine();
            split(s);
        }
    }

    public static void split(String s){
        while(s.length()>=8){
            System.out.println(s.substring(0,8));
            s=s.substring(8);
        }
        if(s.length()<8 && s.length()>0){
            s+="00000000";
            System.out.println(s.substring(0,8));
        }
    }
}
