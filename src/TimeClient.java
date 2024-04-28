import java.net.*;
import java.io.*;

public class TimeClient {
    private String ip;
    private int port;
    private Protocol protocol;

    public TimeClient(String ip, int port, Protocol protocol) {
        this.ip = ip;
        this.port = port;
        this.protocol = protocol;
    }
    public String getTime() throws IOException {
        switch(protocol) {
            case tcp:
                return getTcpTime();
            case udp:
                return getUdpTime();
            default:
                return "no such protocol";
        }
    }
    private String getUdpTime() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getLocalHost();
        byte[] buf = "udp".getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
        buf = new byte[1024];
        packet = new DatagramPacket(buf, 15);
        socket.receive(packet);
        String time = new String(packet.getData(), 0, packet.getLength());
        socket.close();
        return time;
    }
    private String getTcpTime() throws IOException {
        Socket clientSocket = new Socket(ip, port);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out.println("tcp");
        String resp = in.readLine();
        in.close();
        out.close();
        clientSocket.close();
        return resp;
    }
}
