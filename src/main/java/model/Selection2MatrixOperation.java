package model;

import org.ejml.simple.SimpleMatrix;

import java.util.List;

public class Selection2MatrixOperation {

    private Double avgC1;
    private Double avgC2;
    private SimpleMatrix matrix;
    private SimpleMatrix tMatrix;


    public Selection2MatrixOperation(List<Double> furnitureGroupC1List, List<Double> furnitureGroupC2List) {
        this.avgC1 = furnitureGroupC1List.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        this.avgC2 = furnitureGroupC2List.stream().mapToDouble(Double::doubleValue).average().getAsDouble();


        matrix = new SimpleMatrix(
                new double[][] {
                        furnitureGroupC1List.stream().mapToDouble(val -> val - avgC1).toArray(),
                        furnitureGroupC2List.stream().mapToDouble(val -> val - avgC2).toArray(),
                }
        );

        tMatrix = matrix.transpose();
    }

    public Double det() {
        return matrix.mult(tMatrix).determinant();
    }

    public Double getAvgC1() {
        return avgC1;
    }

    public Double getAvgC2() {
        return avgC2;
    }
}
