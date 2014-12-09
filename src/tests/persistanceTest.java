package tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class persistanceTest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2035472560509783641L;

	public static void main(String[] args) {
		new persistanceTest();
	}
	
	public persistanceTest(){
		String s = "Hello World";
		String s2 = "";
		try{
			FileOutputStream f_out = new FileOutputStream("testObjectData.data");
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
			obj_out.writeObject(s);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			FileInputStream f_in = new FileInputStream("testObjectData.data");
			ObjectInputStream obj_in = new ObjectInputStream(f_in);
			s2 = (String) obj_in.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(s.equals(s2)){
			System.out.println("LOOK AT THAT! THEY ARE THE SAME!");
		}
		
	}

}
