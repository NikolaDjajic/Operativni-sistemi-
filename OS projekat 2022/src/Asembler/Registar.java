package Asembler;


public class Registar {
	private String ime,adresa;
	private int vrijednost;
	
	public Registar(String ime,int adresa,int vrijednost) {
		this.ime=ime;
		this.adresa=adresa+"";
		this.vrijednost=vrijednost;
	}
	
	public Registar(String ime,String adresa,int vrijednost) {
		this.ime=ime;
		this.adresa=adresa;
		this.vrijednost=vrijednost;
	}

	public String getIme() {
		return this.ime;
	}
	
	public String getAdresa() {
		return this.adresa;
	}

	public int getVrijednost() {
		return this.vrijednost;
	}
	
	public void setVrijednost(int vr) {
		this.vrijednost=vr;
	}
}
