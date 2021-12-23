import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerHandler extends Thread {
    private final ServerSocket server;
    public DataOutputStream oos;
    public DataInputStream ois;
    public Peer peer;

    public PeerHandler(ServerSocket server,Peer peer) throws IOException {
        this.server = server;
        this.peer = peer;
    }

    @Override
    public void run() {
        Socket conn = null;
        try {
            conn = server.accept();
            oos = new DataOutputStream(conn.getOutputStream());
            ois = new DataInputStream(conn.getInputStream());
            byte option = ois.readByte();
            switch (option){
                case 1:
                    String file = ois.readUTF();
                    DataInputStream inFromFile = new DataInputStream(
                            new FileInputStream(peer.getShared_directory()+file));
                    int bufferSize = 8192;
                    byte[] buf = new byte[bufferSize];
                    int count = 0;
                    while((count + inFromFile.read(buf))!= -1){
                        oos.write(buf,0,count);
                    }
                    oos.flush();
                case 2:
                    // Ping
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
