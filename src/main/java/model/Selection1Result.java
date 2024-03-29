package model;

public class Selection1Result {


    private int c1;
    private double fisher;

    public Selection1Result(int featureNumber, double fisher) {
        this.c1 = featureNumber;
        this.fisher = fisher;
    }

    public double getFisher() {
        return fisher;
    }

    public int getC1() {
        return c1;
    }

    @Override
    public String toString() {
        return "Selection1Result{" +
                "c1=" + c1 +
                ", fisher=" + fisher +
                '}';
    }
}
