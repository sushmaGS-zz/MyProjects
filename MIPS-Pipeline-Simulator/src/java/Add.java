package com.sample1;

public class Add extends Instruction {

	public Add(String[] params) {
		stages = new String[] {"IF","ID","EX","M1","WB"}; //six stage pipeline
		operation = params[0];
		operands = new String[] {params[1],params[2],params[3]};
	}

	@Override
	public void run() {
		double addend1 = InstructionManager.fpregs.get(operands[1]);
		
		double addend2 = InstructionManager.fpregs.get(operands[2]);
		
		double sum = addend1 + addend2;
		InstructionManager.fpregs.put(operands[0], sum);
		}
	
}
