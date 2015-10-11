package test;

import java.math.BigInteger;
import java.util.Random;

/**
 * result:
 * 1654591337368911640128593748203215386720970120252520089602335933244413366225468017652935449265871
 * *
 * 2
 * =
 * 3309182674737823280257187496406430773441940240505040179204671866488826732450936035305870898531743
 * @author Daniel
 *
 */
public class TotientGen {
	public static void main(String[] args) {
		int i = 0;
		Random r = new Random(1234);
		while (true) {
			BigInteger p = BigInteger.valueOf(2);
			BigInteger q = BigInteger.probablePrime(320, r);
			BigInteger n1 = p.multiply(q).add(BigInteger.ONE);
			if (n1.isProbablePrime(20)) {
				System.out.println(n1 + "!!!1");
				System.out.println(p);
				System.out.println(q);
				break;
			} else {
				if (i % 1000 == 0) {
					System.out.println(i + "\t\tNOOO " + n1);
				}
			}
			i++;
		}
	}
}
