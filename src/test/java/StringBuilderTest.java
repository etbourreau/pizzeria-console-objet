package test.java;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class StringBuilderTest {
	
	public static void main(String[] args) {
		
		//StringBuilder
		System.out.println("\n\n\n// StringBuilder //");
		StringBuilder sb = new StringBuilder();
		sb.append("Bonjour").append(" le monde !");
		System.out.println(sb.toString());
		
		
		//Date
		System.out.println("\n\n\n// Date //");
		Calendar c = Calendar.getInstance();
		System.out.println(c.getTimeInMillis());
		System.out.println(c.getTime());
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss EEEE dd MMMM yyyy");
		System.out.println(format.format(c.getTime()));
		
		//ArrayList
		System.out.println("\n\n\n// List //");
		List<objetRandom> objets = new ArrayList<objetRandom>();
		for(int i = 0; i<50; i++){
			objets.add(new objetRandom());
		}
		boolean first = true;
		sb = new StringBuilder();
		for(objetRandom or : objets){
			//draw space between values
			if(first){ first = false; }else{ sb.append(" "); }
			sb.append(or.value);
		}
		System.out.print(sb.toString());
	}
	
	static class objetRandom{
		public int value;
		
		public objetRandom(){
			java.util.Random rd = new java.util.Random();
			value = rd.nextInt(1_000_000);
		}
	}

}
