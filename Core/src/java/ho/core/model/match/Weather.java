package ho.core.model.match;

public enum Weather {

	RAINY(0),
	OVERCAST(1),
	PARTIALLY_CLOUDY(2),
	SUNNY(3);
	
    private final int id;
    private Weather(int id) {
    	this.id = id;
    }
    
    public int getId() {
    	return this.id;
    }
    
}
