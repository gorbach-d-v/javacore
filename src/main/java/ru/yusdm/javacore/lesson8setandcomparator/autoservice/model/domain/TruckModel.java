package ru.yusdm.javacore.lesson8setandcomparator.autoservice.model.domain;

public class TruckModel extends Model {

    private int weight;
    private boolean embeddedKitchen;
    private int tankSize;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isEmbeddedKitchen() {
        return embeddedKitchen;
    }

    public void setEmbeddedKitchen(boolean embeddedKitchen) {
        this.embeddedKitchen = embeddedKitchen;
    }

    public int getTankSize() {
        return tankSize;
    }

    public void setTankSize(int tankSize) {
        this.tankSize = tankSize;
    }
}
