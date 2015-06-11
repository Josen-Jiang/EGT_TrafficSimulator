package trafficSim.XMLParser;

public class norm {

	String name;
	int id;
	String left, front, right;
	String modality, action;
	
	public norm(String name, int id, String left, String front, String right, String modality, String action){
		this.name = name;
		this.id = id;
		this.left = left;
		this.front = front;
		this.right = right;
		this.modality = modality;
		this.action = action;
	}

	public String getName() {
  	return name;
  }

	public void setName(String name) {
  	this.name = name;
  }

	public int getId() {
  	return id;
  }

	public void setId(int id) {
  	this.id = id;
  }

	public String getLeft() {
  	return left;
  }

	public void setLeft(String left) {
  	this.left = left;
  }

	public String getFront() {
  	return front;
  }

	public void setFront(String front) {
  	this.front = front;
  }

	public String getRight() {
  	return right;
  }

	public void setRight(String right) {
  	this.right = right;
  }

	public String getModality() {
  	return modality;
  }

	public void setModality(String modality) {
  	this.modality = modality;
  }

	public String getAction() {
  	return action;
  }

	public void setAction(String action) {
  	this.action = action;
  }
	
	
}
