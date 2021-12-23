import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@SuppressWarnings("all")

public class Tracker {

    // int port = 7800; // port of tracker
    ObjectInputStream input;
    ObjectOutputStream output;

    public static ConcurrentHashMap<String, PeerInfo> registeredPeers = new ConcurrentHashMap<>(); //showing registered peers
    public static ConcurrentHashMap<Integer, String> tokens = new ConcurrentHashMap<>(); // mapping tokens with usernames
    public static HashMap<String, Integer> ConnectedPeersInfo = new HashMap<>(); //showing connected peers
    public static ConcurrentHashMap<String, PeerInfo> details = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, Vector<String>> fileToPeer = new ConcurrentHashMap<>();
    public static HashSet<String> availableFiles = new HashSet<>();

    public static void main(String args[]) throws IOException {
        ServerSocket providerSocket;
        Socket connection = null;

        try {
            providerSocket = new ServerSocket(1441, 10);
            System.out.println("The server is open at port: " + 1441);
            System.out.println("Server socket created.Waiting for connection...");

            //noinspection InfiniteLoopStatement
            //STATIC METRITIS THREADS ME PINAKA KAI TON METRITI NA AFKSOMEIONETAI
            while (true) {
                try {
                    //Client connection
                    connection = providerSocket.accept();
                    Thread handler = new Handler(connection);
                    handler.start();




                } catch (Exception e) {
                    System.err.println("IOException");
                }

                /*try {
                    providerSocket.close();
                } catch (IOException ioException) {
                    System.err.println("Unable to close. IOexception");
                }*/

            }
        } catch (IOException e) {
            System.err.println("IOException");
        }

    }


    public boolean replylist(String title) {
        if (fileToPeer.containsKey(title)) {
            return true;
        }
        return false;
    }
    public boolean isActive(String username) throws IOException {
        InetAddress geek = InetAddress.getByName(registeredPeers.get(username).getAddress());
        if (geek.isReachable(5000)){
            return true;
        }
        return  false;
    }

}