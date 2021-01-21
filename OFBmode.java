import java.util.*;

public class OFBmode extends TEA {
    public OFBmode(){
        this.key = null;
    }

    public OFBmode(int[] keyAdd){
        this.key = new int[4];
        key[0] = keyAdd[0];
        key[1] = keyAdd[1];
        key[2] = keyAdd[2];
        key[3] = keyAdd[3];

    }

    public int[] encrypt(int[] plainText, int[] previous){
        //Check if the user defined the key
        if(key == null){
            System.out.println("Key is not defined!");
            System.exit(0);
        }

        /* Diving the block into left and right sub blocks */
        int left = previous[0];
        int right = previous[1];

        sum = 0;		//initialize the sum variable

        for(int i=0; i<32;i++){
            sum += DELTA;
            left += ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
            right += ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);

        }

        int block[] = new int[2];
        block[0] = left ^ plainText[0];
        block[1] = right ^ plainText[1];

        int[] cipherblock = new int[4];
        cipherblock[0] = left;
        cipherblock[1] = right;
        cipherblock[2] = block[0];
        cipherblock[3] = block[1];

        return cipherblock;

    }

    public int[] decrypt(int[] cipherText){
        if(key == null){
            System.out.println("Key is not defined!");
            System.exit(0);
        }

        /* Diving the block into left and right sub blocks */
        int left = cipherText[0];
        int right = cipherText[1];

        sum = DELTA << 5;		//initialize the sum variable

        for(int i=0; i<32;i++){
            right -= ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);
            left -= ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
            sum -= DELTA;
        }

        int block[] = new int[2];
        block[0] = left;
        block[1] = right;

        return block;

    }
}
