package Memorija;

import java.util.ArrayList;

//RADI VALJDA 

public class BuddyS {
	
	static int ukupnaMemorija;
	static ArrayList<Integer> djelioci=new ArrayList<Integer>();
	static ArrayList<Cvor> drvo=new ArrayList<Cvor>();
	

	//dijelimo ukupnu memoriju na 2 do kraja
	public static void djelioci() {
		djelioci.add(ukupnaMemorija);
		int n=ukupnaMemorija;
		while(n>0) {
			n/=2;
			djelioci.add(n);
		}
	}
	
	public static void popunjavanje(int vr) {
		
		int trazeni=0;
		//pronadjimo koji to blok bi odgovarao nasem 
		for(int i=0;i<djelioci.size();i++) {
			if(vr>djelioci.get(i)) {
				trazeni=djelioci.get(i-1);
				break;
			}
		}
		System.out.println("trazeni "+trazeni);
		int konstTrazeni=trazeni;
		
		//sada pogledajmo da li u nasem drvetu ima slobodan blok te vrijednosti
		
		boolean popunjeno=false;
		for(int i=0;i<drvo.size();i++) {
			if(drvo.get(i).vrijednost==trazeni && !drvo.get(i).blok.zauzet) {
				System.out.println("Ovdje ne bi trebalo uci");
				drvo.get(i).blok.setZauzet();
				popunjeno=true;
			}
		}
		//ako nema trazicemo duplo veci od njega slobodan, koji cemo podijeliti na 
		//nas zeljeni blok
		if(!popunjeno) {
			System.out.println("sssss");
			int trenutniNajmanji=0;
			while(!popunjeno) {
				trazeni*=2;
				System.out.println("trazeni u while petlji je sad "+trazeni);
				for(int i=0;i<drvo.size();i++) {
					if(drvo.get(i).vrijednost==trazeni && !drvo.get(i).blok.zauzet) {
						System.out.println("ima li istog ko trazenog ikad");
						while(trenutniNajmanji!=konstTrazeni) {
							System.err.println("trenutni najmanji "+trenutniNajmanji);
							System.out.println("-----");
							System.err.println("prvi trazeniii "+konstTrazeni);
							drvo.get(i).lijevo=new Cvor(new Blok(trazeni/2,drvo.get(i).blok.adresa+1));
							drvo.get(i).desno=new Cvor(new Blok(trazeni/2,drvo.get(i).blok.adresa+2));
							drvo.get(i).blok.setZauzet();
							drvo.get(i).lijevo.blok.setZauzet();
							drvo.add(drvo.get(i).lijevo);
							drvo.add(drvo.get(i).desno);
							trenutniNajmanji=trazeni/2;
							trazeni/=2;
							i=drvo.indexOf(drvo.get(i).lijevo);
					
						}
						if(trenutniNajmanji==konstTrazeni)
							popunjeno=true;
						
					}
					else
						continue;
				}
			
		}}
		
		
	}
	
	public static void main(String[]args) {
		
		Cvor korijen=new Cvor(new Blok(1024,1001));
		drvo.add(korijen);
		ukupnaMemorija=1024;
		djelioci();
		//for(int i=0;i<djelioci.size();i++)
			//System.out.println(djelioci.get(i));
		
		popunjavanje(80);
		for(int i=0;i<drvo.size();i++)
			System.out.println(i+" : "+drvo.get(i).blok.velicina);
	}
	

}

class Cvor{
	
	Blok blok;
	Cvor lijevo;
	Cvor desno;
	int vrijednost;
	
	Cvor(Blok b){
		this.vrijednost=b.velicina;
		this.blok=b;
		lijevo=null;
		desno=null;
	}
	
}

