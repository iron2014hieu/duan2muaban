package com.example.duan2muaban.nighmode_vanchuyen.vanchuyen;

public class CountryItem {
    private String mCountryName;
    private String mPrice;
    private int mFlagImage;

    public CountryItem(String countryName, String price,  int flagImage) {
        mCountryName = countryName;
        mPrice = price;
        mFlagImage = flagImage;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public int getFlagImage() {
        return mFlagImage;
    }
}
