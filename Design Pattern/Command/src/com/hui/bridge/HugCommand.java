package com.hui.bridge;

public class HugCommand extends Command {

	@Override
	public void execute() {
		System.out.println("Really?  * O * |^ V ^");
		
	}

	@Override
	public void undo() {
		System.out.println("Shit! + @ +");
		
	}

}
