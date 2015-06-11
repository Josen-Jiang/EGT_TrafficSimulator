package trafficSim.utils.norms;


/**
 * 
 * @author javi
 *
 */
public class NormPrecondition {
	
	/**
	 * 
	 */
	private SetOfPredicatesWithTerms predicatesWithTerms;
		
	/**
	 * 
	 */
	public NormPrecondition() 	{
		this.predicatesWithTerms = new SetOfPredicatesWithTerms();
	}
	
	/**
	 * 
	 * @param predicates
	 */
	public NormPrecondition(SetOfPredicatesWithTerms predicatesWithTerms)
	{
		this();
		
		for(String p : predicatesWithTerms.getPredicates())	{
			this.predicatesWithTerms.add(p, predicatesWithTerms.getTerms(p));
		} 
	}

	/**
	 * 
	 * @param predicates
	 */
	public void add(SetOfPredicatesWithTerms predicates)
	{
		for(String p : predicates.getPredicates())
			for(String t : predicates.getTerms(p))
				this.add(p,t);
	}
	
	/**
	 * 
	 * @param predicate
	 * @param terms
	 */
	public void add(String predicate, String term) {
		this.predicatesWithTerms.add(predicate, term);
	}
	
	/**
	 * 
	 * @return
	 */
	public SetOfPredicatesWithTerms getPredicatesWithTerms()	{
		return this.predicatesWithTerms;
	}
	
	/**
	 * 
	 * @return
	 */
	public SetOfPredicates getPredicates()	{
		return this.predicatesWithTerms.getPredicates();
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public SetOfTerms getTerms(String p) 	{
		return this.predicatesWithTerms.getTerms(p);
	}
	
	/**
	 * 
	 * @param precondition
	 * @return
	 */
	public boolean equals(NormPrecondition precondition) 	{
		return this.predicatesWithTerms.equals(precondition.getPredicatesWithTerms());
	}
	
	/**
	 * 
	 */
	public String toString()
	{
		return this.predicatesWithTerms.toString();
	}
}
