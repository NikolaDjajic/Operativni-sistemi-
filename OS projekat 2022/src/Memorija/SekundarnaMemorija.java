package Memorija;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import FajlSistem.FajlMemorija;
import Procesor.StanjeProcesa;

public class SekundarnaMemorija {

	private static int velicina;
	private static Blok[] blokovi;
	private static int brojBlokova;
	private static ArrayList<FajlMemorija> fajlovi;
	private static int brojac=0;
	
	public static void main(String[]args) {
		SekundarnaMemorija sm=new SekundarnaMemorija();
	}

	public SekundarnaMemorija() {
		velicina=2048;
		brojBlokova=velicina/Blok.getVelicinaFajl();
		blokovi=new Blok[brojBlokova];
		for(int i=0;i<brojBlokova;i++) {
			Blok novi=new Blok(i);
			blokovi[i]=novi;
		}
		fajlovi=new ArrayList<>();
		final File folder = new File("C:\\Users\\Sara\\Documents\\GitHub\\Operativni-sistemi-\\OS projekat 2022\\Programi\\cao");
		final File folder1 = new File("C:\\Users\\Sara\\Documents\\GitHub\\Operativni-sistemi-\\OS projekat 2022\\Programi\\caos");
		listFilesForFolder(folder);
		listFilesForFolder(folder1);
		for(int i=0;i<brojac;i++)
			sacuvaj(fajlovi.get(i));
		
	}
	
	
	//u ram
	public static void prebacivanje(FajlMemorija fajl) {
		if(BuddyS.imaLiMjesta(fajl)) {
			BuddyS.popunjavanje(fajl,fajl.getVelicina());
			System.out.println("Fajl je presao na radnu memoriju. ");
			BuddyS.getZauzetiBlokovi();
			BuddyS.getSlobodniBlokovi();
		}
		else {
			System.out.println("Nedovoljno prostora.");
			BuddyS.getZauzetiBlokovi();
			BuddyS.getSlobodniBlokovi();
		}
		
		
		
	}
	
	//cuvanje
	
	public static void sacuvaj(FajlMemorija fajl) {
		
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
			
			for(int i=0;i<brojBlokova;i++)
				if(!blokovi[i].zauzet) {
					blokovi[i].setZauzet();
					if(brojac==0) {
						indeks=blokovi[i];
						fajl.setIndexBlok(i);
						blokovi[i].dodajSadrzaj("ib  ".getBytes());
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
							System.out.println("Ucitano u sekundarnu memoriju.");
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
	
	public static void brisanjeFajla(FajlMemorija file) {
		boolean tr=false;
		for(int i=0;i<fajlovi.size();i++) {
			if(file.getIme().equals(fajlovi.get(i).getIme())) {
					tr=true;
					break;
			}
		}
        if (!tr)
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
            System.out.println("Fajl je izbrisan.");
            ispisiBrojSlobodnihBlokova();
            ispisiBrojZauzetihBlokova();
        }
        fajlovi.remove(file);
    }

    public static String readFile(FajlMemorija file) {
        String read = "";
        int index = file.getIndexBlok();
        Blok in = blokovi[index];
        System.err.println(in.getLista());
        for (int i = 0; i < in.getLista().length; i++) {
            int index2 = in.getLista()[i];
            // System.out.println(index2);
            byte[] content = blokovi[index2].getSadrzaj();
            for (byte singleByte : content)
                read += (char) singleByte;
        }
        return read;
    }

    private static int numberOfFreeBlocks() {
        int counter = 0;
        for (int i = 0; i < brojBlokova; i++)
            if (!blokovi[i].zauzet)
                counter++;
        return counter;
    }
    
    private static int numberOfOccupiedBlocks() {
    	 int counter = 0;
         for (int i = 0; i < brojBlokova; i++)
             if (blokovi[i].zauzet)
                 counter++;
         return counter;
    }
    
    public static void ispisiBrojSlobodnihBlokova() {
    	System.out.println("-----------");
    	System.out.println("Ukupna velicina memorije SM : "+velicina);
    	System.out.println("-----------");
    	System.out.println("Broj slobodnih blokova SM : "+numberOfFreeBlocks());
    	System.out.println("-----------");
    }
    
    public static void ispisiBrojZauzetihBlokova() {
    	System.out.println("Broj zauzetih blokova SM : "+numberOfOccupiedBlocks());
    }

    public boolean sadrzi(String fileName) {
        for (FajlMemorija f : fajlovi)
            if (f.getIme().equals(fileName))
                return true;
        return false;
    }
    
    public static String readFile(File f) {
    	String rez="";
    	
    	try {
			Scanner citac = new Scanner(f);
			while (citac.hasNextLine()) {
				String linija = citac.nextLine();
				rez+=linija+" ";
			}
			
			citac.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.printf("Error! \n");
			e.printStackTrace();
		}
    	
    	return rez;
    }
    
    public static void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
                
            } else {
                System.out.println(fileEntry.getName());
                fajlovi.add(new FajlMemorija(fileEntry.getName(),readFile(fileEntry).getBytes()));
                brojac++;
            }
        }
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

   /* public static void printMemoryAllocationTable() {
        System.out.println("Name of file\tIndex block\tBlocks occupied by this file\t\t\t\t\tLength");
        for (FajlMemorija file : fajlovi)
            System.out.println(file.getIme() + "\t\t" + file.getIndexBlok() + "\t" +
                    printList(blokovi[file.getIndexBlok()].getLista()) + "\t\t\t\t\t" + file.getDuzina());
    }*/
}
