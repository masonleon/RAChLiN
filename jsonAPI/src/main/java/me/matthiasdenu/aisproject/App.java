package me.matthiasdenu.aisproject;
import java.io.IOException;

import dk.tbsalling.aismessages.demo.SimpleDemoApp;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        try {
            SimpleDemoApp.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
