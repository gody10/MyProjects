import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.*;
import java.util.Objects;


public class MMLabServer {

    int port = 7800; // port of server

    public static void main(String args[]) {

        new MMLabServer().openServer();  //open server

    }

    void SendMessageToJS(String mes){
        PrintWriter pw;

        try {

            pw = new PrintWriter(s.getOutputStream());
            pw.write(mes);
            pw.flush();
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    ServerSocket providerSocket;
    Socket connection = null;
    Socket s;

    void openServer() {

        try {

            providerSocket = new ServerSocket(port);
            System.out.println("The server is open at port: " + port);
            int clientOption;


            while (true) {
                s = new Socket("192.168.2.8",7667);
                connection = providerSocket.accept();
                System.out.println("Client connected");

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String message = inFromClient.readLine();

                System.out.println("Message Received: " + message);

                SendMessageToJS(message);


                connection.close();
                s.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                providerSocket.close();

            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }
        }

    }

}

