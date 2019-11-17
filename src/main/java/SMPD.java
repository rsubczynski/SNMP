import method.*;
import model.Selection1Result;
import model.Selection2Result;
import model.VectorModel;
import modules.GroupChooser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SMPD {

    SMPD(int percentCountFromFileToLearn) {


        List<Selection1Result> selection1Result = new SelekcjaCechD1().selekcjaCech(5);
        List<Integer> featureList1 = selection1Result.stream().map(Selection1Result::getC1).collect(Collectors.toList());


        Selection2Result selection12Result = new SelekcjaCechD2().getSelekcja2Cech();
        List<Integer> featureList2 = Arrays.asList(selection12Result.getC1(), selection12Result.getC2());

        GroupChooser.getGroupChooserInstance().init(percentCountFromFileToLearn);

        List<VectorModel> trainingGroupWithoutSelection = GroupChooser.getGroupChooserInstance().getAllTrainingGroup();
        List<VectorModel> testGroupWithoutSelection = GroupChooser.getGroupChooserInstance().getAllTestGroup();

        List<VectorModel> trainingGroupSelection1 = GroupChooser.getGroupChooserInstance().getFilteredTrainingGroup(featureList1);
        List<VectorModel> testGroupSelection1 = GroupChooser.getGroupChooserInstance().getFilteredTestGroup(featureList1);

        List<VectorModel> trainingGroupSelection2 = GroupChooser.getGroupChooserInstance().getFilteredTrainingGroup(featureList2);
        List<VectorModel> testGroupSelection2 = GroupChooser.getGroupChooserInstance().getFilteredTestGroup(featureList2);

        findAnswer("Bez Selekcji", testGroupWithoutSelection,trainingGroupWithoutSelection);
        findAnswer("Selekcja 2 cech D1", testGroupSelection1,trainingGroupSelection1); // do poprawy
        findAnswer("Selekcja 2 cech D2", testGroupSelection2,trainingGroupSelection2);
    }





    private void findAnswer(String methodName, List<VectorModel> testGroup, List<VectorModel> trainingGroup) {

        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Metody: " + methodName);

        new NajbliższySasiad(testGroup, trainingGroup);
        new NajblizsziSasiasiedzi(5, testGroup, trainingGroup);
        new NajmniejszychSrednich(testGroup, trainingGroup);
        new KNM(testGroup, trainingGroup); // to do poprawy


        System.out.println("---------------------------------------------------------------------------------");
        System.out.println();
    }
}
