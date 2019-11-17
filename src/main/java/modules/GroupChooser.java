package modules;

import model.VectorModel;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupChooser {

    private static GroupChooser groupChooserInstance;
    private List<VectorModel> trainingGroup = new ArrayList<>();
    private List<VectorModel> testGroup = new ArrayList<>();

    private GroupChooser() {
    }

    public static GroupChooser getGroupChooserInstance() {
        if (groupChooserInstance == null) {
            groupChooserInstance = new GroupChooser();
        }
        return groupChooserInstance;
    }

    public void init(int precept) {
        try {
            int filesCount = Util.getPrecentFromAll(precept);

            for (int i = 0; i < filesCount; i++) {
                int result = randomNumber();
                trainingGroup.add(FileManager.getFileManagerInstance().getAllObjectList().get(result));
                FileManager.getFileManagerInstance().getAllObjectList().remove(result);
            }
            testGroup = FileManager.getFileManagerInstance().getAllObjectList();
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }

    private int randomNumber() {
        Random r = new Random();
        int low = 0;
        int high = FileManager.getFileManagerInstance().getAllObjectList().size();
        return r.nextInt(high - low) + low;
    }

    public List<VectorModel> getAllTrainingGroup() {
        return trainingGroup;
    }

    public List<VectorModel> getAllTestGroup() {
        return testGroup;
    }

    public List<VectorModel> getFilteredTrainingGroup(List<Integer> featureIdList) {
        return select(trainingGroup, featureIdList);
    }

    public List<VectorModel> getFilteredTestGroup(List<Integer> featureIdList) {
        return select(testGroup, featureIdList);
    }

    private List<VectorModel> select(List<VectorModel> startObjecsList, List<Integer> featureIdList) {
        List<VectorModel> selectionList = new ArrayList<>();

        if(featureIdList.size() == 0){
            return new ArrayList<>();
        }

        for (VectorModel obj : startObjecsList) {
            VectorModel temp = new VectorModel(obj.getName(), new ArrayList<>());
            for(Integer id : featureIdList){
                temp.setFeature(obj.getFeatureList().get(id));
            }
            selectionList.add(temp);
        }
        return selectionList;
    }




}

