import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> keylist = new ArrayList<Integer>();
        System.out.println("Generate key?(y/n): ");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        if (answer.equals("y")){
            System.out.println("Enter key size: ");
            int size = in.nextInt();
            Random rand = new Random();
            for (int i = 0; i != size; i++){
                keylist.add(rand.nextInt());
            }
            System.out.println("Now key is:" + keylist.toString());
        } else if (answer.equals("n")){
            System.out.println("Use default key?(y/n): ");
            answer = in.nextLine();
            if (answer.equals("y")){
                keylist.add(10);
                keylist.add(12);
                keylist.add(13);
                keylist.add(14);
                System.out.println("Now key is:" + keylist.toString());
            } else if (answer.equals("n")){
                System.out.println("Enter key size: ");
                int size = in.nextInt();
                System.out.println("Enter key: ");
                for (int i = 0; i != size; i++){
                    keylist.add(in.nextInt());
                }
                System.out.println("Now key is:" + keylist.toString());
            }

        }
        String testfile;
        System.out.println("Test on image of text file? (img/txt): ");
        switch (answer){
            case "img":
                testfile = "img";
                break;
            case "txt":
                testfile = "txt";
                break;
        }

        System.out.println("Choose mode: \n1.ECB\n2.CBC\n3.CFB\n4.OFB" );
        answer = in.nextLine();
        switch (answer) {
            case "1":

                break;
            case "2":

                break;
            case "3":

                break;
            case "4":

                break;
        }
    }
}
