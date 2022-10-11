package Memorija;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class test_buddy {
	
	static Scanner scan=new Scanner(System.in);
	
	
	//lista u kojoj cuvam sve cvorove
	static ArrayList<Cvor> drvo1=new ArrayList<Cvor>();
	
	
	public static void postavljanje(int vr) {
		
		//da bismo uvijek imali poredane slobodne cvorove po njihovim velicinama od najmanjeg ka najvecem
		ArrayList<Integer> poVelicini=new ArrayList<Integer>();
		for(int i=0;i<drvo1.size();i++) {
			if(!drvo1.get(i).blok.zauzet)
				poVelicini.add(drvo1.get(i).blok.velicina);
		}
		Collections.sort(poVelicini);
		
		
		
		for(int i=0;i<drvo1.size();i++) {
			
			//koji to nasoj trazenoj vrijednosti cvor odgovara KOJI K OVO NE RADI
			int min=poVelicini.get(poVelicini.size()-1);
			for(int y=0;y<poVelicini.size();y++) {
				//onaj koji ce minimalno zauzeti 
				if(vr<=min/2)
					min=poVelicini.get(y);
			}
			
			
			
			if(!drvo1.get(i).blok.zauzet && drvo1.get(i).blok.velicina==min) {
			
				if(vr<=(drvo1.get(i).blok.velicina/2)) {
					
					//nove dvije grane
					drvo1.get(i).lijevo=new Cvor(new Blok(drvo1.get(i).blok.velicina/2,drvo1.get(i).blok.adresa+1));
					drvo1.get(i).desno=new Cvor(new Blok(drvo1.get(i).blok.velicina/2,drvo1.get(i).blok.adresa+2));
					drvo1.get(i).lijevo.blok.setZauzet();
					drvo1.add(drvo1.get(i).lijevo);
					drvo1.add(drvo1.get(i).desno);
				}
			}
			
			else {
				continue;
			}
		}
		


	

		//bezuspjesni slucaj kme
		/*if(vr<=korijen.blok.velicina/) {
			korijen.lijevo=new Cvor(new Blok(korijen.blok.adresa/2,korijen.blok.adresa+1));
			korijen.desno=new Cvor(new Blok(korijen.blok.adresa/2,korijen.blok.adresa+1));
			korijen.lijevo.blok.setZauzet();
			korijen.lijevo.blok.dodajSadrzaj(vr+"");
		}
		else {
			korijen.blok.setZauzet();
		}*/
		
		
		/*for(Map.Entry<Cvor,Integer> entry : drvo.entrySet()) {
			if(!entry.getKey().blok.zauzet) {
				if(vr<=(entry.getKey().blok.velicina/2)) {
					entry.getKey().lijevo=new Cvor(new Blok(entry.getKey().blok.velicina/2,entry.getKey().blok.adresa+1));
					entry.getKey().desno=new Cvor(new Blok(entry.getKey().blok.velicina/2,entry.getKey().blok.adresa+2));
					entry.getKey().lijevo.blok.setZauzet();
					drvo.put(entry.getKey().lijevo, entry.getValue()*2);
					drvo.put(entry.getKey().desno, entry.getValue()*2+1);
					
				}}
				else if(vr>(entry.getKey().blok.velicina/2)) {
					entry.getKey().blok.setZauzet();
				}
				else {
					continue;
				}
			
		}*/
		
		
	}
	
	public static void main(String[]args) {
		
		//roditelj je na poziciji i, njegova djeca pozicije i+1,i+2
		
		
		Cvor korijen=new Cvor(new Blok(1024,1000));
	
		drvo1.add(korijen);
		
		for(int i=0;i<5;i++) {
			for(int j=0;j<drvo1.size();j++) {
		
				System.out.println("----");
				System.out.println(j+": "+drvo1.get(j).blok.velicina);
				
			}
			System.out.println("Unesite zeljenu vrijednost:");
			int vr=scan.nextInt();
			postavljanje(vr);
		}
		
	}

}

class Cvor{
	
	Blok blok;
	Cvor lijevo;
	Cvor desno;
	
	Cvor(Blok b){
		this.blok=b;
		lijevo=null;
		desno=null;
	}
	
}

