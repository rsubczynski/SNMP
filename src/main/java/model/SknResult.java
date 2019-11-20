package model;

public class SknResult {
    private int c1;
    private int c2;
    private double fisher;

    public SknResult(int current, int next, double fisher) {
        this.c1 = current;
        this.c2 = next;
        this.fisher = fisher;
    }

    public Integer getC1() {
        return c1;
    }

    public Integer getC2() {
        return c2;
    }

    public double getFisher() {
        return fisher;
    }
}
