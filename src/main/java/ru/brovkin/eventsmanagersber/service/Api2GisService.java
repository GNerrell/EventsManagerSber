package ru.brovkin.eventsmanagersber.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.brovkin.eventsmanagersber.DTO.CoordinatesDTO;
import ru.brovkin.eventsmanagersber.model.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@Service
public class Api2GisService {

    public static final String API_KEY_2GIS = "9ef2dfa1-7d97-419a-b808-b0e872a7d933";

    public Api2GisService() {

    }

    public CoordinatesDTO getCoordinatesFromLocationAddress(Location location) {
        try {
            URL url = getUrlFromLocation(location);
            CoordinatesDTO coordinates = getCoordinatesDTO(url);
            if (coordinates != null) return coordinates;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public CoordinatesDTO getCoordinatesFromLocationAddress(String city, String street, String house) {
        try {
            URL url = getUrlFromLocationData(city, street, house);
            CoordinatesDTO coordinates = getCoordinatesDTO(url);
            if (coordinates != null) return coordinates;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Nullable
    private CoordinatesDTO getCoordinatesDTO(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            StringBuilder response = getResponse(connection);

            CoordinatesDTO lat = getCoordinatesDTO(response);
            if (lat != null) {
                return lat;
            }
        }
        connection.disconnect();
        return null;
    }

    private static StringBuilder getResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response;
    }

    private static CoordinatesDTO getCoordinatesDTO(StringBuilder response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.toString());
        JsonNode itemsNode = root.path("result").path("items");
        if (itemsNode.isArray() && !itemsNode.isEmpty()) {
            JsonNode pointNode = itemsNode.get(0).path("point");
            double lat = pointNode.path("lat").asDouble();
            double lon = pointNode.path("lon").asDouble();
            return new CoordinatesDTO(lat, lon);
        }
        return null;
    }

    private static URL getUrlFromLocation(Location location) throws MalformedURLException {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://catalog.api.2gis.com/3.0/items/geocode?q=");
        urlString.append(location.getCity()).append(",%20");
        urlString.append(location.getStreet()).append(",%20");
        urlString.append(location.getHouse()).append(",%20");
        urlString.append("&fields=items.point&key=");
        urlString.append(API_KEY_2GIS);
        return new URL(urlString.toString());
    }

    private static URL getUrlFromLocationData(String city, String street, String house) throws MalformedURLException {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://catalog.api.2gis.com/3.0/items/geocode?q=");
        urlString.append(city).append(",%20");
        urlString.append(street).append(",%20");
        urlString.append(house).append(",%20");
        urlString.append("&fields=items.point&key=");
        urlString.append(API_KEY_2GIS);
        return new URL(urlString.toString());
    }

}
