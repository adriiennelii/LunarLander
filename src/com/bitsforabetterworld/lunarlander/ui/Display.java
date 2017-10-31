package com.bitsforabetterworld.lunarlander.ui;
import javax.swing.*;


public class Display {
   public static void createAndShowGUI() {
       //Create and set up the window.
       JFrame frame = new JFrame("HelloWorldSwing");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       //Add the ubiquitous "Hello World" label.
       JLabel label = new JLabel("Hello World");
       frame.getContentPane().add(label);

       //Display the window.
       frame.pack();
       frame.setVisible(true);
   }
}
