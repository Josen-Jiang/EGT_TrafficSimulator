package trafficSim.style;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import repast.simphony.visualization.visualization2D.style.DefaultStyle2D;
import trafficSim.agent.Car;
import trafficSim.car.CarReasonerState;
import edu.umd.cs.piccolo.nodes.PText;

/**
 * CarStyle2D - Colors agents based on source positions.
 *
 * @author Javier Morales
 *
 */
public class CarStyle2D extends DefaultStyle2D {

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
		Car car = (Car)o;

		if(car.isCasualStop()) {
			return Color.WHITE;
		}
		if(car.isCollided()) {
			return Color.RED;
		} 
		else if(car.getReasonerState() == CarReasonerState.NormWillBeApplied) {
			return Color.GREEN;
		}
		else if(car.getReasonerState() == CarReasonerState.NormWillBeViolated) {
			return Color.MAGENTA;
		}
		else {
			return car.getColor();
		}

	}

	/**
	 * Returns the stroke to paint
	 */
	public Stroke getStroke(Object o){
		Car car = (Car)o;
		if(!car.isCollided() && car.getReasonerState() != CarReasonerState.NormWillBeApplied
				&& car.getReasonerState() != CarReasonerState.NormWillBeViolated &&
				!car.isCasualStop()) {
			return new BasicStroke();
		}
		return null;
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
		PText label = null;

		Car c = (Car)object;
		int steps = c.getPosition().getDirection().get90DegreeStepsFromNorth();

		if(c.isCollided()) {
			label = new PText("");
		}
		else if(c.isCasualStop()) {
			label = new PText("S");
			label.setOffset((width/7.0),(height));
			label.setTextPaint(Color.RED);
		}
		else if(c.getReasonerState() == CarReasonerState.NormWillBeApplied)	{
			label = new PText(String.valueOf(c.getNormToApply().getId()));
			label.setOffset((width/7.0),(height));
		}
		else if(c.getReasonerState() == CarReasonerState.NormWillBeViolated)	{
			label = new PText(String.valueOf(c.getNormToViolate().getId()));
			label.setOffset((width/7.0),(height));
		}
		else
		{
			switch(steps) {
			case 0:
				label = new PText("^");
				break;
			case 1:
				label = new PText(">");
				break;
			case 2:
				label = new PText("v");
				break;
			case 3:
				label = new PText("<");
				break;
			}
			label.setOffset((width/3.5),(height));
		}

		return label;
	}
}