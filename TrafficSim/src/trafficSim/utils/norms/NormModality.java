package trafficSim.utils.norms;

/**
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public enum NormModality {

	/**
	 * 
	 */
	Prohibition,
	
	/**
	 * 
	 */
	Obligation,
	
	/**
	 * 
	 */
	Permission;
	
	/**
	 * 
	 */
	public String toString()
	{
		switch(this)
		{
		case Prohibition: 	return "prh";
		case Obligation: 		return "obl";
		case Permission:	 	return "perm";
		default:						return "";
		}
	}
}

