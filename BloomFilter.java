package hui;

import java.util.Arrays;
import java.util.Random;

/**
 * ? Bloom Filter is to hash a set of elements to a bit array, k is the hash
 * function numbers for each element e, hash it into k bits, set them all to 1.
 * look up: also hash into, then check whether all k bits are 1 I need firstly
 * look up whether this element is already at this bitmap, is not, I COULD hash
 * into the bitmap need to be initial as 0; if e is found duplicate, ignore it
 * 
 * stream->flow->element
 * 
 * @author Harry Sun
 *
 */

public class BloomFilter {
	int[] bitmap = new int[Configuration.m];// from 0-m the bitmap need to be initial as 0; as Prof. said in class
	int[] khashfunctions = new int[Configuration.k]; // The number of hash function is defined by Configuration.
	int[] elementsToEncoded = new int[Configuration.numOfElement];

	int foundedNumber;

	public int[] generateRandomNumber(int[] x, int n) {
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			x[i] = Math.abs(rand.nextInt());
		}
		return x;
	}

	public void generateElement() {
		foundedNumber = 0;
		generateRandomNumber(khashfunctions, Configuration.k);
		generateRandomNumber(elementsToEncoded, Configuration.numOfElement);

	}

	public void encodeElements() {
		for (int i :elementsToEncoded) {
			encode(i);
			
		}
		
	}
	public void encode(int element) { 
		int[] kHashValue = new int[Configuration.k];	
		for(int i =0;i<Configuration.k;i++) { 
			kHashValue[i] = (khashfunctions[i] ^element) % Configuration.m;
		}
		for(int i = 0; i < Configuration.k;i++) {
				
			if(isAlreadyAt(element)) //when insert, you need first find whether the element is already at the bitmap
				return; 
			for (int j:kHashValue) {//not at
				if (bitmap[j] == 0)
					bitmap[j] = 1;
			}
			break; 
		}
		
	}


	public void lookupElements() {
		for (int i : elementsToEncoded) {
			if (isAlreadyAt(i))
				foundedNumber++;
		}
	}

	public boolean isAlreadyAt(int element) {
		int[] kHashValue = new int[Configuration.k];
		for (int i = 0; i < Configuration.k; i++) {
			kHashValue[i] = (khashfunctions[i] ^ element) % Configuration.m;
		}

		for (int i : kHashValue) {
			if (bitmap[i] == 1) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		//Configuration.m = Integer.parseInt(args[0]);// bitmap size 10000
		//Configuration.numOfElement = Integer.parseInt(args[1]);// need to encoded number 1000
		//Configuration.k = Integer.parseInt(args[2]);// 7

		//here I write a hard code for convenience
		Configuration.m = 10000;
		Configuration.numOfElement = 1000;
		Configuration.k = 7;
		BloomFilter bf = new BloomFilter();// k hash functions has been generate
		bf.generateElement();
		bf.encodeElements();
		bf.lookupElements();
		System.out.println("The number of elements found in A is:" + bf.foundedNumber);

		bf.generateElement();
		bf.lookupElements();// kind of like false-positive
		System.out.println("The number of elements found in B is:" + bf.foundedNumber);

	}

}
