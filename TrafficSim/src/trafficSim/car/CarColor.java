package trafficSim.car;

import java.awt.Color;

import trafficSim.Main;

/**
 * Color for a car
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public class CarColor {

	//-----------------------------------------------------------------
	// Attributes
	//-----------------------------------------------------------------
	
	private static final int COLOR_BROWN = 0;
	private static final int COLOR_CYAN = 1;
	private static final int COLOR_YELLOW = 2;
	private static final int COLOR_PURPLE = 3;
	private static final int COLOR_GREEN = 4;
	private static final int COLOR_ORANGE = 5;
	
	private Color color;
	
	//-----------------------------------------------------------------
	// Methods
	//-----------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public CarColor() {
		int n = Main.getRandom().nextInt(6);
		float s = 0.5f, b = 0.65f;
		
		switch(n) {
		case COLOR_BROWN:
			color = Color.getHSBColor(0.1f, s, b);
			break;
		case COLOR_CYAN:
			color = Color.CYAN;
			break;
		case COLOR_YELLOW:
			color = Color.YELLOW;
			break;
		case COLOR_PURPLE:
			color = Color.getHSBColor(0.7f, s,b);
			break;
		case COLOR_GREEN:
			color = Color.getHSBColor(0.3f, s,b);
			break;
		case COLOR_ORANGE:
			color = Color.ORANGE;
			break;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getColor() {
		return this.color;
	}
}
