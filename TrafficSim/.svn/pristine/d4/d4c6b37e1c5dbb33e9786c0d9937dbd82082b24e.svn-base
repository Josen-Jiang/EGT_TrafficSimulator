package trafficSim.config;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

/**
 * Class for basic configuration
 * 
 * @author Javier Morales (jmoralesmat@gmail.com)
 *
 */
public class TrafficConfiguration {

	/**
	 * 
	 */
	public static int NUM_EXEC;
	
	//-----------------------------------------------------------------
	// Graphical User Interface settings
	//-----------------------------------------------------------------

	/**
	 * 
	 */
	public static final float ROAD_POSITION = 0f;

	/**
	 * 
	 */
	public static final float WALL_POSITION = 1f;

	/**
	 * Learning rate of the method
	 */
	private static float LEARNING_RATE = 0.2f;

	//-----------------------------------------------------------------
	// Simulator settings
	//-----------------------------------------------------------------

	/**
	 * 
	 */
	public static int SIM_NUM_TICKS_NORMSET_EVAL = 5000;

	/**
	 * 
	 */
	public static long SIM_NUM_TICKS_CONVERGENCE = 0;

	/**
	 * Defines if the simulator uses the advanced GUI
	 */
	public static boolean SIM_ADVANCED_GUI = false;

	/**
	 * Max number of cars at the same time in the scenario. 
	 */
	public static int SIM_NEW_CARS_FREQUENCY;

	/**
	 * Number of cars to emit at the same time 
	 */
	public static int SIM_NUM_CARS_TO_EMIT;

	/**
	 * Random seed
	 */
	public static long SIM_RANDOM_SEED = 0l;

	/**
	 * Defines if the simulator uses a GUI
	 */
	public static boolean SIM_GUI;

	/**
	 * Max ticks to simulate
	 */
	public static long SIM_MAX_TICKS;

	/**
	 * Path for data
	 */
	public static boolean SIM_USE_SEMAPHORES;

	/**
	 * Path for data
	 */
	public static boolean SIM_ONLY_SEMAPHORES;

	/**
	 * Path for data
	 */
	public static final String SIM_DATA_PATH = "experiments/data";

	/**
	 * Name for the norms file. This file saves the
	 * resulting norms in different executions  
	 */
	public static final String SIM_TOTAL_NORMS_FILE = "TrafficNormsTotal.dat";

	/**
	 * Name for the norms file. This file saves the
	 * resulting norms in different executions  
	 */
	public static final String SIM_FINAL_NORMS_FILE = "TrafficNormsFinal.dat";

	/**
	 * Name for the norm sets file. This file saves the
	 * resulting norm sets in different executions  
	 */
	public static final String SIM_FINAL_NORMSETS_FILE = "TrafficNormSetsFinal.dat";

	/**
	 * Probability to violate a norm
	 */
	public static double SIM_NORM_VIOLATION_RATE;

	//-----------------------------------------------------------------
	// IRON settings
	//-----------------------------------------------------------------

	/**
	 * The minimum score for a norm
	 */
	public static double NORM_GEN_EFF_THRESHOLD;

	/**
	 * The minimum score for a norm
	 */
	public static double NORM_GEN_NEC_THRESHOLD;

	/**
	 * The minimum score for a norm
	 */
	public static double NORM_SPEC_EFF_THRESHOLD;

	/**
	 * The minimum score for a norm
	 */
	public static double NORM_SPEC_NEC_THRESHOLD;

	/**
	 * Length of historic sliding window that is summed for metrics
	 */
	private static int NORM_UTILITY_WINDOW_SIZE;

	/**
	 * The minimum score for a norm
	 */
	private static double NORM_DEFAULT_UTILITY;

	/**
	 * 
	 */
	private static long NUM_TICKS_TO_CONVERGE;

	/**
	 * 
	 */
	private static int NORM_WEIGHT_COL;

	/**
	 * 
	 */
	private static int NORM_WEIGHT_NO_COL;

	/**
	 * 
	 */
	private static int NORM_WEIGHT_FLUID_TRAFFIC;

	//-----------------------------------------------------------------
	// Methods
	//-----------------------------------------------------------------

	/**
	 * Initializes the configuration
	 */
	public static void init() {
		Parameters p = RunEnvironment.getInstance().getParameters();

		// Simulator parameters
		SIM_NEW_CARS_FREQUENCY = (Integer)p.getValue("SimNewCarsFreq");
		SIM_NUM_CARS_TO_EMIT = (Integer)p.getValue("SimNumCarsToEmit");
		SIM_RANDOM_SEED = (Long)p.getValue("SimRandomSeed");
		SIM_MAX_TICKS = (Long)p.getValue("SimMaxTicks");
		SIM_GUI = (Boolean)p.getValue("SimGUI");
		SIM_USE_SEMAPHORES = (Boolean)p.getValue("SimUseSemaphores");
		SIM_ONLY_SEMAPHORES = (Boolean)p.getValue("SimOnlySemaphores");
		SIM_NORM_VIOLATION_RATE = (Double)p.getValue("SimNormViolationRate");

		// IRON settings
		NORM_GEN_EFF_THRESHOLD = (Double)p.getValue("NormsGenEffThreshold");
		NORM_GEN_NEC_THRESHOLD = (Double)p.getValue("NormsGenNecThreshold");
		NORM_SPEC_EFF_THRESHOLD = (Double)p.getValue("NormsSpecEffThreshold");
		NORM_SPEC_NEC_THRESHOLD = (Double)p.getValue("NormsSpecNecThreshold");

		NORM_UTILITY_WINDOW_SIZE = (Integer)p.getValue("NormsUtilityWindowSize");
		NORM_DEFAULT_UTILITY = (Double)p.getValue("NormsDefaultUtility");
		NUM_TICKS_TO_CONVERGE = (Integer)p.getValue("NumTicksToConverge");
		NORM_WEIGHT_COL = (Integer)p.getValue("NormsWeightCol");
		NORM_WEIGHT_NO_COL = (Integer)p.getValue("NormsWeightNoCol");
		NORM_WEIGHT_FLUID_TRAFFIC = (Integer)p.getValue("NormsWeightFluidTraffic");
		
		NUM_EXEC = (Integer)p.getValue("NumExec");
	}

	/**
	 * 
	 * @param flag
	 */
	public synchronized static void setUseAdvancedGUI(boolean flag) {
		SIM_ADVANCED_GUI = flag;
	}

	/**
	 * 
	 * @return
	 */
	public synchronized static boolean getUseAdvancedGUI() {
		return SIM_ADVANCED_GUI;
	}
}
