package Asembler;
import java.util.ArrayList;

import Procesor.RasporedjivacProcesa;
import Procesor.StanjeProcesa;

public class Asembler {

	private static final Operacija halt = new Operacija("HLT","0000");
	private static final Operacija add = new Operacija("ADD","0001");
	private static final Operacija sub = new Operacija("SUB","0010");
	private static final Operacija mul = new Operacija("MUL","0011");
	private static final Operacija inc = new Operacija("INC","0100");
	private static final Operacija dec = new Operacija("DEC","0101");
	private static final Operacija mov = new Operacija("MOV","0110");
	private static final Operacija jmp = new Operacija("JMP","0111");

	static ArrayList<Operacija>sveOperacije=new ArrayList<Operacija>();
	
	public static Registar R1 = new Registar("R1", "0001", 0);
	public static Registar R2 = new Registar("R2", "0010", 0);
	public static Registar R3 = new Registar("R3", "0011", 0);
	public static Registar R4 = new Registar("R4", "0100", 0);
	
	public Asembler() {
		
		sveOperacije.add(halt);
		sveOperacije.add(add);		
		sveOperacije.add(sub);
		sveOperacije.add(mul);
		sveOperacije.add(inc);
		sveOperacije.add(dec);
		sveOperacije.add(mov);
		sveOperacije.add(jmp);
	}

	public static void add(String reg1, String reg2) {  //Sabira reg1 sa reg2 i upisuje u reg1
		Registar r1 = getRegistarPoAdresi(reg1);
		
		if(reg2.length()==4) {
			Registar r2 = getRegistarPoAdresi(reg2);
			r1.setVrijednost(r1.getVrijednost()+r2.getVrijednost());
			System.out.println("Registru "+r1.getIme()+" dodajem "+ r2.getIme());
			
		}else {
			r1.setVrijednost(r1.getVrijednost()+izBin(reg2));
			System.out.println("Registru "+r1.getIme()+" dodajem "+izBin(reg2));
		}
	}
	
	public static void sub(String reg1, String reg2) {	//Oduzima reg1 od reg2 i upisuje u reg1
		Registar r1 = getRegistarPoAdresi(reg1);
		
		if(reg2.length()==4) {
			Registar r2 = getRegistarPoAdresi(reg2);
			r1.setVrijednost(r1.getVrijednost()-r2.getVrijednost());
			System.out.println("Od registra "+r1.getIme()+" oduzimam "+ r2.getIme());

		}else {
			r1.setVrijednost(r1.getVrijednost()-izBin(reg2));
			System.out.println("Od registra "+r1.getIme()+" oduzimam "+izBin(reg2));
		}
	}
	
	public static void mul(String reg1, String reg2) {	//Mnozi reg1 sa reg2 i upisuje u reg1
		Registar r1 = getRegistarPoAdresi(reg1);
		
		if(reg2.length()==4) {
			Registar r2 = getRegistarPoAdresi(reg2);
			r1.setVrijednost(r1.getVrijednost()*r2.getVrijednost());
			System.out.println("Registar "+r1.getIme()+" mnozim sa "+ r2.getIme());

		}else {
			r1.setVrijednost(r1.getVrijednost()*izBin(reg2));
			System.out.println("Registar "+r1.getIme()+" mnozim sa "+izBin(reg2));

		}
	}
	
	public static void inc(String reg) {	//reg+=1
		Registar r = getRegistarPoAdresi(reg);
		r.setVrijednost(r.getVrijednost()+1);
		System.out.println("Registar "+r.getIme()+" inkrementiram");

	}

	public static void dec(String reg) {	//reg-=1
		Registar r = getRegistarPoAdresi(reg);
		r.setVrijednost(r.getVrijednost()-1);
		System.out.println("Registar "+r.getIme()+" dekrementiram");
	}
	
	public static void mov(String reg1, String reg2) {	// postavlja vrijednost registra1 na registar2
		Registar r1 = getRegistarPoAdresi(reg1);
		
		if(reg2.length()==4) {
			Registar r2 = getRegistarPoAdresi(reg2);
			r1.setVrijednost(r2.getVrijednost());
			System.out.println("Registaru "+r1.getIme()+" pisem vrijednost od "+r2.getIme());
		}else {
			r1.setVrijednost(izBin(reg2));
			System.out.println("Registaru "+r1.getIme()+" pisem vrijednost od "+izBin(reg2));
		}
	}

	public static void halt() {
		RasporedjivacProcesa.trenutniProces.stanje=StanjeProcesa.DONE;
	}
	
	private static Registar getRegistarPoImenu(String ime) { // vraca registar na osnovu imena registra
		
		if(R1.getIme().equals(ime))
			return R1;
		if(R2.getIme().equals(ime))
			return R2;
		if(R3.getIme().equals(ime))
			return R3;
		if(R4.getIme().equals(ime))
			return R4;
		return null;
	}
	
	private static Registar getRegistarPoAdresi(String adresa) { // vraca registar na osnovu adrese registra
		
		if(R1.getAdresa().equals(adresa))
			return R1;
		if(R2.getAdresa().equals(adresa))
			return R2;
		if(R3.getAdresa().equals(adresa))
			return R3;
		if(R4.getAdresa().equals(adresa))
			return R4;
		return null;
	}
	
	public static String generisiMasinski(ArrayList<String>asemblInstr) {
		String masinski="";
		
		for(String naredba:asemblInstr) {
			
			String[]podaci=naredba.split(" ");
			Operacija op=vratiOperacijuPoImenu(podaci[0]);
			
			
			if(!op.getNaziv().equals("HLT")) {
				
				if(op.getNaziv().equals("INC")) 
					masinski+=op.getKod()+" "+getRegistarPoImenu(podaci[1]).getAdresa();
					
				else if(op.getNaziv().equals("DEC"))
					masinski+=op.getKod()+" "+ getRegistarPoImenu(podaci[1]).getAdresa();
					
				else {
				String reg1=podaci[1];
				String reg2=podaci[2];
				masinski+=op.getKod()+" ";
				
				if(jelBroj(reg1))
					masinski+=uBin(reg1)+" ";
				else {
					Registar r1=getRegistarPoImenu(reg1);
					masinski+=r1.getAdresa()+" ";
				}
				
				if(jelBroj(reg2))
					masinski+=uBin(reg2);
				else {
					Registar r2=getRegistarPoImenu(reg2);
					masinski+=r2.getAdresa();
				}				
				}
			}
			masinski+="\n";
		}
		return masinski;
	}

	
	
	public static Operacija vratiOperacijuPoKodu(String kod) {
		
		for(Operacija op:sveOperacije)
			if(op.getKod().equals(kod))
				return op;
		return null;
	}
	
	public static Operacija vratiOperacijuPoImenu(String ime) {
		
		for(Operacija op:sveOperacije)
			if(op.getNaziv().equals(ime)) {
				return op;
			}
		return null;
	}

	public static void ispisiRegistre() {
		System.out.println();
		System.out.println("Registri:");
		System.out.println("R1 = [ " + R1.getVrijednost() + " ]");
		System.out.println("R2 = [ " + R2.getVrijednost() + " ]");
		System.out.println("R3 = [ " + R3.getVrijednost() + " ]");
		System.out.println("R4 = [ " + R4.getVrijednost() + " ]");
		System.out.println();
	}

	public static void resetRegistre() {
		R1.setVrijednost(0);
		R2.setVrijednost(0);
		R3.setVrijednost(0);
		R4.setVrijednost(0);
	}

	public static int izBin(String vr) {
        return Integer.parseInt(vr, 2);
    }
	
    public static String uBin(String s) {
        int value = Integer.parseInt(s);
        return String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0');
    }
    
    public static boolean jelBroj(String str) { 
    	  try {  
    	    Double.parseDouble(str);  
    	    return true;
    	  } catch(NumberFormatException e){  
    	    return false;  
    	  }  
    	}
    
	public static void main(String[]args) {
		
		Asembler a = new Asembler();
		
		ArrayList<String> st=new ArrayList<String>();
		st.add("ADD R1 4");
		st.add("ADD R2 R3");
		st.add("INC R1");
		st.add("DEC R2");
		st.add("SUB R2 R1");
		st.add("MUL R2 R1");
		st.add("HLT");	
		
		String masinski=generisiMasinski(st);
		System.out.println(masinski);
	
	//	a.izvrsiMasinski(masinski);
		
	}
}