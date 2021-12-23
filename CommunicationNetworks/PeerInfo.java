@SuppressWarnings("all")

public class PeerInfo {

    // Peer information variables
    protected String username, password, shared_directory,address; // username and password of each peer
    protected int port;
    protected int count_downloads, count_failures = 0; // total downloads and failures
    protected int TOKEN_ID;
 // total downloads and failures

    //Constructor
    public PeerInfo(String username, String password,String shared_directory,String address){
        this.username = username;
        this.password = password;
        this.shared_directory = shared_directory;
        this.address = address;
    }


    public PeerInfo(String address, int port, String username,int count_downloads,int count_failures){
        this.address = address;
        this.port = port;
        this.username = username;
        this.count_downloads = count_downloads;
        this.count_failures = count_failures;
    }

    public PeerInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public PeerInfo(String path){
        this.shared_directory=path;
    }
    public PeerInfo() {    }

    //GETTERS
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getShared_directory() {
        return shared_directory;
    }

    public String getAddress() {
        return address;
    }



    public int getCount_downloads() {
        return count_downloads;
    }

    public int getCount_failures() {
        return count_failures;
    }

    public int getTOKEN_ID() {
        return TOKEN_ID;
    }

    //SETTERS


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setShared_directory(String shared_directory) {
        this.shared_directory = shared_directory;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setCount_downloads(int count_downloads) {
        this.count_downloads = count_downloads;
    }

    public void setCount_failures(int count_failures) {
        this.count_failures = count_failures;
    }

    public void setTOKEN_ID(int TOKEN_ID) {
        this.TOKEN_ID = TOKEN_ID;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    //  INCREMENTS
    public void incDownloads(){
        count_downloads++;
    }

    public void incFailures(){
        count_failures++;
    }

    public boolean doesPasswordEqual(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "PeerInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", shared_directory='" + shared_directory + '\'' +
                ", address='" + address + '\'' +
                ", port=" + port +
                ", count_downloads=" + count_downloads +
                ", count_failures=" + count_failures +
                ", TOKEN_ID=" + TOKEN_ID +
                '}';
    }
}
