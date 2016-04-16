package com.roaringcatgames.kitten2d.ashley.components;

public class Range {

    private float min = 0f;
    private float max = 0f;

    private float diff = 0f;

    public float getMin(){
        return min;
    }

    public float getMax(){
        return max;
    }

    public Range(){
        this.min = 0f;
        this.max = 0f;
    }

    public Range(float min, float max){
        this.min = min;
        this.max = max;
        this.calcDiff();
    }

    public void setMin(float m){
        this.min = m;
        this.calcDiff();
    }

    public void setMax(float m){
        this.max = m;
        this.calcDiff();
    }

    public float getBetween(float position){
        if(position > 1f){ position = 1f; }
        if(position < 0f) { position = 0f; }

        if(position == 1f){
            return max;
        }

        if(position == 0f){
            return min;
        }

        float diffPos = position*diff;

        return min+diffPos;

    }

    private void calcDiff(){
        this.diff = max-min;
    }
}