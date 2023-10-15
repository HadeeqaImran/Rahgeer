package com.webapp.Rahgeer.Model;

public class MealPrice {
    private float breakfastPrice;
    private float lunchPrice;
    private float dinnerPrice;

    public MealPrice(float breakfastPrice, float lunchPrice, float dinnerPrice) {
        this.breakfastPrice = breakfastPrice;
        this.lunchPrice = lunchPrice;
        this.dinnerPrice = dinnerPrice;
    }

    public float getBreakfastPrice() {
        return breakfastPrice;
    }

    public void setBreakfastPrice(float breakfastPrice) {
        this.breakfastPrice = breakfastPrice;
    }

    public float getLunchPrice() {
        return lunchPrice;
    }

    public void setLunchPrice(float lunchPrice) {
        this.lunchPrice = lunchPrice;
    }

    public float getDinnerPrice() {
        return dinnerPrice;
    }

    public void setDinnerPrice(float dinnerPrice) {
        this.dinnerPrice = dinnerPrice;
    }
}
