package edu.smith.cs.csc212.p4;

public class SecretExit extends Exit {

	private boolean hidden; 
	public SecretExit(String target, String description) {
		super(target, description);
		//automatically sets hidden places as hidden 
		this.hidden =true; 
		

	}
	//if it is hidden return hidden 
	public boolean isHidden() {
		return hidden ; 
		
	}
	///if the place is searched for it is no longer hidden 
	public void search() {
		this.hidden = false; 
	}


}
