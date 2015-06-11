package trafficSim.utils;

import java.util.List;
import java.util.Map;

import trafficSim.norms.tree.TrafficPredicateInconsistencies;
import trafficSim.utils.norms.SetOfPredicates;
import trafficSim.utils.norms.SetOfPredicatesWithTerms;
import trafficSim.utils.norms.SetOfTerms;
import trafficSim.utils.norms.TermsOntology;

/**
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public class TrafficGrammar {

	/**
	 * 
	 */
	public static TermsOntology termsOntology;

	/**
	 * 
	 */
	private SetOfPredicates predicates;

	/**
	 * 
	 */
	private SetOfTerms terms;

	/**
	 * 
	 */
	public TrafficGrammar()
	{
		this.predicates = new SetOfPredicates();
		this.terms = new SetOfTerms();
		termsOntology = new TermsOntology();

		

		this.createTermsOntology();
	}

	/**
	 * 
	 */
	private void createTermsOntology()
	{
		termsOntology.addTerm("*");
		
		termsOntology.addTerm("car");
		termsOntology.addTerm("<");
		termsOntology.addTerm(">");
		termsOntology.addTerm("^");
		termsOntology.addTerm("v");
		
		termsOntology.addTerm("noCar");
		termsOntology.addTerm("-");
		termsOntology.addTerm("w");
		
		termsOntology.addRelationship("car", "*");
		termsOntology.addRelationship("noCar", "*");
		
		termsOntology.addRelationship("<", "car");
		termsOntology.addRelationship("^", "car");
		termsOntology.addRelationship(">", "car");
		termsOntology.addRelationship("v", "car");
		
		termsOntology.addRelationship("-", "noCar");
		termsOntology.addRelationship("w", "noCar");
	}
//
//	/**
//	 * Generates the scope of a car
//	 * 
//	 * @param carMap
//	 * @param car
//	 * @return
//	 */
//	public AgentContext getAgentContext(long carId, View view)
//	{
//		TrafficView tView = (TrafficView)view;
//		CarContext context = CarContextFactory.getCarContextIn(tView, carId, CarContext.Type.Front);
//
//		return context;
//	}
//
//	/**
//	 * 
//	 */
//	@Override
//	public List<AgentAction> getAgentActions(long agentId, ViewStream viewStream) {
//		List<AgentAction> actions = new ArrayList<AgentAction>();
//		actions.add(CarAction.Go);
//
//		return actions;
//	}
//
//	/**
//	 * 
//	 */
//	@Override
//	public List<AgentAction> getParentActions(AgentAction action) {
//		return null;
//	}

	//--------------------------------------------------------------------------------
	// Private methods
	//--------------------------------------------------------------------------------


//	/**
//	 * 
//	 */
//	@Override
//	public SetOfPredicatesWithTerms getPredicates(AgentContext context)
//	{
//		return FactFactory.generatePredicates(context);
//	}
//

	/**
	 * 
	 */
	public TermsOntology getTermsOntology() 	{
		return termsOntology;
	}

	/**
	 * 
	 */
	public SetOfPredicates getPredicates() {
		return this.predicates;
	}

	/**
	 * 
	 */
	public SetOfTerms getTerms() {
		return this.terms;
	}
	
//		/**
//		 * 
//		 * @return
//		 */
//	public boolean isConsistent(SetOfPredicatesWithTerms predicates)
//	{ 
//		Map<Integer, List<String>> inconsistencies = TrafficPredicateInconsistencies.getAll();
//
//		// Check each inconsistency
//		for(Integer incKey : inconsistencies.keySet())
//		{
//			List<String> inc = inconsistencies.get(incKey);
//			int matches = 0;
//
//			for(String p : predicates.getPredicates())
//			{
//				DefaultMetrics.nodesTracker.incNumVisitedPredicates();
//				
//				String term = predicates.getTerms(p).get(0);
//				if(term.toString().equals(inc.get(matches)))
//				{
//					matches++;
//					if((matches + 1) > inc.size()) {
//						return false;
//					}
//				}
//			}
//		}
//		return true;
//	}

	///**
	//* 
	//* @param context
	//* @return
	//*/
	//private List<AgentContext> getAllChildContexts(CarContext context)
	//{
	//	List<AgentContext> allChildContexts =  new ArrayList<AgentContext>();
	//	CarContext carContext = context;
	//	CarContext child;
	//	
	//	int numRows = carContext.getNumRows();
	//	int numCols = carContext.getNumCols();
	//	
	//	for(int row=0; row<numRows; row++)
	//	{
	//		for(int col=0; col<numCols; col++) 
	//		{
	//			// Only generate children for those positions that contain the value "Anything"
	//			String codDesc = carContext.get(row,col);
	//			
	//			if(TrafficStateManager.getType(codDesc) == StateType.Anything)
	//			{
	//				for(String desc : CarScopeCellDomains.getDomain(row, col))
	//				{
	//					child = new CarContext(Type.Front);
	//					child.copy(carContext);
	//					
	//					String cellCodDesc = TrafficStateCodifier.codify(desc);
	//					child.set(row, col, cellCodDesc);
	//					allChildContexts.add(child);
	//				}
	//			}
	//		}
	//	}
	//	return allChildContexts;
	//}
}
