package method;

import model.Selection2Result;
import model.Selection2MatrixOperation;
import model.VectorModel;
import modules.FileManager;

import java.util.*;
import java.util.stream.Collectors;

public class SelekcjaCechD2 {

    private Map<String, List<VectorModel>> sortedMapItemByGroupName =
            FileManager.getFileManagerInstance().getAllObjectList()
                    .stream()
                    .collect(Collectors.groupingBy(VectorModel::getName));

    private List<Selection2Result> featureSelectionResultList = new ArrayList<>();
    ;

    public SelekcjaCechD2() {
        int furnitureCount = FileManager.getFileManagerInstance().getAllObjectList().get(0).getFeatureList().size();

        for (int i = 0; i < furnitureCount; i++) {
            for (int j = 0; j < furnitureCount; j++) {
                int finalI = i;
                int finalJ = j;
                if (featureSelectionResultList.parallelStream()
                        .noneMatch(x ->
                                (x.getC1() == finalI && x.getC2() == finalJ) || (x.getC1() == finalJ && x.getC2() == finalI))) {
                    featureSelectionResultList.add(call(i, j));
                }

            }
        }
    }


    private Selection2Result call(int c1, int c2) {
        List<Selection2MatrixOperation> pointForSelection = new ArrayList<>();
        double avgC1 = 0;
        double avgC2 = 0;
        double determine = 0;

        Set<String> groupNameList = sortedMapItemByGroupName.keySet();

        for (String groupName : groupNameList) {
            List<Double> furnitureGroupC1List = sortedMapItemByGroupName.get(groupName).stream().map(item -> item.getFeatureList().get(c1)).collect(Collectors.toList());
            List<Double> furnitureGroupC2List = sortedMapItemByGroupName.get(groupName).stream().map(item -> item.getFeatureList().get(c2)).collect(Collectors.toList());
            pointForSelection.add(new Selection2MatrixOperation(groupName, furnitureGroupC1List, furnitureGroupC2List));
        }

        for (Selection2MatrixOperation cechy : pointForSelection) {
            avgC1 -= cechy.getAvgC1();
            avgC2 -= cechy.getAvgC2();
            determine += cechy.det();
        }


        return new Selection2Result(c1, c2, avgC1, avgC2, determine);
    }

    public Selection2Result getSelekcja2Cech() {
        return featureSelectionResultList
                .stream()
                .max((Comparator.comparingDouble(Selection2Result::getFisher)))
                .get();
    }
}
