import Procesor.RasporedjivacProcesa;
import Procesor.StanjeProcesa;

public class Asembler {

	public static final String halt = "0000";
	public static final String add = "0001";
	public static final String sub = "0010";
	public static final String mul = "0011";
	public static final String inc = "0100";
	public static final String dec = "0101";
	public static final String store = "0110";
	public static final String load = "0111";
	public static final String mov = "1000";
	public static final String jmp = "1001";
	public static final String s = "1010";
	public static final String jmpd = "1011";
	public static final String h = "1100";
	public static final String g = "1101";

	public static Registar R1 = new Registar("R1", 0001, 0);
	public static Registar R2 = new Registar("R2", 0002, 0);
	public static Registar R3 = new Registar("R3", 0003, 0);
	public static Registar R4 = new Registar("R4", 0004, 0);


	public static void add(String reg1, String reg2) {  //Sabira reg1 sa reg2 i upisuje u reg1
		Registar r1 = getRegistarPoImenu(reg1);
		Registar r2 = getRegistarPoImenu(reg2);
		if (r1 != null && r2 != null)
			r1.vrijednost += r2.vrijednost;
	}
	
	public static void add(String reg, int broj) {	//Sabira reg sa brojem i upisuje u reg
		Registar r = getRegistarPoImenu(reg);
			if (r != null)
				r.vrijednost += broj;
	}
	
	public static void sub(String reg1, String reg2) {	//Oduzima reg1 od reg2 i upisuje u reg1
		Registar r1 = getRegistarPoImenu(reg1);
		Registar r2 = getRegistarPoImenu(reg2);
		if (r1 != null && r2 != null)
			r1.vrijednost -= r2.vrijednost;
	}
	
	public static void sub(String reg, int broj) {	//Oduzima reg od broja i upisuje u reg
		Registar r = getRegistarPoImenu(reg);
			if (r != null)
				r.vrijednost -= broj;
	}
	
	public static void mul(String reg1, String reg2) {	//Mnozi reg1 sa reg2 i upisuje u reg1
		Registar r1 = getRegistarPoImenu(reg1);
		Registar r2 = getRegistarPoImenu(reg2);
		if (r1 != null && r2 != null)
			r1.vrijednost *= r2.vrijednost;
	}
	
	public static void mul(String reg, int broj) {	//Mnozi reg sa brojem i upisuje u reg
		Registar r = getRegistarPoImenu(reg);
			if (r != null)
				r.vrijednost *= broj;
	}
	
	public static void inc(String reg) {	//eg+=1
		Registar r = getRegistarPoImenu(reg);
		r.vrijednost += 1;
	}

	public static void dec(String reg) {	//reg-=1
		Registar r = getRegistarPoImenu(reg);
		r.vrijednost -= 1;
	}
	/*
	public static void store(String reg, String adr) {		//Upise vr registra u ram
		Registar r = getRegistarPoImenu(reg);
		if (r != null)
			Ram.setAt(Integer.parseInt(adr, 2), r.vrijednost);
	}

	public static void load(String reg, String adr) {		//Ucita vr registra iz memorije
		Registar r = getRegistarPoImenu(reg);
		if (r != null) {
			if (!Ram.isOcupied(Integer.parseInt(adr, 2)))
				r.vrijednost = Ram.getAt(Integer.parseInt(adr, 2));
		}
	}
	*/
	
	public static void mov(String reg1, String reg2) {	// postavlja vrijednost registra1 na registar2
		Registar r1 = getRegistarPoImenu(reg1);
		Registar r2 = getRegistarPoImenu(reg2);
		if (r1 != null && r2 != null) {
			r1.vrijednost = r2.vrijednost;
		}
	}
/*
	public static void jmp(String adr) {
		int temp = Integer.parseInt(adr, 2);
		if (temp >= Shell.limit) {
			Shell.currentlyExecuting.setState(ProcessState.TERMINATED);
			System.out.println("Error with adress in process " + Shell.currentlyExecuting.getName());
			return;
		}
		Shell.PC = temp;
	}
*/
	public static void halt() {
		RasporedjivacProcesa.trenutniProces.stanje=StanjeProcesa.DONE;
	}

	
	private static Registar getRegistarPoImenu(String ime) { // vraca registar na osnovu imena registra
		
		if(R1.ime.equals(ime))
			return R1;
		if(R2.ime.equals(ime))
			return R2;
		if(R3.ime.equals(ime))
			return R3;
		if(R4.ime.equals(ime))
			return R4;
		return null;
	}
	
	private static Registar getRegistarPoAdresi(String adresa) { // vraca registar na osnovu adrese registra
		
		if(R1.adresa.equals(adresa))
			return R1;
		if(R2.adresa.equals(adresa))
			return R2;
		if(R3.adresa.equals(adresa))
			return R3;
		if(R4.adresa.equals(adresa))
			return R4;
		return null;
	}


	public static void ispisiRegistre() {
		System.out.println("Registri:");
		System.out.println("R1 vrijednost - [ " + R1.getVrijednost() + " ]");
		System.out.println("R2 vrijednost - [ " + R2.getVrijednost() + " ]");
		System.out.println("R3 vrijednost - [ " + R3.getVrijednost() + " ]");
		System.out.println("R4 vrijednost - [ " + R4.getVrijednost() + " ]");
	}

	public static void resetRegistre() {
		R1.vrijednost = 0;
		R2.vrijednost = 0;
		R3.vrijednost = 0;
		R4.vrijednost = 0;
	}

	public static void main(String[]args) {
		
		Asembler a = new Asembler();
		
	}
}