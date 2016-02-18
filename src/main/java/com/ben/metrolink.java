package com.ben;


import com.ben.util.ScreenOutput;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by ben on 2/9/2016.
 */
public class Metrolink {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("application-context.xml");
        AppOutput output = (AppOutput) context.getBean("output");

        Director director = new Director();


        director.showStops();

        int stopNumber = 0;
        try {
            stopNumber = director.getStopNumber();
            director.createStop(stopNumber);
        } catch (Exception e) {
            output.print("Invalid stop number.");
        }

        director.showNextArrival();

    }

}
