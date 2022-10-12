package Memorija;

public class Blok {
	int velicina;
	boolean zauzet=false;
	String sadrzaj;
	int adresa;
	
	
	public Blok(int velicina,int adresa) {
		this.velicina=velicina;
		this.adresa=adresa;
	}
	
	public Blok(int velicina) {
		this.velicina=velicina;
	}
	
	public void dodajSadrzaj(String sadrzaj) {
		this.sadrzaj=sadrzaj;
		
	}
	
	public void obrisiSadrzaj() {
		this.sadrzaj=null;
	}
	
	public void setZauzet() {
		zauzet=true;
	}
	
	public void setSlobodan() {
		zauzet=false;
	}
	
	
	

	
	

}
