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
	and parses through everything to get values like cityName, and items from JSONArrays like
	weather by creating a JSONArray object called weatherArray and getting an object
	from it. It then formats the output and returns it with the collected values.

Database:
Using SQLite for ease of use (JDBC built-in support)
Not using Maven, downloaded SQLite JAR and added dependency 

DatabaseManager class created feb 5th:
	createDatabase() method implemented feb 5th:
		Create SQLite database and checks connection
	createTable() method implemented feb 5th:
		creates statement object based on connection (DATABASE_URL), executes
		statement.execute(argument is SQL command, we pass the String sql)
		command (String sql): creates table, unique id for each row, auto-incrementing,
		stores values as either text, real, int, DATETIME (timestamp)
	insertWeatherData() method implemented feb 5th:
		preparedStatement is precompiled SQL statement based on String sql:
			INSERT INTO adds new row in weather table
		Setters assign values to '?' placeholders 

modified WeatherDataFetcher class to STORE DATA feb 5th:
	modified parseWeatherData() method feb 5th:
		after parsing values from JSON, call insertWeatherData method from
		DatabaseManager class, pass parsed values from JSON, storing in database

TESTS PERFORMED FEB 5th:
ran DatabaseManager.java, connects to SQLite database and creates table
ran WeatherDataFetcher.java, fetches, parses, and stores parsed data in weather_data.db
manually checked database using DB Browser for SQLite
		
	
