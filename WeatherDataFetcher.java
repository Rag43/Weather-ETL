import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDataFetcher {
    private static final String API_KEY = "1e2eca0b6ea529d8546d4d409944c025";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    public static String getWeatherData(String city) {
        try {
            String urlString = BASE_URL + city + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { //Success
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return parseWeatherData(response.toString());

            } else {
                return "Error: Unable to fetch data. Response Code: " + responseCode;
            }


        } catch (Exception e) {
            return "Exception: " + e.getMessage();

        }
    }

    public static String parseWeatherData(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);

            //Extract relevant data by parsing the JSON
            String cityName = jsonObject.getString("name");

            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp");
            double feelsLike = main.getDouble("feels_like");
            int humidity = main.getInt("humidity");

            // "weather" is an array | we get the first element (0) and get "description"
            JSONArray weatherArray = new JSONArray(jsonObject.getJSONArray("weather"));
            String weatherDescription = weatherArray.getJSONObject(0).getString("description");

            //Get wind
            JSONObject wind = jsonObject.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            //Format output
            return "Weather in " + cityName + ":\n" +
                    "Temperature: " + temperature + "°C (Feels like " + feelsLike + "°C)\n" +
                    "Humidity: " + humidity + "%\n" +
                    "Conditions: " + weatherDescription + "\n" +
                    "Wind Speed: " + windSpeed + " m/s";

        } catch (Exception e){
            return "Error parsing JSON: " + e.getMessage();

        }
    }
    public static void main(String[] args) {
        String city = "Chicago"; //Make it any city
        String weatherData = getWeatherData(city);
        System.out.println(weatherData);
    }
}
