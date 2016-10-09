package com.sample1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * This class is used to manage instances of the Instruction class
 * Its main purpose is to determine when an instruction can increment to the next pipeline stage
 */
public class InstructionManager {

	public static ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	public static Map<String,String> iregs;
	public static Map<String,Double> fpregs;
	public static Map<String,Double> mem;
	
	/*This instruction creates appropriate subclass instances of the abstract class Instruction, It then pushes the Instruction object onto the instructions ArrayList */
	


InstructionManager(Map<String,String> iregsHM, Map<String,Double> fpregsHM, Map<String,Double> memHM, String[][] code) {

		iregs = new HashMap<String,String>(iregsHM);

		fpregs = new HashMap<String,Double>(fpregsHM);
		mem = new HashMap<String,Double>(memHM);
		
		for (int i=0; i<code.length; i++) {
			if(!code[i][0].equals("NEXT"))
			{
				switch (code[i][0]) {
				case "LD":
					this.pushInstruction(new Load(code[i]));
					break;
				case "SD":
					this.pushInstruction(new Store(code[i]));
					break;
				case "DADD":
					this.pushInstruction(new Add(code[i]));
					break;
				case "DMUL":
					this.pushInstruction(new Mult(code[i]));
					break;
				case "BNEZ":
					this.pushInstruction(new Load(code[i]));
					break;
				}
			}
			else
			{
				switch (code[i][1]) {
				case "LD":
					this.pushInstruction(new Load(code[i]));
					break;
				case "SD":
					this.pushInstruction(new Store(code[i]));
					break;
				case "DADD":
					this.pushInstruction(new Add(code[i]));
					break;
				case "DMUL":
					this.pushInstruction(new Mult(code[i]));
					break;
				case "BNEZ":
					this.pushInstruction(new Load(code[i]));
					break;
				}
			}
		}
	}

	private void pushInstruction(Instruction inst) {
		// TODO Auto-generated method stub
		instructions.add(inst);
	}
	
	public static double getRegVal(String register) {
		return mem.get(iregs.get(register));
	}
	
	/*
	 * Gets memory location in I-Register
	 */
	public static String parseMemLocation(String iregStr) {
		String[] strArr = iregStr.split("\\)");
		iregStr = strArr[0];
		String[] iregArr = iregStr.split("\\(");
		int offset = Integer.parseInt(iregArr[0]);
		if (iregs.containsKey(iregArr[1]) == false) {
			iregs.put(iregArr[1], "0");
		}
		int memLocation = Integer.parseInt(iregs.get(iregArr[1])) + offset;
		
		return Integer.toString(memLocation);
	}

	/*
	 * This generates the timing structure
	 */
	void exec() {
		while (instructions.get(instructions.size() -1).currentStage != "WB") {
			
			for (int i=0; i<instructions.size(); i++) {
				Instruction inst = instructions.get(i);
				if (i == 0) {
					//first instruction has no dependencies
					inst.incrementStage();
				}
				if (i>0) {
					if ((instructions.get(i-1).currentStage.equals("ID")) && (inst.currentStage.equals("ns"))) { //"ns" means "not started"
						inst.incrementStage();
					} else if (instructions.get(i-1).getCurStageIndex() > instructions.get(i-1).getStageIndex("ID")) {
						if (inst.hasDependency()) {
							inst.stall();
						} else {
							inst.incrementStage();
						}
					} else {
						inst.waitStart();
					}
				}
			}
		}
		
	}
	
	/*
	 * runs the list of instructions
	 */
	void runCode() {
		for (int i=0; i<instructions.size(); i++) {
			System.out.println(instructions.get(i));
			instructions.get(i).run();
		}
	}
	
	/*
	 * Prints to console. Should be used if tabbing in output file doesn't work
	 */
	void print() {
		System.out.print("\t");
		for (int i=0; i<instructions.size(); i++) {
			int num = i + 1;
			System.out.print("I#" + num + "\t");
		}
		System.out.println();
		for (int i=0; i<instructions.get(instructions.size()-1).stageList.size(); i++) {
			int num = i+1;
			System.out.print("c#" + num + "\t");
			
			for (int j=0; j<instructions.size(); j++) {
				if (i < instructions.get(j).stageList.size()) {
					String stage = instructions.get(j).stageList.get(i);
					if ((stage == "ns") || (stage == "f")) {
						System.out.print(" ");
					} else {
						System.out.print(stage);
					}
					
					System.out.print("\t");
				}
			}
			System.out.println();
		}
	}

	
	ArrayList<Instruction> getInstructions() {
		return instructions;
	}
	
	Map<String,Double> getFPregs() {
		return fpregs;
	}
	
	
	
}
