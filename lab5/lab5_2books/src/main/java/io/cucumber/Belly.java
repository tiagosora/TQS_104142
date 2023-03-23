package io.cucumber;

public class Belly {
    private int cukesInBelly;

    public Belly() {
        this.cukesInBelly = 0;
    } 

    public void eat(int cukes) {
        this.cukesInBelly = cukes;
        System.out.println("I have " + this.cukesInBelly + " cukes in my belly");
    }
    public void wait(int hours) {
        this.cukesInBelly = 0;
        System.out.println("I wait " + hours + " hour");
    }

    public int getCukesInBelly() {
        return cukesInBelly;
    }
    public void setCukesInBelly(int cukesInBelly) {
        this.cukesInBelly = cukesInBelly;
    }
}
