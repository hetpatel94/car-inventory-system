import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    public static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        //JDBC connection.
        Connection conn = null;
        Statement stmt = null;
        try {
            //setting logger config.
            setLoggerSetting();

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop", "root", "rootroot"); // for MySQL only
            if (conn != null) {
                System.out.println("Successfully connected to MySQL database.");
            }
            stmt = conn.createStatement();

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
                    addCar(car, stmt);
                } else if (option == 'S' || option == 's') {
                    System.out.println("To Search Car: \nPlease enter a Car Name :- ");
                    getCarByName(input.next(), stmt);
                } else if (option == 'T' || option == 't') {
                    System.out.println("To Get Total No. of Car By Name Car: \nPlease enter a Car Name :- ");
                    System.out.println("Total No. of Car is : " + getTotalNumberOfCarByName(input.next(), stmt));
                } else if (option == 'D' || option == 'd') {
                    displayAllCar(stmt);
                } else if (option == 'R' || option == 'r') {
                    displayAllCar(stmt);
                    System.out.println("To Remove Car From Inventory: \nPlease enter a CarID From Above List :- ");
                    deteleCarFromInventory(input.next(), stmt);
                    displayAllCar(stmt);
                } else {
                    System.out.println("This is not a valid Option! Please Select Another");
                    logger.log(Level.INFO, "Selected Option is = " + option);
                }
                System.out.println("Thank you For using Car Inventory System !");
            } while (option != 'q');
        } catch (SQLException | ClassNotFoundException ex) {
            logger.log(Level.WARNING, "Error While Get JDBC Connection Root Cause = " + ex);
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //add car
    public static void addCar(Car car, Statement stmt) throws SQLException {
        String query = "INSERT INTO car_inventory(carID, name, carMaker, type, carVariant, engineType, price, year) VALUES('" + car.getCarID() + "', '" + car.getName() + "', '" + car.getCarMaker() + "', '" + car.getType() + "', '" + car.getCarVariant() + "', '" + car.getEngineType() + "', '" + car.getPrice() + "', '" + car.getYear() + "')";
        stmt.executeUpdate(query);
        logger.log(Level.INFO, "Data has been Inserted to database. Record = " + car.toString());
    }

    //detele the car record by ID
    public static void deteleCarFromInventory(String carID, Statement stmt) throws SQLException {
        String query = "DELETE FROM car_inventory WHERE carID = '" + carID + "'";
        stmt.executeUpdate(query);
        logger.log(Level.INFO, "Record has been Removed From database table successfully. carID = " + carID);
    }


    //search by name
    public static void getCarByName(String name, Statement stmt) throws SQLException {
        String query = "SELECT * FROM car_inventory WHERE name = '" + name + "'";
        ResultSet rset = stmt.executeQuery(query);

        System.out.println("___________________________________________________________________________");
        System.out.println("| carID | name | carMaker | type | carVariant | engineType | price | year |");
        System.out.println("___________________________________________________________________________");
        while (rset.next()) {
            System.out.println("| " + rset.getString("carID") + " | " + rset.getString("name") + " | " + rset.getString("carMaker") + " | " + rset.getString("type") + " | " + rset.getString("carVariant") + " |" + rset.getString("engineType") + " | $" + rset.getDouble("price") + " | " + rset.getInt("year") + " |");
        }
        System.out.println("___________________________________________________________________________");

        logger.log(Level.INFO, "Record From database. carName = " + name);
    }

    //get number of car by name
    public static int getTotalNumberOfCarByName(String name, Statement stmt) throws SQLException {
        String query = "SELECT count(*) FROM car_inventory WHERE name = '" + name + "'";
        ResultSet rset = stmt.executeQuery(query);
        rset.next();
        return rset.getInt(1);
    }

    //get all cars
    public static void displayAllCar(Statement stmt) throws SQLException {
        String query = "SELECT * FROM car_inventory";
        ResultSet rset = stmt.executeQuery(query);

        System.out.println("___________________________________________________________________________");
        System.out.println("| carID | name | carMaker | type | carVariant | engineType | price | year |");
        System.out.println("___________________________________________________________________________");
        while (rset.next()) {
            System.out.println("| " + rset.getString("carID") + " | " + rset.getString("name") + " | " + rset.getString("carMaker") + " | " + rset.getString("type") + " | " + rset.getString("carVariant") + " |" + rset.getString("engineType") + " | $" + rset.getDouble("price") + " | " + rset.getInt("year") + " |");
        }
        System.out.println("___________________________________________________________________________");
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
