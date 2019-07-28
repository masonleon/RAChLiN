package com.aisMessageListener.AisDecodeMessageStore.jdbc.util;

import org.postgresql.geometric.PGpoint;

public class CoordinateUtil {

    private CoordinateUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static PGpoint getCoord(Float latitude, Float longitude) {
        return new PGpoint(latitude, longitude);
    }
}
