package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import src.Exceptions.AddressOutOfBounds;
import src.Exceptions.IncorrectRegisterValue;
import src.Exceptions.NonExistingRegister;
import src.RegisterFile.RegisterFile;

public class CPU {
    static  int instructionPointer;
    static Memory m;
    static int clockCycle;


    public CPU(){
        instructionPointer = 0;
         m= new Memory();
       clockCycle = 1;
    }

    public static  void readFile(String program) throws FileNotFoundException , IOException, AddressOutOfBounds {

        BufferedReader reader = new BufferedReader(new FileReader(program));
        String line="";
       
        String [] input;
        while ((line = reader.readLine()) != null) {
            input=line.split(" ");
            convertToBinary(input);
           System.out.println(Arrays.toString(input));
        }
    }






    public static  void convertToBinary(String[] input) throws AddressOutOfBounds {

       

     if(instructionPointer > 1023){
        throw new AddressOutOfBounds("The Instruction Memory is full");
     }


        String result = "";
        int r = identifyNoOfIterations(input[0]);
      




        
        switch(input[0]){
         
            case "ADD":result ="0000";break;
            case "SUB":result ="0001";break;
            case "MUL":result ="0010";break;
            case "MOVI":result ="0011";break;
            case "JEQ":result ="0100";break;
            case "AND":result ="0101";break;
            case "XORI":result ="0110";break;
            case "JMP":result ="0111";break;
            case "LSL":result ="1000";break;
            case "LSR":result ="1001";break;
            case "MOVR":result ="1010";break;
            case "MOVM":result ="1011";break;
            default: new NonExistingRegister("The Operation is not supported").getMessage();


        }

        if(input[0].equals("MOVI")){
            result+=getRegisterBinary(input[1])+"00000";
            if(Integer.parseInt(input[2]) < 0){
                String tmp = toBinaryTwoComplement(Integer.parseInt(input[2].substring(1)));
                result += signExtendBinary(tmp, 18, true);
            }
            else{
                result += signExtendBinary(Integer.toBinaryString(Integer.parseInt(input[2])), 18, false);
               }
            m.memory[instructionPointer] = result;
            instructionPointer++;
            return;
        }
        else if(input[0].equals("LSL") || input[0].equals("LSR")){
            
            result+=getRegisterBinary(input[1])+getRegisterBinary(input[2])+"00000"+signExtendBinary(Integer.toBinaryString(Integer.parseInt(input[3])),13,false);

            m.memory[instructionPointer] = result;
            instructionPointer++;
            return;
        }
          




        switch(r){
            case 4:result+=getRegisterBinary(input[1])+getRegisterBinary(input[2])+getRegisterBinary(input[3])+"0000000000000";break;
            case 3:
            
            result+=getRegisterBinary(input[1])+getRegisterBinary(input[2]);
            //+signExtendBinary(Integer.toBinaryString(Integer.parseInt(input[3])),18);break;
            if(Integer.parseInt(input[3]) <0) {
                String tmp = toBinaryTwoComplement(Integer.parseInt(input[3].substring(1)));
                result += signExtendBinary(tmp, 18, true);
            }
           else{
            result += signExtendBinary(Integer.toBinaryString(Integer.parseInt(input[3])), 18, false);
           }
           break;
           
           
           
           
           
            case 2:result+=signExtendBinary(Integer.toBinaryString(Integer.parseInt(input[1])),28,false);
            break;
        }
        

        m.memory[instructionPointer] = result;
        instructionPointer++;
      

    

        
     


    }






    public static String signExtendBinary(String binary,int x,boolean isNegative) {
        // Check if the binary number is positive or negative by checking the most significant bit
        
        
        // If the binary number is negative, extend it with 1's to the left
        if (isNegative) {
            while (binary.length() < x) {
                binary = "1" + binary;
            }
        }
        // If the binary number is positive, extend it with 0's to the left
        else {
            while (binary.length() < x) {
                binary = "0" + binary;
            }
        }
        
        return binary;
    }
   






public static String getRegisterBinary(String string){
  switch(string){
    case "R0":return "00000";
    case "R1":return "00001";
    case "R2":return "00010";
    case "R3":return "00011";
    case "R4":return "00100";
    case "R5":return "00101";
    case "R6":return "00110";
    case "R7":return "00111";
    case "R8":return "01000";
    case "R9":return "01001";
    case "R10":return "01010";
    case "R11":return "01011";
    case "R12":return "01100";
    case "R13":return "01101";
    case "R14":return "01110";
    case "R15":return "01111";
    case "R16":return "10000";
    case "R17":return "10001";
    case "R18":return "10010";
    case "R19":return "10011";
    case "R20":return "10100";
    case "R21":return "10101";
    case "R22":return "10110";
    case "R23":return "10111";
    case "R24":return "11000";
    case "R25":return "11001";
    case "R26":return "11010";
    case "R27":return "11011";
    case "R28":return "11100";
    case "R29":return "11101";
    case "R30":return "11110";
    case "R31":return "11111";




  }
  return new NonExistingRegister("This is not a General Purpose Register").getMessage();

}

    public  static int identifyNoOfIterations(String string) {
        switch(string){

        case "ADD","SUB","MUL","AND","LSL","LSR":return 4;
        case "MOVI","JEQ","XORI","MOVR","MOVM":return 3;
        case "JMP":return 2;
        }
        return 0;
    }









    public static String toBinaryTwoComplement(int num) {
        // Determine the number of bits needed to represent the number
        int bits = 32 - Integer.numberOfLeadingZeros(Math.abs(num));
        
        // Get the two's complement representation of the number
        int complement = (1 << bits) - Math.abs(num);
        
        // Convert the complement to a binary string
        String binary = Integer.toBinaryString(complement);
        
        // Pad the binary string with leading zeros if necessary
        while (binary.length() < bits) {
            binary = "0" + binary;
        }
        
        return binary;
    }








    public static void startRunning() throws NumberFormatException, NonExistingRegister, AddressOutOfBounds, IncorrectRegisterValue
    {
  
        System.out.println("I am in start Running");
        String theNextInstructionToBeDecoded = "";
        String theNextInstructionToBeExecuted = "";
        String theNextInstructionToUseMemory = "";
        String theNextInstructionToBeWrittenBack = "";
        int decodeCounter = 0;
        int executeCounter = 0;
        int limit = 7 + ((instructionPointer) * 2);
        for(;clockCycle<=limit;clockCycle++){
            if(clockCycle == 11){
                System.out.println("safwat gad3");
            }
          if(clockCycle % 2 == 1){
            theNextInstructionToBeDecoded =  Stages.fetch();
          }
          if(clockCycle == 4){
            System.out.println("lol");
          }
         if(clockCycle> 1 && !(theNextInstructionToBeDecoded .equals( ""))){
            if(decodeCounter == 0)
                decodeCounter+=1;
            else
            {
                
                Stages.decode(theNextInstructionToBeDecoded);
                theNextInstructionToBeExecuted = theNextInstructionToBeDecoded;
                theNextInstructionToBeDecoded ="";
                decodeCounter = 0;
                
            }
         }
         if(clockCycle > 3 && !(theNextInstructionToBeExecuted.equals(""))){
            if(executeCounter == 0)
                executeCounter+=1;
            else
            {
                Stages.execute();
                theNextInstructionToUseMemory = theNextInstructionToBeExecuted;
                theNextInstructionToBeExecuted = "";
                executeCounter = 0;
            }
         }
         if(clockCycle > 5 && !(theNextInstructionToUseMemory.equals(""))){
            if(clockCycle % 2 == 0)
            {
                Stages.memory();
                theNextInstructionToBeWrittenBack = theNextInstructionToUseMemory;
                theNextInstructionToUseMemory = "";
            }
         }
         if(clockCycle > 6 && !(theNextInstructionToBeWrittenBack.equals(""))){
            if(clockCycle % 2 == 1)
            {
                Stages.writeBack();
                theNextInstructionToBeWrittenBack = "";
            }
         }
         System.out.println("The current clock cycle is: "+clockCycle);
        }
        //Printing the register file here
        RegisterFile.getRegisterFile().printRegisters();
        //Printing the memory here
         m.printMemory();
     }




public static void executeProgram(String fileName) throws FileNotFoundException, IOException, NumberFormatException, NonExistingRegister, AddressOutOfBounds, IncorrectRegisterValue{
    readFile(fileName);
    startRunning();

}


public static void main(String[]args) throws FileNotFoundException, IOException, NumberFormatException, NonExistingRegister, AddressOutOfBounds, IncorrectRegisterValue{


    // CPU c = new CPU();
    // executeProgram("theFile.txt");

    // String address = "1010101010101010101010101010";
    // int parsedAddress = Integer.parseInt(address,2);  
    // int currentPC = 0b11110000000000000000000000000000;
    // System.out.println(Integer.bitCount(currentPC));
    // int x = parsedAddress | currentPC;
    // System.out.println(x);
//     int num = 0b11010110; // the binary representation of the integer
//     int first4Bits = (num >> 4) & 0b1111; // shift right by 4 and mask the first 4 bits

//    System.out.println("The first 4 bits of " + num + " are " + Integer.toBinaryString(first4Bits));

//    int t = RegisterFile.getRegisterFile().getPCRegister().getData() & 0b11110000000000000000000000000000;
//                 t = t >>> 28;
//                 String ye = Integer.toBinaryString(t);
//                 RegisterFile.getRegisterFile().getPCRegister().setData(Integer.parseInt(ye.concat(jumpAddress)));
//     int currentPC = 0b11110000000000000000000000000000;
//     currentPC = currentPC >>> 28;
//     String w = Integer.toBinaryString(currentPC);
//     System.out.println(w);
//     String address = "1010101010101010101010101010";
//     //System.out.println(w.concat(address));
//     w+=address;
//     System.out.println(w);
//     System.out.println((int)Long.parseLong(w, 2));




// long x =5 ;
// int y = (int)x;
CPU c = new CPU();
c.executeProgram("theFile.txt");
}
}
