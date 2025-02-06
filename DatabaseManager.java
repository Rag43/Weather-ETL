import java.sql.*; // Connection, DriverManager, SQLException, Statement, ResultSet
import java.util.Scanner;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:weather_data.db";

    public static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)){
            if (connection != null) {
                System.out.println("Connected to SQLite Database.");
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());

        }
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS weather (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "city TEXT, " +
                "temperature REAL, " +
                "feels_like REAL, " +
                "humidity INTEGER, " +
                "conditions TEXT, " +
                "wind_speed REAL, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Weather table created (if not exists).");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertWeatherData(String city, double temperature,
                                         double feelsLike, int humidity, String conditions,
                                         double windSpeed) {
        String sql = "INSERT INTO weather (city, temperature, feels_like," +
                " humidity, conditions, wind_speed) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, city);
            preparedStatement.setDouble(2, temperature);
            preparedStatement.setDouble(3, feelsLike);
            preparedStatement.setInt(4, humidity);
            preparedStatement.setString(5, conditions);
            preparedStatement.setDouble(6, windSpeed);

            preparedStatement.executeUpdate();
            System.out.println("Weather data inserted successfully!");


        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());

        }
    }

    public static void retrieveWeatherData() {
        // SQL statement to retrieve last 10 records from weather
        String sql = "SELECT * FROM weather ORDER BY timestamp DESC LIMIT 10";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) { // resultSet holds executed query
            System.out.println("\n--- Weather History ---");
            while (resultSet.next()) { // loop through each row
                // collect all values for each row
                int id = resultSet.getInt("id");
                String city = resultSet.getString("city");
                double temperature = resultSet.getDouble("temperature");
                double feelsLike = resultSet.getDouble("feels_like");
                int humidity = resultSet.getInt("humidity");
                String conditions = resultSet.getString("conditions");
                double windSpeed = resultSet.getDouble("wind_speed");
                String timestamp = resultSet.getString("timestamp");

                // format & print values for each row
                System.out.println("ID: " + id);
                System.out.println("City: " + city);
                System.out.println("Temperature: " + temperature + "°C (Feels like " + feelsLike + "°C)");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Conditions: " + conditions);
                System.out.println("Wind Speed: " + windSpeed + " m/s");
                System.out.println("Timestamp: " + timestamp);
                System.out.println("------------------------");
            }


        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());

        }
    }

    public static void retrieveWeatherDataByCity(String city) {
        String sql = "SELECT * FROM weather WHERE city = ? ORDER BY timestamp DESC LIMIT 5";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, city); // Replace ? with city parameter
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("\n--- Weather History for " + city + " ---");
            boolean dataFound = false;

            while (resultSet.next()) { // Loop through the rows
                dataFound = true;
                // Get, format, and print values from row
                System.out.println("Temperature: " + resultSet.getDouble("temperature")
                        + "°C (Feels like " + resultSet.getDouble("feels_like") +
                        "°C)");
                System.out.println("Humidity: " + resultSet.getInt("humidity") + "%");
                System.out.println("Conditions: " + resultSet.getString("conditions"));
                System.out.println("Wind Speed: " + resultSet.getDouble("wind_speed") +
                        " m/s");
                System.out.println("Timestamp: " + resultSet.getString("timestamp"));
                System.out.println("------------------------");

            }

            if (!dataFound) {
                System.out.println("No weather data found for " + city);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());

        }


    }

    public static void main(String[] args) {
        createDatabase();
        createTable();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter city name to retrieve weather records: ");
        String city = scanner.nextLine();
        retrieveWeatherDataByCity(city);

    }
}
