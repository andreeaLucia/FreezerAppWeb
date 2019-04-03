package freezer.service;

import java.util.Arrays;
import java.util.Vector;

public class Main {
	public static void main(String[] args) {
		int[] nums = {1,4,5};
				
		//System.out.println(removeDuplicates(nums));
		int [] digits = {8, 5, 3};
		
		//System.out.println(Arrays.toString(plusOne(digits)));
		System.out.println(canConstruct("aa", "aab"));
	}
	

	public static int removeDuplicates(int[] nums) {
		if (nums.length == 0) return 0;
		int i = 0;
		for (int j = 1; j < nums.length; j++) {
			if (nums[j] != nums[i]) {
				i++;
				nums[i] = nums[j];
			}
		}
		return i + 1;
	}
	
	public static int[] plusOne(int[] digits) {
		int[] newDigits = new int[digits.length + 1];
		int digit;
		int integer = 0;
		int c = 0;
		int contor = 0;
		for(int i = 0; i < digits.length; i++) {
			integer = 10 * integer + digits[i];
			contor++;
		}
		int number = integer + 1;
		while(number > 0) {
		    digit = number % 10;
			number = number / 10;
			newDigits[c++] = digit;			
		}
		
		return newDigits;
	}

	
	public static boolean canConstruct(String ransomNote, String magazine) {

		for(int i = 0; i < ransomNote.length(); i++) {
			char c = ransomNote.charAt(i);
			if(magazine.contains(c+"")) {
				magazine = magazine.replaceFirst(c+"","");
			}
			else {
				return false;
			}
		}
		return true;
	}	
}
