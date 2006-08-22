// %1127327012462:plugins%
/*
 * ITrainingPoint.java
 *
 * Created on 29. März 2005, 15:10
 */
package plugins;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public interface ITrainingPoint {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Mathod that returns the correct training point
     *
     * @param trtype Training type code
     * @param playerMatchPosition player position code
     *
     * @return training points earned in that match
     */
    public Double getTrainingPoint(int trtype, Integer playerMatchPosition);
}
