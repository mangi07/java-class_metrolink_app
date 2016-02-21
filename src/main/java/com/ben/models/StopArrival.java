package com.ben.models;

import javax.persistence.*;

/**
 * Created by ben on 2/9/2016.
 */
@Entity
@Table(name = "metrolink_stops")
public class StopArrival {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arrival_time", unique = true, nullable = false)
    private String arrivalTime;
    @Column(name = "stop_name")
    private String stopName;
    @Column(name = "service_id")
    private String serviceId;

    public String getStopName() {
        return this.stopName;
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }
}
