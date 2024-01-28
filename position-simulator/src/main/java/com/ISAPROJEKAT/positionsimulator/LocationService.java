package com.ISAPROJEKAT.positionsimulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {


    public List<Location> generateRoute(Location startPosition, Location endPosition) throws IOException {

        String apiUrl = "http://router.project-osrm.org/route/v1/driving/" +
                startPosition.getLongitude() + "," + startPosition.getLatitude() + ";" +
                endPosition.getLongitude() + "," + endPosition.getLatitude() + "?overview=false&steps=true";

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(apiUrl);
        HttpResponse response = httpClient.execute(request);


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }


            String jsonString = result.toString();
            System.out.println(jsonString);

            List<Location> route = new ArrayList<>();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(jsonString);

                JsonNode steps = root.path("routes").get(0).path("legs").get(0).path("steps");
                for (JsonNode step : steps) {
                    JsonNode intersections = step.path("intersections");
                    for (JsonNode intersectionNode : intersections) {
                        JsonNode locationNode = intersectionNode.path("location");
                        double longitude = locationNode.get(1).asDouble();
                        double latitude = locationNode.get(0).asDouble();
                        route.add(new Location(longitude, latitude));
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
                route = new ArrayList<>();
            }

            return route;


        }
    }
}
