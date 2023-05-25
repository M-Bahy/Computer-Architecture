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


/// Ezyk ya omar ya adel
public void setData(int data) throws AddressOutOfBounds, IncorrectRegisterValue {
    if(this instanceof PCRegister){
        if(data < 0 || data > 1024){
            throw new AddressOutOfBounds();
        }   
        else
        {
            this.data = data;
        }
    }
    else{
       
        this.data = data;
    }
    

}



public Register( String name, int data){
this.size = 32;  
this.name = name;
this.data = data;

};











}