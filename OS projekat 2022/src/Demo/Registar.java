package Demo;

public class Registar {
	private String ime;
	private int adresa,vrijednost;
	
	public Registar(String ime,int adresa,int vrijednost) {
		this.ime=ime;
		this.adresa=adresa;
		this.vrijednost=vrijednost;
	}
	
	public Registar(String ime,String adresa,int vrijednost) {
		this.ime=ime;
		this.adresa=Integer.parseInt(adresa);
		this.vrijednost=vrijednost;
	}

	public String getIme() {
		return ime;
	}

	public int getAdresa() {
		return adresa;
	}

	public int getVrijednost() {
		return vrijednost;
	}
	
}
