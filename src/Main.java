import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    public static Logger logger = Logger.getLogger(Main.class.getName());


    //fake db
    public static ArrayList<Car> cars = new ArrayList<>();

    public static void main(String[] args) {
        //setting logger config.
        setLoggerSetting();

        char option = 'q';
        do {
            System.out.println("============================================");
            System.out.println("*** Welcome to Car Inventory System.     ***");
            System.out.println("*** Enter A to Add Car to Inventory.     ***");
            System.out.println("*** Enter S to Search Car By Name.       ***");
            System.out.println("*** Enter T to Get Total Car By Name.    ***");
            System.out.println("*** Enter D to Display all Cars.         ***");
            System.out.println("*** Enter R to Remove Car From Inventory.***");
            System.out.println("*** Enter q to Exit the Program.         ***");
            System.out.println("============================================");


            // get input from user.
            Car car = new Car();
            Scanner input = new Scanner(System.in);
            option = input.next().charAt(0); // get 1 char input from keyboard.
            if (option == 'A' || option == 'a') {
                System.out.println("To Add Car: \nPlease enter a car details :- ");
                car.setCarID(getCarID());
                System.out.print("Please Enter The Car Name : ");
                car.setName(input.next());
                System.out.print("Please Enter The Car Maker Name : ");
                car.setCarMaker(input.next());
                System.out.print("Please Enter The Car Type (Coupe/Sedan/SUV/VAN/Truck) : ");
                car.setType(input.next());
                System.out.print("Please Enter The Car Variant : ");
                car.setCarVariant(input.next());
                System.out.print("Please Enter The Car Engine Type (Gasoline/Hybrid/EV) : ");
                car.setEngineType(input.next());
                System.out.print("Please Enter The Car Price : ");
                car.setPrice(input.nextDouble());
                System.out.print("Please Enter The Car Build Year : ");
                car.setYear(input.nextInt());
                addCar(car);
            } else if (option == 'S' || option == 's') {
                System.out.println("To Search Car: \nPlease enter a Car Name :- ");
                car = getCarByName(input.next());
                if (car != null) {
                    System.out.println(car.toString());
                }
            } else if (option == 'T' || option == 't') {
                System.out.println("To Get Total No. of Car By Name Car: \nPlease enter a Car Name :- ");
                System.out.println("Total No. of Car is : " + getTotalNumberOfCarByName(input.next()));
            } else if (option == 'D' || option == 'd') {
                displayAllCar();
            } else if (option == 'R' || option == 'r') {
                System.out.println("To Remove Car From Inventory: \nPlease enter a CarID From Below List :- ");
                displayAllCar();
                deteleCarFromInventory(input.next());
                displayAllCar();
            } else {
                System.out.println("This is not a valid Option! Please Select Another");
                logger.log(Level.INFO, "Selected Option is = " + option);
            }
            System.out.println("Thank you For using Car Inventory System !");
        } while (option != 'q');
    }

    //add car
    public static void addCar(Car car) {
        cars.add(car);
        logger.log(Level.INFO, "Data has been Inserted to database. Record = " + car.toString());
    }

    //detele the car record by ID
    public static void deteleCarFromInventory(String carID) {
        cars.removeIf(e -> e.getCarID().equals(carID));
        logger.log(Level.INFO, "Record has been Removed From database. carID = " + carID);
    }


    //search by name
    public static Car getCarByName(String name) {
        for (Car car : cars) {
            if (car.getName().equals(name)) {
                return car;
            }
        }
        System.out.println("Car Not Found!");
        logger.log(Level.WARNING, "Record Not Found From database. carName = " + name);
        return null;
    }

    //get number of car by name
    public static int getTotalNumberOfCarByName(String name) {
        int total = 0;
        for (Car car : cars) {
            if(car.getName().equals(name)){
                total++;
            }
        }
        return total;
    }

    //get all cars
    public static void displayAllCar() {
        for (Car car : cars) {
            System.out.println(car.toString());
        }
    }

    // Uniqe CarID Generater
    public static String getCarID() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    //Set loggin Setting
    public static void setLoggerSetting() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }
        logger.setLevel(Level.FINE);
        logger.addHandler(new ConsoleHandler());
        //adding custom handler
        logger.addHandler(new MyHandler());
        try {
            //FileHandler file name with max size and number of log files limit
            Handler fileHandler = new FileHandler("/Users/hetpatel/temp/logger.log", 20000, 1);
            fileHandler.setFormatter(new MyFormatter());
            //setting custom filter for FileHandler
            fileHandler.setFilter(new MyFilter());
            logger.addHandler(fileHandler);

            logger.log(Level.CONFIG, "Config data");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
