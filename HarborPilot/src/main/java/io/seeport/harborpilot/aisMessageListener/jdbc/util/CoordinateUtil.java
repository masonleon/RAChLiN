package io.seeport.harborpilot.aisMessageListener.jdbc.util;

import org.postgresql.geometric.PGpoint;

/**
 * Utility class for constructing and handling Postgres coordinate points.
 */
public class CoordinateUtil {

    /**
     * Prevent instantiation of utility class by deliberately not supporting public constructor and throwing an error
     * when accessed via reflection.
     */
    private CoordinateUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Gets a Postgres coordinate with a latitude and longitude value.
     * @param latitude a float
     * @param longitude a float
     * @return a PGPoint
     */
    public static PGpoint getCoord(Float latitude, Float longitude) {
        return new PGpoint(latitude, longitude);
    }
}
