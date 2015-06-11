package trafficSim.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jess.JessEvent;
import jess.JessException;
import jess.JessListener;
import jess.Rete;
import trafficSim.Main;
import trafficSim.agent.Car;
import trafficSim.car.context.CarContext;
import trafficSim.config.TrafficConfiguration;
import trafficSim.factory.FactFactory;
import trafficSim.factory.FactFactory.FactType;
import trafficSim.utils.norms.Norm;
import trafficSim.utils.norms.NormPrecondition;
import trafficSim.utils.norms.NormativeSystem;
import trafficSim.utils.norms.SetOfPredicatesWithTerms;

/**
 * Reasoner for the agent. Contains the rule engine to do reasoning
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public class CarReasoner implements JessListener {

	//------------------------------------------------------------
	// Attributes																															
	//------------------------------------------------------------

	/**
	 * The state of the reasoner
	 */
	private CarReasonerState state;

	/**
	 * Template to define a car (and noCar) and its properties for JESS rules
	 */
	private String leftPositionTemplate = "(deftemplate l (slot content))";
	private String frontPositionTemplate = "(deftemplate f (slot content))";
	private String rightPositionTemplate = "(deftemplate r (slot content))";

	/**
	 * The current norm set
	 */
	private NormativeSystem normativeSystem;

	/**
	 * THe norms that have been fired in the current step
	 */
	private List<Norm> applicableNorms;

	/**
	 * The rule engine
	 */
	private Rete ruleEngine;

	/**
	 * The last applied norm
	 */
	private Norm normToApply;

	/**
	 * The last violated norm
	 */
	private Norm normToViolate;

	/**
	 * 
	 */
	private boolean casualStop;

	//------------------------------------------------------------
	// Constructors
	//------------------------------------------------------------

	/**
	 * Constructor
	 */
	public CarReasoner()
	{
		this.state = CarReasonerState.NoNormActivated;
		this.casualStop = false;

		ruleEngine = new Rete();

		// Add this reasoner ass a listener of the rule engine
		ruleEngine.addJessListener(this);
		ruleEngine.setEventMask(ruleEngine.getEventMask() | JessEvent.DEFRULE_FIRED);

		try
		{
			// Add templates to knowledge base
			ruleEngine.reset();
			ruleEngine.eval(leftPositionTemplate);
			ruleEngine.eval(frontPositionTemplate);
			ruleEngine.eval(rightPositionTemplate);

		}
		catch (JessException e) {
			e.printStackTrace();
		}

		normativeSystem = new NormativeSystem();
		applicableNorms = new ArrayList<Norm>();
	}

	//------------------------------------------------------------
	// Methods
	//------------------------------------------------------------

	/**
	 * 
	 */
	public void reset()
	{
		this.applicableNorms.clear();
		this.normToViolate = null;
		this.normToApply = null;
	}

	/**
	 * 
	 */
	public void reason()
	{
		// Reason process
		try {
			ruleEngine.run();
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Does reasoning to activate rules in base of the facts in the knowledge base 
	 */
	public CarAction decideAction(Car car, CarContext context) {
		boolean violate;
		int violateProb;
		this.state = CarReasonerState.NoNormActivated;

		Random rand = Main.getRandom();

		// Sometimes a car stops to buy tobacco or the newspaper
		//		int numTicksTraveling = myCar.getNumTicksTraveling();
		//		int numTicksToStop = myCar.getNumTicksToStop();
		//		int tickToStopAt = myCar.getTickToStopAt();
		//		
		//		if(numTicksTraveling >= tickToStopAt &&
		//				numTicksTraveling < (tickToStopAt+numTicksToStop))
		//		{
		//			this.casualStop = true;
		//			return CarAction.Stop;
		//		}

		this.reset();

		// Add world facts
		SetOfPredicatesWithTerms predicates = FactFactory.generatePredicates(context);
		this.addFacts(predicates);

		// Collided cars remain stopped
		if(car.isCollided()) {
			return CarAction.Stop;
		}

		this.reason();

		// Remove random component of JESS
		Collections.sort(applicableNorms);

		// Obtain next supposed action to do according to the norm specification
		// TODO: Maybe here in the future several norms are fired for a certain situation
		// and we will have to choose what norm to apply (in base of some criteria).
		for(Norm n : applicableNorms)
		{
			//			System.out.println("Norm " + n.getName() + " fired with scope:");
			//			System.out.println(n.getContext());
			violate = false;

			// Decide if applying the norm or not 
			violateProb = (int)(TrafficConfiguration.SIM_NORM_VIOLATION_RATE * 100.0f);
			int num = rand.nextInt(100)+1;
			violate = (num <= violateProb) ? true : false;

			// Randomly choose if applying the norm or not. Case apply the norm
			if(violate)
			{
				this.normToViolate = n;
				state = CarReasonerState.NormWillBeViolated;
				return CarAction.Go;
			}
			else
			{
				this.normToApply = n;
				state = CarReasonerState.NormWillBeApplied;

				CarAction action = (CarAction)n.getAction();
				return action.getOpposite();
			}
		}
		// Let the facts base empty and return the action chosen by the car
		applicableNorms.clear();

		return CarAction.Go;
	}
	
	/**
	 * Adds a new rule to the knowledge base of the car if the norm doesn't exist yet
	 */
	public void addNorm(Norm norm)
	{
//		CarContext context = (CarContext)norm.getPrecondition();
		NormPrecondition precondition = norm.getPrecondition();
		String facts = FactFactory.generateFacts(precondition.getPredicatesWithTerms(), FactType.Norm);

		// Generate rule
		String jessRule = "(defrule " + norm.getName() + " \"N\" "+ facts + 
//				(facts.length() > 5? "(and " + facts + ")" : "") +   
				"=> )";

		try
		{
			normativeSystem.add(norm);
			ruleEngine.eval(jessRule);
		}
		catch (JessException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Removes a norm from the facts base, but the norm will
	 * exist yet in our memory
	 *
	 * @param norm
	 */
	public void removeNorm(Norm norm) {
		try
		{
			normativeSystem.remove(norm);
			ruleEngine.unDefrule(norm.getName());
		}
		catch (JessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a collection of facts to the knowledge base of the car. A fact is a
	 * car Y not collided which exists in the scope of the car X. In the fact we
	 * save position, direction, distance and velocity of the car
	 * 
	 * @param scope The scope of the car
	 */
	public String addFacts(SetOfPredicatesWithTerms predicates) 
	{
		String facts = FactFactory.generateFacts(predicates, FactType.CarReasoner);
		
		try
		{
			// Clear previous facts and add new ones
			ruleEngine.eval("(reset)");
			ruleEngine.eval(facts);
		}
		catch (JessException e) {
			e.printStackTrace();
		}		
		return facts;
	}

	/**
	 * Returns true if the last applicable norm was finally applied. False else
	 * 
	 * @return
	 */
	public CarReasonerState getState() {
		return state;
	}

	/**
	 * Returns the last norm that has been applied by the car
	 * 
	 * @return
	 */
	public Norm getNormToApply() {
		return this.normToApply;
	}

	/**
	 * Returns the last norm that has been violated by the car
	 * @return
	 */
	public Norm getNormToViolate() {
		return this.normToViolate;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCasualStop() {
		return this.casualStop;
	}

	//--------------------------------------------------------------------------------
	// Rules
	//--------------------------------------------------------------------------------

	/**
	 * Fired when a rule has been activated. Updates the linked norm
	 */
	public void eventHappened(JessEvent je) throws JessException
	{
		int normId = -1;
		int type = je.getType();

		switch (type) {
		case JessEvent.DEFRULE_FIRED:
			normId = obtainFiredRule(je.getObject());
			break;
		}

		// Activate the norm associated to this rule
		applicableNorms.add(normativeSystem.getNormWithId(normId));
	}

	/**
	 * Returns the name of the fired rule
	 * 
	 * @param o
	 * @return
	 */
	private int obtainFiredRule(Object o)
	{
		String s = o.toString();
		int ind = s.indexOf("MAIN::");
		int i = ind + 6;
		int j = i;

		while(!s.substring(j, j+1).equals(" ")) {
			j++;
		}
		return Integer.valueOf(s.substring(i+1, j));
	}

	/**
	 * 
	 * @return
	 */
	public List<Norm> getApplicableNorms()
	{
		return this.applicableNorms;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Norm> getNorms()
	{
		return this.normativeSystem.getNorms();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasNorms()
	{
		return this.getNorms().size() > 1;
	}
}
