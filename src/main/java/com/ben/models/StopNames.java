package com.ben.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ben on 2/19/2016.
 */
@Entity
@Table(name = "stops")
public class StopNames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stop_id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "stop_name")
    private String stopName;

    public String getStopName() {
        return stopName;
    }
}
