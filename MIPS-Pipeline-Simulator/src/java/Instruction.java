package com.sample1;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Instruction {
	
	public String[] stages; //the stages associated with a particular type of instruction

	public String[] operands;
	public String operation;
	public ArrayList<String> stageList = new ArrayList<String>(); //this is the list of IF, ID, EXE, etc along with the appropriate stalls and wait-to-begin characters
	public String currentStage = "ns"; //default is ns (not started)
	
	/*
	 * This increments the pipeline stage and adds the new stage to the stageList
	 */
	public void incrementStage() {
		if (currentStage == "ns") {
			currentStage = stages[0];
			stageList.add(currentStage);
		} else if (currentStage == stages[stages.length-1]){
			stageList.add("f");
		} else {
			int csIndex = Arrays.asList(stages).indexOf(currentStage);
			currentStage = stages[csIndex+1];
			stageList.add(currentStage);
		}
	}
	
	
	public void stall() {
		stageList.add("s");
	}
	
	public void waitStart() {
		stageList.add("ns");
	}
	
	/*
	 * returns the index of a particular stage
	 */
	public int getStageIndex(String stage) {
		return Arrays.asList(stages).indexOf(stage);
	}
	
	/*
	 * returns the current stageList index
	 */
	public int getCurStageIndex() {
		return Arrays.asList(stages).indexOf(currentStage);
	}
	
	public boolean hasDependency() {
		if ((this.hasWAW()) || (this.hasRAW()) || (this.hasWAR())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasWAW() {
		boolean dependency = false;
		int index = InstructionManager.instructions.indexOf(this);
		for (int i=0; i<index; i++) {
			Instruction prevInst = InstructionManager.instructions.get(i);
			/*
			 * the following offset is necessary because S.D, ADD.D, and MUL.D resolve during the last execution phase,
			 * while L.D resolves during MEM.
			 */
			int offset = 0;
			if ((prevInst.operation.equals("DADD")) || (prevInst.operation.equals("DMUL")) || (prevInst.operation.equals("SD"))) {
				offset = -1;
			}
			if (prevInst.getCurStageIndex() < prevInst.getStageIndex("MEM") + offset) {
				/*
				 * compare memory locations if I-REGSITERS
				 */
				if ((this.operands[0].matches(".*R.{1}[0-9]{1,2}.{1}")) && (prevInst.operands[0].matches(".*R.{1}[0-9]{1,2}.{1}"))) {
					if (InstructionManager.parseMemLocation(this.operands[0]).equals(InstructionManager.parseMemLocation(prevInst.operands[0]))) {
						dependency = true;
					}
				} else if (this.operands[0].equals(prevInst.operands[0])) {
					dependency = true;
				}
			}
		}
		
		
		return dependency;
	}
	
	public boolean hasRAW() {
		boolean dependency = false;
		int index = InstructionManager.instructions.indexOf(this);
		for (int i=0; i<index; i++) {
			Instruction prevInst = InstructionManager.instructions.get(i);
			/*
			 * the following offset is necessary because S.D, ADD.D, and MUL.D resolve during the last execution phase,
			 * while L.D resolves during MEM.
			 */
			int offset = 0;
			if ((prevInst.operation.equals("DADD")) || (prevInst.operation.equals("DMUL")) || (prevInst.operation.equals("SD"))) {
				offset = -1;
			}
			if (prevInst.getCurStageIndex() < prevInst.getStageIndex("MEM") + offset) {
				for (int k=1; k<this.operands.length; k++) {
					/*
					 * compare memory locations if I-REGSITERS
					 */
					if ((this.operands[0].matches(".*R.{1}[0-9]{1,2}.{1}")) && (prevInst.operands[k].matches(".*R.{1}[0-9]{1,2}.{1}"))) {
						if (InstructionManager.parseMemLocation(this.operands[k]).equals(InstructionManager.parseMemLocation(prevInst.operands[0]))) {
							dependency = true;
						}
					} else if (this.operands[k].equals(prevInst.operands[0])) {
						dependency = true;
					}
				}
			}
		}
		return dependency;
	}
	
	
	public boolean hasWAR() {
		boolean dependency = false;
		int index = InstructionManager.instructions.indexOf(this);
		for (int i=0; i<index; i++) {
			Instruction prevInst = InstructionManager.instructions.get(i);
			/*
			 * the following offset is necessary because S.D, ADD.D, and MUL.D resolve during the last execution phase,
			 * while L.D resolves during MEM.
			 */
			int offset = 0;
			if ((prevInst.operation.equals("DADD")) || (prevInst.operation.equals("DMUL")) || (prevInst.operation.equals("SD"))) {
				offset = -1;
			}
			if (prevInst.getCurStageIndex() < prevInst.getStageIndex("MEM") + offset) {
				for (int k=1; k<prevInst.operands.length; k++) {
					/*
					 * compare memory locations if I-REGSITERS
					 */
					if ((this.operands[0].matches(".*R.{1}[0-9]{1,2}.{1}")) && (prevInst.operands[k].matches(".*R.{1}[0-9]{1,2}.{1}"))) {
						if (InstructionManager.parseMemLocation(this.operands[0]).equals(InstructionManager.parseMemLocation(prevInst.operands[k]))) {
							dependency = true;
						}
					} else if (this.operands[0].equals(prevInst.operands[k])) {
						dependency = true;
					}
				}
			}
		}
		
		
		return dependency;
	}

	/*
	 * This is abstract because only the subclass methods are defined.
	 */
	public abstract void run();

}
