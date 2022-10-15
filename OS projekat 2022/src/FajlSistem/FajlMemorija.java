package FajlSistem;

import java.nio.charset.StandardCharsets;

import Memorija.Blok;

public class FajlMemorija {

	private String ime;
	private int velicina;
	private int indexBlok;
	private int duzina;
	private static byte[] sadrzaj=new byte[0];
	private String sadrzajString;
	
	public void setIndexBlok(int i) {
		this.indexBlok=i;
	}
	
	public void setDuzina(int l) {
		this.duzina=l;
	}
	
	public FajlMemorija(String ime, byte[] sadrzaj) {
		this.ime=ime;
		sadrzaj=sadrzaj;
		sadrzajString = new String(sadrzaj, StandardCharsets.UTF_8);
		velicina=sadrzaj.length;
	}
	
	public void setSadrzajString() {
		this.sadrzajString = new String(sadrzaj, StandardCharsets.UTF_8);
	}
	
	
	public static byte[] dio(int index) {
		byte[] dio=new byte[Blok.getVelicinaFajl()];
		int brojac=0;
		for(int i=index*Blok.getVelicinaFajl();i<sadrzaj.length;i++) {
			dio[brojac]=sadrzaj[i];
			if(brojac==Blok.getVelicinaFajl()-1)
				break;
			brojac++;
		}
		while(brojac<Blok.getVelicinaFajl()-1) {
			byte[] b=" ".getBytes();
			dio[brojac]=b[0];
			brojac++;
		}
		return dio;
	}
	
	public String getSadrzaj() {
		return this.sadrzajString;
	}
	
	public byte[] getBajtSadrzaj() {
		return sadrzaj;
	}
	
	
	public int getDuzina() {
		return duzina;
	}
	
	public String getIme() {
		return ime;
	}
	
	public int getIndexBlok() {
		return indexBlok;
	}
	
	public int getVelicina() {
		return velicina;
	}
}
