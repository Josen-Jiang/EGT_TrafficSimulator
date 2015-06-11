package trafficSim.utils.norms;

import java.util.HashMap;
import java.util.List;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

/**
 * 
 * @author javi
 *
 */
public class TermsOntology {

	/**
	 * 
	 */
	private DirectedSparseMultigraph<String, NetworkEdge> termsGraph;

	/**
	 * 
	 */
	private SetOfTerms terms;
	
	/**
	 * 
	 */
	private HashMap<String, Integer> termsLevels;
	
	/**
	 * 
	 */
	public TermsOntology()
	{
		this.termsGraph = new DirectedSparseMultigraph<String, NetworkEdge>();
		this.termsLevels = new HashMap<String, Integer>();
		this.terms = new SetOfTerms();
	}
	
	/**
	 * 
	 * @param term
	 */
	public void addTerm(String term) 
	{
		// Add term if it does not exist
		if(!this.contains(term))
		{
			this.termsGraph.addVertex(term.toString());
			this.terms.add(term);
			this.termsLevels.put(term, 0);
		}
	}
	
	/**
	 * 
	 */
	public void addRelationship(String t1, String t2)
	{
		this.termsGraph.addEdge(new NetworkEdge(), t1, t2);
		this.termsLevels.put(t2, termsLevels.get(t1) + 1);
	}
	
	/**
	 * 
	 * @param norm
	 * @return
	 */
	public boolean contains(String term)
	{
		for(String t : this.terms)
		{
			if(t.equals(term))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public SetOfTerms getTerms()
	{
		return this.terms;
	}
	
	/**
	 * 
	 * @return
	 */
	public SetOfTerms getChildTerms(String term)
	{
		SetOfTerms childTerms = new SetOfTerms();
		for(NetworkEdge edge : this.termsGraph.getInEdges(term.toString()))
		{
			childTerms.add(this.termsGraph.getSource(edge));
		}
		return childTerms;
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 */
	public SetOfTerms getCoveredTerms(String term)
	{
		SetOfTerms childTerms = new SetOfTerms();
		this.addChildTerms(term, childTerms);
		
		return childTerms;
	}
	
	/**
	 * 
	 * @param term
	 * @param terms
	 */
	private void addChildTerms(String term, SetOfTerms terms)
	{
		SetOfTerms children = this.getChildTerms(term);
		
		if(children.isEmpty()) {
			terms.add(term);
			return;
		}
		else {
			for(String child : children)
				this.addChildTerms(child, terms);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getParentTerm(String term) 
	{
		for(NetworkEdge edge : this.termsGraph.getOutEdges(term)) {
			return this.termsGraph.getDest(edge);
		}
		return null;
	}
	
	/**
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public String getMostSpecifficGeneralisation(String t1, String t2)
	{		
		
		List<String> t1Parents = this.getParentTerms(t1); 
		List<String> t2Parents = this.getParentTerms(t2);
		
		for(String term : t1Parents) {
			
			if(t2Parents.contains(term))
			{
				return term;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 */
	public SetOfTerms getParentTerms(String term)
	{
		SetOfTerms parentTerms = new SetOfTerms();
		this.getParentTerms(term, parentTerms);
		
		return parentTerms;
	}
	
	/**
	 * 
	 * @param term
	 * @param parentTerms
	 */
	private void getParentTerms(String term, SetOfTerms parentTerms)
	{
		String parent = this.getParentTerm(term);
		if(parent != null)
		{
			parentTerms.add(parent);
			this.getParentTerms(parent, parentTerms);
		}
		return;		
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 */
	public int getLevel(String term)
	{
		if(!this.termsLevels.containsKey(term))
			return -1;
		
		return this.termsLevels.get(term);
	}
}
