package Procesor;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import Asembler.Asembler;
import Asembler.Operacija;


public class RasporedjivacProcesa extends Thread{
	
	static ArrayList<Proces> sviProcesi = new ArrayList<Proces>();
	public static ArrayList<Proces> redIzvrsavanja = new ArrayList<Proces>();
	public static Proces trenutniProces;
	
	public RasporedjivacProcesa() {
		
	}
	
	@Override
	public void run() {
		//while (true) {
            synchronized (this) {
            	
            	for(Proces p:sviProcesi) {
            		if(p.stanje!= StanjeProcesa.BLOCKED && p.stanje!= StanjeProcesa.DONE && p.stanje!= StanjeProcesa.TERMINATED) {
            			p.stanje=StanjeProcesa.READY;
            			redIzvrsavanja.add(p);
            			
            			updatetRI();
            		}
            	}
           
            	if(redIzvrsavanja.size()>0) {
            		Proces next = redIzvrsavanja.get(0);
            		
            		redIzvrsavanja.remove(next);
                    izvrsiProces(next);
                    }
                }
		}
            
	
	public void izvrsiProces(Proces proces) {
		this.trenutniProces=proces;
		this.trenutniProces.stanje=StanjeProcesa.RUNNING;
		System.out.println("Izvrsavam proces "+ trenutniProces.ID+" "+ trenutniProces.ime+" "+trenutniProces.putanja);
		
		izvrsiMasinski(this.trenutniProces);
		this.trenutniProces.stanje=StanjeProcesa.DONE;
	}
	
	public void termProces(String naziv) {
		for(Proces p : sviProcesi)
			if(p.ime.equals(naziv))
				p.stanje=StanjeProcesa.TERMINATED;
	}
	
	public static void izvrsiMasinski(Proces p) {
		String[]instrukcije=p.masinskeInstrukcije.split("\n");
		
		for(String instrukcija:instrukcije) {
			String[] podaci=instrukcija.split(" ");
			String kod=podaci[0];
			Operacija operacija=Asembler.vratiOperacijuPoKodu(kod);
			
			try {
				Thread.sleep(900);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(operacija.getNaziv().equals("HALT")) {
				Asembler.halt();
				return;
			}
			
			if(operacija.getNaziv().equals("INC")) {
				Asembler.inc(podaci[1]);
			}
			if(operacija.getNaziv().equals("DEC")) {
				Asembler.dec(podaci[1]);
			}
			if(operacija.getNaziv().equals("ADD")) {
				Asembler.add(podaci[1],podaci[2]);
			}
			if(operacija.getNaziv().equals("SUB")) {
				Asembler.sub(podaci[1],podaci[2]);
			}
			if(operacija.getNaziv().equals("MUL")) {
				Asembler.mul(podaci[1],podaci[2]);
			}
			if(operacija.getNaziv().equals("MOV")) {
				Asembler.mov(podaci[1],podaci[2]);
			}
			Asembler.ispisiRegistre();
		}
		Asembler.resetRegistre();
	}
		
	public static void ucitajProces(String naziv,String put) {
		Proces novi = new Proces(naziv,put);
		//System.err.println(novi+" "+put);
		sviProcesi.add(novi);
		updatetRI();
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
		System.out.println("Procesi u redu izvrsavanja:");
		if(redIzvrsavanja.size()>0)
			for(Proces p : redIzvrsavanja)
				System.out.println(p.ID+" "+ p.ime+" "+p.prioritet+" "+p.stanje);
		else
			System.out.println("Nijedan proces nije ucitan!");
	}
	
	public static void ispisiSveProcese() {
		System.out.println("Svi procesi:");
		if(sviProcesi.size()>0)
			for(Proces p : sviProcesi)
				System.out.println(p.ID+" "+ p.ime+" "+p.prioritet+" "+p.stanje);
		else
			System.out.println("Nijedan proces nije ucitan!");
	}
	
	public void prioritetProcesa(int id,int vr) {
		for(Proces p:sviProcesi)
			if(p.ID==id)
				p.prioritet=vr;	
	}
	
	public static void setOut(OutputStream out) {
		System.setOut(new PrintStream(out, true));
	}
	
	public static void main(String[]args) {
	}
}
