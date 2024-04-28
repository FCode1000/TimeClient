import java.sql.Time;
import java.util.Arrays;
import java.time.*;
public class Main {
    public static void main(String[] args) throws Exception {
        experiment();
        String IP = "127.0.0.1";
        int PORT = 8080;
        Protocol PROTOCOL = Protocol.udp;
        try{
            String[] input = args[0].split(":");
            if(input.length == 0){
                System.out.println("Using default connection udp:127.0.0.1:8080");
            }
            else if (input[0].equals("udp")) {
                PROTOCOL = Protocol.udp;
                IP = input[1];
            }
            else if (input[0].equals("tcp")) {
                PROTOCOL = Protocol.tcp;
                IP = input[1];
            }
            else{
                IP = input[0];
                if(input.length == 2){
                    PORT = Integer.parseInt(input[2]);
                }
            }
            if(input.length == 3){
                PORT = Integer.parseInt(input[3]);
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            IP = "127.0.0.1";
            PORT = 8080;
            PROTOCOL = Protocol.tcp;
            System.out.println("Using default connection tcp:127.0.0.1:8080");
        }
        catch(Exception e){e.printStackTrace();}
        TimeClient timeClient = new TimeClient(IP, PORT, PROTOCOL);
        System.out.println(timeClient.getTime());
    }
    public static void experiment(){
        int totalRequests = 100000;
        System.out.println("Start des Experiments");
        System.out.println("Anzahl der Abfragen: " + totalRequests);
        //tcp
        TimeClient tcpTimeClient = new TimeClient("127.0.0.1", 8080, Protocol.tcp);
        TimeClient udpTimeClient = new TimeClient("127.0.0.1", 8080, Protocol.udp);
        long tcpStart = System.currentTimeMillis();
        for (int i = 0; i < totalRequests; i++) {
            try{
                tcpTimeClient.getTime();
            }catch(Exception e){e.printStackTrace();}
        }
        long tcpEnd = System.currentTimeMillis();
        System.out.println("Benötigte Zeit mit tcp: " + (tcpEnd-tcpStart)+"ms");

        long udpStart = System.currentTimeMillis();
        for (int i = 0; i < totalRequests; i++) {
            try{
                udpTimeClient.getTime();
            }catch(Exception e){e.printStackTrace();}
        }
        long udpEnd = System.currentTimeMillis();
        System.out.println("Benötigte Zeit mit udp: " + (udpEnd-udpStart)+"ms");
        System.out.println("Ende des Experiments");
    }
}