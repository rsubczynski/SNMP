package model;

public class Point {

    private String pointParentName;
    private String vectorName;
    private double distance = 0;
    private VectorModel vm;


    public Point(VectorModel point, VectorModel trainingVector) {
        for (int i = 0; i < point.getFeatureList().size(); i++) {
            distance += (Math.sqrt(Math.pow(point.getFeatureList().get(i) - trainingVector.getFeatureList().get(i), 2d)));
        }
        pointParentName = point.getName();
        vectorName = trainingVector.getName();
        this.vm = point;
    }


    public String getVectorName() {
        return vectorName;
    }

    public double getDistance() {
        return distance;
    }

    public VectorModel getVm() {
        return vm;
    }

    public boolean isThisSameName() {
        return pointParentName.equals(vectorName);
    }

    @Override
    public String toString() {
        return "Point{" +
                "pointParentName='" + pointParentName + '\'' +
                ", vectorName='" + vectorName + '\'' +
                ", distance=" + distance +
                ", isThisSameName=" + isThisSameName() +
                '}';
    }
}
