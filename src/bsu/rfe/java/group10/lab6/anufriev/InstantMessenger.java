package bsu.rfe.java.group10.lab6.anufriev;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class InstantMessenger implements MessageListener {

   private String sender;

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

        if(senderName.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(),"Write sender name","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(destinationAddress.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(),"Write destination address","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(message.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(),"Message is empty","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        final Socket socket = new Socket(destinationAddress,SERVER_PORT);

        final DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF(senderName);
        out.writeUTF(message);
        socket.close();


    } catch (UnknownHostException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(new JFrame(),"Can't send the message:destined host not found","Error",JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(new JFrame(),"Can't send the message","Error",JOptionPane.ERROR_MESSAGE);
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

                    final String address = ((InetSocketAddress)socket
                            .getRemoteSocketAddress())
                            .getAddress()
                            .getHostAddress();


                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(),"Error in server functioning","Mistake",JOptionPane.ERROR_MESSAGE);
            }
        }
    }).start();
}

private void messageReceived(){

}
    private void notifyListeners(Peer sender, String message) {
        synchronized (listeners) {
            for (MessageListener listener : listeners) {
               listener.messageReceived(sender, message);
            }
        }
   }
}
