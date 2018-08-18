package com.levi.route.api.util;

public class HaversinCalculatorUtil {

	public static double distanceBetweenTwoPoints(double startLat, double startLong,
            double endLat, double endLong) {

		double dLat  = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat   = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);			
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return 6371 * c;
	}

	public static double haversin(double val) {
			return Math.pow(Math.sin(val / 2), 2);
	}
	
}