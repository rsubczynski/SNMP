package model;

public class KnmHelper {

    private VectorModel helpVm;
    private String tempolaryGroupName;

    public KnmHelper(VectorModel vectorModel, String tempolaryGroupName) {
        this.tempolaryGroupName = tempolaryGroupName;
        this.helpVm = vectorModel;
    }

    public VectorModel getHelpVm() {
        return helpVm;
    }

    public String getTempolaryGroupName() {
        return tempolaryGroupName;
    }
}
