import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class testOFBmode {

    public void main(int[] key, String filetype, String filename) throws IOException{
        Random rand = new Random();
        //int[] key = {10,12,13,14};						//instantiating a key
        OFBmode ofb = new OFBmode(key);					//instantiating a TEA class

        int[] img = new int[2];

        int IV[] = {rand.nextInt(),rand.nextInt()};		//generating a random IV

        FileInputStream imgIn = new FileInputStream("image\\" + filename);
        FileOutputStream imgOut = new FileOutputStream("image\\OFBencrypt" + filetype);

        DataInputStream dataIn = new DataInputStream(imgIn);
        DataOutputStream dataOut = new DataOutputStream(imgOut);

        if (filetype.equals(".bmp")) {
            /* Skipping the first 10 blocks
             * each block is 64 bit. Thus, ReadInt() is applied twice
             * because ReadInt() return 32 bits
             */

            for (int i = 0; i < 10; i++) {
                if (dataIn.available() > 0) {
                    img[0] = dataIn.readInt();
                    img[1] = dataIn.readInt();
                    dataOut.writeInt(img[0]);
                    dataOut.writeInt(img[1]);
                }
            }
        }


        boolean firstTime = true;		//to know when to apply IV or the previous encrypted block
        int cipher[] = new int[2];
        int[] cipherblock = new int[4];
        boolean check = true;			//to catch where the reading from the file is stopped
        while(dataIn.available() > 0){
            try{
                img[0] = dataIn.readInt();
                check = true;
                img[1] = dataIn.readInt();
                if(firstTime){		//if true, the block is passed with IV to be encrypted by TEA algorithm
                    cipherblock = ofb.encrypt(img, IV);

                    firstTime = false;		//set firstTime to false sense IV is only encrypted in the first block
                }
                else
                    System.arraycopy(cipherblock, 0, cipher, 0, 2);
                    cipherblock = ofb.encrypt(img, cipher);		//pass the block with the previous encrypted block

                dataOut.writeInt(cipherblock[2]);
                dataOut.writeInt(cipherblock[3]);
                check = false;
            }catch(EOFException e){				//exception is thrown if the file ends and dataIn.readInt() is executed
                if(!check){						//if false, it means last block were not encrypted
                    dataOut.writeInt(img[0]);
                    dataOut.writeInt(img[1]);
                }else							//if true, it means only last half a block is not encrypted
                    dataOut.writeInt(img[0]);
            }

        }
        dataIn.close();
        dataOut.close();

        /*~~~~~~~~~~~~~~~~~~~~~~~Decrypting the Image ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        DataInputStream dataIn1 = new DataInputStream(new FileInputStream("image\\OFBencrypt" + filetype));
        DataOutputStream dataOut1 = new DataOutputStream(new FileOutputStream("image\\OFBdecrypt" + filetype));

        if (filetype.equals(".bmp")) {
            for (int i = 0; i < 10; i++) {
                if (dataIn1.available() > 0) {
                    img[0] = dataIn1.readInt();
                    img[1] = dataIn1.readInt();
                    dataOut1.writeInt(img[0]);
                    dataOut1.writeInt(img[1]);
                }
            }
        }

        int[] copyCipher = new int[2];
        firstTime = true;
        int plain[] = new int[2];
        int plainblock[] = new int[4];
        check = true;

        while(dataIn1.available() > 0){
            try{
                img[0] = dataIn1.readInt();
                check = true;
                img[1] = dataIn1.readInt();

                if(firstTime){							//if true, the first block is passed with IV to be decrytped
                    plainblock = ofb.encrypt(img, IV);
                    firstTime = false;					//set first time to false
                }else									//if false, the block is passed with the previously encrypted block
                    System.arraycopy(plainblock, 0, plain, 0, 2);
                    plainblock = ofb.encrypt(img, plain);

                dataOut1.writeInt(plainblock[2]);
                dataOut1.writeInt(plainblock[3]);

                copyCipher[0] = img[0];				//Save the previously encryted block in copyCipher to use it
                copyCipher[1] = img[1];

                check = false;
            }catch(EOFException e){
                if(!check){
                    dataOut1.writeInt(img[0]);
                    dataOut1.writeInt(img[1]);
                }else
                    dataOut1.writeInt(img[0]);
            }

        }
        dataIn1.close();
        dataOut1.close();

        imgOut.close();
        imgIn.close();

    }
}
