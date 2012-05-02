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
    public static final int osmosis = 7;
    
    // Convert an on field position to a training position
    public static int getTrainingPosition(int playerPosition)
    {
    	int trainPosition = keeper;
    	switch (playerPosition)
    	{
    		case ISpielerPosition.KEEPER:
    		case ISpielerPosition.keeper:
    			break;
    		case ISpielerPosition.BACK:
    		case ISpielerPosition.BACK_DEF:
    		case ISpielerPosition.BACK_OFF:
    		case ISpielerPosition.BACK_TOMID:
    		case ISpielerPosition.rightBack:
    		case ISpielerPosition.leftBack:
    			trainPosition = wingBack;
    			break;
    		case ISpielerPosition.CENTRAL_DEFENDER:
    		case ISpielerPosition.CENTRAL_DEFENDER_OFF:
    		case ISpielerPosition.CENTRAL_DEFENDER_TOWING:
    		case ISpielerPosition.rightCentralDefender:
    		case ISpielerPosition.middleCentralDefender:
    		case ISpielerPosition.leftCentralDefender:
				trainPosition = centralDefender;
				break;
    		case ISpielerPosition.WINGER:
    		case ISpielerPosition.WINGER_DEF:
    		case ISpielerPosition.WINGER_OFF:
    		case ISpielerPosition.WINGER_TOMID:
    		case ISpielerPosition.rightWinger:
    		case ISpielerPosition.leftWinger:
    			trainPosition = winger;
    			break;
    		case ISpielerPosition.MIDFIELDER:
    		case ISpielerPosition.MIDFIELDER_DEF:
    		case ISpielerPosition.MIDFIELDER_OFF:
    		case ISpielerPosition.MIDFIELDER_TOWING:
    		case ISpielerPosition.leftInnerMidfield:
    		case ISpielerPosition.centralInnerMidfield:
    		case ISpielerPosition.rightInnerMidfield:
    			trainPosition = innerMidfielder;
    			break;
    		case ISpielerPosition.FORWARD:
    		case ISpielerPosition.FORWARD_DEF:
    		case ISpielerPosition.FORWARD_TOWING:
    		case ISpielerPosition.leftForward:
    		case ISpielerPosition.centralForward:
    		case ISpielerPosition.rightForward:
    			trainPosition = forward;
    			break;
    	}
    	return trainPosition;
    }
}
