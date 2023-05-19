package src.RegisterFile;

import src.Exceptions.AddressOutOfBounds;
import src.Exceptions.IncorrectRegisterValue;

public class Register{
private int size;
private String name;
private int data;



public int getSize() {
    return size;
}







public String getName() {
    return name;
}






public int getData() {
    return data;
}



public void setData(int data) throws AddressOutOfBounds, IncorrectRegisterValue {
    if(this instanceof PCRegister){
        if(data < 0 || data > 255){
            throw new AddressOutOfBounds();
        }   
        else
        {
            this.data = data;
        }
    }
    else{
        if(data < -2147483648 || data > 2147483647){
            throw new IncorrectRegisterValue();
        }
        else{
            this.data = data;
        }
    }
    

}



public Register( String name, int data){
this.size = 32;  
this.name = name;
this.data = data;

};











}