import java.io.*;
import java.net.*;
import java.util.*;

@SuppressWarnings("all")

public class Peer extends PeerInfo {
    private static int port = 7777;
    private ServerSocket peerServer;
    private Socket trackerConn = null;
    private ObjectOutputStream buffer;
    boolean LoginFlag = false;
    ObjectInputStream objectInputStream = null;


    /*private Socket nextPeer;*/
    public static void main(String[] args) {
        Scanner skr = new Scanner(System.in);
        String path = skr.nextLine();
        new Peer(""+path).startPeer();
    }

    public void startPeer(){
        //ObjectOutputStream objectOutputStream = null;
        Random r = new Random();
        int port = r.nextInt(8000-3000) + 3000;

        String message;
        boolean whileFlag = true;
        try {
            peerServer = new ServerSocket(port);
            PeerHandler handler = new PeerHandler(peerServer,this);
            trackerConn = new Socket("localhost",1441);
            buffer = new ObjectOutputStream(trackerConn.getOutputStream());
            objectInputStream = new ObjectInputStream(trackerConn.getInputStream());
            System.out.println(getShared_directory());
            while (whileFlag) {
                if(!LoginFlag) {
                    System.out.println("*********");
                    System.out.println("Options");
                    System.out.println("1.Register");
                    System.out.println("2.Login");
                    System.out.println("3.Download");
                    System.out.println("4.Disconnect");
                }
                else{
                    System.out.println("*********");
                    System.out.println("Options");
                    System.out.println("1.Register");
                    System.out.println("2.Login");
                    System.out.println("3.Download");
                    System.out.println("4.Logout");
                }

                Scanner skr = new Scanner(System.in);
                int choice = Integer.parseInt(skr.nextLine());
                buffer.writeObject(choice);
                Message credentials;
                switch (choice) {
                    case 1: //register
                        System.out.println("Enter username");
                        this.setUsername(skr.nextLine());
                        System.out.println("Enter password");
                        this.setPassword(skr.nextLine());
                        credentials = new Message(username, password);
                        this.register(credentials);
                        //byte reply = 0;
                        byte reply = objectInputStream.readByte();
                        if (reply == -1) {
                            System.out.println("There was an error");
                        } else {
                            System.out.println("You are Registered");
                        }
                        break;
                    case 2: //login
                        System.out.println("Enter username");
                        this.setUsername(skr.nextLine());
                        System.out.println("Enter password");
                        this.setPassword(skr.nextLine());
                        credentials = new Message(username, password);
                        this.login(credentials);
                        //byte reply2 = -1;
                        byte reply2 = objectInputStream.readByte();
                        //when a user tries to login w/out being registered, there is a message only in handler
                        if (reply2 == -1) {
                            System.out.println("There was an error");
                        } else {
                            System.out.println("You are Logged in");
                            LoginFlag = true;
                            inform();

                        }
                        break;
                    case 3://does not work
                        HashSet<String> files = (HashSet<String>) objectInputStream.readObject();//stuck here
                        System.out.println("Available files in the system");
                        for (String f:files) {
                            System.out.println(f);
                        }
                        System.out.println("Please choose which one you want to download");
                        String fileToDownload = skr.nextLine();
                        boolean fileExists = details(fileToDownload);
                        ArrayList<PeerInfo> peerInfos = (ArrayList<PeerInfo>) objectInputStream.readObject();
                        double min = Double.POSITIVE_INFINITY;
                        int k = 0;
                        for (int i = 0; i < peerInfos.size(); i++) {
                           PeerInfo peerInfo = peerInfos.get(i);
                            double formula = Math.pow(0.9,peerInfo.getCount_downloads()) *
                                    Math.pow(1.2,peerInfo.getCount_failures());
                            if (formula < min){
                                min = formula;
                                k = i;
                            }
                        }
                        boolean download = simpleDownload(fileToDownload,peerInfos.get(k));
                        if (download){
                            System.out.println("File downloaded succefully");
                            //notify
                        }
                        else{
                            System.out.println("Couldn't download file");
                            //notify
                        }
                    case 4: //logout or disconnect
                        buffer.writeBoolean(LoginFlag);
                        buffer.flush();
                        if(LoginFlag==true) {
                            System.out.println("Please give username for confirmation");
                            String answer = skr.nextLine();
                            Message confirmation = new Message(answer);
                            buffer.writeObject(confirmation);
                            buffer.flush();
                            byte logout = objectInputStream.readByte();
                            if (logout == 1) {
                            System.out.println("User was Logged Out");
                            LoginFlag = false;
                            }
                            else {
                                System.out.println("Something went wrong try again.");
                            }
                        }
                        else { //LoginFlag==false
                            System.out.println("Are you sure you want to disconnet?(Y/N))");
                            String answer = skr.nextLine();
                            boolean answerIs = answer.equals("Y");
                            buffer.writeBoolean(answerIs);
                            buffer.flush();
                            whileFlag = false;

                        }
                        break;

                }
                 buffer.reset();
            }
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                objectInputStream.close();
                buffer.close();
                System.exit(1);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


    public void register(Message message){
        try{
            System.out.println("Sending register request...");
            buffer.writeObject(message);
            buffer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(Message message){
        try {
            System.out.println("Sending login request...");
            buffer.writeObject(message);
            buffer.flush();
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean details(String file){
        boolean fileExists = false;
        try {
            buffer.writeObject(file);
            buffer.flush();
            fileExists = objectInputStream.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileExists;
    }

    public boolean simpleDownload(String file, PeerInfo peer){
        try {
            Socket peerToDownloadFrom = new Socket(peer.address,peer.port);
            ObjectOutputStream oos = new ObjectOutputStream(peerToDownloadFrom.getOutputStream());
            oos.writeByte(1);
            File fi = new File(shared_directory+file);
            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(peerToDownloadFrom.getInputStream()));
            OutputStream out = new FileOutputStream("shared_directory/"+file);
            // Local save path, the file name will automatically inherit from the server side.
            oos.writeChars(file);
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            int count = 0;
            while((count + in.read(buf))!= -1){
                out.write(buf,0,count);
            }
        } catch (UnknownHostException unknownHostException) {
            return false;
        } catch (FileNotFoundException fileNotFoundException) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
        return true;
    }
    public void inform() throws IOException {
        File f = new File(""+shared_directory);
        FileFilter filter = new FileFilter() {

            public boolean accept(File f)
            {
                return f.getName().endsWith("txt");
            }
        };
        File[] matchingFiles = f.listFiles(filter);
        Vector<String> fileNames = new Vector<>();
        for (File file: matchingFiles) {
            fileNames.add(file.getName());
        }
        buffer.writeObject(fileNames);
        buffer.flush();
        System.out.println("Informing tracker about my files");
    }
    public Peer(String shared_directory) {
        setShared_directory(shared_directory);
    }

}