package trafficSim.utils.norms;

import java.util.List;

/**
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public class NormativeSystem {

	/**
	 * 
	 */
	private NormSet normSet;
	
	/**
	 * 
	 */
	public NormativeSystem() {
		this.normSet = new NormSet();
	}
	
	/**
	 * 
	 * @param norm
	 */
	public void add(Norm norm) {
		this.normSet.add(norm);
	}
	
	/**
	 * 
	 * @param norm
	 */
	public void remove(Norm norm) {
		this.normSet.remove(norm);
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return this.normSet.size();
	}
	
	/**
	 * 
	 * @param norm
	 * @return
	 */
	public boolean contains(Norm norm)
	{
		return this.normSet.contains(norm);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Norm> getNorms() {
		return this.normSet.getNorms();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Norm getNormWithId(int id) {
		return this.normSet.getNormWithId(id); 
	}
}
