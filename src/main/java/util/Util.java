package util;

import model.Point;
import model.VectorModel;
import modules.FileManager;

import java.util.*;
import java.util.stream.Collectors;


public class Util {

    public static int getPrecentFromAll(int percent) throws ArithmeticException {
        if (percent > 95 || percent < 0) {
            throw new ArithmeticException("Bad precent value");
        }
        return (int) ((FileManager.getFileManagerInstance().getAllObjectList().size() / 100.0f) * percent);
    }

    public static void showResult(String methodName, List<VectorModel> testGroups, List<Point> pointList) {
        long countSuccess = pointList.stream().filter(Point::isThisSameName).count();
        float percent = (countSuccess * 100.0f) / testGroups.size();
        System.out.println(methodName + ": " + countSuccess + "/" + testGroups.size() + " " + percent + "%");
    }

    public static List<VectorModel> prepareVectorListWithAvg(List<VectorModel> trainingGroup) {
        List<VectorModel> newTrainingGroup = new ArrayList<>();

        Map<String, List<VectorModel>> sortedMap = trainingGroup
                .stream()
                .sorted(Comparator.comparing(VectorModel::getName))
                .collect(Collectors.groupingBy(VectorModel::getName));

        sortedMap.forEach((k, v) -> {
            List<VectorModel> groupListItems = sortedMap.get(k);
            newTrainingGroup.add(new VectorModel(groupListItems.get(0).getName(), Util.listFeatureWithAvg(groupListItems)));
        });

        return newTrainingGroup;
    }

    public static List<Double> listFeatureWithAvg(List<VectorModel> groupListItems) {
        List<Double> resultList = new ArrayList<>();
        int fetureCount = groupListItems.get(0).getFeatureList().size();

        for (int i = 0; i < fetureCount; i++) {
            double count = 0;
            for (VectorModel groupListItem : groupListItems) {
                count += groupListItem.getFeatureList().get(i);
            }
            resultList.add(count / groupListItems.size());
        }

        return resultList;
    }

}


