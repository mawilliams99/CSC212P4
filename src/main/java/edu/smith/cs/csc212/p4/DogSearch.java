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
	 * This constructor builds our Dog search game.
	 */
	public DogSearch() {
	///this creates the main entry hall of the hotel and adds exits to it 
		Place entranceHall = insert(
				Place.create("entranceHall", "Oh no your dog got off his leash and ran into an abandoned hotel!\n"
						+ "You have to find him! Search through the hotel!"));
		entranceHall.addExit(new Exit("basement", "There are stairs leading down."));
		entranceHall.addExit(new Exit("stairway0", "There are stairs leading up."));
		entranceHall.addExit(new Exit("office", "There is a door that says 'staff only' on it."));
		entranceHall.addExit(new Exit("elevator", "There is an elevator and it looks like it still works!"));
		///this creates the basement of the hotel and adds exits to it 		
		Place basement = insert(
				Place.create("basement", "You have found the basement of the hotel.\n" + 
		                           "It is darker down here.\n" +
						"There's puddles on the floor and it's very quiet. It doesn't seem like your dog is here."
						+ " But there is an old bagel you could 'take' it as a snack"
						));
		basement.addItem("old bagel");
		basement.addExit(new Exit("entranceHall", "There are stairs leading up."));
		///this creates the office of the hotel and adds exits to it 		
		Place office = insert(
				Place.create("office", "You're inside the staff office. There are security cameras displayed"
						+ " on the wall. You check and can see your dog got in the elevator!!"
						+ "There is also a staff key in this room I would suggest you 'take' it "
						+ " just in case!"             
						));
		office.addItem("Staff Key");
		office.addExit(new Exit("entranceHall", "Go back to the lobby."));
		
		///this allows it so you can go up multiple flights of stairs before getting to the roof
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
		///there is no way back off the roof so the game will end here because the player gets stuck
		Place roof = insert(
				Place.terminal("roof", "You're on the roof."
						+ "I think the door may have locked behind you..."             
						));
		
		//the elevator has exits on multiple floors 
		Place elevator = insert(
				Place.create("elevator", "You're inside the elevator. Which "
						+ " floor would you like to go to?"             
						));
		elevator.addExit(new Exit("entranceHall", "Go back to the lobby."));
		elevator.addExit(new Exit("secondFloor", "Take it to the second floor."));
		elevator.addExit(new Exit("thirdFloor", "Take it to the third floor."));
		///this creates the second floor of the hotel and adds exits to it 
		Place secondFloor = insert(
				Place.create("secondFloor", "The second floor looks like a construction site."
						+ " Your dog isn't here"             
						));
		secondFloor.addExit(new Exit("elevator", "Go back in the elevator."));
		///this creates the third floor of the hotel and adds exits to it 
		Place thirdFloor = insert(
				Place.create("thirdFloor", "You don't see your dog but you can hear him barking!"
						+ " I think he may be in one of the hotel rooms! Maybe you should 'search' the "
						+ " floor"             
						));
		thirdFloor.addExit(new Exit("elevator", "Go back in the elevator."));
		thirdFloor.addExit(new SecretExit("hotelroom1", "Try to get into the first hotel room."));
		thirdFloor.addExit(new LockedExit("hotelroom2", "You need a key to open this door", "Staff Key"));
		///because this is a terminal place the game ends when the player gets here because they have won
		Place hotelroom1 = insert(Place.terminal("hotelroom1", "You have found your dog!.\n"
				+"It's time to go home :)"));
		
		Place hotelroom2 = insert(Place.create("hotelroom2", "This room is just filled with "
				+ "pictures of Elen Degeneres....why is it locked... creepy "));
		hotelroom2.addExit(new Exit("thirdFloor", "Go back into the hallway."));


		
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


