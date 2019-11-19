package model;

public class KnmPoint {

    private Point point;
    private String name;

    public KnmPoint(Point point, String templarGroupName) {
        this.point = point;
        this.name = templarGroupName;
    }

    public Double getDistance(){
        return point.getDistance();
    }

    public String getName() {
        return name;
    }

    public VectorModel getStartVM(){
        return point.getVm();
    }
}
