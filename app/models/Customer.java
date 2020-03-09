package models;

public class Customer {

    private long id;
    private String name;
    private long spend;

    public Customer() { }

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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpend(long spend) {
        this.spend = spend;
    }
}
