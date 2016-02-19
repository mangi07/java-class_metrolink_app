package com.ben.util;

import com.ben.AppOutput;
import org.springframework.stereotype.Component;

/**
 * Created by ben on 2/10/2016.
 */

@Component(value = "screen")
public class ScreenOutput implements AppOutput{

    public void print (String s) {
        System.out.println(s);
    }
}
