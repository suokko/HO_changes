package ho.core.training;

import ho.core.model.player.ISpielerPosition;

public class TrainingPosition {
	public static final int keeper = 0;
    public static final int wingBack = 1;
    public static final int centralDefender = 2;
    public static final int winger = 3;
    public static final int innerMidfielder = 4;
    public static final int forward = 5;
    public static final int setPiece = 6;
    
    // Convert an on field position to a training position
    public static int getTrainingPosition(int playerPosition)
    {
    	int trainPosition = keeper;
    	switch (playerPosition)
    	{
    		case ISpielerPosition.keeper:
    			break;
    		case ISpielerPosition.rightBack:
    		case ISpielerPosition.leftBack:
    			trainPosition = wingBack;
    			break;
    		case ISpielerPosition.rightCentralDefender:
    		case ISpielerPosition.middleCentralDefender:
    		case ISpielerPosition.leftCentralDefender:
				trainPosition = centralDefender;
				break;
    		case ISpielerPosition.rightWinger:
    		case ISpielerPosition.leftWinger:
    			trainPosition = winger;
    			break;
    		case ISpielerPosition.leftInnerMidfield:
    		case ISpielerPosition.centralInnerMidfield:
    		case ISpielerPosition.rightInnerMidfield:
    			trainPosition = innerMidfielder;
    			break;
    		case ISpielerPosition.leftForward:
    		case ISpielerPosition.centralForward:
    		case ISpielerPosition.rightForward:
    			trainPosition = forward;
    			break;
    	}
    	return trainPosition;
    }
}
