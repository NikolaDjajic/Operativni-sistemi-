package Procesor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class RasporedjivacProcesa extends Thread{
	
	static ArrayList<Proces> sviProcesi = new ArrayList<Proces>();
	static ArrayList<Proces> redIzvrsavanja = new ArrayList<Proces>();
	public static Proces trenutniProces;
	
	
	public RasporedjivacProcesa() {
		
	}
	
	@Override
	public void run() {
	
	}
	
	
	public void izvrsiProces(Proces proces) {
		this.trenutniProces=proces;
		
		
	}
	
	public static void updatetRI() {

		Comparator comparator = new Comparator<Proces>() {

			@Override
			public int compare(Proces o1, Proces o2) {
				if(o1.prioritet>o2.prioritet)
					return 1;
				else if(o1.prioritet<o2.prioritet)
					return -1;	
				return 0;
			}
		};
		
		Collections.sort(redIzvrsavanja,comparator);
	}
	
	public static void isipisiRedIzvrsavanja() {
		for(Proces p : redIzvrsavanja)
			System.out.println(p.ime);
	}
	
	public static void main(String[]args) {
		
		Proces p = new Proces("p1",3);
		Proces p1 = new Proces("p2",5);
		Proces p2 = new Proces("p3",1);
		Proces p3 = new Proces("p4",9);
		Proces p4 = new Proces("p5",0);
		Proces p5 = new Proces("p6",13);
		Proces p6 = new Proces("p7",2);
		RasporedjivacProcesa.isipisiRedIzvrsavanja();
		
	}
	
	
	
	
	
}
