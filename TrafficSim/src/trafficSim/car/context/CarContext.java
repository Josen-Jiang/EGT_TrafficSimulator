package trafficSim.car.context;

import trafficSim.map.TrafficMatrix;

/**
 *  The scope of a car. This is the part of the model visible by a car
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public class CarContext extends TrafficMatrix {

	/**
	 * 
	 */
	public static int NUM_ROWS = 1;
	
	/**
	 * 
	 */
	public static int NUM_COLS = 3;
	
	/**
	 * 
	 */
	protected Type type;
	
	/**
	 * 
	 */
	public CarContext(Type type)
	{
		super(NUM_ROWS, NUM_COLS);
		this.type = type;
	}
	
	/**
	 * 
	 * @return
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * 
	 * @author Javier Morales (jmoralesmat@gmail.com)
	 *
	 */
	public enum Type {
		Left,
		Front,
		Right
	}
	
	/**
	 * 
	 */
  public boolean equals(CarContext otherContext)
	{
		return this.getDistance(otherContext) == 0;
  }
	
	/**
	 * 
	 */
	public String toString()
	{
		return "|" + super.toString() + "|";
	}
}
