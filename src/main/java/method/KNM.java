package method;

import model.Point;
import model.VectorModel;
import util.Util;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KNM {

    public KNM(List<VectorModel> testGroup, List<VectorModel> trainingGroup) {
        double margin = 0.2;
        int successCounter = 0;

        List<Point> x = start(testGroup, trainingGroup);
        List<Point> y = start(testGroup, Util.prepareVectorListWithAvg(trainingGroup));

        for (int i = 0; i < x.size(); i++) {

            double downMargin = y.get(i).getDistance() - (y.get(i).getDistance() * margin);
            double topMargin = y.get(i).getDistance() + (y.get(i).getDistance() * margin);
            double firstDistance = x.get(i).getDistance();

            if (downMargin <= firstDistance && firstDistance <= topMargin)  {
                 successCounter ++;
            }
        }

        float percent = (successCounter * 100.0f) / x.size();
        System.out.println("KNM" + ": " + successCounter + "/" + x.size() + " " + percent + "%");

    }

    private List<Point> start(List<VectorModel> testGroup, List<VectorModel> trainingGroup) {

        return testGroup.stream().map(point -> trainingGroup.stream()
                .map(trainingVector -> new Point(point, trainingVector))
                .min(Comparator.comparingDouble(Point::getDistance))
                .get())
                .collect(Collectors.toList());
    }
}
