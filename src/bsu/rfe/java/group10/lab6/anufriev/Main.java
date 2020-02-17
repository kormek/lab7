package bsu.rfe.java.group10.lab6.anufriev;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MainFrame frame1 = new MainFrame();
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame1.setVisible(true);
            }
        });


    }
}
