package Asembler;

public class Operacija {
	
	static String naziv;
	static String kod;
	
	Operacija(String naziv,String kod){
		this.naziv=naziv;
		this.kod=kod;
	}
	
	public static String getName() {
		return naziv;
	}
	
	public static String getKod() {
		return kod;
	}
	
	

}
