package com.bitsforabetterworld.lunarlander;

import java.util.EnumSet;

public class AutonomousControl implements Control {
	//did this so I wouldn't have to constantly change the values below, now only have to do it up here. 
	static final double TARGET_THETA_POS = 0.2;
    static final double TARGET_THETA_NEG = -0.2;
    static final double TARGET_DTHETA_POS = 0.2;
    static final double TARGET_DTHETA_NEG = -0.2;
    static final double TARGET_DY_NEG = -7.8;
    static final double TARGET_Y = 300;

    @Override
    public EnumSet<Command> getCommand(Position position, Velocity velocity) {
		//started out with command none by default. will be overrided with thrust or rotate if needed. if not, then don't have to change anything. 
        Command thrustCommand = Command.None;
        Command rotateCommand = Command.None;
        
        // TODO: write code to decide if we should thrust (by setting thrustCommand = Command.Thrust) or
        // turn on the rotation motors (by settings rotateCommand = Command.RollClockwise 
        // or Command.RollCounterclockwise)
        //
        // Use the Position and Velocity of the lander to decide what action (if any) to take.
        //
        // The goal is to have a successful landing, which means that your lander gets to a Y position of 0
        // with the following requirements:
        //
        // - Vertical velocity (dy) less than -8 m/s
        // - Horizontal velocity (dx) between -5 and 5 m/s
        // - Angle (theta) between -0.3 and 0.3 radians
        // - Change in angle (dtheta) between -0.2 and 0.2 radians/second
        //
        // Try to get a high score! You get points for finishing a level, for fuel left at the
		// end of a level, and for landing on the landing pad.
		
        double y = position.getY();
        double theta = position.getTheta();

        double dx = velocity.getDx();
        double dy = velocity.getDy();
        double dtheta = velocity.getDtheta();

        if (theta < TARGET_THETA_NEG || theta > TARGET_THETA_POS) {
            if (theta < TARGET_THETA_NEG) {
                rotateCommand = Command.RollClockwise;
				rotateCommand = calculateDTheta(dtheta, rotateCommand);
			}
            if (theta > TARGET_THETA_POS) {
                rotateCommand = Command.RollCounterclockwise;
                rotateCommand = calculateDTheta(dtheta, rotateCommand);
            }
        }

        if (dx > 4.0 && theta > -0.1 && y < 800) {
            thrustCommand = Command.Thrust;
            System.out.println("dx > 3.0 ----- thrust");
        }

        if (dx < -4.0 && theta > 0.1 && y < 800) {
            thrustCommand = Command.Thrust;
            System.out.println("dx < 4.0 +++++ thrust");
        }

        if (y < TARGET_Y && dy < TARGET_DY_NEG) {
            thrustCommand = Command.Thrust;
        }

        if (y < 200 && (dx < -4.0 || dx > 4.0)) {
            thrustCommand = Command.Thrust;
        }

        
        System.out.println(thrustCommand + " " + rotateCommand);

        return EnumSet.of(thrustCommand, rotateCommand);
    }
    
    private Command calculateDTheta(double dtheta, Command rotateCommand) {
        Command rotate = rotateCommand;
        if (dtheta < TARGET_DTHETA_NEG || dtheta > TARGET_DTHETA_POS) {
            if (dtheta < TARGET_DTHETA_NEG) {
                rotate = Command.RollClockwise;
            }
            if (dtheta > TARGET_DTHETA_POS) {
                rotate = Command.RollCounterclockwise;
            }   
        }
        return rotate;
    }

    @Override
    public void reset() {
        // If there's anything you need to clean up when we start a new level, clean it up here.
    }
}


