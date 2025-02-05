# simpleETL documentation
WeatherDataFetcher.java:
fetches data from OpenWeatherMap API through HTTP request
data is unorganized and extraneous
getWeatherData() method implemented feb 4th

Java doesn't have built-in JSON parsing, JSON Parsing done through org.json library
downloaded JSON library from Maven Repository

WeatherDataFetcher.java updated to parse JSON:
parseWeatherData() method implemented feb 4th:
	creates a JSONObject (jsonObject) which is the whole json returned by getWeatherData()
	and parses through everything to get values like cityName, anditems from JSONArrays like
	weather by creating a JSONArray object called weatherArray and getting an object
	from it. It then formats the output and returns it with the collected values.
