import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class ExperimentCreator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ExperimentCreator();
		//System.exit(0);
	}

	public ExperimentCreator(){
		
		ArrayList<Integer> popu_a = new ArrayList<Integer>();
		popu_a.add(10);
		popu_a.add(30);
		popu_a.add(50);
		popu_a.add(70);
		popu_a.add(90);
//		popu_a.add(35);
//		popu_a.add(37);
//		popu_a.add(39);
//		popu_a.add(41);
//		popu_a.add(43);
//		popu_a.add(45);

		
		ArrayList<Integer> popu_b = new ArrayList<Integer>();
		popu_b.add(90);
		popu_b.add(70);
		popu_b.add(50);
		popu_b.add(30);
		popu_b.add(10);
//		popu_b.add(65);
//		popu_b.add(63);
//		popu_b.add(61);
//		popu_b.add(59);
//		popu_b.add(57);
//		popu_b.add(55);
		
		/**
		 * To test different car densities within the same population scenarios.
		 */
		ArrayList<Float> dens = new ArrayList<Float>();
		dens.add(0.25f);
		//dens.add(0.1f);
//		dens.add(0.2f);
//		dens.add(0.3f);
//		dens.add(0.4f);
//		dens.add(0.5f);

		FileWriter launcherFileWriter = null;
		PrintWriter launcherPrintWriter = null;
		
		String experimentOutputFile = "ExperimentOutputData";
		String experimentsFolderName = "Experiment";
		
		ArrayList<String> foldersLine = readFile("experiments/files/Classpath/classpath.txt");
		
		String launcherFileName = "batch/launchers/Experiments_Launcher";
		String projectDir = "PROJECT_DIR=$NORMLAB_DIR\n";
		
	
		int fitnessTime = 500;
		int penalty = 5;
		int numtickswithoutmoving = 5;		
		int experimentRepetition = 100;
		
		
		try{
  		/* Create launcher output files*/
  		launcherFileWriter = new FileWriter(launcherFileName+".sh");
  		launcherPrintWriter = new PrintWriter(launcherFileWriter);
  
  		/* Add folders configuration */
  		for (int j = 0; j < foldersLine.size(); j++) {
  			launcherPrintWriter.write(foldersLine.get(j));
  			launcherPrintWriter.write("\n");
  		}    
  		launcherPrintWriter.write(projectDir);
  		String population = "";
  		String popufolder = "";
  		for (int i = 0 ; i < 1 ; i++){
  			switch (i){
  				case 0:
  					popufolder = "n1vsn2";
  					population = "Population-n1vsn2.xml";
  					break;
  				case 1:
  					popufolder = "n2vsno";
  					population = "Population-n2vsnonorm.xml";
  					break;
  				case 2:
  					popufolder = "n1vsno";
  					population = "Population-n1vsnonorm.xml";
  					break;
  			}
  			for (int j = 0; j < popu_a.size(); j++){
     				for (int d = 0; d < dens.size(); d++){
    				
        		/* Prepare folders for the experiment */
        		String experimentsFolderNameWithParameters = "$NORMLAB_DIR/output/" + experimentsFolderName+"-"+popu_a.get(j)+popufolder+popu_b.get(j)+"-density"+dens.get(d);
        		
        		//String population = getPopulationName(populationURL);
        
        		/* Create new folder into the experiments folder. This new folder will 
        		 * keep all the files resulting from the experiments */
        		launcherPrintWriter.write("mkdir " + experimentsFolderNameWithParameters + "\n");
  
        		
        		String populationSwitcherLine = "java -cp $PROJECT_DIR/bin batch.PopulationSwitcher "
  						+ "$PROJECT_DIR'/experiments/files/populations/use/"+population+"' $PROJECT_DIR'/experiments/files/populations/Population.xml' \n";
  
        		String batchParamsSwitcherLine = "java -cp $PROJECT_DIR/bin batch.BatchParamsSwitcher "+
        		popu_a.get(j)+" "+popu_b.get(j)+ " "+dens.get(d)+" "+fitnessTime+" "+ penalty+" "+ numtickswithoutmoving +
        		" $PROJECT_DIR'/batch/batch_params.xml'\n";
        		
        		/* Change population and batch params */
        		launcherPrintWriter.write(populationSwitcherLine);
        		launcherPrintWriter.write(batchParamsSwitcherLine);
        		
        		
        		launcherPrintWriter.write("for i in `seq "+ experimentRepetition +"`; do\n");
        		
        		
        		String simulationLauncherLine = "CLASSPATH=$CLASSPATH $JAVA_EXECUTABLE $JAVA_FLAGS -Xss10M -Xms1024M "
        			+ "-Xmx1g repast.simphony.batch.BatchMain -params $PROJECT_DIR/batch/batch_params.xml "
        			+ "$PROJECT_DIR/trafficSim.rs > "+ experimentsFolderNameWithParameters +"/" + popufolder + "-Run$i-log.txt";
        		
        		launcherPrintWriter.write("  cd $PROJECT_DIR\n");
        		launcherPrintWriter.write("  " + simulationLauncherLine + "\n");
        		launcherPrintWriter.write("  mv $PROJECT_DIR/experiments/logs/* "+ experimentsFolderNameWithParameters +"/Experiment_output_$i.txt\n");
        		launcherPrintWriter.write("done\n");
  				}
    		}
  		}
  	}
  	catch (Exception e) {
  		e.printStackTrace();
  	}
  	finally {
    		try {
    			/* Close output files */
    			if (null != launcherFileWriter)
    				launcherFileWriter.close();
    		}
    		catch (Exception e2) {
    			e2.printStackTrace();
    		}
    }
	}
	
	
	/**
	 * 
	 * @param fichero
	 * @return
	 */
	private ArrayList<String> readFile(String fichero) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String linea = "";
		ArrayList<String> texto = new ArrayList<String>();
		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).

			//Para hacer experimentos para el cluster 
			archivo = new File (fichero);
			//Experimentos en ordenador
			// archivo = new File ("Classpath.txt");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			while((linea = br.readLine())!=null){
				texto.add(linea);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}		
		return texto;
	}
}
