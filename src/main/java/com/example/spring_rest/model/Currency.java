package com.example.spring_rest.model;public enum Currency {

    USD("United States Dollar", "$"),
    EUR("Euro", "€"),
    UAH("Ukrainian Hryvnia", "₴"),
    GBP("British Pound", "£"),
    CHF("Swiss Franc", "₣");

    private final String fullName;
    private final String symbol;

    Currency(String fullName, String symbol){
        this.fullName = fullName;
        this.symbol = symbol;
    }

    public String getFullName(){
        return fullName;
    }

    public String getSymbol(){
        return symbol;
    }





}
