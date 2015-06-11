package trafficSim.utils.norms;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public class NormSet {

	/**
	 * 
	 */
	private List<Norm> norms;
	
	/**
	 * 
	 */
	public NormSet() {
		this.norms = new ArrayList<Norm>();
	}
	
	/**
	 * 
	 * @param norm
	 */
	public void add(Norm norm) {
		if(!this.norms.contains(norm))
			this.norms.add(norm);
	}
	
	/**
	 * 
	 * @param norm
	 */
	public void add(List<Norm> norms) {
		for(Norm norm : norms)
			if(!this.norms.contains(norm))
				this.norms.add(norm);
	}
	
	/**
	 * 
	 * @param norm
	 */
	public void remove(Norm norm) {
		this.norms.remove(norm);
	}

	/**
	 * 
	 * @return
	 */
	public int size() {
		return this.norms.size();
	}

	

	/**
	 * 
	 * @param norm
	 * @return
	 */
	public boolean contains(Norm norm)
	{
		for(Norm n : this.norms) {
			if(n.getId() == norm.getId())
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param norm
	 * @return
	 */
	public List<Norm> getNorms() {
		return this.norms;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Norm getNormWithId(int id)
	{
		Norm norm = null;
		for(Norm n : this.norms) {
			if(n.getId() == id) {
				norm = n;
				break;
			}
		}
		return norm;
	}
	
	/**
	 * 
	 */
	public void clear() {
		this.norms.clear();
	}
	
//	/**
//	 * 
//	 * @param norm
//	 * @return
//	 */
//	public int getNumMatches(Norm norm)
//	{
//		int count = 0;
//		for(Norm n : this.norms)
//		{
//			if(norm.getId() == n.getId())
//				count++;
//		}
//		return count;
//	}
}
