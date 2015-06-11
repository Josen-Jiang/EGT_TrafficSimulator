package trafficSim.utils.norms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * 
 * @author javi
 *
 */
public class SetOfTerms extends ArrayList<String> {
	
	/**
	 * 
	 */
//	private HashMap<String, Term> terms = new HashMap<String, Term>();
	
	/**
	 * 
	 */
  private static final long serialVersionUID = -3888253546378482204L;

	/**
	 * 
	 */
	public SetOfTerms() {
		super();
		
	}
	
	/**
	 * 
	 * @param terms
	 */
	public SetOfTerms(Collection<String> terms) 	{
		super(terms);
	}
	
	/**
	 * 
	 * @param terms
	 * @return
	 */
	public boolean contains(SetOfTerms terms)
	{
		for(String t : terms)
			if(!this.contains(t))
				return false;
		return true;
	}
	/**
	 * 
	 * @param terms
	 * @return
	 */
	public boolean equals(SetOfTerms otherSet)
	{
		this.sort();
		otherSet.sort();
		
		int numTermsA = this.size();
		int numTermsB = otherSet.size();
		
		if(numTermsA != numTermsB)
			return false;
		
		for(int i=0; i<numTermsA; i++)
		{
			if(!this.get(i).equals(otherSet.get(i)))
				return false;
		}
		return true;
	}
	
	/**
	 * @return 
	 * 
	 */
	public boolean add(String term) 
	{
		if(!this.contains(term))
		{
			super.add(term);
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
	
//
///**
// * 
// * @param term
// * @return
// */
//public boolean contains(Term term)
//{
//	return terms.get(term.toString()) != null;
		
//	for(Term t : this)
//	{
//		if(t.equals(term))
//			return true;
//	}
//	return false;
//}

}
