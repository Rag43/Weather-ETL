import java.sql.*;

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

    public static void main(String[] args) {
        createDatabase();
        createTable();
    }
}
