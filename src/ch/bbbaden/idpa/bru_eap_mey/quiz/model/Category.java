package ch.bbbaden.idpa.bru_eap_mey.quiz.model;

public class Category {
	
	private String name;
	private String description;
	
	public Category(String nName, String nDescription) {
		this.name = nName;
		this.description = nDescription;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String newDesc) {
		this.description = newDesc;
	}
	
}
