package recommender.model;

public class Item {
    private long id;
    private double value;

    public Item(long id, double value){
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }
    public double getValue() {
        return value;
    }
}
