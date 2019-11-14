package method;

import model.Selection1Result;
import model.VectorModel;
import modules.FileManager;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SelekcjaCechMet1 {

    private List<VectorModel> selectionLIst = new ArrayList<>();

    public List<Selection1Result> selekcjaCech(int limit) {

        List<VectorModel> allObjectWithAvg = Util.prepareVectorListWithAvg(FileManager.getFileManagerInstance().getAllObjectList());

        Map<String, List<VectorModel>> gropedMap = FileManager.getFileManagerInstance()
                .getAllObjectList()
                .stream()
                .collect(Collectors.groupingBy(VectorModel::getName));


        gropedMap.forEach((s, vectorModels) -> {
                    VectorModel selekcja = new VectorModel(s);

                    List<VectorModel> vectorModelList = gropedMap.get(s);

                    double rank = 0d;
                    Double averageDistance = 0d;
                    for (int count = 0; count < vectorModelList.get(0).getFeatureList().size(); count++) {

                        for (VectorModel item :vectorModelList) {
                            Double rowValue = item.getFeatureList().get(count);
                            averageDistance = allObjectWithAvg.stream().filter(vm -> isThisSameName(vectorModelList, vm)).findFirst().get().getFeatureList().get(count);
                            rank += Math.pow((rowValue - averageDistance), 2);

                        }
                        selekcja.set(averageDistance, Math.sqrt(rank));
                    }
                    selectionLIst.add(selekcja);
                }

        );

        List<Selection1Result> results = new ArrayList<>();
        for (int index = 0; index < selectionLIst.get(0).getFeatureList().size(); index++) {

            double featureMinus = 0;
            double odchylanieSum = 0;
            for (VectorModel vectorModel : selectionLIst) {
                featureMinus = -vectorModel.getFeatureList().get(index);
                odchylanieSum = +vectorModel.getOdchylenie().get(index);
            }
            double fisher = (Math.abs(featureMinus)) / odchylanieSum;
            results.add(new Selection1Result(index, fisher));
        }

        return sortResult(results,limit);
    }

    private List<Selection1Result> sortResult(List<Selection1Result> results, int limit) {
        return results
                .stream()
                .sorted((p1,p2) -> Double.compare(p2.getFisher(), p1.getFisher()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private boolean isThisSameName(List<VectorModel> vectorModelList, VectorModel vm) {
        return vm.getName().equals(vectorModelList.get(0).getName());
    }


}

