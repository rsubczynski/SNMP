package method;

import model.Selection1Result;
import model.VectorModel;
import modules.FileManager;
import util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SelekcjaCechD1 {

    public List<Selection1Result> selekcjaCech(int limit) {


        Map<String, List<VectorModel>> gropedMap = FileManager.getFileManagerInstance()
                .getAllObjectList()
                .stream()
                .collect(Collectors.groupingBy(VectorModel::getName));

        List<VectorModel> groupA = gropedMap.get("A");
        List<VectorModel> groupB = gropedMap.get("B");

        return calc(groupA,groupB).stream()
                .sorted(Comparator.comparingDouble(Selection1Result::getFisher).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private List<Selection1Result> calc(List<VectorModel> groupA, List<VectorModel> groupB) {
        List<Selection1Result> tempList = new ArrayList<>();

        for(int i=0; i<groupA.get(0).getFeatureList().size(); i++) {
            int finalI = i;
            double valueA = 0L;
            double valueB = 0L;


            Double avgGroup1Feature = groupA.stream().mapToDouble(item -> item.getFeatureList().get(finalI)).average().getAsDouble();
            Double avgGroup2Feature = groupA.stream().mapToDouble(item -> item.getFeatureList().get(finalI)).average().getAsDouble();

            for (VectorModel vectorModel : groupA) {
                valueA += Math.pow(vectorModel.getFeatureList().get(finalI) - avgGroup1Feature, 2);
            }

            for (VectorModel vectorModel : groupB) {
                valueB += Math.pow(vectorModel.getFeatureList().get(finalI) - avgGroup2Feature, 2);
            }

            double dot1 = Math.sqrt(valueA);
            double dot2 = Math.sqrt(valueB);

            double fisher = (Math.abs(avgGroup1Feature - avgGroup2Feature)) / dot1 + dot2;
            fisher = Double.isNaN(fisher) ? fisher : 0;
            tempList.add(new Selection1Result(finalI,fisher));

        }

        return tempList;
    }

}

