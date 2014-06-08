package com.klpd.lovetest.util;

 
public class Utility {

	public static int getLoveStrength(String boyName, String girlName) {

		long totalOfMult = boyName.hashCode() * girlName.hashCode();
		totalOfMult = Math.abs(totalOfMult);
		String asciiBoyAndGirl = totalOfMult + "";
		asciiBoyAndGirl = asciiBoyAndGirl.replace("-", "");
		return getOptimizedLengthOfValue(asciiBoyAndGirl);

	}

	public static int getOptimizedLengthOfValue(String asciiCodeOfBoyAndGirl) {
		int total = 0;
		for (int i = 0; i < asciiCodeOfBoyAndGirl.length(); i++) {

			total += Integer.parseInt(asciiCodeOfBoyAndGirl.charAt(i) + "");

			if (total > 100) {
				String str = "" + total;
				int n = Integer.parseInt(str.charAt(2) + "");
				str = str.substring(0, 2);
				total = Integer.parseInt(str) + n;

			}
		}

		return total;
	}

	 
}
