package method;

import model.Point;
import model.VectorModel;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static util.Util.showResult;

public class NajbliższySasiad {

    public NajbliższySasiad(){ }

    public NajbliższySasiad(List<VectorModel> testGroups, List<VectorModel> trainingGroups){
        NN("Najblizszy sasiad",testGroups,trainingGroups);
    }

    void NN(String methodName, List<VectorModel> testGroups, List<VectorModel> trainingGroups) {
        List<Point> pointList = testGroups
                .stream()
                .map(point -> trainingGroups
                        .stream()
                        .map(trainingVector -> new Point(point, trainingVector))
                        .min(Comparator.comparingDouble(Point::getDistance))
                        .get())
                .collect(Collectors.toList());

        showResult(methodName, testGroups, pointList);
    }
}
