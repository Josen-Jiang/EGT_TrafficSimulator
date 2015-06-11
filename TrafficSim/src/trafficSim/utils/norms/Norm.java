package trafficSim.utils.norms;

import trafficSim.agent.AgentAction;


/**
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public class Norm implements Comparable<Norm> {

	//------------------------------------------------------------------------------------------	
	// Attributes
	//------------------------------------------------------------------------------------------
	
	/**
	 * 
	 */
	private int id;

	/**
	 * 
	 */
	private NormPrecondition precondition;

	/**
	 * 
	 */
	private NormModality modality;
	
	/**
	 * 
	 */
	private AgentAction action;

	
	//------------------------------------------------------------------------------------------	
	// Methods
	//------------------------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public Norm(NormPrecondition precondition, NormModality modality, AgentAction action) 
	{
		this.precondition = precondition;
		this.modality = modality;
		this.action = action;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return "N" + id;
	}
	
	/**
	 * 
	 */
	public NormPrecondition getPrecondition() 	{
		return this.precondition;
	}
	
	/**
	 * 
	 * @return
	 */
	public NormModality getModality() {
		return this.modality;
	}

	/**
	 * 
	 * @return
	 */
	public AgentAction getAction() {
		return this.action;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(int id)	{
		this.id = id;
	}
	
	/**
	 * 
	 */
	public int compareTo(Norm otherNorm)
	{
		if(otherNorm.getId() < this.id) {
			return 1;
		}
		else if(otherNorm.getId() > this.id) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString()
	{
		return this.getName() + ": (" + this.precondition + ", " + modality + "(" + action + "))";
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescription()
	{
		return "(" + this.precondition + ", " + modality + "(" + action + "))";
	}
}
