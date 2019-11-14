package model;

public class Selection2Result {

    private int c1;
    private int c2;
    private double sumAvg1;
    private double sumAvg2;
    private double determine;


    public Selection2Result(int c1, int c2, double sumaSrednich1, double sumaSrednich2, double determine) {
        this.c1 = c1;
        this.c2 = c2;
        this.sumAvg1 = sumaSrednich1;
        this.sumAvg2 = sumaSrednich2;
        this.determine = determine;
    }

    public int getC1() {
        return c1;
    }

    public int getC2() {
        return c2;
    }

    public double getFisher() {

        if(determine==0){
            return 0;
        }

        double m1 = Math.pow(sumAvg1, 2);
        double m2 = Math.pow(sumAvg2, 2);
        double sqrt = Math.sqrt(m2 + m1);


        return Math.abs(sqrt) / determine;
    }

    @Override
    public String toString() {
        return "Selection2Result{" +
                "c1=" + c1 +
                ", c2=" + c2 +
                ", Fisher=" + getFisher() +
                '}';
    }
}
