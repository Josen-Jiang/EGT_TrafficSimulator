package trafficSim.style;

import java.awt.Color;
import java.awt.Paint;

import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.visualization.visualization2D.style.ValueLayerStyle;
import trafficSim.config.TrafficConfiguration;

/**
 * NormStyle2D - Coloring of value layer (higher performance than agent color) used to paint background of blocking norms red and background of non-lane fields green.
 *
 * @author Jan Koeppen (jankoeppen@gmx.net)
 *
 */
public class CarMapPositionStyle2D implements ValueLayerStyle {
	
	//-----------------------------------------------------------------
	// Attributes
	//-----------------------------------------------------------------
	
//	/**
//	 * 
//	 */
//	private TrafficConfiguration config = Main.getConfig();
	
	/**
	 * 
	 */
  private final Color cWall = Color.getHSBColor(0.6f, 0.8f, 0.4f);
  
  /**
   * 
   */
  private final Color cRoad = Color.LIGHT_GRAY;

  /**
   * 
   */
  private ValueLayer layer;
  
	//-----------------------------------------------------------------
	// Methods
	//-----------------------------------------------------------------

  /**
   * Adds the new value layer
   */
  public void addValueLayer(ValueLayer layer) {
    this.layer = layer;
    
  }

  /**
   * Returns the color based on the value at given coordinates.
   */
  public Paint getPaint(double... coordinates) {
    double v = layer.get(coordinates);
      
    if (v == TrafficConfiguration.WALL_POSITION) return cWall;
    return cRoad;
  }
  
  /**
   * 
   */
  public int getRed(double... coordinates) {
    return 0; 
  }

  /**
   * 
   */
  public int getGreen(double... coordinates) {
    return 0;
  }

  /**
   * 
   */
  public int getBlue(double... coordinates) {
    return 0;
  }

  /**
   * 
   */
  public float getCellSize() {
    return 20.0f;
  }
}
