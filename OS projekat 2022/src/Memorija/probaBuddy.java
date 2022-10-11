package Memorija;

import java.util.ArrayList;

public class probaBuddy {

	int size;
	static ArrayList<Blok> blokovi= new ArrayList<Blok>();
	
	public probaBuddy( ) {
	
		Blok bl=new Blok(1024,0001);
		blokovi.add(bl);
	}
	
	public void dodaj(String sadrzaj,int velicina) {
		int br=1;
		
		while(br!=10) {
		
		System.out.println("uso");
		
		int i=blokovi.size()-1;
			
		
			if(blokovi.get(i).velicina/2>=velicina && blokovi.get(i).zauzet==false) {
				Blok prosli=blokovi.remove(i);
				blokovi.add(new Blok(prosli.velicina/2));
				blokovi.add(new Blok(prosli.velicina/2));
			//	break;
			}
			else if(blokovi.get(i).velicina/2<velicina && blokovi.get(i).velicina>=velicina && blokovi.get(i).zauzet==false) {
				blokovi.get(i).sadrzaj=sadrzaj;
				blokovi.get(i).zauzet=true;
				//break;
			}
			else
				i--;
			br++;
		}
	}
	
	public static void ispisiTabelu() {
		for (Blok b:blokovi) 
			System.out.println(b.velicina+" | "+b.sadrzaj);
	}
	
	public static void main(String [] args) {
		
		probaBuddy pb = new probaBuddy();
		pb.dodaj("Cao",51);
		pb.dodaj("Opa",26);
		ispisiTabelu();
	}
	
}
