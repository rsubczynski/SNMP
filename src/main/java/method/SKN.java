package method;

import model.Selection1Result;
import model.SknResult;
import model.VectorModel;
import modules.FileManager;
import org.ejml.simple.SimpleMatrix;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SKN {

    public List<Integer> call() {

        int size = FileManager.getFileManagerInstance().getAllObjectList().get(0).getFeatureList().size();

        List<Selection1Result> selection1Result = new FisherSelekcjaCechD1().selekcjaCech(1);
        List<Integer> featureList1 = selection1Result.stream().map(Selection1Result::getC1).collect(Collectors.toList());
        List<SknResult> sknResultList = new ArrayList<>();

        Map<String, List<VectorModel>> gropedMap = FileManager.getFileManagerInstance().getAllObjectList()
                .stream()
                .collect(Collectors.groupingBy(VectorModel::getName));

        List<VectorModel> grupedA = gropedMap.get("A");
        List<VectorModel> grupedB = gropedMap.get("B");

        int wynik = featureList1.get(0);

        for (int i = 0; i < size-1; i++) {
            int current = i;
            int next = i + 1;
            if (wynik == current) {
                current = current - 1;
                sknResultList.add(calc(next, current, grupedA, grupedB));
            } else {
                sknResultList.add(calc(next, current, grupedA, grupedB));
            }
        }

        SknResult max = sknResultList.stream().max(Comparator.comparingDouble(SknResult::getFisher)).get();

        return Stream.of(featureList1.get(0), max.getC1(), max.getC2()).collect(Collectors.toList());
    }

    private SknResult calc(int current, int next, List<VectorModel> grupedA, List<VectorModel> grupedB) {
        List<Double> a1Feature = new ArrayList<>();
        List<Double> a2Feature = new ArrayList<>();

        List<Double> b1Feature = new ArrayList<>();
        List<Double> b2Feature = new ArrayList<>();

        for (VectorModel vectorModel : grupedA) {
            a1Feature.add(vectorModel.getFeatureList().get(current));
            a2Feature.add(vectorModel.getFeatureList().get(next));
        }

        for (VectorModel vectorModel : grupedB) {
            b1Feature.add(vectorModel.getFeatureList().get(current));
            b2Feature.add(vectorModel.getFeatureList().get(next));
        }

        Double a1FeatureAvg = a1Feature.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        Double a2FeatureAvg = a2Feature.stream().mapToDouble(Double::doubleValue).average().getAsDouble();

        Double b1FeatureAvg = b1Feature.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        Double b2FeatureAvg = b2Feature.stream().mapToDouble(Double::doubleValue).average().getAsDouble();

        double dlugosc = Math.sqrt(Math.pow(a1FeatureAvg - a2FeatureAvg, 2d) + Math.pow(b1FeatureAvg - b2FeatureAvg, 2d));

        SimpleMatrix matrixA = new SimpleMatrix(
                new double[][]{
                        a1Feature.stream().mapToDouble(val -> val - a1FeatureAvg).toArray(),
                        a2Feature.stream().mapToDouble(val -> val - a2FeatureAvg).toArray(),
                }
        );

        SimpleMatrix matrixB = new SimpleMatrix(
                new double[][]{
                        b1Feature.stream().mapToDouble(val -> val - b1FeatureAvg).toArray(),
                        b2Feature.stream().mapToDouble(val -> val - b2FeatureAvg).toArray(),
                }
        );


        double detA = matrixA.mult(matrixA.transpose()).determinant();

        double detB = matrixB.mult(matrixB.transpose()).determinant();

        double fisher = dlugosc / (detA + detB);

        return new SknResult(current, next, fisher);
    }


    public void callForOnePoint(){

    }
}
