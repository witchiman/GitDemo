package com.hui.bridge;

public class ShoppingCommand extends Command {

	@Override
	public void execute() {
		System.out.println("Go to suppermarket! v_v");
		
	}

	@Override
	public void undo() {
		System.out.println("Thank goodness! -o-");
		
	}

}
