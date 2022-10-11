package Procesor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Proces {
	
	int ID,prioritet;
	String ime;
	int[] vrijednostRegistara;		//Koristimo da sacuvamo vr reg kad blokiramo proces, pa treba da nastavimo sa procesom 
	public StanjeProcesa stanje;
	int brojacInstrukcija=-1;		//Kada je -1 znaci da pravimo proces, a kad nije -1 znaci da nastavljamo proces od instrukcije na indeksu od brojaca
	ArrayList<String> instrukcije;
	Random rand = new Random(5);
	
	
	public Proces(String naziv,int pr) {
		this.ID=RasporedjivacProcesa.sviProcesi.size();
		this.instrukcije=new ArrayList<String>();
		this.ime=naziv;
		this.prioritet=pr;
		
		RasporedjivacProcesa.sviProcesi.add(this);
		RasporedjivacProcesa.redIzvrsavanja.add(this);
		RasporedjivacProcesa.updatetRI();
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
		//	MemoryManager.removeProcess(this);			Da ga izbaci iz memorije
			stanje = StanjeProcesa.TERMINATED;
		}
	}

	
	
	
	
}
