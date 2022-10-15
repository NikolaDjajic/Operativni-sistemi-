package GUI;
import java.io.*;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Scanner;

import Asembler.Asembler;
import FajlSistem.FajlMemorija;
import FajlSistem.Fajlovi;
import Memorija.BuddyS;
import Memorija.SekundarnaMemorija;
import Procesor.Proces;
import Procesor.RasporedjivacProcesa;
import Procesor.StanjeProcesa;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private static TextArea gore = new TextArea();
	private static TextField dole = new TextField();
	private static Button izlaz;
	private static Button minimize;
	private static Button maximize;
	private PipedInputStream inp = new PipedInputStream();
	private PipedOutputStream out = new PipedOutputStream();
	private StringBuilder outStringBuilder = new StringBuilder();
	private OutputStream outStream;
	private static ArrayList<String> listaKomandi= new ArrayList<String>();
	private static int iter=0;
	public static Fajlovi f;
	private static RasporedjivacProcesa rp;
	private static Asembler a;
	private static SekundarnaMemorija sm;
	private static BuddyS bs;
	private static FajlMemorija fm;
	
	public static void boot() {
		f = new Fajlovi("Programi");
		rp = new RasporedjivacProcesa();
		a = new Asembler();
		sm = new SekundarnaMemorija();
		bs = new BuddyS();
	}
	
	public static void main(String[] args) throws IOException {
		
		boot();
		launch(args);
	}

	public static void ocistiTerminal() {
		gore.setText("");
		dole.clear();
	}

	private void dodajTextGore() {
		if (outStringBuilder.length() > 0) {
			gore.appendText(outStringBuilder.toString());
			outStringBuilder = new StringBuilder();
		}
	}
	
	public static String prethodna() {
		String rez = "";
		if (!listaKomandi.isEmpty()) {
			if (iter >= 0) {
				iter--;
				if (iter <= 0)
					iter = 0;
				rez = listaKomandi.get(iter);
			}
		}
		return rez;
	}

	public static String iduca() {
		String rez = "";
		if (!listaKomandi.isEmpty())
			if (iter < listaKomandi.size() - 1) {
				iter++;
				if (iter > listaKomandi.size() - 1)
					iter = listaKomandi.size() - 1;
				rez = listaKomandi.get(iter);
			}
		return rez;
	}
	
	public String getHelp() {
		String rez="";
		rez+="ls\t\t\tLista fajlove u trenutnom direktoriju\n";
		rez+="..\t\t\tOtvara roditeljski direktorijum\n";
		rez+="cd [naziv]\t\tMijenja trenutni folder\n";
		rez+="mkd [naziv]\t\tPravi novi dir\n";
		rez+="mkf [naziv]\t\tPravi novi file\n";
		rez+="del [naziv]\t\tBrise dir ili file\n";
		rez+="clear\t\t\tCisti terminal\n";
		rez+="pr \t\t\tLista svih procesa\n";
		rez+="ri \t\t\tRed izvrsavanja\n";
		rez+="load [naziv]\t\tUcitava file(Proces)\n";
		rez+="mem [naziv]\t\tIspisuje sekundarnu memoriju\n";
		rez+="ram\t\t\tIspisuje ram\n";
		rez+="exe\t\t\tIzvrsava program\n";
		rez+="exit\t\t\tIzlaz\n";
		
		return rez;	
	}
	
	public void komande(String kom) {
		String []niz=kom.split("\\s+");
		
		//String sadr="";
		
		if(niz[0].equals("help"))
			System.out.println(getHelp());
		
		if(niz[0].equals("clear"))
			gore.clear();
		
		if(niz[0].equals("ls"))
			f.ispisiTr();	
		
		if(niz[0].equals(".."))
			f.changeDir("..");
		
		if(niz[0].equals("cd"))
			f.changeDir(niz[1]);
		
		if(niz[0].equals("mkd"))
			f.mkd(niz[1]);
	
		if(niz[0].equals("mkf")) {
			f.mkf(niz[1]);
			sm.sacuvaj(new FajlMemorija(niz[1],"slabsadrzaj".getBytes()));
			
		}
	
		if(niz[0].equals("del")) {
			String[] niz1 = f.getListTren();
			File f1=new File(f.getTrStr()+"\\"+niz[1]);
			sm.brisanjeFajla(new FajlMemorija(niz[1],sm.readFile(f1).getBytes()));
			for(String s1:niz1)
				if(niz[1].equals(s1)) 
					f.del(new File(f.getTrStr()+"\\"+niz[1]));	
		}
		if(niz[0].equals("prior")) 
			rp.prioritetProcesa(Integer.parseInt(niz[1]), Integer.parseInt(niz[2]));
				
		
		if(niz[0].equals("pr"))
			rp.ispisiSveProcese();
		if(niz[0].equals("ri"))
			rp.isipisiRedIzvrsavanja();
		// Loaduje proces
		String zaExe="";
		if(niz[0].equals("load")) {	
			//Iz sek u ram
			
			if(f.uStablu(niz[1])) {
				
				zaExe=f.trenutni.getAbsolutePath()+"\\"+niz[1];
				File ff=new File(f.trenutni.getAbsolutePath()+"\\"+niz[1]);
				FajlMemorija fm=new FajlMemorija(niz[1],sm.readFile(ff).getBytes());
				
				if(bs.imaLiMjesta(fm)) {
					rp.ucitajProces(niz[1],f.trenutni.getAbsolutePath()+"\\"+niz[1]);
					sm.prebacivanje(new FajlMemorija(niz[1],sm.readFile(ff).getBytes()));
				}
				else {
					System.out.println("Nemate mjesta");
				}
			}
			else {
				System.out.println("Taj program se ne nalazi u ovoj datoteci");
				System.out.println(f.trenutni.getAbsolutePath());
			}
		}
		
		if(niz[0].equals("exe")) {
			rp.run();
			//if(!rp.isAlive()) {
				//ovdje treba oslobadjanje
				//File f1=new File(zaExe);
				//bs.oslobadjanje(new FajlMemorija(f1.getName(),sm.readFile(f1).getBytes()));
			//}
		}
		
		if(niz[0].equals("mem")) {
			sm.ispisiBrojSlobodnihBlokova();
			sm.ispisiBrojZauzetihBlokova();
		}
		
		if(niz[0].equals("ram")) {
			bs.getSlobodniBlokovi();
		
		}
		/*	try {
				rp.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		
			
				
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		inp.connect(out);
	
		izlaz = new Button("X");
		izlaz.setPrefSize(5, 5);
		minimize = new Button("_");
		minimize.setPrefSize(5, 5);
		maximize = new Button("Max");

		HBox buttons = new HBox(5);
		buttons.setAlignment(Pos.TOP_RIGHT);

		buttons.getChildren().addAll(minimize, maximize, izlaz);

		gore = new TextArea();
		gore.setMinSize(900, 450);
		gore.setEditable(false);
		gore.setText("Dobrodosli u OS simulator!\n");

		dole = new TextField();
		dole.setMinSize(900, 62);
		
		
		
		izlaz.setOnAction(e -> {
			System.exit(0);
		});

		minimize.setOnAction(e -> {
			Stage stage = (Stage) minimize.getScene().getWindow();
			stage.setIconified(true);
			dole.requestFocus();
		});

		maximize.setOnAction(e -> {
			Stage stage = (Stage) maximize.getScene().getWindow();
			if (!stage.isMaximized())
				stage.setMaximized(true);
			else
				stage.setMaximized(false);
			dole.requestFocus();
		});

		dole.setOnAction(e -> {
			try {
				byte array[] = dole.getText().getBytes();
				out.write(array);
				
				listaKomandi.add(dole.getText());
				iter=listaKomandi.size();
				
				String	str=dole.getText();
				if(str.equals("exit"))
					System.exit(0);
				
				gore.appendText(">" + f.trenutni.getAbsolutePath() + "\n");
				gore.appendText(">" + dole.getText() + "\n");
				dole.clear();
				
				komande(str);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		dole.setOnKeyPressed((e) -> {
			if (e.getCode().equals(KeyCode.UP)) {
				System.err.println("GORE");
				String last = prethodna();
				if (!last.equals("")) {
					dole.setText(last);
					dole.positionCaret(last.length());
				}
				e.consume();
			} else if (e.getCode().equals(KeyCode.DOWN)) {
				String next = iduca();
				if (!next.equals("")) {
					dole.setText(next);
					dole.positionCaret(next.length());
				}
				e.consume();
			}
		});

		outStream = new OutputStream() {
			public void write(int b) throws IOException {
				outStringBuilder.append((char) b);
				if (((char) b) == '\n')
					dodajTextGore();
			}
		};
		
		f.setOut(outStream);
		rp.setOut(outStream);

		VBox root = new VBox(15);
		root.setPadding(new Insets(10, 30, 30, 30));
		root.getChildren().setAll(buttons, gore, dole);
		VBox.setVgrow(gore, Priority.ALWAYS);
		Scene scene = new Scene(root, 1200, 700);
		scene.getStylesheets().add("application.css");


		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();

		dole.requestFocus();
	}
}