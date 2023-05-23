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
    
  
    static Queue<String> DXEX;
    static Queue<String> EXMEM;
    static Queue<String> MEMWB;

    public static String fetch() throws AddressOutOfBounds, IncorrectRegisterValue 
    {
        if(RegisterFile.getRegisterFile().getPCRegister().getData() == 1024 || CPU.m.memory[RegisterFile.getRegisterFile().getPCRegister().getData()].equals(""))
            return "";
        instruction = CPU.m.memory[RegisterFile.getRegisterFile().getPCRegister().getData()];
        RegisterFile.getRegisterFile().getPCRegister().incrementPC();
        return instruction;
    }
    
    public static void decode(String instruction) throws NumberFormatException, NonExistingRegister 
    {
        String opcode = instruction.substring(0, 4);
        String r1Address = instruction.substring(4, 9);
        String r2Address = instruction.substring(9, 14);
        String r3Address = instruction.substring(14, 19);
        String shamt = instruction.substring(19);
        String imm = instruction.substring(14);
        String jumpAddress = instruction.substring(4);
        int r1 = RegisterFile.getRegisterFile().getRegister(Integer.parseInt(r1Address)).getData();
        int r2 = RegisterFile.getRegisterFile().getRegister(Integer.parseInt(r2Address)).getData();
        int r3 = RegisterFile.getRegisterFile().getRegister(Integer.parseInt(r3Address)).getData();
        DXEX.add(opcode);
        DXEX.add(r1Address);
        DXEX.add(r2Address);
        DXEX.add(r3Address);
        DXEX.add(shamt);
        DXEX.add(imm);
        DXEX.add(jumpAddress);
        DXEX.add(r1+"");
        DXEX.add(r2+"");
        DXEX.add(r3+"");
        
    }

    public static void execute() throws NumberFormatException, AddressOutOfBounds, IncorrectRegisterValue 
    {
        String opcode = DXEX.remove();
        String r1Address = DXEX.remove();
        String r2Address = DXEX.remove();
        String r3Address = DXEX.remove();
        String shamt = DXEX.remove();
        String imm = DXEX.remove();
        String jumpAddress = DXEX.remove();
        int r1 = Integer.parseInt(DXEX.remove());
        int r2 = Integer.parseInt(DXEX.remove());
        int r3 = Integer.parseInt(DXEX.remove());
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
                break;

            case 1: //Sub: R1 = R2-R3
                resultant = r2 - r3;
                writeBackFlag = true;
                break;

            case 2: //Mul: R1 = R2*R3
                resultant = r2 * r3;
                writeBackFlag = true;
                break;

            case 3: //Movi: R1 = IMM (R2 will be 0)
                resultant = Integer.parseInt(imm,2);
                writeBackFlag = true;
                break;

            case 4: //Jump if equal: IF(R1 == R2) {PC = PC+1+IMM }
                if(r1 == r2)
                    RegisterFile.getRegisterFile().getPCRegister().setData(RegisterFile.getRegisterFile().getPCRegister().getData() + 1 + Integer.parseInt(imm,2));
                writeBackFlag = false; 
                break;

            case 5: //AND: R1 = R2 & R3
                resultant = r2 & r3;
                writeBackFlag = true;
                break;

            case 6: //XORi: R1 = R2 XOR IMM
                resultant = r2 ^ Integer.parseInt(imm,2);
                writeBackFlag = true;
                break;

            case 7: //Jump: PC = PC[31:28] || ADDRESS
                //int t = RegisterFile.getRegisterFile().getPCRegister().getData() & 0b11110000000000000000000000000000;
                //RegisterFile.getRegisterFile().getPCRegister().setData(t | Integer.parseInt(jumpAddress,2));
                int t = RegisterFile.getRegisterFile().getPCRegister().getData() & 0b11110000000000000000000000000000;
                t = t >>> 28;
                String ye = Integer.toBinaryString(t);
                RegisterFile.getRegisterFile().getPCRegister().setData(Integer.parseInt(ye + jumpAddress));
                writeBackFlag = false;
                break;

            case 8: //Shift left: R1 = R2 << SHAMT
                resultant = r2 << Integer.parseInt(shamt,2);
                writeBackFlag = true;
                break;

            case 9: //Shift right: R1 = R2 >>> SHAMT
                resultant = r2 >> Integer.parseInt(shamt,2);
                writeBackFlag = true;
                break;

            case 10: //Move to register: R1 = MEM[R2 + IMM]
                memoryAddress = r2+Integer.parseInt(imm,2);
                if(memoryAddress < 1024 || memoryAddress > 2047)
                    throw new AddressOutOfBounds("Memory Address invalid.");
                toBeStored = -1;
                loadStoreFlag = -1;
                writeBackFlag = true;
                break;

            case 11: //Move to memory: MEM[R2 + IMM] = R1
                memoryAddress = r2 + Integer.parseInt(imm,2);
                if(memoryAddress < 1024 || memoryAddress > 2047)
                     throw new AddressOutOfBounds("Memory Address invalid.");
                toBeStored = r1;
                loadStoreFlag = 1;
                writeBackFlag = false;
                break;
        }
        EXMEM.add(r1Address);
        EXMEM.add(resultant+"");
        EXMEM.add(writeBackFlag+"");
        EXMEM.add(toBeStored+"");
        EXMEM.add(memoryAddress+"");
        EXMEM.add(loadStoreFlag+"");
     

        
    }

    public static void memory()
    {
        int r1Address = Integer.parseInt(EXMEM.remove());
        int resultant = Integer.parseInt(EXMEM.remove());
        boolean writeBackFlag = Boolean.parseBoolean(EXMEM.remove());
         int toBeStored = Integer.parseInt(EXMEM.remove());
        int memoryAddress = Integer.parseInt(EXMEM.remove());
        int loadStoreFlag = Integer.parseInt(EXMEM.remove());
        if(loadStoreFlag == -1)
        {
            //flag is true, this memory operation is a load
            resultant =  Integer.parseInt(CPU.m.memory[memoryAddress]);
        }
        else if(loadStoreFlag == 1)
        {
            String  oldValue = CPU.m.memory[memoryAddress];
            CPU.m.memory[memoryAddress] = toBeStored+"";
            System.out.println("The value of data memory location " + memoryAddress + " has been changed from " + oldValue + " to " + toBeStored);
        }
        MEMWB.add(r1Address+"");
        MEMWB.add(resultant+"");
        MEMWB.add(writeBackFlag+"");


    }

    public static void writeBack() throws AddressOutOfBounds, IncorrectRegisterValue, NumberFormatException, NonExistingRegister
    {
        int r1Address = Integer.parseInt(MEMWB.remove());
        int resultant = Integer.parseInt(MEMWB.remove());
        boolean flag = Boolean.parseBoolean(MEMWB.remove());

        if(flag)
        {
         int oldValue = RegisterFile.getRegisterFile().getRegister(r1Address).getData();
           RegisterFile.getRegisterFile().getRegister(r1Address).setData(resultant);
           System.out.println("The value of register " + "R"+r1Address + " has been changed from " + oldValue + " to " + resultant);
        }
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
