package bsu.rfe.java.group10.lab6.anufriev;

public interface MessageListener {
    void messageReceived(Peer sender,String message);
}
