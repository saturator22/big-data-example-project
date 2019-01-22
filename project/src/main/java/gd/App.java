package gd;

import gd.EventFactory.EventFactory;
import gd.EventModelStructure.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class App {
    public static void main( String[] args ) throws FileNotFoundException {
        EventFactory eventFactory = new EventFactory();

        PrintWriter printWriter = new PrintWriter(new File("test.csv"));
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 10 ; i++) {
            Event event = eventFactory.getRandomEvent();
            sb.append(event.toString());
        }

        printWriter.write(sb.toString());
        printWriter.close();
    }
}
