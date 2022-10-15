package Memorija;

import FajlSistem.FajlMemorija;

public class Blok {
	
	int velicina;
	private String imeFajlaUBloku;
	private final static int velicina_fajl=4;
	boolean zauzet=false;
	private byte[] sadrzaj;
	private String sadrzajString;
	int adresa;
	private int[] lista;
	private FajlMemorija fm;
	
	public Blok(int velicina,int adresa) {
		this.velicina=velicina;
		this.adresa=adresa;
		this.sadrzaj=null;
		this.sadrzajString=null;
		this.fm=null;
	}
	
	public void setFajl(FajlMemorija f) {
		this.fm=f;
	}
	
	public FajlMemorija getFajl() {
		return this.fm;
	}
	
	public Blok(int velicina) {
		this.velicina=velicina;
	}
	
	public void setImeFajlaUBloku(String im) {
		this.imeFajlaUBloku=im;
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
		return this.sadrzajString;
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
		this.sadrzajString=null;
	}
	
	public void setZauzet() {
		zauzet=true;
	}
	
	public void setSlobodan() {
		zauzet=false;
	}
	
	
	

	
	

}
