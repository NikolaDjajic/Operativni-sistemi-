package Memorija;

import java.util.ArrayList;

import FajlSistem.FajlMemorija;

public class SekundarnaMemorija {

	static int velicina;
	static Blok[] blokovi;
	static int brojBlokova;
	static ArrayList<FajlMemorija> fajlovi;
	
	public SekundarnaMemorija() {
		velicina=2048;
		brojBlokova=velicina/Blok.getVelicinaFajl();
		blokovi=new Blok[brojBlokova];
		for(int i=0;i<brojBlokova;i++) {
			Blok novi=new Blok(i);
			blokovi[i]=novi;
		}
		fajlovi=new ArrayList<>();
	}
	
	private static int numberOfFreeBlocks() {
        int counter = 0;
        for (int i = 0; i < brojBlokova; i++)
            if (!blokovi[i].zauzet)
                counter++;
        return counter;
    }
	
	//cuvanje
	
	public void sacuvaj(FajlMemorija fajl) {
		
		int reminder=fajl.getVelicina()%Blok.getVelicinaFajl();
		int broj;
		
		if(reminder==0)
			broj=fajl.getVelicina()/Blok.getVelicinaFajl();
		else
			broj=(fajl.getVelicina()+4-reminder)/Blok.getVelicinaFajl();
		broj++;
		
		if(numberOfFreeBlocks()>=broj) {
			int brojac=0;
			int listaBrojac=0;
			Blok indeks=null;
			int pomocnaLista[]=null;
			
			for(int i=0;i<broj;i++)
				if(!blokovi[i].zauzet) {
					blokovi[i].setZauzet();
					if(brojac==0) {
						indeks=blokovi[i];
						fajl.setIndexBlok(i);
						blokovi[i].dodajSadrzaj("ib ".getBytes());
						brojac++;
						pomocnaLista=new int[broj-1];
					}
					else {
						pomocnaLista[listaBrojac]=i;
						blokovi[i].dodajSadrzaj(FajlMemorija.dio(brojac));
						
						listaBrojac++;
						brojac++;
						
						if(brojac==broj) {
							fajl.setDuzina(brojac);
							fajlovi.add(fajl);
							indeks.setLista(pomocnaLista);
							return;
							
						}
					}
				}
		}
		else {
			System.out.println("Nemate dovoljno prostora da biste kreirali fajl.");
		}
	}
	public void brisanjeFajla(FajlMemorija file) {
        if (!fajlovi.contains(file))
            System.out.println("Vas fajl nije u sekundarnoj memoriji.");
        else {
            int index = file.getIndexBlok();
            Blok in = blokovi[index];
            for (int i = 0; i < in.getLista().length; i++) {
                int index2 = in.getLista()[i];
                blokovi[index2].setSlobodan();;
                blokovi[index2].dodajSadrzaj(null);;
            }
            blokovi[index].setSlobodan();;
            blokovi[index].dodajSadrzaj(null);;
        }
        fajlovi.remove(file);
    }

    public String readFile(FajlMemorija file) {
        String read = "";
        int index = file.getIndexBlok();
        Blok in = blokovi[index];
        for (int i = 0; i < in.getLista().length; i++) {
            int index2 = in.getLista()[i];
            // System.out.println(index2);
            byte[] content = blokovi[index2].getSadrzaj();
            for (byte singleByte : content)
                read += (char) singleByte;
        }
        return read;
    }

   

    public boolean sadrzi(String fileName) {
        for (FajlMemorija f : fajlovi)
            if (f.getIme().equals(fileName))
                return true;
        return false;
    }
    

    public FajlMemorija getFile(String fileName) {
        for (FajlMemorija f : fajlovi)
            if (f.getIme().equals(fileName))
                return f;
        return null;
    }

    public static int getVelicina() {
        return velicina;
    }

    public static Blok[] getBlokovi() {
        return blokovi;
    }

    public ArrayList<FajlMemorija> getFajlovi() {
        return fajlovi;
    }

    private static String printList(int[] list) {
        String list2 = "";
        for (int i = 0; i < list.length; i++) {
            list2 += list[i] + ", ";
        }
        return list2.substring(0, list2.length() - 2);
    }

    public static void printMemoryAllocationTable() {
        System.out.println("Name of file\tIndex block\tBlocks occupied by this file\t\t\t\t\tLength");
        for (FajlMemorija file : fajlovi)
            System.out.println(file.getIme() + "\t\t" + file.getIndexBlok() + "\t" +
                    printList(blokovi[file.getIndexBlok()].getLista()) + "\t\t\t\t\t" + file.getDuzina());
    }
}
