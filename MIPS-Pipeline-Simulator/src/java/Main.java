package com.sample1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
	
	public static Map<String,String> iregs = new HashMap<String,String>();
	public static Map<String,Double> fpregs = new HashMap<String,Double>();
	public static Map<String,Double> mem = new HashMap<String,Double>();
	public static String[][] code;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("**************************************************");
		System.out.println("---------------- MIPS64 SIMULATOR ----------------");
		System.out.println("**************************************************");
		System.out.println();
		
		//initialize input reader
		Scanner kb = new Scanner(System.in);
		//System.out.print("Enter input filename: ");
		//String inputFile = kb.nextLine();
		String inputFile = "C:\\Users\\hemanthsivaram\\Documents\\workspace\\adv topics\\MIPS\\src\\com\\sample1\\"
				+ "input.txt";
		File file = new File(inputFile);
		
		//initialize file reader
		Scanner in = new Scanner(file);
		
		
		//READ FILE
		
		String input = null;
		
		//read in I-REGISTERS
		ArrayList<String> iregList = new ArrayList<String>();
		//skip first line
		in.nextLine();
		while(in.hasNextLine() && !((input = in.nextLine()).equals("FP-REGISTERS"))){
		    iregList.add(input);
		}
		System.out.println("\n\niregList\n"+iregList);
		
		//read in FP-REGISTERS
		ArrayList<String> fpregList = new ArrayList<String>();
		while(in.hasNextLine() && !((input = in.nextLine()).equals("MEMORY"))){
		    fpregList.add(input);
		}
		System.out.println("\n\nfpregList\n"+fpregList);
		
		//read in MEMORY
		ArrayList<String> memList = new ArrayList<String>();
		while(in.hasNextLine() && !((input = in.nextLine()).equals("CODE"))){
		    memList.add(input);
		}
		System.out.println("\n\nmemList\n"+memList);
		
		//read in instructions
		ArrayList<String> codeList = new ArrayList<String>();
		while(in.hasNextLine()){
			input = in.nextLine();
		    codeList.add(input);
		}
		
		System.out.println("\n\ncodeList");
		for (int i = 0; i < codeList.size(); i++) {
			System.out.println(codeList.get(i));
		}
		System.out.println();
		//close Scanner
		in.close();
		
		//process data
		
		//map i-register values

		for (int i=0; i<iregList.size(); i++) {
			String[] vals = iregList.get(i).split("\\s+");
			iregs.put(vals[0], vals[1]);
		}
		System.out.println(iregs);
		//map fp-register values
		
		for (int i=0; i<fpregList.size(); i++) {
			String[] vals = fpregList.get(i).split("\\s+");
			fpregs.put(vals[0], Double.parseDouble(vals[1]));
		}
		System.out.println(fpregs);
		//map memory values
		
		for (int i=0; i<memList.size(); i++) {
			String[] vals = memList.get(i).split("\\s+");
			mem.put(vals[0], Double.parseDouble(vals[1]));
		}
		System.out.println(mem);
			
		//create code array
		code = new String[codeList.size()][];
		for (int i=0; i<codeList.size(); i++) {
			System.out.println(codeList.get(i));
			code[i] = codeList.get(i).replaceAll("," , " ").trim().split("\\s+");
			//System.out.println(code[i]);
		}
		
			
		InstructionManager mgr = new InstructionManager(iregs,fpregs,mem,code);
		mgr.exec();
		
		
		mgr.print();
		System.out.println("hello");
		mgr.runCode();
		
		ArrayList<Instruction> instructions = mgr.getInstructions();
		Map<String,Double> fpregs = mgr.getFPregs();
		
		//initialize file writers
		System.out.print("Enter timing filename: ");
		//String timingfile = kb.nextLine();
		String timingfile = "C:\\Users\\hemanthsivaram\\Documents\\workspace\\adv topics\\MIPS\\src\\com\\sample1\\"
				+ "timingfile.txt";
		System.out.print("Enter register filename: ");
		//String registerfile = kb.nextLine();
		String registerfile = "C:\\Users\\hemanthsivaram\\Documents\\workspace\\adv topics\\MIPS\\src\\com\\sample1\\"
				+ "registerfile.txt";
		//close scanner
		kb.close();
		
		
		//write to timing file
		PrintWriter writer = new PrintWriter(timingfile);
		writer.print("    ");
		for (int i=0; i<instructions.size(); i++) {
			int num = i + 1;
			writer.print("I#" + num + "\t");
		}
		writer.println();
		for (int i=0; i<instructions.get(instructions.size()-1).stageList.size(); i++) {
			int num = i+1;
			//This is necessary to avoid tabbing issues in the output file.
			String spacing = num + "";
			if (num < 10) {
				spacing = num + " ";
			};
			writer.print("c#" + spacing + "");
			
			for (int j=0; j<instructions.size(); j++) {
				if (i < instructions.get(j).stageList.size()) {
					String stage = instructions.get(j).stageList.get(i);
					if ((stage == "ns") || (stage == "f")) {
						writer.print(" ");
					} else {
						writer.print(stage);
					}
					
					writer.print("\t");
				}
			}
			writer.println();
		}
		writer.close();
		
		//write to register file
		writer = new PrintWriter(registerfile, "UTF-8");
		for (String key : fpregs.keySet()) {
		    writer.println(key + "\t" + fpregs.get(key));
		}
		writer.close();
		
		System.out.println("\nWritten to file.");
	}

}
