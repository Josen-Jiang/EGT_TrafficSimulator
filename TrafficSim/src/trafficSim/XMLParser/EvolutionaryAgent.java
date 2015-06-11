package trafficSim.XMLParser;

import java.util.ArrayList;


public class EvolutionaryAgent {
	
	
	int quantity;
	ArrayList<norm> ns;
	norm n;
		
	public EvolutionaryAgent(int quantity, ArrayList<norm> ns){
		this.quantity = quantity;
		this.ns = ns;
	}
	
	public int getQuantity(){
		return this.quantity;
	}
	
	public ArrayList<norm> getNS(){
		return this.ns;
	}
}
