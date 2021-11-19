package ua.com.alevel.entity;

public class Countries {

    private int ISO;
    private String nameOfCountry;

    public int getISO() {
        return ISO;
    }

    public void setISO(int ISO) {
        this.ISO = ISO;
    }

    public String getNameOfCountry() {
        return nameOfCountry;
    }

    @Override
    public String toString() {
        return "Country{" +
                "nameOfCountry='" + nameOfCountry + '\'' +
                ", ISO=" + ISO +
                '}';
    }

    public void setNameOfCountry(String nameOfCountry) {
        this.nameOfCountry = nameOfCountry;
    }

}
