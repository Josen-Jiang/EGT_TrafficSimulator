package trafficSim.utils.norms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * 
 * @author javi
 *
 */
public class SetOfPredicates extends ArrayList<String> {
	
	/**
	 * 
	 */
  private static final long serialVersionUID = -2387415177640951583L;

	/**
	 * 
	 */
	public SetOfPredicates() {
		super();
	}
	
	/**
	 * 
	 * @param terms
	 */
	public SetOfPredicates(Collection<String> predicates) 	{
		super(predicates);
	}
	
	/**
	 * @return 
	 * 
	 */
	public boolean add(String predicate) 
	{
		if(!this.contains(predicate))
		{
			super.add(predicate);
			this.sort();
			
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 */
	public void sort() {
		Collections.sort(this);	
	}
}
