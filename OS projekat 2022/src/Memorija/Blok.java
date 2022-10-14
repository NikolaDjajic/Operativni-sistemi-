package Memorija;

public class Blok {
	int velicina;
	String imeFajlaUBloku;
	private final static int velicina_fajl=4;
	boolean zauzet=false;
	byte[] sadrzaj;
	String sadrzajString;
	int adresa;
	int[] lista;
	
	
	public Blok(int velicina,int adresa) {
		this.velicina=velicina;
		this.adresa=adresa;

	}
	
	public Blok(int velicina) {
		this.velicina=velicina;
	}
	
	public void setImeFajlaUBloku(String im) {
		imeFajlaUBloku=im;
	}
	
	public String getImeFajlaUBloku() {
		return imeFajlaUBloku;
	}
	
	public void setLista(int[] lista) {
		this.lista=lista;
	}
	
	public int[] getLista() {
		return lista;
	}
	
	public byte[] getSadrzaj() {
		return sadrzaj;
	}
	
	public String getSadrzajString() {
		return sadrzajString;
	}
	
	public void dodajSadrzajString(String s) {
		this.sadrzajString=s;
	}
	
	
	public static int getVelicinaFajl() {
		return velicina_fajl;
	}
	
	public void dodajSadrzaj(byte[] sadrzaj) {
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
