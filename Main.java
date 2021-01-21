import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        //key generation
        ArrayList<Integer> keylist = new ArrayList<Integer>();
        System.out.println("Generate key?(y/n): ");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        try {
            if (answer.equals("y")) {
                Random rand = new Random();
                for (int i = 0; i != 4; i++) {
                    keylist.add(rand.nextInt());
                }
                System.out.println("Now key is:" + keylist.toString());
            } else if (answer.equals("n")) {
                System.out.println("Use default key?(y/n): ");
                answer = in.nextLine();
                if (answer.equals("y")) {
                    keylist.add(10);
                    keylist.add(12);
                    keylist.add(13);
                    keylist.add(14);
                    System.out.println("Now key is:" + keylist.toString());
                } else if (answer.equals("n")) {
                    System.out.println("Enter key (SIZE MUST BE 4!!!): ");
                    for (int i = 0; i != 4; i++) {
                        keylist.add(in.nextInt());
                    }
                    System.out.println("Now key is:" + keylist.toString());
                }

            }
            if (keylist.size() == 0){
                throw new Exception("Key can not be empty!");
            }

            int[] key = new int[keylist.size()]; // keylist.toArray() returns Object[]
            for (int i = 0; i != keylist.size(); i++){
            key[i] = keylist.get(i);
            }

            //file choosing
            String filetype = null;
            String filename = null;
            System.out.println("Test on image of text file? (img/txt): ");
            //answer = in.nextLine(); //programm skips 1 in.nextLine() dont know why
            answer = in.nextLine();
            if (answer.equals("img") || answer.equals("txt")) {
                if (answer.equals("img")) {
                    filetype = ".bmp";
                    System.out.println("Enter file name in image/ folder without .bmp: ");
                    filename = in.nextLine();
                    filename += filetype;
                    System.out.println("Working with: " + filename);
                } else {
                    filetype = ".txt";
                    System.out.println("Enter file name in image/ folder without .txt: ");
                    filename = in.nextLine();
                    filename += filetype;
                    System.out.println("Working with: " + filename);
                }
            } else {
                throw new Exception("Wrong file type!");
            }

            //mode choosing
            System.out.println("Choose mode: \n1.ECB\n2.CBC\n3.CFB\n4.OFB");
            answer = in.nextLine();
            switch (answer) {
                case "1":
                    System.out.println("Working in ECB mode");
                    testECBmode ecb = new testECBmode();
                    ecb.main(key, filetype, filename);
                    break;
                case "2":
                    System.out.println("Working in CBC mode");
                    testCBCmode cbc = new testCBCmode();
                    cbc.main(key, filetype, filename);
                    break;
                case "3":
                    System.out.println("Working in CFB mode");
                    testCFBmode cfb = new testCFBmode();
                    cfb.main(key, filetype, filename);
                    break;
                case "4":
                    System.out.println("Working in OFB mode");
                    testOFBmode ofb = new testOFBmode();
                    ofb.main(key, filetype, filename);
                    break;
            }
            if (answer.length() == 0){
                throw new Exception("Wrong number of mode!");
            }
            System.out.println("All done!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
