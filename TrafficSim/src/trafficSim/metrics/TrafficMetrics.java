package trafficSim.metrics;

import repast.simphony.annotate.AgentAnnot;

/**
 * Metrics manager
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
@AgentAnnot(displayName = "Metrics Agent")
public class TrafficMetrics {

//	private IronAgent ironAgent;
//	private IronMetrics ironMetrics;
	private NormSetsFileManager normSetsFileManager;
	
	private double tick;
	
	/**
	 * 
	 */
//	private IronConfiguration config;
	
	/**
	 * 
	 */
	private long numNormClauseAdditions, numNormClauseRemovals;
	
	/**
	 * 
	 */
	private String metricsFileName = "metrics.dat";
	
	/**
	 * 
	 */
	private int IRONConvergenceTick;
	
	/**
	 * 
	 */
	private float NSEffAtIRONConvergenceTick, NSNecAtIRONConvergenceTick, 
	NSMinimalityAtIRONConvergenceTick, NSSimplicityAtIRONConvergenceTick;
	
	//-----------------------------------------------------------------
	// Methods
	//-----------------------------------------------------------------

	/**
	 * Constructor
	 *  
	 * @param mainClass
	 */
	public TrafficMetrics()
	{
//		this.ironAgent = ironAgent;
//		this.ironMetrics = ironAgent.getMetrics();
//		this.config = ironMetrics.getConfiguration();
		
		this.numNormClauseAdditions = 0;
		this.numNormClauseRemovals = 0;
		
		this.NSEffAtIRONConvergenceTick = 0;
		this.NSNecAtIRONConvergenceTick = 0;
		this.NSMinimalityAtIRONConvergenceTick = 0;
		this.NSSimplicityAtIRONConvergenceTick = 0;
		
//		this.getIRONConvergenceTick();
	}

//	/**
//	 * Computes the metrics for the simulator
//	 */
//	public void update() 
//	{
//		this.tick = RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
//		
//		this.numNormClauseAdditions += this.computeNumClauses(this.ironMetrics.getNormsAddedThisTick());
//		this.numNormClauseRemovals += this.computeNumClauses(this.ironMetrics.getNormsRemovedThisTick());
//		
//		if(this.tick == this.IRONConvergenceTick)
//		{
//			float NSeff = 0f, NSnec = 0f;
//			List<Norm> NS =  this.ironAgent.getNormativeSystem().getNorms();
//			
//			Goal goal = config.getSystemGoals().get(0);
//			for(Norm norm : NS)
//			{
//				float eff = this.ironMetrics.getNormUtility(norm).getScoreAverage(Dimension.Effectiveness, goal);
//				float nec = this.ironMetrics.getNormUtility(norm).getScoreAverage(Dimension.Necessity, goal);
//				NSeff += eff;
//				NSnec += nec;
//			}
//			NSeff /= NS.size();
//			NSnec /= NS.size();
//			
//			this.NSEffAtIRONConvergenceTick = NSeff;
//			this.NSNecAtIRONConvergenceTick = NSnec;
//			this.NSMinimalityAtIRONConvergenceTick = this.ironAgent.getMetrics().getNormativeSystemCardinality();
//			this.NSSimplicityAtIRONConvergenceTick = this.computeNumClauses(NS);
//		}
//	}
//
//	/**
//	 * Prints the ironMetrics
//	 */
//	public void print() {
//		System.out.print(this.resume());
//	
//	}
//	
//	public String resume()
//	{
//		String s = Integer.toString((int)tick);
//
//		float NSeff = 0f, NSnec = 0f;
//		List<Norm> NS =  this.ironAgent.getNormativeSystem().getNorms();
//		
//		Goal goal = config.getSystemGoals().get(0);
//		for(Norm norm : NS)
//		{
//			float eff = this.ironMetrics.getNormUtility(norm).getScoreAverage(Dimension.Effectiveness, goal);
//			float nec = this.ironMetrics.getNormUtility(norm).getScoreAverage(Dimension.Necessity, goal);
//			NSeff += eff;
//			NSnec += nec;
//		}
//		NSeff /= NS.size();
//		NSnec /= NS.size();
//		
//		s += " " + this.ironAgent.getMetrics().getNormativeSystemCardinality();
//		s += " " + this.computeNumClauses(ironAgent.getNormativeSystem().getNorms());
//		s += " " + NSeff;
//		s += " " + NSnec;
//		
//		s += " " + this.ironMetrics.getNumNormAdditions();
//		s += " " + this.ironMetrics.getNumNormRemovals();
//		s += " " + this.numNormClauseAdditions;
//		s += " " + this.numNormClauseRemovals;
//		
//		s += " " + this.ironMetrics.getNodesTracker().getNumCreatedGeneralisationNodes();
//		s += " " + this.ironMetrics.getNodesTracker().getNumVisitedGeneralisationNodes();
//		s += " " + this.ironMetrics.getNodesTracker().getNumCreatedNorms();
//		s += " " + this.ironMetrics.getNodesTracker().getNumVisitedNorms();
//		s += " " + this.ironMetrics.getNodesTracker().getNumVisitedPredicates();
//		s += " " + this.ironMetrics.getNodesTracker().getNumQueriesToNN();
//		
//		s += " " + this.NSMinimalityAtIRONConvergenceTick;
//		s += " " + this.NSSimplicityAtIRONConvergenceTick;
//		s += " " + this.NSEffAtIRONConvergenceTick;
//		s += " " + this.NSNecAtIRONConvergenceTick;
//		
//		s += "\n";
//		
//		return s;
//	}

	/**
	 * Save files 
	 */
	public void save() 
	{
//		File metricsFile = new File(this.metricsFileName);
//		
//		try {
//	    BufferedWriter writer = new BufferedWriter(new FileWriter(metricsFile, true));
//	    writer.write(this.resume());
//	    writer.close();
//    } 
//		catch (IOException e) {
//	    e.printStackTrace();
//    }
//		
//		this.normSetsFileManager = new NormSetsFileManager(this, ironAgent, ironMetrics.hasConverged());
//		this.normSetsFileManager.load();
//		this.normSetsFileManager.save();
	}

//	/**
//	 * 
//	 * @param NS
//	 * @return
//	 */
//	private int computeNumClauses(List<Norm> norms)
//	{
//		int ret = 0;
//		for(Norm norm : norms)
//		{
//			for(String predicate : norm.getPrecondition().getPredicatesWithTerms().getPredicates())
//				if(!norm.getPrecondition().getTerms(predicate).get(0).equals("*"))
//					ret++;
//		}		
//		return ret;
//	}

//	/**
//	 * 
//	 * @return
//	 */
//	public boolean hasConverged()
//	{
//		return this.ironMetrics.hasConverged();
//	}
	
//	/**
//	 * 
//	 */
//	private void getIRONConvergenceTick()
//	{
//		File IRONConvergenceTicksFile = new File("IRONConvergenceTicks");
//		
//		try {
//	    BufferedReader reader = new BufferedReader(new FileReader(IRONConvergenceTicksFile));
//	    
//	    int i=0;
//	    do{
//	    	IRONConvergenceTick = Integer.parseInt(reader.readLine());
//	    	i++;
//	    }while(i != TrafficConfiguration.NUM_EXEC);
//	    
//	    	reader.close();
//    } 
//		catch (IOException e) {
//	    e.printStackTrace();
//    }
//	}
	
	//-----------------------------------------------------------------
	// Number of cases
	//-----------------------------------------------------------------

	/**
	 * Returns the current number of cases name
	 * 
	 * @return
	 */
	public String getNumberOfCasesName(){
		return "Cases";
	}

	/**
	 * Returns the current number of cases
	 * 
	 * @return
	 */
	public int getNumberOfCases(){
		return 0;
	}

	//-----------------------------------------------------------------
	// Number of solutions
	//-----------------------------------------------------------------

	/**
	 * Returns the current number of solutions name
	 * 
	 * @return
	 */
	public String getNumberOfSolutionsName(){
		return "NumSolutions";
	}

	/**
	 * Returns the current number of solutions
	 * 
	 * @return
	 */
	public int getNumberOfSolutions(){
		return 0;
	}

	//-----------------------------------------------------------------
	// Number of norms
	//-----------------------------------------------------------------

	/**
	 * Returns the current number of norms name
	 * 
	 * @return
	 */
	public String getNumberOfNormsName() {
		return "Norms generated";
	}

	/**
	 * Returns the current number of norms
	 * 
	 * @return
	 */
	public int getNumberOfNorms() {
		return 0;
	}

	//-----------------------------------------------------------------
	// Number of active norms
	//-----------------------------------------------------------------

	/**
	 * Returns the current number of active norms name
	 * 
	 * @return
	 */
	public String getNumberOfActiveNormsName() {
		return "Active norms";
	}

	/**
	 * Returns the current number of active norms
	 * 
	 * @return
	 */
	public int getNumberOfActiveNorms() {
		return 0;
	}


	//-----------------------------------------------------------------
	// Average number of collisions
	//-----------------------------------------------------------------

	/**
	 * Returns the current average of collisions name
	 * 
	 * @return
	 */
	public String getAvgTotalCollisionsName(){
		return "AvgCollisions";
	}

	/**
	 * Returns the current average of collisions
	 * 
	 * @return
	 */
	public float getAvgTotalCollisions(){
		return 0f;
	}

	//-----------------------------------------------------------------
	// Average number of no violation collisions
	//-----------------------------------------------------------------

	/**
	 * Returns the current average of no violation collisions name
	 * 
	 * @return
	 */
	public String getAvgNoViolCollisionsName(){
		return "AvgNoViolCollisions";
	}

	/**
	 * Returns the current average of no violation collisions
	 * 
	 * @return
	 */
	public float getAvgNoViolCollisions() {
		return 0f;
	}

	//----------------------------------------------------------------
	// Number of cars currently in the map
	//----------------------------------------------------------------

	/**
	 * Returns the current number of cars in the scenario name
	 * 
	 * @return
	 */
	public String getNumberOfCarsInScenarioName(){
		return "NumCarsInScenario";
	}

	/**
	 * Returns the current number of cars in the scenario
	 * 
	 * @return 
	 */
	public float getNumberOfCarsInScenario() {
		return 0f;
	}

	//----------------------------------------------------------------
	// Number of cars currently in the map
	//----------------------------------------------------------------

	/**
	 * 
	 * @return
	 */
	public String getCarsThroughputName(){
		return "CarsThroughput";
	}

	/**
	 * 
	 */
	public float getCarsThroughput() {
		return 0f;
	}

	//----------------------------------------------------------------
	// Number of cars currently in the map
	//----------------------------------------------------------------

	/**
	 * Returns the current number of stops name
	 * 
	 * @return
	 */
	public String getNumberOfStopsName(){
		return "NumberOfStops";
	}

	/**
	 * Returns the current number of stops
	 * 
	 * @return
	 */
	public float getNumberOfStops() {
		return 0f;
	}
}
