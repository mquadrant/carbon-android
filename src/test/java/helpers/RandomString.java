package helpers;

import java.util.Random;

public class RandomString {

    int range;
    public RandomString(int range){
        this.range = range;
    }

    public String getString(int count){
        String randomString = "";
        for(int i = 0; i<count;i++){
            randomString += randomNumber(range) ;
        }
        return randomString;
    }

    private int randomNumber(int range){
        Random rand = new Random();
        // Obtain a number between [0 - 49].
        return rand.nextInt(range);
    }


}
