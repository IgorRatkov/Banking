package com.company;

public class BankCard {
    private String cardNumber;
    private String pinCard;
    public  BankCard(String cardNumber,String pinCard){
        this.cardNumber=cardNumber;
        this.pinCard=pinCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPinCard() {
        return pinCard;
    }

    public void setPinCard(String pinCard) {
        this.pinCard = pinCard;
    }
}



/*
        *class Patient {

    private String name;

    public Patient(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }
}

 */