import java.util.*;

public class CFBmode extends TEA{
    public CFBmode(){
        this.key = null;
    }

    public CFBmode(int[] keyAdd){
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

        return block;

    }

    public int[] decrypt(int[] cipherText, int[] previous){
        if(key == null){
            System.out.println("Key is not defined!");
            System.exit(0);
        }

        /* Diving the block into left and right sub blocks */
        int left = previous[0];
        int right = previous[1];

        //sum = DELTA << 5;		//initialize the sum variable
        sum = 0xC6EF3720;

        for(int i=0; i<32;i++){
            right -= ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);
            left -= ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
            sum -= DELTA;
        }

        int block[] = new int[2];
        block[0] = left ^ cipherText[0];
        block[1] = right ^ cipherText[1];

        return block;

    }
}
