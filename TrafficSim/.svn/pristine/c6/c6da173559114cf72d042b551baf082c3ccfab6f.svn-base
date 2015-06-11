package trafficSim.utils.norms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author javi
 *
 */
public class SetOfPredicatesWithTerms {
		
	/**
	 * 
	 */
	private SetOfPredicates predicates;

	/**
	 * 
	 */
	private HashMap<String, SetOfTerms> OR_terms;
	
	/**
	 * 
	 */
	public SetOfPredicatesWithTerms()
	{
		this.predicates = new SetOfPredicates();
		this.OR_terms = new HashMap<String, SetOfTerms>();
	}
	
	/**
	 * 
	 * @param otherSet
	 */
	public SetOfPredicatesWithTerms(SetOfPredicatesWithTerms otherSet)
	{
		this();
		for(String p : otherSet.getPredicates()) {
			for(String t : otherSet.getTerms(p))
			{
//				DefaultMetrics.nodesTracker.incNumVisitedPredicates();
				this.add(p, t);
			}
		}
	}
	
	/**
	 * 
	 * @param p
	 * @param t
	 */
	public void add(String predicate, String term)
	{
		this.predicates.add(predicate);
		if(!this.OR_terms.containsKey(predicate))
		{
			this.OR_terms.put(predicate, new SetOfTerms());		
		}
		this.OR_terms.get(predicate).add(term);
//		this.sort();
	}
	
	/**
	 * 
	 * @param p
	 * @param t
	 */
	public void add(String predicate, SetOfTerms terms)
	{
		for(String term : terms) {
			this.add(predicate, term);
		}
	}
	
	/**
	 * 
	 * @param predicate
	 * @param term
	 */
	public void remove(String predicate, String term)
	{
		this.OR_terms.get(predicate).remove(term);
	}
	
	/**
	 * 
	 * @return
	 */
	public SetOfPredicates getPredicates()	{
		return this.predicates;
	}
	
//	/**
//	 * 
//	 * @return
//	 */
//	public SetOfTerms getTerms()	{
//		return new SetOfTerms(this.OR_terms.values());
//	}
	
	/**
	 * 
	 * @param predicate
	 * @return
	 */
	public SetOfTerms getTerms(String predicate)	{
		return this.OR_terms.get(predicate);
	}

	/**
	 * 
	 */
	public void sort()	{
		List<String> sortedPredicates = new ArrayList<String>();
		
		int i = this.predicates.indexOf("l");
		if(i != -1)
			sortedPredicates.add(predicates.get(i));
		i = this.predicates.indexOf("f");
		if(i != -1)
			sortedPredicates.add(predicates.get(i));
		i = this.predicates.indexOf("r");
		if(i != -1)
			sortedPredicates.add(predicates.get(i));
		
		predicates.clear();
		predicates.addAll(sortedPredicates);
//		Collections.sort(this.predicates);
	}
	
	/**
	 * 
	 * @param predicates
	 * @return
	 */
	public boolean contains(SetOfPredicatesWithTerms predicates)
	{
		for(String predicate: predicates.getPredicates())
			if(!this.contains(predicate, predicates.getTerms(predicate)))
					return false;
		
		return true;
	}
	
	/**
	 * 
	 * @param predicate
	 * @return
	 */
	public boolean contains(String predicate, String term)
	{
		for(String p : predicates)
		{
			List<String> myTerms = this.OR_terms.get(predicate);
			for(String t : myTerms)
			{
				if(p.equals(predicate) && t.equals(term))
					return true;	
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param predicate
	 * @return
	 */
	public boolean contains(String predicate, SetOfTerms terms)
	{
		for(String term : terms)
		{
			if(!this.contains(predicate, term))
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 */
	public void clear()
	{
		this.predicates.clear();
		this.OR_terms.clear();
	}
	
		/**
	 * 
	 * @param pwt
	 * @return
	 */
	public boolean equals(SetOfPredicatesWithTerms otherSet)
	{
		return this.toString().equals(otherSet.toString());
	}
	
	/**
	 * 
	 */
	public String toString()
	{
		String s = "";
		int i=0;
		
		for(String p : this.getPredicates())
		{
			int j=0;
			
			if(i>0) {
				s += "&";
			}
			s += p + "(";
			
			for(String t : this.getTerms(p))
			{
				if(j>0) {
					s += "|";
				}
				s += t;
				j++;
			}
			s += ")";
			i++;
		}
		return s;
	}
}
