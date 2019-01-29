package gd;

import gd.EventFactory.EventFactory;
import gd.EventModelStructure.EventModel;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class App {
    public static void main( String[] args ) throws IOException {

        //ip, port of netcat source
        String ip = "192.168.130.131";
        int port = 56565;

        // Create and connect via socket
        Socket socket = new Socket(ip, port);
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
        PrintWriter pw = new PrintWriter(dOut);

        EventFactory eventFactory = new EventFactory();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 10 ; i++) {
            EventModel eventModel = eventFactory.getRandomEvent();
            sb.append(eventModel.toString());
            System.out.println(eventModel.toString());
        }

        pw.write(sb.toString());
        pw.flush();
        pw.close();
    }
}
