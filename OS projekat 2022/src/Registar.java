

public class Registar {
	String ime,adresa;
	int vrijednost;
	
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
		return ime;
	}

	public int getVrijednost() {
		return vrijednost;
	}
	
}
