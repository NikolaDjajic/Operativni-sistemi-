package Procesor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import com.sun.javafx.webkit.UIClientImpl;

import Asembler.Asembler;

public class Proces {
	
	int ID,prioritet;
	String ime;
	int[] vrijednostRegistara;		//Koristimo da sacuvamo vr reg kad blokiramo proces, pa treba da nastavimo sa procesom 
	public StanjeProcesa stanje;
	int brojacInstrukcija=-1;		//Kada je -1 znaci da pravimo proces, a kad nije -1 znaci da nastavljamo proces od instrukcije na indeksu od brojaca
	ArrayList<String> instrukcije;
	Random rand = new Random(5);
	String putanja;
	ArrayList<String> asemblerskeInstrukcije;
	String masinskeInstrukcije;
	static String sadrzajDatoteke;
	
	public Proces(String naziv,int pr,String put) {
		this.ID=RasporedjivacProcesa.sviProcesi.size();
		this.instrukcije=new ArrayList<String>();
		this.ime=naziv;
		this.prioritet=pr;
		this.putanja=put;
		asemblerskeInstrukcije=new ArrayList<String>();
		citajDatoteku();
		this.masinskeInstrukcije=Asembler.generisiMasinski(asemblerskeInstrukcije);
		
		
	//	RasporedjivacProcesa.sviProcesi.add(this);
	}
	
	public Proces(String naziv,String put) {
		this.ID=RasporedjivacProcesa.sviProcesi.size();
		this.instrukcije=new ArrayList<String>();
		this.ime=naziv;
		this.putanja=put;
		asemblerskeInstrukcije=new ArrayList<String>();
		citajDatoteku();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.masinskeInstrukcije=Asembler.generisiMasinski(this.asemblerskeInstrukcije);

		
	//	RasporedjivacProcesa.sviProcesi.add(this);
	}
	
	public void sacuvajVrijednostRegistara(int[] vrReg) {
		for (int i = 0; i < vrReg.length; i++)
			vrijednostRegistara[i] = vrReg[i];
	}
	
	public void blockProces() {
		if (stanje == StanjeProcesa.RUNNING) {
			this.stanje = StanjeProcesa.BLOCKED;
			if (RasporedjivacProcesa.redIzvrsavanja.contains(this))
				RasporedjivacProcesa.redIzvrsavanja.remove(this);
		}
	}

	public void unblockProces() {
		if (stanje == StanjeProcesa.BLOCKED) {
				stanje = StanjeProcesa.READY;
			System.out.println("Process " + ime + " is unblocked");
			RasporedjivacProcesa.redIzvrsavanja.add(this);
		}
	}
	
	public void terminateProces() {
		if (stanje == StanjeProcesa.READY || stanje == StanjeProcesa.RUNNING) {
			stanje = StanjeProcesa.TERMINATED;
			if (RasporedjivacProcesa.redIzvrsavanja.contains(this))
				RasporedjivacProcesa.redIzvrsavanja.remove(this);
		} else if (stanje == StanjeProcesa.BLOCKED) {
			stanje = StanjeProcesa.TERMINATED;
		}
	}

	public void citajDatoteku() {
		try {
			File fajl = new File(this.putanja);
			
			Scanner citac = new Scanner(fajl);
			
			while (citac.hasNextLine()) {
				String linija = citac.nextLine();
				asemblerskeInstrukcije.add(linija);
			}
			
			citac.close();
			prioritet = asemblerskeInstrukcije.size();
			this.stanje=StanjeProcesa.READY;
			
		} catch (FileNotFoundException e) {
			System.out.printf("Error! \n");
			e.printStackTrace();
		}
	}
	
	public static String getSadrzaj() {
		return sadrzajDatoteke;
	}
	
	public void ispisiAsemblerskeInst() {
		for(String s:asemblerskeInstrukcije)
			System.out.println(s);
	}
	
	public static void main(String[]args) {
	//	Asembler a = new Asembler();
	//	Proces p = new Proces("cao","C:\\Users\\pc\\Desktop\\os1\\Operativni-sistemi--main\\OS projekat 2022\\Programi\\cao\\test01.asm");
		
		
	//	p.ispisiAsemblerskeInst();
		
	
		
	//	p.masinskeInstrukcije=a.generisiMasinski(p.asemblerskeInstrukcije);
	
	//	System.out.println(p.masinskeInstrukcije);
		
		
	//	a.izvrsiMasinski(p.masinskeInstrukcije);
		
	}
	
	
}
