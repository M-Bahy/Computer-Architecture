import java.util.ArrayList;
import java.util.Vector;

public class Stages 
{
    //Execute case 4 and 7 need to be reviewed
    //Memroy method needs to be changed according to 3mora code

    //Dummy registerFile, Memory, and PC register variables
    static int registerFile [] = {22,16,87,54,102,434,33,67,98,49,55,9,18,1,32,45};
    static int memory[] = {-1321039047,992777138};
    static int pc = 0; //case 4 in execute needs to be reviewed with change of PC

    static int instruction;

    //Decode variables
    static int opcode = 0;      // bits31:28
    static int r1Address = 0;   // bits27:23
    static int r2Address = 0;   // bit22:18
    static int r3Address = 0;   // bits17:13 incase of R-format
    static int shamt = 0;       // bits12:0 incase of R-format
    static int imm = 0;         // bits17:0 incase of I-format
    static int jumpAddress = 0; // bits27:0 incase of J-format
    static int r1 = 0;
    static int r2 = 0;
    static int r3 = 0;

    //Execute variables
    static int resultant = 0;
    static boolean loadStoreFlag; //true = load op, false = store op
    static int memoryAddress;
    static int toBeStored = -1;
    static boolean writeBackFlag = false; //false = no writeBack required
    
    public static void fetch() 
    {
        instruction = memory[pc];
        decode(instruction);
        pc++;
    }
    
    public static void decode(int instruction) 
    {
        opcode = instruction & 0b11110000000000000000000000000000;
        opcode = opcode >>> 28;
        r1Address = instruction & 0b00001111100000000000000000000000;
        r1Address >>>= 24;
        r2Address = instruction & 0b00000000011111000000000000000000;
        r2Address >>>= 20;
        r3Address = instruction & 0b00000000000000111110000000000000;
        r3Address >>>= 16;
        shamt = instruction & 0b00000000000000000001111111111111;
        imm = instruction & 0b00000000000011111111111111111111;
        jumpAddress = instruction & 0b00001111111111111111111111111111;
        r1 = registerFile[r1Address];
        r2 = registerFile[r2Address];
        r3 = registerFile[r3Address];
    }

    public static void execute(int operandA, int operandB, int operation) 
    {
        operation = convertBinaryToDecimal(operation);
        int intopA = convertBinaryToDecimal(operandA);
        int intopB = convertBinaryToDecimal(operandB);
        int temp = 0;

        switch (operation) 
        {
            case 0: //Add: R1 = R2+R3
                temp = intopA + intopB;
                resultant = convertDecimalToBinary(temp);
                writeBackFlag = true;
                break;

            case 1: //Sub: R1 = R2-R3
                temp = intopA - intopB;
                resultant = convertDecimalToBinary(temp);
                writeBackFlag = true;
                break;

            case 2: //Mul: R1 = R2*R3
                temp = intopA * intopB;
                resultant = convertDecimalToBinary(temp);
                writeBackFlag = true;
                break;

            case 3: //Movi: R1 = IMM (R2 will be 0)
                resultant = imm;
                writeBackFlag = true;
                break;

            case 4: //Jump if equal: IF(R1 == R2) {PC = PC+1+IMM }
                if(operandA == operandB)
                    pc = pc + 1 + imm;  
                break;

            case 5: //AND: R1 = R2 & R3
                resultant = operandA & operandB;
                writeBackFlag = true;
                break;

            case 6: //XORi: R1 = R2 XOR IMM
                resultant = operandA ^ imm;
                writeBackFlag = true;
                break;

            case 7: //Jump: PC = PC[31:28] || ADDRESS
                //String result = ""+ (convertDecimalToBinary(convertBinaryToDecimal(pc & 0b11110000000000000000000000000000))) + jumpAddress;
                //pc = Integer.parseInt(result);
                pc = pc | jumpAddress;
                break;

            case 8: //Shift left: R1 = R2 << SHAMT
                resultant = operandA << shamt;
                writeBackFlag = true;
                break;

            case 9: //Shift right: R1 = R2 >>> SHAMT
                resultant = operandA >> shamt;
                writeBackFlag = true;
                break;

            case 10: //Move to register: R1 = MEM[R2 + IMM]
                memoryAddress= operandA+imm;
                loadStoreFlag = true;
                writeBackFlag = true;
                break;

            case 11: //Move to memory: MEM[R2 + IMM] = R1
                memoryAddress = operandB + imm;
                toBeStored = operandA;
                loadStoreFlag = false;
                break;
        }
    }

    public static int memory(int memAddress, int toBeStored, boolean flag)
    {
        if(flag)
        {
            //flag is true, this memory operation is a load
            return memory[memAddress];
        }
        else
        {
            memory[memAddress] = toBeStored;
            return -1;
        }
    }

    public static void writeBack(boolean flag)
    {
        if(flag)
        {
            registerFile[r1Address] = resultant;
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

    public static void main(String[]args)
    {
        //Below is how we call each method
        fetch();
        decode(instruction);

        if(convertBinaryToDecimal(opcode)!=4)
            execute(r2, r3, opcode);
        else
            execute(r1, r2, opcode);

        memory(memoryAddress, toBeStored, loadStoreFlag);
        writeBack(writeBackFlag);
    }

}
