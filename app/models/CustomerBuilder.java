package models;

public class CustomerBuilder {
    private long id;
    private String name;
    private long spend = 0;

    public CustomerBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder setSpend(long spend) {
        this.spend = spend;
        return this;
    }

    public Customer build() {
        return new Customer(id, name, spend);
    }
}