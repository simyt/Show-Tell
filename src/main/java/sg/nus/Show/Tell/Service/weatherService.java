package sg.nus.Show.Tell.Service;


import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.nus.Show.Tell.Model.weatherDescr;

@Service
public class weatherService {
    public static final String weather_URL = "https://api.openweathermap.org/data/2.5/weather";
    public static final String unit = "metric";

    @Value("${weather.key}")
    private String apiKey;

    public List<weatherDescr> cityWeather (String city) {
    //construct the URL to call
        String url = UriComponentsBuilder
            .fromUriString(weather_URL)
            .queryParam("appid", apiKey)
            .queryParam("q", city.replace(" ", "+")) //cities with more than 1 word
            .queryParam("units", unit)
            .toUriString();

        System.out.printf(">>>> url $s\n", url);

        //Make the GET request
        RequestEntity<Void> req = RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

            ResponseEntity<String> resp;
        //Make the request
        try {
            RestTemplate template = new RestTemplate();
            resp = template.exchange(req, String.class);

            System.out.printf("Status code: %d\n", resp.getStatusCode().value());
            System.out.printf("Payload: %s\n", resp.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); //return empty string
        }
        
        //Process the body and convert the data to Json
        JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
        JsonObject data = reader.readObject();
        return data.getJsonArray("weather").stream() // returns Json value here
         .map(value -> value.asJsonObject()) //change the Json value to Json Obj
         .map(j -> new weatherDescr(j.getString("main"),j.getString("description"),j.getString("icon")))
         .toList();
    }

}
