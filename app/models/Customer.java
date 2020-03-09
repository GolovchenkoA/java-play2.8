package models;

public class Customer {

    private long id;
    private String name;
    private long spend;
    private String tookTime;

    public Customer(long id, String name, long spend) {
        this.id = id;
        this.name = name;
        this.spend = spend;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getSpend() {
        return spend;
    }

    public void setTookTime(String tookTime) {
        this.tookTime = tookTime;
    }
}
