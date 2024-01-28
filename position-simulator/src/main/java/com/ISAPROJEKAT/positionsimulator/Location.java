package com.ISAPROJEKAT.positionsimulator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

@Getter
@Setter

public class Location  {



    public double Latitude;

    public double Longitude;

    public Location(double latitude, double longitude) {
        Latitude = latitude;
        Longitude = longitude;

    }

    public Location() {
    }
}
