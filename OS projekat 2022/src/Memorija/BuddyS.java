package Memorija;

import java.util.ArrayList;

//fali slobodna memorija

public class BuddyS {
	
	static int ukupnaMemorija;
	static ArrayList<Integer> djelioci=new ArrayList<Integer>();
	static ArrayList<Cvor> drvo=new ArrayList<Cvor>();
	static int najveciSlobodanBlok;
	
public static void popunjavanje(int vr) {
	
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
		//cvorKojiPunim.blok.dodajSadrzaj();
		}
		
		
	}

	
	public static void oslobadjanje(byte[] sadrzaj) {
		
		for(int i=0;i<drvo.size();i++) {
			
			if(drvo.get(i).blok.sadrzaj!=null && drvo.get(i).blok.sadrzaj.equals(sadrzaj) ) {
				
				drvo.get(i).blok.obrisiSadrzaj();
				
				//hocemo li slijepiti ili ne
				if(!drvo.get(i).brat.blok.zauzet) {
					System.out.println("---obrisan sadrzaj "+sadrzaj+" iz bloka sa pozicije "+i+ "obrisan je i njegov parnjak na poziciji "+drvo.indexOf(drvo.get(i).brat));
					Cvor lijevo=drvo.get(i).roditelj.lijevo;
					Cvor desno=drvo.get(i).roditelj.desno;
					drvo.get(i).roditelj.blok.setSlobodan();
					drvo.remove(lijevo);
					drvo.remove(desno);
					break;
				}
				//ako necemo, oslabadjam samo trazeni blok
				else {
					System.out.println("---obrisan sadrzaj "+sadrzaj+" iz bloka sa pozicije "+i+ ", njegov parnjak je zauzet na poziciji "+drvo.indexOf(drvo.get(i).brat));
					drvo.get(i).blok.setSlobodan();
				}
				
			}
			else {
				continue;
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
	
	public static void main(String[]args) {
		
		Cvor korijen=new Cvor(new Blok(1024,1001));
		drvo.add(korijen);
		ukupnaMemorija=1024;
		djelioci();
		//for(int i=0;i<djelioci.size();i++)
			//System.out.println(djelioci.get(i));
		
		popunjavanje(80);
		for(int i=0;i<drvo.size();i++) {
			System.out.println(i+" : "+drvo.get(i).blok.velicina+"|"+drvo.get(i).blok.zauzet);
			if(drvo.get(i).blok.sadrzaj!=null)
				System.err.println("sadrzaj "+drvo.get(i).blok.sadrzaj+" je u "+i);
			
		}
		System.out.println("----");
		
		byte[] s=(80+"").getBytes();
		oslobadjanje(s);
		for(int i=0;i<drvo.size();i++) {
			System.out.println(i+" : "+drvo.get(i).blok.velicina+"|"+drvo.get(i).blok.zauzet);
			if(drvo.get(i).blok.sadrzaj!=null)
				System.err.println("sadrzaj "+drvo.get(i).blok.sadrzaj+" je u "+i);
		}
		System.out.println("----");
		popunjavanje(80);
		for(int i=0;i<drvo.size();i++) {
			System.out.println(i+" : "+drvo.get(i).blok.velicina+"|"+drvo.get(i).blok.zauzet);
			if(drvo.get(i).blok.sadrzaj!=null)
				System.err.println("sadrzaj "+drvo.get(i).blok.sadrzaj+" je u "+i);
		}
		System.out.println("----");
		popunjavanje(10);
		for(int i=0;i<drvo.size();i++) {
			System.out.println(i+" : "+drvo.get(i).blok.velicina+"|"+drvo.get(i).blok.zauzet);
			if(drvo.get(i).blok.sadrzaj!=null)
				System.err.println("sadrzaj "+drvo.get(i).blok.sadrzaj+" je u "+i);
			
		}
		byte[] s1=(80+" ").getBytes();
		oslobadjanje(s1);
		for(int i=0;i<drvo.size();i++) {
			System.out.println(i+" : "+drvo.get(i).blok.velicina+"|"+drvo.get(i).blok.zauzet);
			if(drvo.get(i).blok.sadrzaj!=null)
				System.err.println("sadrzaj "+drvo.get(i).blok.sadrzaj+" je u "+i);
		}
		System.out.println("----");
		popunjavanje(530);
		for(int i=0;i<drvo.size();i++) {
			System.out.println(i+" : "+drvo.get(i).blok.velicina+"|"+drvo.get(i).blok.zauzet);
			if(drvo.get(i).blok.sadrzaj!=null)
				System.err.println("sadrzaj "+drvo.get(i).blok.sadrzaj+" je u "+i);
			
		}
		System.out.println("----");
		popunjavanje(90);
		for(int i=0;i<drvo.size();i++) {
			System.out.println(i+" : "+drvo.get(i).blok.velicina+"|"+drvo.get(i).blok.zauzet);
			if(drvo.get(i).blok.sadrzaj!=null)
				System.err.println("sadrzaj "+drvo.get(i).blok.sadrzaj+" je u "+i);
			
		}
		
		
		
		
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

