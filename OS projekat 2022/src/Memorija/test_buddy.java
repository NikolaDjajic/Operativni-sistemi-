package Memorija;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// NE RADIIIIIIIIIIIII al nek stoji 
public class test_buddy {
	
	static Scanner scan=new Scanner(System.in);
	
	
	//lista u kojoj cuvam sve cvorove
	static ArrayList<Cvor> drvo1=new ArrayList<Cvor>();
	static ArrayList<Cvor> slobodniDijelovi=new ArrayList<Cvor>();
	
	static int sumaSlobodnih=0;
	
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
					drvo1.get(i).blok.setZauzet();
					//nove dvije grane
					drvo1.get(i).lijevo=new Cvor(new Blok(drvo1.get(i).blok.velicina/2,drvo1.get(i).blok.adresa+1));
					drvo1.get(i).desno=new Cvor(new Blok(drvo1.get(i).blok.velicina/2,drvo1.get(i).blok.adresa+2));
					drvo1.get(i).lijevo.blok.setZauzet();
					slobodniDijelovi.add(drvo1.get(i).desno);
					slobodniDijelovi.remove(drvo1.get(i).blok);
					sumaSlobodnih+=drvo1.get(i).desno.blok.velicina;
					sumaSlobodnih-=drvo1.get(i).blok.velicina;
					drvo1.add(drvo1.get(i).lijevo);
					drvo1.add(drvo1.get(i).desno);
				}
			}
			
			else {
				continue;
			}
		}
		


	


		
		
	}
	
	public static void main(String[]args) {
		
		//roditelj je na poziciji i, njegova djeca pozicije i+1,i+2
		
		
		Cvor korijen=new Cvor(new Blok(1024,1000));
	
		drvo1.add(korijen);
		
		for(int i=0;i<5;i++) {
			
			System.out.println("Unesite zeljenu vrijednost:");
			int vr=scan.nextInt();
			postavljanje(vr);
			
			for(int j=0;j<drvo1.size();j++) {
				
				System.out.println("----");
				System.out.println(j+": "+drvo1.get(j).blok.velicina);
				
			}
			for(int j=0;j<slobodniDijelovi.size();j++) {
				System.err.println("-->"+slobodniDijelovi.get(j).blok.velicina);
			}
		}
		
	}

}


