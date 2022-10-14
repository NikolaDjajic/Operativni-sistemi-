package Asembler;

public class Operacija {
	
	 private String naziv;
	 private String kod;
	
	Operacija(String naziv,String kod){
		this.naziv=naziv;
		this.kod=kod;
	}
	
	public  String getNaziv() {
		return naziv;
	}
	 public String getKod() {
		return kod;
	}
	
	

}
