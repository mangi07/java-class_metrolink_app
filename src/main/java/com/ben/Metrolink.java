package com.ben;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by ben on 2/9/2016.
 */

@Component
public class Metrolink{

    @Autowired
    AppOutput output;
    @Autowired
    Director director;


    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("application-context.xml");
        /*Metrolink metrolink = (Metrolink) context.getBean("metrolink");

        metrolink.director.showStops();

        int stopNumber = 0;
        try {
            stopNumber = metrolink.director.getStopNumber();
            metrolink.director.createStop(stopNumber);
        } catch (Exception e) {
            metrolink.output.print("Invalid stop number.");
        }

        metrolink.director.showNextArrival();
*/
    }

}
