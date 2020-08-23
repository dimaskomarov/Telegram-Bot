package ua.home.dima;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class Weather {
    private static final String API_KEY = "6b90dcd1788757259ff2d0a3f6b81017";

    public static String getWeatherByCityName(String city) throws IOException {
        String urlString =
                String.format(
                        "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",
                        city,
                        API_KEY);

        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        StringBuilder infoInJson = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while(bufferedReader.ready()) {
            infoInJson.append(bufferedReader.readLine());
        }
        bufferedReader.close();

        Map<String, Object> result = jsonToMap(infoInJson.toString());
        Map<String, Object> mainJson = jsonToMap(result.get("main").toString());
        Map<String, Object> windJson = jsonToMap(result.get("wind").toString());
        double temperature = (double) mainJson.get("temp");
        int temperatureInCelsius = (int) (temperature - 273.15);
        double windSpeed = (double) windJson.get("speed");
        return "temperature: " + temperatureInCelsius + "\n" + "wind speed: " + windSpeed;
    }

    private static Map<String, Object> jsonToMap(String infoInJson) {
        return (Map<String, Object>) new Gson().fromJson(infoInJson, Object.class);
    }
}
