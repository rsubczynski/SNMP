package method;

import model.KnmHelper;
import model.KnmPoint;
import model.Point;
import model.VectorModel;
import util.Util;

import java.util.*;
import java.util.stream.Collectors;

import static util.Util.showResult;


public class KNM {
    private static List<String> tempGroupNameTable = Arrays.asList("A","B","C");

    public KNM(List<VectorModel> testGroup, List<VectorModel> trainingGroup, int k) {
        List<VectorModel> results = new ArrayList<>();

        Map<String, List<VectorModel>> gropedMap = trainingGroup
                .stream()
                .collect(Collectors.groupingBy(VectorModel::getName));

        List<VectorModel> groupASuccessList;
        List<VectorModel> groupBSuccessList;
        do {
            groupASuccessList = start(k, gropedMap.get("A"));
        }
        while (!(groupASuccessList.size() == k));

        do {
            groupBSuccessList = start(k, gropedMap.get("B"));
        }
        while (!(groupBSuccessList.size() == k));

        results.addAll(groupASuccessList);
        results.addAll(groupBSuccessList);


        List<Point> pointList = testGroup
                .stream()
                .map(point -> results
                        .stream()
                        .map(trainingVector -> new Point(point, trainingVector))
                        .min(Comparator.comparingDouble(Point::getDistance))
                        .get())
                .collect(Collectors.toList());

        showResult("K-NM", testGroup, pointList);

    }

    private List<VectorModel> start(int k, List<VectorModel> group) {
        List<VectorModel> randomPoints = new ArrayList<>();
        List<KnmHelper> knmHelperList = new ArrayList<>();

        // losowanie srodkow
        for (int i = 0; i < k; i++) {
            int index = k <= 1 ? i : randomNumber(group);

            VectorModel randomElement = group.get(index);
            knmHelperList.add(new KnmHelper(randomElement, tempGroupNameTable.get(i)));

        }
        // Przypisanie do grupy pierwszej A lub B
        List<KnmPoint> pierwszeZnalezienieGrup = group.stream()
                .map(item -> knmHelperList
                        .stream()
                        .map(vm -> new KnmPoint(new Point(vm.getHelpVm(), item), vm.getTempolaryGroupName()))
                        .min(Comparator.comparingDouble(KnmPoint::getDistance))
                        .get())
                .collect(Collectors.toList());

        //Podzial na grupy
        Map<String, List<KnmPoint>> grupedK = pierwszeZnalezienieGrup.stream().collect(Collectors.groupingBy(KnmPoint::getName));

        // czyszenie srodkow
        knmHelperList.clear();


        // Oblicznie nowych srodkow
        for (int i=0; i<grupedK.size() ;i++){
           List<VectorModel> noweSrodki = grupedK.get(tempGroupNameTable.get(i))
                   .stream()
                   .map(KnmPoint::getStartVM)
                   .collect(Collectors.toList());

            knmHelperList.add(new KnmHelper(Util.prepareVectorListWithAvg(noweSrodki).get(0), tempGroupNameTable.get(i)));
            randomPoints.add(Util.prepareVectorListWithAvg(noweSrodki).get(0));


        }

        // Przypisanie wedlug nowych srodkow
        List<KnmPoint> drugieDnalezienieGrup = group.stream()
                .map(item -> knmHelperList
                        .stream()
                        .map(vm -> new KnmPoint(new Point(vm.getHelpVm(), item), vm.getTempolaryGroupName()))
                        .min(Comparator.comparingDouble(KnmPoint::getDistance))
                        .get())
                .collect(Collectors.toList());


        int counter = 0;
        // Porównanie
        for(int i = 0; i< group.size(); i++){
            if(pierwszeZnalezienieGrup.get(i).getName().equals(drugieDnalezienieGrup.get(i).getName())){
                counter++;
            }
        }

        return counter == group.size() ? randomPoints : new ArrayList<>();
    }


    private int randomNumber(List<VectorModel> trainingGroup) {
        Random r = new Random();
        int low = 0;
        int high = trainingGroup.size();
        return r.nextInt(high - low) + low;
    }

}
