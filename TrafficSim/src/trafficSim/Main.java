package trafficSim;
import java.util.ArrayList;
import java.util.Random;

import org.ejml.simple.SimpleMatrix;

import repast.simphony.annotate.AgentAnnot;
import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import trafficSim.XMLParser.EvolutionaryAgent;
import trafficSim.XMLParser.XMLParser;
import trafficSim.agent.Car;
import trafficSim.agent.TrafficElement;
import trafficSim.car.CarAction;
import trafficSim.config.TrafficConfiguration;
import trafficSim.egt.Egt;
import trafficSim.map.CarMap;
import trafficSim.metrics.TrafficMetrics;
import trafficSim.utils.TrafficGrammar;
import trafficSim.utils.norms.Norm;
import trafficSim.utils.norms.NormModality;
import trafficSim.utils.norms.NormPrecondition;

/**
 * Scene Manager - Main class of implementation. Controls cooperation
 * of all the components, agent generation etc.
 *
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
@AgentAnnot(displayName = "Main Agent")
public class Main implements TrafficElement
{

	//-----------------------------------------------------------------
	// Static
	//-----------------------------------------------------------------

	/**
	 * Random machine
	 */
	private static Random random;
	
	/**
	 * 
	 */
	private static CarMap carMap = null;

	
	private static Egt egt;
	/**
	 * 
	 */
	public static TrafficGrammar grammar = new TrafficGrammar();
	
	//-----------------------------------------------------------------
	// Attributes
	//-----------------------------------------------------------------


	/**
	 * 
	 */
	private TrafficMetrics trafficMetrics;
	
	/**
	 * 
	 */
	private static TrafficConfiguration config;
	
	Parameters p = RunEnvironment.getInstance().getParameters();
		
	public static long tick = 0;
	public static int fitnessTime = 0;
	boolean collisions = true;
	
	int T = (Integer)p.getValue("fitnessTime");
	int population_a = (Integer) p.getValue("popu_a"); // Cars following n1
	int population_b = (Integer) p.getValue("popu_b"); // Cars following n2 or no norm
	int carNumber = population_a + population_b;

	

	//Condition to Stop
	
	int TransformationsInEquilibrium = 3;
	
	float density = 0;
	float actualCrossDensity = 0;
	//-----------------------------------------------------------------
	// Methods
	//-----------------------------------------------------------------

	/**
	 * Constructor
	 * 
	 * @param grid
	 * @param context
	 * @param valueLayer
	 * @param map
	 */
	public Main(Context<TrafficElement> context, Grid<TrafficElement> map){
		long seed = System.currentTimeMillis();
		
		TrafficConfiguration.init();
		
		// Set the defined seed only if the simulation is not batch and the seed is != 0
		if(TrafficConfiguration.SIM_RANDOM_SEED != 0l) {
			seed = TrafficConfiguration.SIM_RANDOM_SEED;
		}
		
		//normProportion = new ArrayList<Integer>();
		random = new Random(seed);
		

//		if(!RunEnvironment.getInstance().isBatch()) {
//			this.initNormsPool(carMap);
//			egt = new Egt(population_a, population_b, strategy1, strategy2, n3);
//
//			//egt = new Egt(population_a, population_b, n1,n2,n3);
//		}else{

			System.out.println("batch mode got it");
			XMLParser leerFichero = new XMLParser();
			ArrayList<EvolutionaryAgent> agente = leerFichero.getPopulationFromXML();
//			carNumber = 0;
//			for(int i = 0 ; i<agente.size(); i++){
//				carNumber += agente.get(i).getQuantity();
//			}
			carMap = new CarMap(context, map, carNumber);
			this.initNormsPool(agente, carMap, population_a, population_b);
			
			//population_a = agente.get(0).getQuantity();
			//population_b = agente.get(1).getQuantity();
			egt = new Egt(population_a, population_b, strategy1, strategy2, n3);

			
			//			
//			egt = new Egt(agente);
		//}
		
		System.out.println("\nStarting simulation with random seed = " + seed);
		
	}

	/*
	 * Step method. Controls everything in the simulation
	 */
	@ScheduledMethod(start=1, interval=1, priority=2)
	public void step(){
		if(tick++ > 0){
			fitnessTime++;
			//System.out.println("\n==================== Tick: " + tick +  " =====================");

			// 1. Move cars, emit cars and handle new collisions
			carMap.step();
			
			density += carMap.getNumCars();
			actualCrossDensity += checkDensityInCross();
			
			/*
			 * Do the Fitness Function
			 */
			if(fitnessTime >= T){
				fitnessTime = 0;
				density = density/T;
				actualCrossDensity = actualCrossDensity / T;
				// Step resume
				this.printStepResume();
				egt.step(carMap);	
				density = 0;
				actualCrossDensity = 0;
			}
		}
		//System.out.println("\n======================= End Step ========================\n");

		// Stop simulation if required update
		if(tick >= TrafficConfiguration.SIM_MAX_TICKS || this.mustStop()){
			System.out.println("End of simulation");
			RunEnvironment.getInstance().endRun();
			
//			System.out.println("Tick NSCardinality NSNumClauses NumCreatedGeneralisationNodes NumVisitedGeneralisationNodes " +
//					"NumCreatedNorms NumVisitedNorms NumVisitedPredicates NumQueriesToNN");
			
		}
	}


	private float checkDensityInCross() {
		int dens = 0;
		
		for (int x = 7 ; x <= 11 ; x++){
			for (int y = 7 ; y <= 11 ; y++){
				if((x == 7 && y == 7) || (x == 7 && y == 11) || (x == 11 && y == 7 )  ||  (x == 11 && y == 11)){
					// Do nothing are walls.
				}else{
					GridPoint p = new GridPoint(x,y);
					if(!carMap.isFree(p)){
						// Car in the intersection.
						dens += 1;
						//System.out.println(p.toString());
						//System.out.println("car in cross");
					}
				}
			}
		}
		return dens;
  }


	Norm n1;
	Norm n2;
	Norm n3;
	Norm n4;
	Norm n5;
	ArrayList<Norm> norms;
	ArrayList<Norm> strategy1;
	ArrayList<Norm> strategy2;
	

	private void initNormsPool(ArrayList<EvolutionaryAgent> agente, CarMap map, int population_a, int population_b) {
		
		/**
		 * Keep security distance.
		 */
		NormPrecondition precond = new NormPrecondition();

		precond = new NormPrecondition();
		precond.add("l", "*");
		precond.add("f", "^");
		precond.add("r", "*");
		n3 = new Norm(precond, NormModality.Prohibition, CarAction.Go);
		n3.setId(3);
		
		for(int i = 0; i < agente.size() ; i++){
			norms = new ArrayList<Norm>();
			for(int j = 0; j < agente.get(i).getNS().size() ; j++){
				precond = new NormPrecondition();
				String precondition = "";
				if(agente.get(i).getNS().get(j).getLeft().equals("right")){
					precondition = ">";
				}else if(agente.get(i).getNS().get(j).getLeft().equals("left")){
					precondition = "<";
				}else{
					precondition = agente.get(i).getNS().get(j).getLeft();
				}
				precond.add("l", ""+precondition);
				if(agente.get(i).getNS().get(j).getFront().equals("right")){
					precondition = ">";
				}else if(agente.get(i).getNS().get(j).getFront().equals("left")){
					precondition = "<";
				}else{
					precondition = agente.get(i).getNS().get(j).getFront();
				}
				precond.add("f", ""+precondition);
				if(agente.get(i).getNS().get(j).getRight().equals("right")){
					precondition = ">";
				}else if(agente.get(i).getNS().get(j).getRight().equals("left")){
					precondition = "<";
				}else{
					precondition = agente.get(i).getNS().get(j).getRight();
				}
				precond.add("r", ""+precondition);
				Norm n = new Norm(precond, NormModality.Prohibition, CarAction.Go);
				n.setId(agente.get(i).getNS().get(j).getId());
				norms.add(n);
			}
			if(i <= 0){
				strategy1 = norms;
			}else{
				strategy2 = norms;
			}
		}
		
		for(int i = 0; i < agente.size() ; i++){
			int population;
			if(i == 0){
				population = population_a;
			}else{
				population = population_b;
			}
			int count = 0;
			for (Car c : map.getAllCars()){
				if((c.getNorms().size() <= 1 || c.getNorms() == null) && count < population){
					count += 1;
					if(i <= 0){
						map.broadcastAddNorm(n3, c);
						for(int n = 0 ; n < strategy1.size() ; n++){
							map.broadcastAddNorm(strategy1.get(0), c);
						}
					}else{
						map.broadcastAddNorm(n3, c);
						for(int n = 0 ; n < strategy2.size() ; n++){
							map.broadcastAddNorm(strategy2.get(n), c);
						}
					}
				}
			}
		}
  }

	private void initNormsPool(CarMap map) {
		
		/**
		 * Give way to left.
		 */
		NormPrecondition precond = new NormPrecondition();
		precond.add("l", ">");
		precond.add("f", "*");
		precond.add("r", "*");
		n1 = new Norm(precond, NormModality.Prohibition, CarAction.Go);
		n1.setId(1);
		
		/**
		 * Give way to right.
		 */
		precond = new NormPrecondition();
		precond.add("l", "*");
		precond.add("f", "*");
		precond.add("r", "<");
		n2 = new Norm(precond, NormModality.Prohibition, CarAction.Go);
		n2.setId(2);
		
		/**
		 * Keep security distance.
		 */
		precond = new NormPrecondition();
		precond.add("l", "*");
		precond.add("f", "^");
		precond.add("r", "*");
		n3 = new Norm(precond, NormModality.Prohibition, CarAction.Go);
		n3.setId(3);
		
		
		/**
		 * Stop when there's no car at sight.
		 */
		precond = new NormPrecondition();
		precond.add("l", "-");
		precond.add("f", "-");
		precond.add("r", "-");
		n4 = new Norm(precond, NormModality.Prohibition, CarAction.Go);
		n4.setId(4);
		
		/**
		 * Give way to left (generilised by n1).
		 */
		precond = new NormPrecondition();
		precond.add("l", ">");
		precond.add("f", ">");
		precond.add("r", ">");
		n5 = new Norm(precond, NormModality.Prohibition, CarAction.Go);
		n5.setId(5);
		
		//normProportion.add(pop_a);
		//normProportion.add(pop_b);
		
		//Add norm to the cars		
		
		for (Car c : map.getAllCars()){
				if(c.getId() <= population_a){	
					map.broadcastAddNorm(n3, c);
					map.broadcastAddNorm(n1, c);
					//map.broadcastAddNorm(n2, c);

				}else{
					map.broadcastAddNorm(n3, c);
					map.broadcastAddNorm(n2, c);
				}
		}
		
		/*map.broadcastAddNorm(n1);
		map.broadcastAddNorm(n2);
		map.broadcastAddNorm(n3);
		map.broadcastAddNorm(n4);
		map.broadcastAddNorm(n5);*/
	}
	
	/**
	 * 
	 */
	private boolean mustStop() {
		//Stop condition criterion.
		//1-. If the norms fitness function don't change in x ticks?
		// 		+ Equilibrium	
		
		if(egt.getEquilibrium() == TransformationsInEquilibrium){
			return true;
		}
		if(egt.getCarsWithNorms() == 0){
			return true;
		}else if(egt.getCarsWithoutNorms() == 0){
			return true;
		}
		
//		if(!collisions){
//		//	return true;
//		}
		return false; 
	}
	
	/**their own method for traffic regulation
	 * Prints information about metrics in the current step
	 */
	private void printStepResume() {
		System.out.println("\nSTEP RESUME");
		System.out.println("----------------------------");
		System.out.println("Step: " + tick);
		System.out.println("Density: "+density);
		System.out.println("Cross Density: "+actualCrossDensity);

	}
	
	//-----------------------------------------------------------------
	// Static methods
	//-----------------------------------------------------------------

	/**
	 * 
	 * @return
	 */
	public static long getTick() {
		return tick;
	}

	/**
	 * 
	 * @return
	 */
	public static Random getRandom() {
		return random;
	}

	/**
	 * 
	 * @return
	 */
	public static CarMap getMap() {
		return carMap;
	}
	
	/**
	 * 
	 */
	public static TrafficConfiguration getConfig() {
		return config;
	}

  public int getX() {
	  return -1;
  }

  public int getY() {
	  return -1;
  }

  public void move() {
	  
  }
}

