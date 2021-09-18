package org.example.WeatherReport;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherClass {
    public static void main(String[] args) throws IOException, ParseException {
    	try {
    		String city;
        	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        	System.out.print("Enter city name: ");
        	city = reader.readLine();
            URL url = new URL("http://api.weatherapi.com/v1/forecast.json?key=230369859c8b3223432454351809&q="+city+"&days=1&aqi=no&alerts=no");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();
//                System.out.println(inline);
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(inline);
                JSONObject jsonObject1 = (JSONObject) jsonObject.get("location");
                JSONObject jsonObject2 = (JSONObject) jsonObject.get("current");
                System.out.println("City Name: "+jsonObject1.get("name"));
                System.out.println("Latitude: "+jsonObject1.get("lat"));
                System.out.println("Longitude: "+jsonObject1.get("lon"));
                System.out.println("Last_updated: "+jsonObject2.get("last_updated"));
                System.out.println("Temperature in celsius: "+jsonObject2.get("temp_c"));
                System.out.println("Temperature in fahrenheit: "+jsonObject2.get("temp_f"));
            }
    	}
    	catch(Exception ex) {
    		System.out.println("No city found");
    	}
    }
}
