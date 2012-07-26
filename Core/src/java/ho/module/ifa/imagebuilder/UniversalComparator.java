package ho.module.ifa.imagebuilder;


import ho.module.ifa.FlagLabel;

import java.util.Comparator;
import java.util.Date;

public class UniversalComparator implements Comparator<Object> {
	private int direction;
	
	public UniversalComparator(int direction) {
		this.direction = direction;
	}
	
    @Override
	public int compare(Object obj1, Object obj2) {
    	int ret = 0;

    	if (obj1 instanceof String) {
            int i = prepairForCompare(obj1).compareTo( prepairForCompare(obj2) );   
    		ret = ( 0 != i ) ? i : ((String)obj2).compareTo( (String)obj1 );
    	}
    	else if (obj1 instanceof Integer) {
    		ret = ((Integer)obj1).compareTo((Integer)obj2);
    	}
    	else if (obj1 instanceof Double) {
    		ret = ((Double)obj1).compareTo((Double)obj2);
    	}
    	else if (obj1 instanceof Date) {
    		ret = ((Date)obj1).compareTo((Date)obj2);
    	}
        else if(obj1 instanceof FlagLabel)
        {
            String tmp1 = ((FlagLabel)obj1).getCountryName();
            String tmp2 = ((FlagLabel)obj2).getCountryName();
            int i = prepairForCompare(tmp1).compareTo(prepairForCompare(tmp2));   
            ret = ( 0 != i ) ? i : (((FlagLabel)obj1).getCountryName()).compareTo(((FlagLabel)obj2).getCountryName());
        }
    	
        return ((ret == 0) ? 1 : ret) * direction;
    }
    
    private String prepairForCompare( Object o )
    {
        return ((String)o).toLowerCase().replace( 'ä', 'a' )
                                        .replace( 'ö', 'o' )
                                        .replace( 'ü', 'u' )
                                        .replace( 'ß', 's' )
                                        .replace( 'í', 'i' )
                                        .replace( '\u010D', 'c' );//?
        
        
    }
}