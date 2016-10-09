package com.sample1;

public class Load extends Instruction {

	public Load(String[] params) {
		stages = new String[] {"IF","ID","EX","M1","WB"}; //five stage pipeline
		operation = params[0];
		operands = new String[] {params[1],params[2]};
	}

	@Override
	public void run() {
		double value = 0; 

		value = InstructionManager.mem.get(InstructionManager.parseMemLocation(operands[1]));
		InstructionManager.fpregs.put(operands[0], value);
	}
	
}
