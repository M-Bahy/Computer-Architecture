package src.Instructions;
public abstract class Instruction {
   private int opcode;
    private InstructionType type;
    private Mnemonic mnemonic;
    private int shamt;
    private int r1;
    private int r2;
    private int r3;
    private int imm;
    private int address;
    // R type instructions : ADD,SUB,MUL,AND,LSL,LSR
    //                        0   1   2   3   4   5
    public Instruction(Mnemonic mnemonic ,  int r1, int r2, int r3, int shamt) { // R type constructor
        // 0 , 1 , 2 , 3 ,    4 , 5 , 6 , 7 , 8 , 9 , 10 , 11
       //ADD,SUB,MUL, MOVI ,JEQ,AND,XORI,JMP,LSL,LSR,MOVR,MOVM
            this.type = InstructionType.R;
            this.shamt = shamt;
            this.r1 = r1;
            this.r2 = r2;
            this.r3 = r3;
            this.mnemonic = mnemonic;
       switch(mnemonic){
        case ADD:// R type
            this.opcode = 0;
            
            break;
        case SUB: // R type 
            this.opcode = 1;
           
            break;
        case MUL:// R type
            this.opcode = 2;
            
            break; 
        
        case AND:// R type
            this.opcode = 5;
           
            break;
        
       
        case LSL:// R type
            this.opcode = 8;
            
            break;
        case LSR:// R type
            this.opcode = 9;
           
            break;
        
    }
    
}
        public Instruction(Mnemonic mnemonic , int r1, int r2, int imm) { // I type constructor
            this.type = InstructionType.I;
            this.r1 = r1;
            this.r2 = r2;
            this.imm = imm;
            this.mnemonic = mnemonic;
            switch(mnemonic){
                case MOVI:// I type
                    this.opcode = 3;
                    
                    break;
                case JEQ: // I type
                    this.opcode = 4;
                    
                    break;
                case XORI:// I type
                    this.opcode = 6;
                    
                    break; 
                case MOVR:// I type
                    this.opcode = 10;
                    
                    break;
                case MOVM:// I type
                    this.opcode = 11;
                    
                    break;
                
            }
    
}

        public Instruction( int address) { // J type constructor
            this.type = InstructionType.J;
            this.address = address;
            this.mnemonic = Mnemonic.JMP;
            this.opcode = 7;
                    
                   
                
            
        }
    public String getOpcode() {
        
        return convertToBinary(opcode, 4);
    }
    public String decode() {
        String decoded = "";
        decoded += getOpcode();
        switch(type){
            case R:
                decoded += convertToBinary(r1, 5);
                decoded += convertToBinary(r2, 5);
                decoded += convertToBinary(r3, 5);
                decoded += convertToBinary(shamt, 13);
                break;
            case I:
                decoded += convertToBinary(r1, 5);
                decoded += convertToBinary(r2, 5);
                decoded += convertToBinary(imm, 18);
                break;
            case J:
                decoded += convertToBinary(address, 28);
                break;
        }
        return decoded;
    }
    public Mnemonic getOperation() {
        return mnemonic;
    }
    private static String convertToBinary(int number, int numberOfBits) {
    String binary = Integer.toBinaryString(number);
    int binaryLength = binary.length();
    if (binaryLength < numberOfBits) {
        binary = "0".repeat(numberOfBits - binaryLength) + binary;
    } else if (binaryLength > numberOfBits) {
        binary = binary.substring(binaryLength - numberOfBits);
    }
    return binary;
}

}

