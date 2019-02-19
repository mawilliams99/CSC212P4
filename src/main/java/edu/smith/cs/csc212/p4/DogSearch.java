package edu.smith.cs.csc212.p4;

import java.util.HashMap;
import java.util.Map;

public class DogSearch implements GameWorld {
	private Map<String, Place> places = new HashMap<>();
	
	/**
	 * Where should the player start?
	 */
	@Override
	public String getStart() {
		return "entranceHall";
	}

	/**
	 * This constructor builds our SpookyMansion game.
	 */
	public DogSearch() {
		Place entranceHall = insert(
				Place.create("entranceHall", "Oh no your dog got off his leash and ran into an abandoned hotel!\n"
						+ "You have to find him! Search through the hotel!"));
		entranceHall.addExit(new Exit("basement", "There are stairs leading down."));
		entranceHall.addExit(new Exit("stairway0", "There are stairs leading up."));
		entranceHall.addExit(new Exit("office", "There is a door that says 'staff only' on it."));
		entranceHall.addExit(new Exit("elevator", "There is an elevator and it looks like it still works!"));
		
		Place basement = insert(
				Place.create("basement", "You have found the basement of the hotel.\n" + 
		                           "It is darker down here.\n" +
						"There's puddles on the floor and it's very quiet. It doesn't seem like your dog is here"
						));
		basement.addExit(new Exit("entranceHall", "There are stairs leading up."));
		
		Place office = insert(
				Place.create("office", "You're inside the staff office. There are security cameras displayed"
						+ " on the wall. You check and can see your dog got in the elevator!!"             
						));
		office.addExit(new Exit("entranceHall", "Go back to the lobby."));
		
		int stairHeight = 4;
		int laststairPart = stairHeight - 1;
		for (int i=0; i<stairHeight; i++) {
			Place stairsUp = insert(Place.create("stairway"+i,"This is a very high staircase "));
			if (i == 0) {
				stairsUp.addExit(new Exit("entranceHall", "Go back."));
			} else {
				stairsUp.addExit(new Exit("stairway"+(i-1), "Go back."));
			}
			if (i != laststairPart) {
				stairsUp.addExit(new Exit("stairway"+(i+1),  "Go forward."));
			} else {
				stairsUp.addExit(new Exit("roof", "There's a door at the top of the stairs"));
			}
		}
		Place roof = insert(
				Place.create("roof", "You're on the roof. Looks like you're dog isn't here."             
						));
		roof.addExit(new Exit("stairway"+laststairPart, "Go back down the stairs."));
		
		Place elevator = insert(
				Place.create("elevator", "You're inside the elevator. Which "
						+ " floor would you like to go to?"             
						));
		elevator.addExit(new Exit("entranceHall", "Go back to the lobby."));
		elevator.addExit(new Exit("secondFloor", "Take it to the second floor."));
		elevator.addExit(new Exit("thirdFloor", "Take it to the third floor."));
		
		Place secondFloor = insert(
				Place.create("secondFloor", "The second floor looks like a construction site."
						+ " Your dog isn't here"             
						));
		secondFloor.addExit(new Exit("elevator", "Go back in the elevator."));
		
		Place thirdFloor = insert(
				Place.create("thirdFloor", "You don't see your dog but you can hear him barking!"
						+ " I think he may be in one of the hotel rooms!"             
						));
		thirdFloor.addExit(new Exit("elevator", "Go back in the elevator."));
		thirdFloor.addExit(new Exit("hotelroom1", "Try to get into the first hotel room."));
		
		Place hotelroom1 = insert(Place.terminal("hotelroom1", "You have found your dog!.\n"
				+"It's time to go home :)"));


		
		///Place crypt = insert(Place.terminal("crypt", "You have found the crypt.\n"
				///+"It is scary here, but there is an exit to outside.\n"+
				///"Maybe you'll be safe out there."));
		
		// Make sure your graph makes sense!
		checkAllExitsGoSomewhere();
	}

	/**
	 * This helper method saves us a lot of typing. We always want to map from p.id
	 * to p.
	 * 
	 * @param p - the place.
	 * @return the place you gave us, so that you can store it in a variable.
	 */
	private Place insert(Place p) {
		places.put(p.getId(), p);
		return p;
	}

	/**
	 * I like this method for checking to make sure that my graph makes sense!
	 */
	private void checkAllExitsGoSomewhere() {
		boolean missing = false;
		// For every place:
		for (Place p : places.values()) {
			// For every exit from that place:
			for (Exit x : p.getVisibleExits()) {
				// That exit goes to somewhere that exists!
				if (!places.containsKey(x.getTarget())) {
					// Don't leave immediately, but check everything all at once.
					missing = true;
					// Print every exit with a missing place:
					System.err.println("Found exit pointing at " + x.getTarget() + " which does not exist as a place.");
				}
			}
		}
		
		// Now that we've checked every exit for every place, crash if we printed any errors.
		if (missing) {
			throw new RuntimeException("You have some exits to nowhere!");
		}
	}

	/**
	 * Get a Place object by name.
	 */
	public Place getPlace(String id) {
		return this.places.get(id);		
	}
}


