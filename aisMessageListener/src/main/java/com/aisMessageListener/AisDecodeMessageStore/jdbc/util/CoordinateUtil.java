package com.aisMessageListener.AisDecodeMessageStore.jdbc.util;

import org.postgresql.geometric.PGpoint;

/**
 * TODO java doc
 */
public class CoordinateUtil {

    /**
     * TODO java doc
     */
    private CoordinateUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * TODO java doc
     */
    public static PGpoint getCoord(Float latitude, Float longitude) {
        return new PGpoint(latitude, longitude);
    }
}
