package com.nadia.weather.service;

import com.nadia.weather.entity.city.City;
import com.nadia.weather.repository.city.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired // This means to get the bean called CityRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CityRepository cities;


    public Iterable<City> getAllCities() {
        // This returns a JSON (or XML) with weather providers
        return cities.findAll();
    }

    public void addCityIntoDatabase(String city, String country) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        City c = City.builder()
                .cityName(city)
                .country(country)
                .build();
        cities.save(c);
    }


    public void addCityIntoDatabase(String city, String subcountry, String country) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        City c = City.builder()
                .cityName(city)
                .country(country)
                .subCountry(subcountry)
                .build();
        cities.save(c);
    }


    public City getCityById(Long id) {
        Optional<City> city = cities.findById(id);
        return city.get();
    }

    public City getCity(String cityName, String country) {
        List<City> foundcities = new ArrayList<>();
        cities.findAll().forEach(foundcities::add); // add each found city  into foundcities
        //foundcities.forEach(System.out::println); //prints all cities of the foundcities list
        for (City c : foundcities) {
            if (cityName.equalsIgnoreCase(c.getCityName()) && country.equalsIgnoreCase(c.getCountry())) {
                return c;
            }
        }
        return null;
    }

    public City getCity(String cityName, String subcountry, String country) {
        List<City> foundcities = new ArrayList<>();
        cities.findAll().forEach(foundcities::add); // add each found city  into foundcities

        for (City c : foundcities) {
            if (cityName == c.getCityName() && subcountry == c.getSubCountry() && country == c.getCountry()) {
                return c;
            }
        }
        return null;
    }

    public boolean hasCity(String cityName, String subcountry, String country) {
        for (City c : cities.findAll()) {
            if (cityName.equalsIgnoreCase(c.getCityName()) && subcountry.equalsIgnoreCase(c.getSubCountry())
                    && country.equalsIgnoreCase(c.getCountry())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCity(String cityName, String country) {
        if (cities.count() > 0 && cityName != null && country != null) {
            for (City c : cities.findAll()) {
                if (cityName == c.getCityName() && country == c.getCountry()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateCity(City c) {
        cities.save(c);
    }

    public void deleteCity(City c) {
        cities.delete(c);
    }

    public void deleteCityById(Long id) {
        cities.deleteById(id);
    }
}