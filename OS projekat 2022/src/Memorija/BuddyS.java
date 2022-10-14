package Memorija;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import FajlSistem.FajlMemorija;


public class BuddyS {
	
	private static int ukupnaMemorija=1024;
	private static ArrayList<Integer> djelioci=new ArrayList<Integer>();
	private static ArrayList<Cvor> drvo=new ArrayList<Cvor>();
	private static ArrayList<Cvor> slobodniBlokovi=new ArrayList<Cvor>();
	private static ArrayList<Cvor> zauzetiBlokovi=new ArrayList<Cvor>();
	private static int najveciSlobodanBlok;
	private static Cvor korijen=new Cvor(new Blok(1024,1001));
	

	public BuddyS(){
		drvo.add(korijen);
	}
	
public static void popunjavanje(FajlMemorija fajl,int vr) {
	if(drvo.size()==0)
		drvo.add(korijen);
	
	
	djelioci();
	for(int i=0;i<drvo.size();i++) {
		if(!drvo.get(i).blok.zauzet) {
			najveciSlobodanBlok=drvo.get(i).vrijednost;
			break;
		}
	}
		
	if(najveciSlobodanBlok<vr) {
			System.out.println("---"+najveciSlobodanBlok+"----");
			System.err.println("Nedovoljno memorije za Vas");
	}
	else {
		
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
			Cvor cvorKojiPunim=null;
			boolean popunjeno=false;
			for(int i=0;i<drvo.size();i++) {
				if(drvo.get(i).vrijednost==trazeni && !drvo.get(i).blok.zauzet) {
					//System.out.println("Ovdje ne bi trebalo uci");
					drvo.get(i).blok.setZauzet();
					popunjeno=true;
					cvorKojiPunim=drvo.get(i);
				}
			}
			//ako nema trazicemo duplo veci od njega slobodan, koji cemo podijeliti na 
			//nas zeljeni blok
		
		
		
			if(!popunjeno) {
				//System.out.println("sssss");
				int trenutniNajmanji=0;
				while(!popunjeno) {
					trazeni*=2;
					//System.out.println("trazeni u while petlji je sad "+trazeni);
					for(int i=0;i<drvo.size();i++) {
						if(drvo.get(i).vrijednost==trazeni && !drvo.get(i).blok.zauzet) {
							//System.out.println("ima li istog ko trazenog ikad");
							while(trenutniNajmanji!=konstTrazeni) {
						
								drvo.get(i).lijevo=new Cvor(new Blok(trazeni/2,drvo.get(i).blok.adresa+1));
								drvo.get(i).desno=new Cvor(new Blok(trazeni/2,drvo.get(i).blok.adresa+2));
								
								
								
								drvo.get(i).blok.setZauzet();
								drvo.get(i).lijevo.blok.setZauzet();
								
								drvo.get(i).lijevo.roditelj=drvo.get(i);
								drvo.get(i).desno.roditelj=drvo.get(i);
								
								drvo.get(i).lijevo.brat=drvo.get(i).desno;
								drvo.get(i).desno.brat=drvo.get(i).lijevo;
								
								cvorKojiPunim=drvo.get(i).lijevo;
								
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
			
		
		//stavimo sadrzaj u nas cvor
		cvorKojiPunim.blok.dodajSadrzaj(fajl.getBajtSadrzaj());
		cvorKojiPunim.blok.dodajSadrzajString( fajl.getSadrzaj());
		cvorKojiPunim.blok.setImeFajlaUBloku(fajl.getIme());
		}
		
		
	}

	
	public static void oslobadjanje(FajlMemorija fajl) {
		
		for(int i=0;i<drvo.size();i++) {
			
			if(drvo.get(i).blok.sadrzajString!=null && drvo.get(i).blok.sadrzajString.equals(fajl.getSadrzaj()) ) {
				
				drvo.get(i).blok.obrisiSadrzaj();
				
				//hocemo li slijepiti ili ne
				if(!drvo.get(i).brat.blok.zauzet) {
					System.out.println("---obrisan sadrzaj "+fajl.getSadrzaj()+" iz bloka sa pozicije "+i+ "obrisan je i njegov parnjak na poziciji "+drvo.indexOf(drvo.get(i).brat));
					Cvor lijevo=drvo.get(i).roditelj.lijevo;
					Cvor desno=drvo.get(i).roditelj.desno;
					drvo.get(i).roditelj.blok.setSlobodan();
					drvo.remove(lijevo);
					drvo.remove(desno);
					break;

				}
				//ako necemo, oslabadjam samo trazeni blok
				else {
					System.out.println("---obrisan sadrzaj "+fajl.getSadrzaj()+" iz bloka sa pozicije "+i+ ", njegov parnjak je zauzet na poziciji "+drvo.indexOf(drvo.get(i).brat));
					drvo.get(i).blok.setSlobodan();
				}
				
			}
			else {
				continue;
			}
		}
		
	

		int i=drvo.size()-1;
		System.out.println("indeks "+i);
	
		
		if(!drvo.get(i).brat.blok.zauzet && !drvo.get(i).blok.zauzet) {
			while(!drvo.get(i).brat.blok.zauzet && !drvo.get(i).blok.zauzet) {
				
				Cvor lijevo=drvo.get(i).roditelj.lijevo;
				Cvor desno=drvo.get(i).roditelj.desno;
				drvo.get(i).roditelj.blok.setSlobodan();
				drvo.remove(lijevo);
				drvo.remove(desno);
				i=drvo.size()-1;

				if(i==0)
					break;
				System.err.println("--"+i);
				for(int j=0;j<drvo.size();j++) {
					System.out.println("--"+drvo.get(j).vrijednost+"--");
				}
			
			}
		}
	
		
	}
	
	public static void djelioci() {
		djelioci.add(ukupnaMemorija);
		int n=ukupnaMemorija;
		while(n>0) {
			n/=2;
			djelioci.add(n);
		}
	}
	
	
	public static ArrayList<Cvor> getDrvo(){
		return drvo;
	}
	
	public static void setSlobodniBlokovi(){
		
		ArrayList<Cvor> s=new ArrayList<Cvor>();
		for(int i=0;i<drvo.size();i++) {
			if(!drvo.get(i).blok.zauzet)
				s.add(drvo.get(i));
		}
		slobodniBlokovi=s;
	}
	
	public static ArrayList<Cvor> getSlobodniBlokovi() {
		
		setSlobodniBlokovi();
		System.out.println("--------");
		for(int i=0;i<slobodniBlokovi.size();i++)
			System.out.println("slobodan blok RAM-a : "+slobodniBlokovi.get(i).vrijednost);
		return slobodniBlokovi;
	}
	
	public static void setZauzetiBlokovi() {
		
		ArrayList<Cvor> s=new ArrayList<Cvor>();
		for(int i=0;i<drvo.size();i++) {
			if(drvo.get(i).blok.zauzet)
				s.add(drvo.get(i));
		}
		zauzetiBlokovi=s;
	}
	
	public static ArrayList<Cvor> getZauzetiBlokovi(){
		setZauzetiBlokovi();
		for(int i=0;i<zauzetiBlokovi.size();i++)
			System.out.println("zauzet blok RAM-a "+zauzetiBlokovi.get(i).vrijednost);
		return zauzetiBlokovi;
	}
	
	public static boolean imaLiMjesta(FajlMemorija file) {
		boolean f=false;
		setSlobodniBlokovi();
		for(int i=0;i<slobodniBlokovi.size();i++) {
			if(file.getVelicina()<slobodniBlokovi.get(i).vrijednost) {
				f=true;
				break;
			}
		}
		return f;
	}
}

class Cvor{
	
	Blok blok;
	Cvor lijevo;
	Cvor desno;
	int vrijednost;
	Cvor roditelj;
	Cvor brat;
	
	Cvor(Blok b){
		this.vrijednost=b.velicina;
		this.blok=b;
		lijevo=null;
		desno=null;
		roditelj=null;
		brat=null;
	}
	
}

