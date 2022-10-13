package FajlSistem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;


public class Fajlovi {
	
	public File parent,trenutni;
	
	public Fajlovi(String pocetni) {
	
	parent = new File (pocetni);
	trenutni = parent;
	}

	public void mkd(String novi) { 					// Napravi dir
		File noviDir = new File(trenutni.getAbsolutePath()+"\\"+novi);		
		noviDir.mkdir();
	}
	
	public void changeDir(String put) {					//Promjeni dir
		if(put.equals("..")) {
			if(trenutni.getAbsolutePath().equals(parent.getAbsolutePath()))
				System.err.println("Ne mozete ici unazad vise!");
			else {
				trenutni = new File (trenutni.getParent());
			}
			return;
		}
		
		String lista[]=trenutni.list();
		
		for(String s : lista) {

			if(s.equals(put)) {
				trenutni = new File(trenutni.getAbsolutePath()+"\\"+put);
				break;
			}
		}
	}
	
	public void mkf(String naziv) {					//Napravi fajl
		File f = new File(trenutni.getAbsoluteFile()+"\\"+naziv);
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ispisiTr() {			
		System.out.println("Trenutno ste u "+trenutni.getAbsolutePath());
		String niz[] = trenutni.list();
		for(String s :niz)
			System.out.println(s);
	}
	
	public void del(File file){				//Obrisi
        
		if(file.isFile()) {
			file.delete();
			return;
		}
		
		for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                del(subfile);
            }
            subfile.delete();
        }
       	file.delete(); 
    }
	
	public String[] getListTren() {
		return trenutni.list();
	}
	
	public String getTrStr() {
		return trenutni.getAbsolutePath();
	}	
	
	/*public static void main(String[] args) {
		Fajlovi f = new Fajlovi("Programi");
		f.ispisiTr();
		
		while(true) {
			Scanner scan = new Scanner(System.in);
		
			String str = scan.nextLine();
			String []niz=str.split("\\s+");
		
			
		
			if(str.equals("end"))
				break;
		
			if(niz[0].equals(".."))
				f.changeDir("..");
			
			if(niz[0].equals("cd"))
				f.changeDir(niz[1]);
		
			if(niz[0].equals("mkd"))
				f.mkd(niz[1]);
		
			if(niz[0].equals("mkf"))
				f.mkf(niz[1]);
		
			if(niz[0].equals("del")) {
				String[] niz1 = f.getListTren();
				for(String s1:niz1)
					if(niz[1].equals(s1)) 
						f.del(new File(f.getTrStr()+"\\"+niz[1]));					
			}
			
			
		
		f.ispisiTr();
		}
	}*/
}