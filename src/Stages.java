package src;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import src.Exceptions.AddressOutOfBounds;
import src.Exceptions.IncorrectRegisterValue;
import src.Exceptions.NonExistingRegister;
import src.RegisterFile.Register;
import src.RegisterFile.RegisterFile;





public class Stages 
{
    //Execute case 4 and 7 need to be reviewed
    //Memroy method needs to be changed according to 3mora code

    //Dummy registerFile, Memory, and PC register variables
    static int registerFile [] = {22,16,87,54,102,434,33,67,98,49,55,9,18,1,32,45};
    static String memory[] = {"-1321039047","992777138"};

    static String instruction;

    //Decode variables
   // static String opcode = "";      // bits31:28
   // static String r1Address = "";   // bits27:23
   // static String r2Address = "";   // bit22:18
   // static String r3Address = "";   // bits17:13 incase of R-format
   // static String shamt = "";       // bits12:0 incase of R-format
  //  static String imm = "";         // bits17:0 incase of I-format
   // static String jumpAddress = ""; // bits27:0 incase of J-format
   // static int r1 = 0;
    //static int r2 = 0;
    //static int r3 = 0;
    //RegisterFile.getRegisterFile().getPCRegister().getData()
    //RegisterFile.getRegisterFile().getPCRegister().incrementPC()

    //Execute variables
    // static int resultant = 0;
    // static boolean loadStoreFlag; //true = load op, false = store op
    // static int memoryAddress;
    // static int toBeStored = 0;
    // static boolean writeBackFlag = false; //false = no writeBack required
    
  
    static Queue<String> DXEX = new LinkedList<String>();
    static Queue<String> EXMEM = new LinkedList<String>();
    static Queue<String> MEMWB = new LinkedList<String>();

    public static String fetch() throws AddressOutOfBounds, IncorrectRegisterValue 
    {
        if(RegisterFile.getRegisterFile().getPCRegister().getData() == 1024 || CPU.m.memory[RegisterFile.getRegisterFile().getPCRegister().getData()] == null)
            return "";
        //CPU.m.printMemory();
        instruction = CPU.m.memory[RegisterFile.getRegisterFile().getPCRegister().getData()];
        RegisterFile.getRegisterFile().getPCRegister().incrementPC();
        System.out.println("The input to the fetch stage is the PC register: " + RegisterFile.getRegisterFile().getPCRegister().getData());
        CPU.s+="The input to the fetch stage is the PC register: " + RegisterFile.getRegisterFile().getPCRegister().getData()+"\n";
        System.out.println("The current instruction being fetched is: "+ instruction);
        CPU.s +="The current instruction being fetched is: "+ instruction+"\n";
        return instruction;
    }
    
    public static void decode(String instruction) throws NumberFormatException, NonExistingRegister 
    {
        if(instruction.equals(""))
            return;

        System.out.println("The current instruction being decoded is: "+ instruction);
        CPU.s += "The current instruction being decoded is: "+ instruction+"\n";
        String opcode = instruction.substring(0, 4);
        String r1Address = instruction.substring(4, 9);
        String r2Address = instruction.substring(9, 14);
        String r3Address = instruction.substring(14, 19);
        String shamt = instruction.substring(19);
        String imm = instruction.substring(14);
        String jumpAddress = instruction.substring(4);
        // int r1 = RegisterFile.getRegisterFile().getRegister(Integer.parseInt(r1Address,2)).getData();
        // int r2 = RegisterFile.getRegisterFile().getRegister(Integer.parseInt(r2Address,2)).getData();
        // int r3 = RegisterFile.getRegisterFile().getRegister(Integer.parseInt(r3Address,2)).getData();
        DXEX.add(opcode);
        DXEX.add(r1Address);
        DXEX.add(r2Address);
        DXEX.add(r3Address);
        DXEX.add(shamt);
        DXEX.add(imm);
        DXEX.add(jumpAddress);
        DXEX.add(RegisterFile.getRegisterFile().getPCRegister().getData()+"");
        // DXEX.add(r1+"");
        // DXEX.add(r2+"");
        // DXEX.add(r3+"");
        
            
    }

    public static void execute() throws NumberFormatException, AddressOutOfBounds, IncorrectRegisterValue, NonExistingRegister 
    {
        if(DXEX.isEmpty())
            return;

        String opcode = DXEX.remove();
        String r1Address = DXEX.remove();
        String r2Address = DXEX.remove();
        String r3Address = DXEX.remove();
        String shamt = DXEX.remove();
        String imm = DXEX.remove();
        String jumpAddress = DXEX.remove();
        int oldPC = Integer.parseInt(DXEX.remove());
        // int r1 = Integer.parseInt(DXEX.remove());
        // int r2 = Integer.parseInt(DXEX.remove());
        // int r3 = Integer.parseInt(DXEX.remove());
        int r1 = RegisterFile.getRegisterFile().getRegister(Integer.parseInt(r1Address,2)).getData();
        int r2 = RegisterFile.getRegisterFile().getRegister(Integer.parseInt(r2Address,2)).getData();
        int r3 = RegisterFile.getRegisterFile().getRegister(Integer.parseInt(r3Address,2)).getData();
       


        String s = "Input to Execute Stage: ";
        s+= "opcode: "+opcode+", ";
        s+= "r1Address: "+r1Address+", ";
        s+= "r2Address: "+r2Address+", ";
        s+= "r3Address: "+r3Address+", ";
        s+= "shamt: "+shamt+", ";
        s+= "imm: "+imm+", ";
        s+= "jumpAddress: "+jumpAddress+", ";
        s+= "oldPC: "+oldPC+", ";
        s+= "r1: "+r1+", ";
        s+= "r2: "+r2+", ";
        s+= "r3: "+r3+", ";  


        System.out.println(s);
        CPU.s+=s+"\n";



        int resultant = 0;
        int loadStoreFlag = 0;
        int memoryAddress = 0;
        int toBeStored = 0;
        boolean writeBackFlag = false;

        
        int temp = 0;
        switch (Integer.parseInt(opcode,2)) 
        {
            case 0: //Add: R1 = R2+R3
                resultant = r2 + r3;
                writeBackFlag = true;
                System.out.println("The current instruction being executed is ADD: R" + Integer.parseInt(r1Address,2) + " = " + "R"+ Integer.parseInt(r2Address,2) + " + R" + Integer.parseInt(r3Address,2));
                CPU.s+="The current instruction being executed is ADD: R" + Integer.parseInt(r1Address,2) + " = " + "R"+ Integer.parseInt(r2Address,2) + " + R" + Integer.parseInt(r3Address,2)+"\n";
                break;

            case 1: //Sub: R1 = R2-R3
                resultant = r2 - r3;
                writeBackFlag = true;
               System.out.println("The current instruction being executed is SUB: R" + Integer.parseInt(r1Address,2) + " = " + "R"+ Integer.parseInt(r2Address,2) + " - R" + Integer.parseInt(r3Address,2));
                CPU.s += "The current instruction being executed is SUB: R" + Integer.parseInt(r1Address,2) + " = " + "R"+ Integer.parseInt(r2Address,2) + " - R" + Integer.parseInt(r3Address,2)+"\n";
                break;

            case 2: //Mul: R1 = R2*R3
                resultant = r2 * r3;
                writeBackFlag = true;
              System.out.println("The current instruction being executed is MUL: R" + Integer.parseInt(r1Address,2) + " = " + "R"+ Integer.parseInt(r2Address,2) + " * R" + Integer.parseInt(r3Address,2));
              CPU.s += "The current instruction being executed is MUL: R" + Integer.parseInt(r1Address,2) + " = " + "R"+ Integer.parseInt(r2Address,2) + " * R" + Integer.parseInt(r3Address,2)+"\n";
                break;

            case 3: //Movi: R1 = IMM (R2 will be 0)
                
                writeBackFlag = true;
               System.out.println("The current instruction being executed is MOVI: R" + Integer.parseInt(r1Address,2) + " = IMM");
                 CPU.s += "The current instruction being executed is MOVI: R" + Integer.parseInt(r1Address,2) + " = IMM"+"\n";
                char c = imm.charAt(0);
                int  n = 0;
                if(c == '1'){
            
                int len = imm.length();
                
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) { 
                     c = imm.charAt(i);
                   char f = c == '0' ? '1' : '0'; 
                  sb.append(f);
                  n = Integer.parseInt(sb.toString(), 2);
                  n= n+1;
                }
                
               // System.out.println("The output : "+n);
                }
                resultant = n == 0? Integer.parseInt(imm,2) : n*-1;
                
                break;

            case 4: //Jump if equal: IF(R1 == R2) {PC = PC+1+IMM }
                if(r1 == r2){

                   c = imm.charAt(0);
                  n = 0;
                if(c == '1'){
            
                int len = imm.length();
                
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) { 
                     c = imm.charAt(i);
                   char f = c == '0' ? '1' : '0'; 
                  sb.append(f);
                  n = Integer.parseInt(sb.toString(), 2);
                  n= n+1;
                }
                
               // System.out.println("The output : "+n);
                }
                resultant = n == 0? Integer.parseInt(imm,2) : n*-1;

              
        
               
                int newPC = oldPC + 1 +resultant-2;
                  System.out.println("OLD PC: "+oldPC);
                  System.out.println("New PC: "+newPC);
                RegisterFile.getRegisterFile().getPCRegister().setData(newPC);
                DXEX.clear();
                if(resultant < 0)
                    CPU.limit += 7 + (((oldPC - newPC)-1) * 2);


                }
                writeBackFlag = false;
               System.out.println("The current instruction being executed is JUMP IF EQUAL: if(R" + Integer.parseInt(r1Address,2) + " == R" +Integer.parseInt(r2Address,2) + ") {PC = PC + 1 + IMM}"); 
               CPU.s += "The current instruction being executed is JUMP IF EQUAL: if(R" + Integer.parseInt(r1Address,2) + " == R" +Integer.parseInt(r2Address,2) + ") {PC = PC + 1 + IMM}"+"\n";
                break;

            case 5: //AND: R1 = R2 & R3
                resultant = r2 & r3;
                writeBackFlag = true;
                System.out.println("The current instruction being executed is AND: R" + Integer.parseInt(r1Address,2) + " = " + "R"+ Integer.parseInt(r2Address,2) + " & R" + Integer.parseInt(r3Address,2));
                CPU.s += "The current instruction being executed is AND: R" + Integer.parseInt(r1Address,2) + " = " + "R"+ Integer.parseInt(r2Address,2) + " & R" + Integer.parseInt(r3Address,2)+"\n";
                break;

            case 6: //XORi: R1 = R2 XOR IMM

               c = imm.charAt(0);
                n = 0;
                if(c == '1'){
            
                int len = imm.length();
                
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) { 
                     c = imm.charAt(i);
                   char f = c == '0' ? '1' : '0'; 
                  sb.append(f);
                  n = Integer.parseInt(sb.toString(), 2);
                  n= n+1;
                }
                
               // System.out.println("The output : "+n);
                }
                resultant = n == 0? Integer.parseInt(imm,2) : n*-1;



                resultant = r2 ^ resultant;
                writeBackFlag = true;
                System.out.println("The current instruction being executed is XORi: R" +Integer.parseInt(r1Address) + " = " + "R"+ Integer.parseInt(r2Address) + " XOR IMM");
                CPU.s += "The current instruction being executed is XORi: R" +Integer.parseInt(r1Address) + " = " + "R"+ Integer.parseInt(r2Address) + " XOR IMM"+"\n";
                break;

            case 7: //Jump: PC = PC[31:28] || ADDRESS
                int t = RegisterFile.getRegisterFile().getPCRegister().getData() & 0b11110000000000000000000000000000;
                t = t >>> 28;
                String ye = Integer.toBinaryString(t);
                System.out.println(ye + jumpAddress);
                System.out.println(Integer.parseInt(ye + jumpAddress,2));
                RegisterFile.getRegisterFile().getPCRegister().setData(Integer.parseInt(ye + jumpAddress,2)-1);
                writeBackFlag = false;
                DXEX.clear();
                System.out.println("The current instruction being executed is JUMP: PC = PC[31:28] || ADDRESS");
                CPU.s += "The current instruction being executed is JUMP: PC = PC[31:28] || ADDRESS"+"\n";
                break;

            case 8: //Shift left: R1 = R2 << SHAMT
                resultant = r2 << Integer.parseInt(shamt,2);
                writeBackFlag = true;
               System.out.println("The current instruction being executed is SHIFT LEFT: R" + Integer.parseInt(r1Address)  + " = " + "R"+ Integer.parseInt(r2Address) + " << SHAMT");
                CPU.s += "The current instruction being executed is SHIFT LEFT: R" + Integer.parseInt(r1Address)  + " = " + "R"+ Integer.parseInt(r2Address) + " << SHAMT"+"\n";
                break;

            case 9: //Shift right: R1 = R2 >>> SHAMT
                resultant = r2 >> Integer.parseInt(shamt,2);
                writeBackFlag = true;
                System.out.println("The current instruction being executed is SHIFT RIGHT: R" + Integer.parseInt(r1Address)  + " = " + "R"+ Integer.parseInt(r2Address) + " >>> SHAMT");
                CPU.s += "The current instruction being executed is SHIFT RIGHT: R" + Integer.parseInt(r1Address)  + " = " + "R"+ Integer.parseInt(r2Address) + " >>> SHAMT"+" \n";
                break;

            case 10: //Move to register: R1 = MEM[R2 + IMM]
              c = imm.charAt(0);
                n = 0;
                if(c == '1'){
            
                int len = imm.length();
                
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) { 
                     c = imm.charAt(i);
                   char f = c == '0' ? '1' : '0'; 
                  sb.append(f);
                  n = Integer.parseInt(sb.toString(), 2);
                  n= n+1;
                }
                
               // System.out.println("The output : "+n);
                }
                resultant = n == 0? Integer.parseInt(imm,2) : n*-1;
                memoryAddress = r2+resultant;
                if(memoryAddress < 1024 || memoryAddress > 2047)
                    throw new AddressOutOfBounds("Memory Address invalid.");
                toBeStored = -1;
                loadStoreFlag = -1;
                writeBackFlag = true;
               System.out.println("The current instruction being executed is LOAD: R" + Integer.parseInt(r1Address) + " = MEM[" + "R"+Integer.parseInt(r2Address) + " + IMM]");
               s+= "The current instruction being executed is LOAD: R" + Integer.parseInt(r1Address) + " = MEM[" + "R"+Integer.parseInt(r2Address) + " + IMM]"+"\n";
                break;

            case 11: //Move to memory: MEM[R2 + IMM] = R1

                 c = imm.charAt(0);
                n = 0;
                if(c == '1'){
            
                int len = imm.length();
                
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) { 
                     c = imm.charAt(i);
                   char f = c == '0' ? '1' : '0'; 
                  sb.append(f);
                  n = Integer.parseInt(sb.toString(), 2);
                  n= n+1;
                }
                
               // System.out.println("The output : "+n);
                }
                resultant = n == 0? Integer.parseInt(imm,2) : n*-1;










                memoryAddress = r2 + resultant;
                if(memoryAddress < 1024 || memoryAddress > 2047)
                     throw new AddressOutOfBounds("Memory Address invalid.");
                toBeStored = r1;
                loadStoreFlag = 1;
                writeBackFlag = false;
                System.out.println("The current instruction being executed is STORE: MEM[R" + Integer.parseInt(r2Address) + " + IMM] = " + "R"+ Integer.parseInt(r1Address));
                s+= "The current instruction being executed is STORE: MEM[R" + Integer.parseInt(r2Address) + " + IMM] = " + "R"+ Integer.parseInt(r1Address)+"\n";
                break;
        }
        EXMEM.add(r1Address);
        EXMEM.add(r2Address);
        EXMEM.add(resultant+"");
        EXMEM.add(writeBackFlag+"");
        EXMEM.add(toBeStored+"");
        EXMEM.add(memoryAddress+"");
        EXMEM.add(loadStoreFlag+"");
     

        
    }

    public static void memory()
    
    {






        if(EXMEM.isEmpty())
            return;
        int r1Address = Integer.parseInt(EXMEM.remove(),2);
        int r2Address = Integer.parseInt(EXMEM.remove(),2);
        int resultant = Integer.parseInt(EXMEM.remove());
        boolean writeBackFlag = Boolean.parseBoolean(EXMEM.remove());
         int toBeStored = Integer.parseInt(EXMEM.remove());
        int memoryAddress = Integer.parseInt(EXMEM.remove());
        int loadStoreFlag = Integer.parseInt(EXMEM.remove());

        String s = "Input to Memory Stage: ";
        s += "r1Address: " + r1Address + ", ";
        s += "r2Address: " + r2Address + ", ";
        s += "Resultant: " + resultant + ", ";
        s += "WriteBackFlag: " + writeBackFlag + ", ";
        s += "ToBeStored: " + toBeStored + ", ";
        s += "MemoryAddress: " + memoryAddress + ", ";
        s += "LoadStoreFlag: " + loadStoreFlag + ", ";
        //System.out.println(s);


        CPU.s+=s;




        if(loadStoreFlag == -1)
        {
            //flag is true, this memory operation is a load
            if(CPU.m.memory[memoryAddress] == null)
               return;
        

            resultant =  Integer.parseInt(CPU.m.memory[memoryAddress]);
           System.out.println("The current instruction being executed is LOAD: R" + r1Address + " = MEM[" + "R"+ r2Address + " + IMM]");
           CPU.s+= "The current instruction being executed is LOAD: R" + r1Address + " = MEM[" + "R"+ r2Address + " + IMM]"+"\n";
        }
        else if(loadStoreFlag == 1)
        {
            String  oldValue = CPU.m.memory[memoryAddress];
            CPU.m.memory[memoryAddress] = toBeStored+"";
           System.out.println("The current instruction being executed is STORE: MEM[R" + r2Address + " + IMM] = " + "R"+ r1Address);
           System.out.println("The value of data memory location " + memoryAddress + " has been changed from " + oldValue + " to " + toBeStored);
            CPU.s+= "The current instruction being executed is STORE: MEM[R" + r2Address + " + IMM] = " + "R"+ r1Address+"\n";
            CPU.s+= "The value of data memory location " + memoryAddress + " has been changed from " + oldValue + " to " + toBeStored+"\n";
        }
        MEMWB.add(r1Address+"");
        MEMWB.add(resultant+"");
        MEMWB.add(writeBackFlag+"");


    }

    public static void writeBack() throws AddressOutOfBounds, IncorrectRegisterValue, NumberFormatException, NonExistingRegister
    {
        if(MEMWB.isEmpty())
            return;
        int r1Address = Integer.parseInt(MEMWB.remove());
        int resultant = Integer.parseInt(MEMWB.remove());
        boolean flag = Boolean.parseBoolean(MEMWB.remove());



       String s ="Input to WriteBack Stage: ";
        s += " r1Address: " + r1Address + ", ";
        s += "Resultant: " + resultant + ", ";
        s += "WriteBackFlag: " + flag + ", ";
        //System.out.println(s);

        CPU.s+=s;

        if(flag)
        {
         int oldValue = RegisterFile.getRegisterFile().getRegister(r1Address).getData();
           RegisterFile.getRegisterFile().getRegister(r1Address).setData(resultant);
          System.out.println("The value of register " + "R"+r1Address + " has been changed from " + oldValue + " to " + resultant);
          CPU.s+= "The value of register " + "R"+r1Address + " has been changed from " + oldValue + " to " + resultant+"\n";
        }
        //System.out.println("I am in write back method testing "+RegisterFile.getRegisterFile().getRegister(1).getData());
    }

    public static Integer convertBinaryToDecimal(Integer binaryNumber) 
    {
        Integer decimalNumber = 0;
        Integer base = 1;
    
        while (binaryNumber > 0) 
        {
            int lastDigit = binaryNumber % 10;
            binaryNumber = binaryNumber / 10;
            decimalNumber += lastDigit * base;
            base = base * 2;
        }
        return decimalNumber;
    }

    public static Integer convertDecimalToBinary(Integer decimalNumber) 
    {
        if (decimalNumber == 0) 
        {
            return decimalNumber;
        }
    
        StringBuilder binaryNumber = new StringBuilder();
        Integer quotient = decimalNumber;
    
        while (quotient > 0) 
        {
            int remainder = quotient % 2;
            binaryNumber.append(remainder);
            quotient /= 2;
        }
    
        binaryNumber = binaryNumber.reverse();
        return Integer.valueOf(binaryNumber.toString());
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

    // public static void main(String[]args)
    // {
        // //Below is how we call each method
        // fetch();
        // decode(instruction);

        // if(convertBinaryToDecimal(opcode)!=4)
        //     execute(r2, r3, opcode);
        // else
        //     execute(r1, r2, opcode);

        // memory(memoryAddress, toBeStored, loadStoreFlag);
        // writeBack(writeBackFlag);
        //System.out.println(signExtendBinary(Integer.parseInt("001001", 2)+"",32,false));

        //opcode = instruction & 0b11110000000000000000000000000000;
        //opcode = opcode >>> 28;
    //     int nine = Integer.parseInt("001001",2);
    //     int converted = convertDecimalToBinary(nine);
    //     int bet = 0b01110000010101010101010101010101;
    //     int bet2 = 0b1111111111111111111111111111;
    //     System.out.println(bet);
    //     System.out.println(""+bet+"");
    //    int w = bet || bet2;
    //    System.out.println(bet|bet2);
    //     converted = converted >>>28;
    //     int n = converted & 0b11110000000000000000000000000000;
    //     System.out.println(converted);
        // String s = "Ahmed";
        // System.out.println(s.substring(0, 1));
    //}

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

    public static void main(String[]args){
        Queue<String> instructions = new LinkedList<String>();
        instructions.add("El bshmohndas nour");

        instructions.add("Eng. Omar Fahim");
        instructions.add("Eng.omar Adel");
        instructions.add("Eng. ahmed safwat");

        instructions.remove();
        System.out.println(instructions);

    }
}
