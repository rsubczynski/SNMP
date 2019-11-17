package method;

import model.Point;
import model.VectorModel;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class NajblizsziSasiasiedzi {


    public NajblizsziSasiasiedzi(int K, List<VectorModel> testGroups, List<VectorModel> trainingGroups) {
        KNN(K, testGroups, trainingGroups);
    }

    private void KNN(int K, List<VectorModel> testGroups, List<VectorModel> trainingGroups) {

        AtomicInteger result = new AtomicInteger();

        testGroups.forEach(point -> {

                    Map<String, List<String>> pointList = trainingGroups.stream()
                            .map(trainingVector -> new Point(point, trainingVector))
                            .sorted(Comparator.comparingDouble(Point::getDistance))
                            .limit(K)
                            .map(Point::getVectorName)
                            .collect(Collectors.groupingBy(x -> x));

                    int count = pointList.size();

                    String nameTheHigherCounter = pointList.entrySet()
                            .stream()
                            .sorted(Comparator.comparingInt(x -> x.getValue().size()))
                            .skip(count - 1)
                            .findFirst()
                            .get()
                            .getKey();


                    if(point.getName().equals(nameTheHigherCounter)){
                        result.getAndIncrement();
                    }
                }
        );

        float percent = (result.get() * 100.0f) / testGroups.size();
        System.out.println("Najbliźsi sąsiedzi" +": " + result.get() + "/" + testGroups.size() + " " + percent + "%");

    }


}