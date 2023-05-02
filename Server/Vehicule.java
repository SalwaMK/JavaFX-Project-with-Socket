package salwa.demo4;

public class Vehicule {
    String num, nameA;
    int year;

    public Vehicule(String num, String nameA, int year) {
        this.num = num;
        this.nameA = nameA;
        this.year = year;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNameA() {
        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
