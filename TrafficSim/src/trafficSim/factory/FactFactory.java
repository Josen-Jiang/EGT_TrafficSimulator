package trafficSim.factory;

import trafficSim.Main;
import trafficSim.car.context.CarContext;
import trafficSim.car.context.TrafficStateManager;
import trafficSim.car.context.TrafficStateManager.StateType;
import trafficSim.utils.norms.SetOfPredicatesWithTerms;
import trafficSim.utils.norms.TermsOntology;

/**
 * Facts generator tool. Generates facts for the car reasoner and to build the
 * condition (left part) of a norm. It adapts the facts to the format of the car
 * reasoner or the norm condition, in base of the FactType passed by parameter  
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public class FactFactory {

	/**
	 * Defines the type of a fact. A fact for a CarReasoner is used to know what is the current
	 * state of the world. A fact for a norm is used to define a situation that fires a norm
	 *   
	 * @author Javier Morales (jmoralesmat@gmail.com)
	 *
	 */
	public enum FactType {
		CarReasoner, Norm
	}

	/**
	 * Generates a string containing a set of facts
	 * 
	 * @param factType
	 * @param scope
	 * @return
	 */
	public static SetOfPredicatesWithTerms generatePredicates(CarContext context)
	{
//		List<Predicate> predicates = new ArrayList<Predicate>();
		SetOfPredicatesWithTerms predicatesWithTerms = new SetOfPredicatesWithTerms();
		int dimX = context.getNumCols();
		int dimY = context.getNumRows();

		// Add new facts for those cars that are not collided
		for(int row=0; row<dimY; row++)
		{ 
			for(int col=0; col<dimX; col++) 
			{
				String codDesc = context.get(row, col);
				StateType type = TrafficStateManager.getType(codDesc);
				String predicate = getPosition(col, dimX);
				String term = "";

				// Wall
				switch(type)
				{
				case Wall:				term = "w";		break;
				case Nothing:			term = "-";		break;
				case Collision:		term = "c";		break;
				case Car:					term = TrafficStateManager.getCarHeading(codDesc).getArrow();	break;
				default: 					break;
				}
				
				predicatesWithTerms.add(predicate, term);
			}
		}
		return predicatesWithTerms;
	}	

	/**
	 * 
	 * @param factType
	 * @param predicates
	 * @return
	 */
	public static String generateFacts(SetOfPredicatesWithTerms predicatesWithTerms, FactType factType)
	{
		String facts = "", refCarFact = "";
		String slotLeftSep, slotRightSep, slotValSep, assertLeftSep = "", assertRightSep = " ";

		// Set syntactic differences between CarReasoner and Norm facts
		slotLeftSep 	= (factType == FactType.CarReasoner ? "(" : "{");
		slotRightSep 	= (factType == FactType.CarReasoner ? ")" : "}");
		slotValSep 		= (factType == FactType.CarReasoner ? " " : " == ");

		if(factType == FactType.CarReasoner) {
			assertLeftSep = "(assert ";
			assertRightSep = ") ";
		}

		facts += assertLeftSep;
		facts += refCarFact;

		for(String predicate : predicatesWithTerms.getPredicates())
		{
			facts += "(" + predicate + slotLeftSep;
			int i=0;
			
			for(String term : predicatesWithTerms.getTerms(predicate))
			{
				TermsOntology ontology = Main.grammar.termsOntology;
				for(String childTerm : ontology.getCoveredTerms(term))
				{
					if(i>0) {
						facts += " || ";
					}
					facts += "content" + slotValSep + childTerm;
					i++;
				}
			}
			facts += slotRightSep + ")";
			
//			for(String term : predicatesWithTerms.getTerms(predicate))
//			{
//				if(i>0) {
//					facts += " || ";
//				}
//				facts += "content" + slotValSep + term;
//				i++;
//			}
//			facts += slotRightSep + ")";			
		}
		facts += assertRightSep;
		return facts;
	}

	
	//--------------------------------------------------------------------------------
	// Utilities
	//--------------------------------------------------------------------------------

	/**
	 * Returns the position of a car relative to us
	 * 
	 * @param x
	 * @param dimX
	 * @return
	 */
	private static String getPosition(int x, int dimX) {
		if(x < dimX/2)			return "l";
		else if(x > dimX/2)	return "r";
		else								return "f";
	}
//
//	/**
//	 * Returns the distance of a car relative to us
//	 * 
//	 * @param x
//	 * @param y
//	 * @param dimX
//	 * @param dimY
//	 * @return
//	 */
//	private static int getDistance(int x, int y, int dimX, int dimY)
//	{
//		double[] point1 = new double[2];
//		double[] point2 = new double[2];
//		point1[0] = dimX/2;
//		point1[1] = dimY;
//		point2[0] = x;
//		point2[1] = y;
//		return (int) Utilities.getDistance(point1, point2);
//	}
}
