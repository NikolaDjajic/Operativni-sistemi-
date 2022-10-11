package Demo;

import java.util.ArrayList;
import java.util.Comparator;

public class Test{

	public static void main(String[] args) {

		ArrayList<vr> lista = new ArrayList<vr>();
		lista.add(new vr("ca",2));
		lista.add(new vr("ca1",6));
		lista.add(new vr("ca2",1));
		lista.add(new vr("ca3",0));
		lista.add(new vr("ca4",4));
		lista.add(new vr("ca5",7));
		lista.add(new vr("ca6",4));
		
		Comparator c = new Comparator<vr>() {

			@Override
			public int compare(vr o1, vr o2) {
				if(o1.prioritet<o2.prioritet)
					return -1;
				else if(o1.prioritet>o2.prioritet)
						return 1;
			return 0;
			}
		};
		
		lista.sort(c);
		
		for(vr v:lista)
			System.out.println(v.ime+" "+ v.prioritet);
	
	}
}
