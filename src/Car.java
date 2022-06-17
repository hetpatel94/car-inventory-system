public class Car {
    private String carID;
    private String name;
    private String carMaker;
    private String type;
    private String carVariant;
    private String engineType;
    private double price;
    private int year;

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarMaker() {
        return carMaker;
    }

    public void setCarMaker(String carMaker) {
        this.carMaker = carMaker;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCarVariant() {
        return carVariant;
    }

    public void setCarVariant(String carVariant) {
        this.carVariant = carVariant;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carID='" + carID + '\'' +
                ", name='" + name + '\'' +
                ", carMaker='" + carMaker + '\'' +
                ", type='" + type + '\'' +
                ", carVariant='" + carVariant + '\'' +
                ", engineType='" + engineType + '\'' +
                ", price=" + price +
                ", year=" + year +
                '}';
    }
}
