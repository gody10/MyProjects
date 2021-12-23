import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

@SuppressWarnings("all")
class Handler extends Thread {
    private Socket conn;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public Random r = new Random();

    Handler(Socket conn) throws IOException {
        this.conn = conn;
        oos = new ObjectOutputStream(conn.getOutputStream());
        ois = new ObjectInputStream(conn.getInputStream());
    }

    public void run() {
        try {
            //get socket writing and reading streams
            System.out.println("New client has connected");
            System.out.println("Connection received from " + conn.getInetAddress().getHostName() + " : " + conn.getPort());
            //create new thread to handle client
            boolean flag = true;
            while(flag){
                int choice = (int) ois.readObject();
                //Message registerInfo2=null;
                switch (choice) {
                    case 1:
                        Message registerInfo = (Message) ois.readObject();
                        this.acceptRegister(registerInfo);
                        break;
                    case 2:
                        Message registerInfo2 = (Message) ois.readObject();
                        int TOKEN_ID = this.acceptLogin(registerInfo2);
                        if (TOKEN_ID != 0){
                            Vector<String> peerFiles = new Vector<>();
                            peerFiles =(Vector<String>) ois.readObject(); //until this point its okay
                            Tracker.fileToPeer.put(TOKEN_ID,peerFiles);
                            for (String str:peerFiles) {
                                Tracker.availableFiles.add(str);
                            }
                        }
                        break;
                    case 3://does not work
                        oos.writeObject(Tracker.availableFiles);//REPLY_LIST in list
                        oos.flush();
                        String file = (String) ois.readObject();
                        ArrayList<PeerInfo> list = new ArrayList<>();
                        for (String f: Tracker.details.keySet()) {
                            if (f.equals(file)){
                                list.add(Tracker.details.get(f));       // reply_details
                            }
                        }
                        if (!list.isEmpty()){
                            oos.writeBoolean(true);
                            oos.flush();
                            oos.writeObject(list);
                            oos.flush();
                        }else{
                            oos.writeBoolean(false);
                        }

                        break;
                    case 4:
                     boolean loginFlag = ois.readBoolean();
                     if(loginFlag==true) {
                         registerInfo2 = (Message) ois.readObject();
                         String username = registerInfo2.getUsername();
                         boolean exists = Tracker.ConnectedPeersInfo.containsKey(username);
                         if (exists) {
                             this.acceptLogout(username);
                         }
                     }//LoginFlag==false
                     else {
                         boolean answerIs = ois.readBoolean();
                         if (answerIs==true){
                             System.out.println("Left.");
                             flag = false;
                         }

                     }
                     break;
                }
            }

        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("IOException on socket : " + e);
            e.printStackTrace();
        }
        finally {
            try {
                ois.close();
                conn.close();
              //  System.exit(1);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void acceptRegister(Message message) {
        try {
            ObjectOutputStream outputStream;
            String username = message.getUsername();
            String password = message.getPassword();
            boolean exists = Tracker.registeredPeers.containsKey(username);
            if (exists){
                oos.writeByte(-1);
                oos.flush();
                System.out.println("This user already exists");
            }
            else{
                PeerInfo info = new PeerInfo(username, password, "", conn.getInetAddress().toString()); //why?
                Tracker.registeredPeers.put(username,info);
                oos.writeByte(1);
                oos.flush();
                System.out.println("A new user just registered");
                //System.out.println(registeredPeers.get(username).toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int acceptLogin(Message message) {
        try {
            String username = message.getUsername();
            String password = message.getPassword();
            boolean exists = Tracker.registeredPeers.containsKey(username); // checks if username exists
            if (!exists) {
                //FALSE
                oos.writeByte(-1);
                oos.flush();
                System.out.println("Something went wrong."); //if it doesnt exist there is an error message
            } else {
                exists = Tracker.registeredPeers.get(username).getPassword().equals(password); //checks if password matches
                if (exists) {

                    int TOKEN_ID = r.nextInt(1000); //assigns token
                    System.out.println("3");
                    while (Tracker.tokens.containsKey(TOKEN_ID)) {
                        TOKEN_ID = r.nextInt(1000);
                    }
                    System.out.println("24");
                    Tracker.tokens.put(TOKEN_ID, username);
                    //outputStream.writeInt(TOKEN_ID);
                    System.out.println("Client has logged in.");
                    oos.writeByte(1);
                    oos.flush();
                    Tracker.ConnectedPeersInfo.put(username,TOKEN_ID);
                    return TOKEN_ID;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void acceptLogout(String username) {
        try {
            Tracker.ConnectedPeersInfo.remove(username);
            oos.writeByte(1);
            oos.flush();
            System.out.println("Succesful logout of: " + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}