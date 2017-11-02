package com.bitsforabetterworld.lunarlander;
import java.io.IOException;

import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

import com.bitsforabetterworld.lunarlander.ui.Display;

public class Main {

	void mainEventLoop(SensorUnit sensorUnit, ControlUnit controlUnit) {
		boolean isDone = false;
		while (!isDone) {
			WorldView worldView = sensorUnit.getWorldView();
			Command command = controlUnit.getCommand(worldView);
		}
	}
	
	public static void main(String[] args) {
		final Lander lander = new Lander.Builder().x(750.0).y(1000.0).thrusterAcceleration(5.0).build();
		final Display display = new Display(lander);
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            		display.createAndShowGUI();
            	}
            	catch (IOException exp) {
            		System.err.println("Exception: "+exp);
            		exp.printStackTrace(System.err);
            	}
            }
		});
		Object waiter = new Object();
		try {
			while (!lander.isLanded()) {
				Thread.sleep(60L);
				SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		            	display.update();
		            }
				});
			}
		}
		catch (InterruptedException ex) {
			System.err.println("Interrupted");
		}
	}

}
