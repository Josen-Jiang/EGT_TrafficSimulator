package trafficSim.egt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import trafficSim.Main;
import trafficSim.agent.Car;
import trafficSim.map.CarMap;
import trafficSim.utils.norms.Norm;

public class Egt {
	

	float fNormTotal, fNoNormTotal;
	float fNorm_average, fNoNorm_average;
	float fNorm_normalized, fNoNorm_normalized;
	
	int k;	
	public static int penalty;

	
	float f;
	float successfulJourneys_norm_total;
	float successfulJourneys_nonorm_total;
	float collisions_norm_total;
	float collisions_nonorm_total;
		
	int new_cars_with_norms, new_cars_without_norms;
	int cars_with_norms, cars_without_norms;
	
	float newCarProportionNorm, newCarProportionNoNorm;
	float carProportionNorm, carProportionNoNorm;
	
	int equilibrium;
	Norm n1,n2,n3;
	
	private String logfilename;
	boolean write;

	
	/**
	 * Constructor of the evolutionary game theory class.
	 * 
	 * @param cars_with_norms
	 * @param cars_without_norms
	 * @param n3 
	 * @param n2 
	 * @param n1 
	 */
	public Egt(int cars_with_norms, int cars_without_norms, Norm n1, Norm n2, Norm n3){
		this.cars_with_norms = cars_with_norms;
		this.cars_without_norms = cars_without_norms;
		this.n1 = n1;
		this.n2 = n2;
		this.n3 = n3;
		
		this.init();
		
	}


private void init() {

	new_cars_with_norms = cars_with_norms;
	new_cars_without_norms = cars_without_norms;
	
	k = 1;
	Parameters p = RunEnvironment.getInstance().getParameters();

	penalty = (Integer)p.getValue("penalty");

	fNormTotal = 0;
	fNoNormTotal = 0;
	
	f = 0;
	successfulJourneys_norm_total = 0;
	successfulJourneys_nonorm_total = 0;
	collisions_norm_total = 0;
	collisions_nonorm_total = 0;
	
	carProportionNorm = 0;
	carProportionNoNorm = 0;
	newCarProportionNorm = 0;
	newCarProportionNoNorm = 0;

	equilibrium = 0;
	
	write = false;
	//Create log data:
	//1-. Filename:
	Date now = new Date();
	String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(now);
	logfilename = "/Volumes/DATA/MASTER_IA/First-Semester/CI/Project/workspace/TrafficSim/experiments/logs/testlog_" + timestamp+ ".csv";
	//2-.	Open the datalog
	try{
    BufferedWriter output = new BufferedWriter(new FileWriter(logfilename));
    output.close();
  }
  catch (IOException e) { 
      System.out.println("Error creating log file ("+logfilename+")");
      e.printStackTrace();
	}	  
  }


//	public Egt(ArrayList<EvolutionaryAgent> agente) {
////		int quantity = 0;
////		norm n = null;
////		for(int i = 0 ; i < agente.size() ; i++){
////			quantity = agente.get(i).getQuantity();
////			n = agente.get(i).getNorm();
////		}
//		this.cars_with_norms = agente.get(0).getQuantity();
//		this.cars_without_norms = agente.get(1).getQuantity();
//		
//	}

ArrayList<Norm> norms;
ArrayList<Norm> strategy1;
ArrayList<Norm> strategy2;

	public Egt(int population_a, int population_b, ArrayList<Norm> strategy1, ArrayList<Norm> strategy2, Norm n3) {
		
		this.cars_with_norms = population_a;
		this.cars_without_norms = population_b;
		
		this.strategy1 = strategy1;
		this.strategy2 = strategy2;
		this.n3 = n3;
		
		this.init();
  }


	public void step(CarMap carMap) {
		
		fitnessFunction(carMap);	
		//Make the asexual reproduction of the norms for the next iterations.
		asexualReproduction(carMap);
		//Actual state is the same as before?
		equilibrium();
		//Save the changes of this fitness time in a log.
		log();
		
		//Reset all the values to calculate new fitness function.
		this.reset(carMap);
  }


	/**
	 * Fitness Function
	 * 
	 * @param carMap
	 * @param penalty
	 */
	private void fitnessFunction(CarMap carMap) {
		this.cars_with_norms = 0;
		this.cars_without_norms = 0;
		int normGroup = strategy1.get(0).getId();
		for (Car c : carMap.getAllCars()){
			//this.printCarData(c);
			
			//Group with the name of the norm
			//	1-. Norm 1 group.
			//	2-. Norm 2 group.
			
			if(c.getGroup() == normGroup){
				cars_with_norms++;
				successfulJourneys_norm_total +=c.getSuccessfulJourneys();
				collisions_norm_total += c.getCollisions();
			}else{
				cars_without_norms++;
				successfulJourneys_nonorm_total +=c.getSuccessfulJourneys();
				collisions_nonorm_total += c.getCollisions();
			}
				
			/**
			 * Fitness function
			 */
			 f = (float) ((k * c.getSuccessfulJourneys()) - (penalty * k * c.getCollisions()));
			 c.setFitness(f);
		}
	
		for (Car c : carMap.getAllCars()){			 
			 if(c.getGroup() == normGroup){
				 fNormTotal += c.getFitness();
			 }else{
				 fNoNormTotal += c.getFitness();
			 }
		}
		/*for (Car c : carMap.getAllCars()){			 
			printCarData(c);
		}*/
		//Fitness averages
		fNorm_average = fNormTotal / cars_with_norms;
		fNoNorm_average = fNoNormTotal / cars_without_norms;
			 
		//Shift the fitness function values to positive
		normalizeTheFitness();
				
		//Calculate the percentage of the fitness functions with the population proportions.
		//	1-. Previous proportions.
		carProportionNorm = (cars_with_norms * 100) / carMap.getAllCars().size();
		carProportionNoNorm = (cars_without_norms * 100) / carMap.getAllCars().size();
		//	2-. Actual proportions.
		newCarProportionNorm = (float) ((carProportionNorm * fNorm_normalized) / ((carProportionNorm * fNorm_normalized) + (carProportionNoNorm * fNoNorm_normalized))) * 100;
		newCarProportionNoNorm = (float) ((carProportionNoNorm * fNoNorm_normalized) / ((carProportionNoNorm * fNoNorm_normalized) + (carProportionNorm * fNorm_normalized))) * 100;
	
		this.printFitnessResume();
  }

	/**
	 * Function to normalize the fitness variables if there is a negative value.
	 */
	private void normalizeTheFitness() {
		//revisar y comprimir
		
		if (fNorm_average < 0 || fNoNorm_average < 0) {
			float m = Math.min(fNorm_average, fNoNorm_average);
			if (fNorm_average < fNoNorm_average) {
				fNorm_average = Math.abs(m);
				if (fNoNorm_average < 0) {
					fNoNorm_average = fNorm_average - Math.abs(fNoNorm_average) + Math.abs(m);
				} else if (fNoNorm_average >= 0) {
					fNoNorm_average = fNorm_average + fNoNorm_average + Math.abs(m);
				}
			} else {
				fNoNorm_average = Math.abs(m);
				if (fNorm_average < 0) {
					fNorm_average = fNoNorm_average - Math.abs(fNorm_average) + Math.abs(m);
				} else if (fNorm_average >= 0) {
					fNorm_average = fNoNorm_average + fNorm_average + Math.abs(m);
				}
			}
		}
		fNorm_normalized = fNorm_average;
		fNoNorm_normalized = fNoNorm_average;  
  }

	/**
	 * Function where the asexual reproduction is done, taking into account
	 * the new proportions calculated with the fitness function.
	 * 
	 * @param map
	 */
	private void asexualReproduction(CarMap map) {
		new_cars_with_norms = 0;
		new_cars_without_norms = 0;
		
		this.removeNorms(map, strategy1, strategy2);
		
		String norm1 = "", norm2 = "";
		
		//Each car decide whereas pick a norm or no norm, by the percentage of probability of these ones.
		for (Car c : map.getAllCars()){
		
			int n = Main.getRandom().nextInt(99);
			n = n+1;
			//System.out.println("random="+n);
			//int n = (int)(100.0 * Math.random()) + 1;
  		if(n <= newCarProportionNorm){
				map.broadcastAddNorm(n3, c);
				for(int i = 0 ; i < strategy1.size() ; i++){
					map.broadcastAddNorm(strategy1.get(i), c);
				}
				map.broadcastAddNorm(strategy1.get(0), c);
				//map.broadcastAddNorm(n2, c);
				norm1 = c.getNorms().get(1).getName();
				new_cars_with_norms++;
  		}else{
  			map.broadcastAddNorm(n3, c);
  			if(strategy2.size() >= 1){
  				for(int i = 0 ; i < strategy2.size() ; i++){
  					map.broadcastAddNorm(strategy2.get(i), c);
  				}
  			}
  			if(c.getNorms().size() == 2){
  				norm2 = c.getNorms().get(1).getName();
  			}else{
			  	norm2 = "No norm";
			  }
  			new_cars_without_norms++;
  			//No norm... so nothing.
  		}
		}	
		System.out.println("Real Proportions... norm ("+norm1+") = "+new_cars_with_norms+" norm ("+norm2+") = "+new_cars_without_norms);

	}

	private void removeNorms(CarMap map, ArrayList<Norm> n1, ArrayList<Norm> n2) {
		for (Car c : map.getAllCars()){
			try{
				for(int i = 0; i<n1.size(); i++){
					c.getReasoner().removeNorm(n1.get(i));
				}
				for(int i = 0; i<n2.size(); i++){
					c.getReasoner().removeNorm(n2.get(i));
				}
			}catch(Exception e){}
		}	  
  }

	
	private void equilibrium() {
		
		if(this.cars_with_norms == this.new_cars_with_norms && this.cars_without_norms == this.new_cars_without_norms){
			equilibrium += 1;
		}else{
			equilibrium = 0;
		}
		System.out.println("equilibrium = "+equilibrium); 
  }
	private void log() {
		//calculate avg succes and collisions
		collisions_nonorm_total = (float) (collisions_nonorm_total / cars_without_norms);
		collisions_norm_total = (float)(collisions_norm_total / cars_with_norms);
		successfulJourneys_nonorm_total = (float) (successfulJourneys_nonorm_total / cars_without_norms);
		successfulJourneys_norm_total = (float) (successfulJourneys_norm_total / cars_with_norms);		
		//System.out.println("num_norm : "+ num_norm);
		try{
			BufferedWriter output = new BufferedWriter(new FileWriter(logfilename, true)); 
			if(!write){
				output.write( "Tick; Norm proportion; No norm proportion; avg_col_norm; avg_col_nonorm; " +
											"avg_suc_norm; avg_suc_nonorm; num_norm; num_nonorm\n");
				write = true;
			}
			output.write( repast.simphony.engine.environment.RunEnvironment.getInstance().getCurrentSchedule().getTickCount()+
					";"+ (float) newCarProportionNorm +"; " +  (float) newCarProportionNoNorm +"; " + collisions_norm_total  + 
					"; " + collisions_nonorm_total +"; "+ successfulJourneys_norm_total  + 
					"; " + successfulJourneys_nonorm_total +"; " + new_cars_with_norms +
					"; "+ new_cars_without_norms+"\n");
      output.close();
    }
    catch (IOException e) { 
        System.out.println("Error writing to log file  ("+logfilename+")");
        e.printStackTrace();
    }  
  }
	
	private void reset(CarMap carMap) {
		for (Car c : carMap.getAllCars()){
			c.setSuccessfulJourneys(0);
			c.setCollisions(0);
			c.setJourneys(0);
			c.setFitness(0);
		}
		//this.cars_with_norms = 0;
		//this.cars_without_norms = 0;
		//this.new_cars_with_norms = 0;
		//this.new_cars_without_norms = 0;
		this.newCarProportionNorm = 0;
		this.newCarProportionNoNorm = 0;
		this.fNormTotal = 0;
		this.fNoNormTotal = 0;	  
  }

	
	private void printCarData(Car c) {
		System.out.println("car = " + c.getId());
		System.out.println("_____________________________________");
		
		System.out.println("Car with  norm = "+c.getNorms().toString());
		System.out.println("travels = "+c.getJourneys());
		System.out.println("Succesful journeys = " + c.getSuccessfulJourneys());
		System.out.println("Collisions = "+c.getCollisions());
		System.out.println("Fitness = "+c.getFitness());
  }

	private void printFitnessResume() {
		System.out.println("fnorm_average = "+fNorm_average);
		System.out.println("fNonorm_average = "+fNoNorm_average);
		System.out.println("fitness normalized for norm 1 = "+fNorm_normalized);
		System.out.println("fitness normalized for norm 2 = "+fNoNorm_normalized);
		System.out.println("Previous proportion norm 1 = "+carProportionNorm);
		System.out.println("Previous proportion norm 2 = "+carProportionNoNorm);
		System.out.println("New proportion fnorm 1 "+newCarProportionNorm);
		System.out.println("New proportion fnorm 2 "+newCarProportionNoNorm);
  }

	public int getEquilibrium() {
	  return this.equilibrium;
  }

	public int getCarsWithNorms() {
	  return this.new_cars_with_norms;
  }

	public int getCarsWithoutNorms() {
	  return this.new_cars_without_norms;
  }

}
