package com.sample1;

public class Mult extends Instruction {

	public Mult(String[] params) {
		stages = new String[] {"IF","ID","EX","M1","WB"}; //eight stage pipeline
		operation = params[0];
		operands = new String[] {params[1],params[2],params[3]};
	}

	@Override
	public void run() {
		double factor1 = InstructionManager.fpregs.get(operands[1]);
		
		double factor2 = InstructionManager.fpregs.get(operands[2]);
		
		double product = factor1 * factor2;
		InstructionManager.fpregs.put(operands[0], product);
	}
	
}
