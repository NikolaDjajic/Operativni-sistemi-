package FajlSistem;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
	
	public void changeDir(String put) {					//Promijeni dir
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
			System.out.println("  -"+s);
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
	
	public boolean uStablu(String naziv) {
		System.err.println(trenutni.list());
		for(String s:trenutni.list())
			if(s.equals(naziv))
				return true;
		return false;
	}
	
	public String[] getListTren() {
		return trenutni.list();
	}
	
	public String getTrStr() {
		return trenutni.getAbsolutePath();
	}

	public static void setOut(OutputStream out) {
		System.setOut(new PrintStream(out, true));
	}
	
}