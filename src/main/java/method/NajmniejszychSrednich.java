package method;

import model.VectorModel;
import util.Util;

import java.util.List;


public class NajmniejszychSrednich {

    private NajbliższySasiad nn = new NajbliższySasiad();

    public NajmniejszychSrednich(List<VectorModel> testGroup, List<VectorModel> trainingGroup) {
        nn.NN("Najmniejszych srednich", testGroup, Util.prepareVectorListWithAvg(trainingGroup));
    }

}
