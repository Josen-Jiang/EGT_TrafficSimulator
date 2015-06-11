package trafficSim.style;


import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import repast.simphony.visualization.visualization2D.style.DefaultStyle2D;
import trafficSim.agent.TrafficLight;
import edu.umd.cs.piccolo.nodes.PText;

/**
 * AgentStyle2D - Colors agents based on source positions.
 *
 * @author Jan Koeppen (jankoeppen@gmx.net)
 *
 */
public class TrafficLightStyle2D extends DefaultStyle2D {

	//----------------------------------------------------------
	// Attributes 
	//----------------------------------------------------------
	int width = 18;
	int height = 18;

	//----------------------------------------------------------
	// Methods 
	//----------------------------------------------------------

	/**
	 * Paint method 
	 */
	@Override
	public Paint getPaint(Object o){
		return ((TrafficLight)o).getColor();
	}

	/**
	 * Returns the stroke to paint
	 */
	public Stroke getStroke(Object o){
		return new BasicStroke();
	}

	/**
	 * Returns the rectangle to paint
	 */
	@Override
	public Rectangle2D getBounds(Object object) {
		return new Rectangle2D.Float(0, 0, width, height);
	}

	/**
	 * Returns the text to paint
	 */
	@Override
	public PText getLabel(Object object) {
		return null;
	}
}