import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Correlation {
    public double correlation(byte[] input, byte[] output){
        int count = 0;
        int size = input.length;
        for (int i = 0; i < size; i++) {
            int inputByte = input[i] & 0xFF;
            int outputByte = output[i] & 0xFF;
            for (int j = 0; j < 8; j++) {
                int tmpIn = (inputByte >>> j) & 1;
                int tmpOut = (outputByte >>> j) & 1;
                count += (2 * tmpIn - 1) * (2 * tmpOut - 1);
            }
        }
        double N = size * 8;
        return count / N;
    }

    public double count1(byte[] source) {
        int result = 0;
        double correlRes = 0;
        int sourceByte;
        int counter;

        for (int i = 0; i < source.length; i++) {
            sourceByte = source[i] & 0xFF;
            counter = 0;
            for (int j = 0; j < 8; j++) {
                sourceByte = (sourceByte >>> 1);
                if ((sourceByte & 1) == 1) {
                    counter++;
                }
            }
            result += counter;
        }
        return result;
    }

    public double count0(byte[] source) {
        int result = 0;
        double correlRes = 0;
        int sourceByte;
        int counter;

        for (int i = 0; i < source.length; i++) {
            sourceByte = source[i] & 0xFF;
            counter = 0;
            for (int j = 0; j < 8; j++) {
                sourceByte = (sourceByte >>> 1);
                if ((sourceByte & 1) == 0) {
                    counter++;
                }
            }
            result += counter;
        }
        return result;
    }

    public void main(String mode, String filetype, String filename) throws IOException {
        FileInputStream plainfile = new FileInputStream("image\\" + filename);
        FileInputStream encryptedfile = new FileInputStream("image\\" + mode + "encrypt" + filetype);
        DataInputStream dataInCorPlain = new DataInputStream(plainfile);
        DataInputStream dataInCorEncrypted = new DataInputStream(encryptedfile);

        ArrayList<Byte> plainlist = new ArrayList<>();
        ArrayList<Byte> encryptedlist = new ArrayList<>();

        while (dataInCorPlain.available() > 0){
            plainlist.add(dataInCorPlain.readByte());
        }
        byte[] plainbytes = new byte[plainlist.size()];
        for (int i = 0; i != plainlist.size(); i++){
            plainbytes[i] = plainlist.get(i);
        }
        //plainlist.clear();

        while (dataInCorEncrypted.available() > 0){
            encryptedlist.add(dataInCorEncrypted.readByte());
        }
        byte[] encryptedbytes = new byte[encryptedlist.size()];
        for (int i = 0; i != encryptedlist.size(); i++){
            encryptedbytes[i] = encryptedlist.get(i);
        }
        //encryptedlist.clear();

        double cor = correlation(plainbytes, encryptedbytes);
        double zeros = count0(encryptedbytes);
        double ones = count1(encryptedbytes);
        System.out.println("Correlation: " + cor);
        System.out.println("0 in encrypted file: " + zeros);
        System.out.println("1 in encrypted file: " + ones);

        dataInCorPlain.close();
        dataInCorEncrypted.close();
        plainfile.close();
        encryptedfile.close();
    }
}
