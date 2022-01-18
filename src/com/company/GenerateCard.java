package com.company;

import java.util.Random;

public class GenerateCard {
    public static String genCardNum() {

        Random random = new Random(5);
        int iCard = random.nextInt(1000000000);
        String newCAN = String.valueOf(iCard);//customer account number
        if (String.valueOf(iCard).length() < 9) {
            for (int i = String.valueOf(iCard).length(); i < 9; i++) {
                newCAN = "0" + newCAN;
            }
        }
        Random randomK = new Random(1);
        int k = random.nextInt(10);
        String cardNumber = "400000" + newCAN + String.valueOf(k);//BIN+customer account+checksum(??)


        return cardNumber;
    }


    public static String genCardPIN() {
        Random random = new Random(5);
        int iPIN = random.nextInt(10000);
        String newPIN = String.valueOf(iPIN);
        if (String.valueOf(iPIN).length() < 4) {
            for (int i = String.valueOf(iPIN).length(); i < 4; i++) {
                newPIN = "0" + newPIN;
            }
        }
        return newPIN;
    }
}
