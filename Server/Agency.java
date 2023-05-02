package salwa.demo4;

public class Agency {
    private String name, address;
    int year;

    public Agency(String name, String address, int year) {
        this.name = name;
        this.address = address;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
