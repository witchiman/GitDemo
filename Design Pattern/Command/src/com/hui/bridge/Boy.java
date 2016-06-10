package com.hui.bridge;

import java.util.ArrayList;
import java.util.List;

public class Boy {
	private String name;	 
	List<Command> commands = new ArrayList<Command>(); 
	
	public String getName() {
		return name;
	}

	public void dosomething() {
	
	}

	public void addCommand(Command c) {
		commands.add(c);
		
	}

	public void executeCommands() {
		for(Command c : commands ) {
			c.execute(); 
		}
		
	}
	
	public void undoCommands () {
		
	}
	
}
