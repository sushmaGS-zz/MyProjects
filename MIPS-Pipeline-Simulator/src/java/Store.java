package com.sample1;

public class Store extends Instruction {

	public Store(String[] params) {
		stages = new String[] {"IF","ID","EX","M1","WB"}; //five stage pipeline
		operation = params[0];
		operands = new String[] {params[1],params[2]};
	}

	@Override
	public void run() {
		double value = 0; 

		value = InstructionManager.fpregs.get(operands[1]);

		InstructionManager.mem.put(InstructionManager.parseMemLocation(operands[0]), value);
	}
	
}
