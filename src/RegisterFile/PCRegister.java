package src.RegisterFile;

import src.Exceptions.AddressOutOfBounds;
import src.Exceptions.IncorrectRegisterValue;

public class PCRegister extends Register{

    public PCRegister() {
        super("PC", 0);
        //TODO Auto-generated constructor stub
    }


  

    public void incrementPC() throws AddressOutOfBounds, IncorrectRegisterValue {
     
            this.setData(this.getData()+1);
        
       
    }


  
 
    


}
