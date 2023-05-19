package src.RegisterFile;

import src.Exceptions.AddressOutOfBounds;
import src.Exceptions.IncorrectRegisterValue;

public class PCRegister extends Register{

    public PCRegister(String name, int data) {
        super("PC", data);
        //TODO Auto-generated constructor stub
    }


  

    public void incrementPC() throws AddressOutOfBounds, IncorrectRegisterValue {
     
            this.setData(this.getData()+1);
        
       
    }


  
 
    


}
