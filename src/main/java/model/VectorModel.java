package model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VectorModel {

    private String name;
    private List<Double> featureList;

    public VectorModel(List<String> list) {
        this.name = list.get(0);
        this.featureList = list.subList(1, list.size()).stream().map(Double::valueOf).collect(Collectors.toList());
    }

    public VectorModel(String name, List<Double> featureList) {
        this.name = name;
        this.featureList = featureList;
    }

    public void setFeature(Double feature) {
        this.featureList.add(feature);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VectorModel that = (VectorModel) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(featureList, that.featureList);
    }

    public String getName() {
        return name;
    }



    public List<Double> getFeatureList() {
        return featureList;
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, featureList);
    }

    @Override
    public String toString() {
        return "VectorModel{" +
                "name='" + name + '\'' +
                ", featureList=" + featureList +
                '}';
    }

}
