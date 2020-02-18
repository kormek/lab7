package bsu.rfe.java.group10.lab6.anufriev;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

public class InstantMessenger implements MessageListener {

   private String sender;
    private List<MessageListener> listeners = new LinkedList<MessageListener>();

    private static final int SERVER_PORT = 5500;

   public InstantMessenger(){
        startServer();
    }

    public void addMessageListener(MessageListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeMessageListener(MessageListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

public void sendMessage(String senderName,String address,String message){
    try{
        //final String this.senderName = senderName;
        final String destinationAddress = address;
        //final String message = textAreaOutgoing.getText();



        final Socket socket = new Socket(destinationAddress,SERVER_PORT);

        final DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF(senderName);
        out.writeUTF(message);
        socket.close();


    } catch (UnknownHostException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,"Can't send the message:destined host not found","Error",JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,"Can't send the message","Error",JOptionPane.ERROR_MESSAGE);
    }
}

private void startServer(){
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
                while(!Thread.interrupted()) {
                    final Socket socket = serverSocket.accept();
                    final DataInputStream in = new DataInputStream(socket.getInputStream());

                    final String senderName = in.readUTF();

                    final String message = in.readUTF();

                    socket.close();

                    notifyListeners(new Peer(senderName,(InetSocketAddress) socket.getRemoteSocketAddress()), message);


                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error in server functioning","Mistake",JOptionPane.ERROR_MESSAGE);
            }
        }
    }).start();
}

public void messageReceived(Peer sender,String message){

}
    private void notifyListeners(Peer sender, String message) {
        synchronized (listeners) {
            for (MessageListener listener : listeners) {
               listener.messageReceived(sender, message);
            }
        }
   }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}
