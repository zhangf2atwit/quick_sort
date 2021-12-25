package edu.wit.cs.comp2350;

/**
 * 
 * holds information about the earth latitude/longitude about a coordinate
 *
 */
public class Coord {

	private double latitude;
	private double longitude;
	private String name;
	private double dist;

	/**
	 * Basic constructor for a Coord, which sets the lat and lon, and sets the
	 * distance of the Coord to 0
	 * 
	 * @param lat The latitude of the coordinate
	 * @param lon The longitude of the coordinate 
	 */
	public Coord(double lat, double lon) {
		latitude = lat;
		longitude = lon;
		dist = 0;
		name = "start";
	}

	/**
	 * Constructor for a Coord, which sets the lat and lon, and sets the
	 * distance of the Coord to the distance to the origin parameter in km
	 * 
	 * @param lat The latitude of the coordinate
	 * @param lon The longitude of the coordinate 
	 * @param origin The coordinate that the distance of the Coord should be based on 
	 */
	public Coord(double lat, double lon, Coord origin) {
		latitude = lat;
		longitude = lon;
		dist = distTo(this, origin);
		name = "unnamed";
	}

	/**
	 * Constructor for a Coord, which sets the lat and lon, and sets the
	 * distance of the Coord to the distance to the origin parameter in km
	 * 
	 * @param lat The latitude of the coordinate
	 * @param lon The longitude of the coordinate 
	 * @param origin The coordinate that the distance of the Coord should be based on 
	 * @param n The name of the location 
	 */
	public Coord(double lat, double lon, Coord origin, String n) {
		latitude = lat;
		longitude = lon;
		dist = distTo(this, origin);
		name = n;
	}

	/**
	 * Getter for the dist variable in the Coord
	 * 
	 * @return the distance of the Coord to its start location
	 */
	public double getDist() {
		return dist;
	}

	/**
	 * equality operator based on the latitude/longitude info only
	 * 
	 * @param that the Coord to compare lat/lon against
	 * @return true iff the Coords match exactly 
	 */
	@Override
	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (that.getClass() != this.getClass())
			return false;


		Coord c = (Coord) that;
		return this.latitude == c.latitude && this.longitude == c.longitude;
	}

	/**	
	 * @return a comma-separated latitude/longitude string 
	 */
	@Override
	public String toString() {
		return String.format("%.7f,%.7f", latitude, longitude);
	}

	/**
	 * If equals() is overridden, hashCode should be as well.
	 * 
	 * @return a hashCode based on lat/lon
	 */
	@Override
	public int hashCode() {
		return Double.hashCode(latitude) + Double.hashCode(longitude);
	}

	/**	
	 * @return a comma-separated latitude/longitude string with the Coord's name tacked on
	 */
	public String toNamedString() {
		return String.format("%.7f,%.7f (%s)", latitude, longitude, name);
	}

	/**
	 * @param color Color to include in Coord string
	 * @return a comma-separated latitude/longitude string with the Coord's color and name tacked on
	 */
	public String toColorString(String color) {
		return String.format("%.7f,%.7f,%s,marker,%s", latitude, longitude, color, name);
	}

	/**
	 * calculates the distance between two coords (assumes earth is spherical)
	 * 
	 * @param here
	 * @param there
	 * @return distance in km from here to there
	 */
	private double distTo(Coord here, Coord there) {

		final int R = 6371; // Radius of the earth

		double lat1 = here.latitude; double lon1 = here.longitude;
		double lat2 = there.latitude; double lon2 = there.longitude;

		Double latDistance = Math.toRadians(lat2 - lat1);
		Double lonDistance = Math.toRadians(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c;

		return distance;
	}
}
