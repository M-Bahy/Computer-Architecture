package src.RegisterFile;

import src.Exceptions.NonExistingRegister;

public class RegisterFile {
    Register[] registers;

    PCRegister pc;


    private static RegisterFile registerFile = new RegisterFile();

    private RegisterFile() {
        
        registers = new Register[32];
        registers[0] = new ZeroRegister();
        for (int i = 1 ;i < registers.length; i++) {
            registers[i] = new Register("R" + i, 0);
        }
        pc = new PCRegister();
    
    }
/// Singelton Design Pattern
    public static RegisterFile getRegisterFile() {
        if(registerFile == null){
            registerFile = new RegisterFile();
        }
        return registerFile;
    }


    public  Register getRegister(int index) throws NonExistingRegister {

        if(index > 31 || index < 0){
             throw new NonExistingRegister();
        }
      
        return registers[index];
     
       
    }


    public  PCRegister getPCRegister() {
        return pc;
    }

    public Register[] getResgiters(){
        return this.registers;
    }


    public static void printRegisters() {
        System.out.println("Registers:");
        for (int i = 0; i < registerFile.registers.length; i++) {
            System.out.println(registerFile.registers[i].getName() + " = " + registerFile.registers[i].getData());
        }
        System.out.println("PC = " + registerFile.pc.getData() + "\n");
    }


    



}
