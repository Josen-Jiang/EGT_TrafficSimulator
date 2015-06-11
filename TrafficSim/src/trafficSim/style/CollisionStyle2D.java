package trafficSim.style;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import repast.simphony.visualization.visualization2D.style.DefaultStyle2D;
import sl.shapes.StarPolygon;
import trafficSim.agent.Collision;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

/**
 * CarStyle2D - Colors agents based on source positions.
 *
 * @author Javier Morales
 *
 */
public class CollisionStyle2D extends DefaultStyle2D {

	/**
	 * 
	 */
	protected Shape s = new StarPolygon(0,0,15,10,12); 
	
		
	//----------------------------------------------------------
	// Attributes 
	//----------------------------------------------------------
	/**
	 * 
	 */
	int width = 30;
	
	/**
	 * 
	 */
	int height = 30;

	//----------------------------------------------------------
	// Methods 
	//----------------------------------------------------------

	public PNode getPNode(Object object, PNode node) {
		PPath path = new PPath(s);
		path.setBounds(rect);
		return path;
	}
	
	/**
	 * Paint method 
	 */
	@Override
	public Paint getPaint(Object o){
		Collision col = (Collision)o;
		if(col.isViolation())
			return Color.PINK;
		
		return Color.RED;
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
	public PText getLabel(Object object)
	{
//		Collision col = (Collision) object;
		PText label= new PText("");
//		label.setOffset((width/6.0),(height));
//		label.setTextPaint(Color.BLACK);
//		
		return label;
	}
}