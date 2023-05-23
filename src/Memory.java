package src;

public class Memory {
    String[]memory;
    public Memory(){
        memory = new String[2048];
    }


    //printing the memory
    public void printMemory(){
        for (int i = 0; i < memory.length; i++) {
            System.out.println("Memory Location "+ i + " has the value: " + memory[i]);
        }
    }
}
