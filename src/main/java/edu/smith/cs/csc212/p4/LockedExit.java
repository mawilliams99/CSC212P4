package edu.smith.cs.csc212.p4;

import java.util.List;

public class LockedExit extends Exit {
	
	String keyNeeded;
	///locked exit constructor. creates a place, a description, and the key need to open the place 
	public LockedExit(String target, String description, String key) {
		super(target, description);
		this.keyNeeded = key;
	}
	///uses an override to allow the door to unlock when the correct key is found and used 
	@Override
	public boolean canUnlock(List<String> stuff) {
		if (stuff.contains(this.keyNeeded)) {
			return true;
		}
		return false;
	}



}
