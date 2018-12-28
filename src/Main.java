/**
 * This program was used to calculate a first estimation of ratings based on the first three Ködem tournaments.
 * The program tries to find ratings so that no player significantly over or under performs with respect to those ratings.
 * This is not guaranteed to converge however it provides acceptable results for the given data set.
 *
 */

public class Main {

	private static double[] ratings = { 2076, 2225, 2280, 2087, 2032, 1911, 1338, 700, 2276, 2168, 1670, 1059, 1914, 1501, 1026, 1865, 1836, 1609, 1905 };
	// DWZ ratings at the time
	private static final double avg = (ratings[0] + ratings[1] + ratings[2] + ratings[3] + ratings[4] + ratings[5] + ratings[6] + ratings[7] + ratings[8] + ratings[9]
	                                   + ratings[10] + ratings[11] + ratings[12] + ratings[13] + ratings[14] + ratings[15] + ratings[16] + ratings[17] + ratings[18]) / 19;
	private static double[] glickoTwo = new double[ratings.length];
	private static double[] score = new double[ratings.length];
	private static double[] points = new double[ratings.length];
	private static String[] names = { "Kolja", "Maxi", "Adrian", "Julian", "Joel", "Marcus", "Hans", "Jannik", "Thilo",
	                                  "Fabian", "Andrei", "Laszlo", "Patrik", "Andreas", "Lukas", "Simon", "Samuel", "Maria", "Marlon" };

	private static double ACCURACY = 0.1; // gets changed locally currently

	private static Result[] result = new Result[88];
	private static double g = 0.0;

	private static void clear() {
		for (int i = 0; i < glickoTwo.length; i++) {
			glickoTwo[i] = 0.0;
			points[i] = 0.0;
		}
	}

	private static void glickoRating() {
		if (ratings.length != glickoTwo.length || ratings.length != score.length || ratings.length != names.length) {
			System.out.println("Error");
			System.exit(1);
		} else {
			for (int i = 0; i < ratings.length; i++) {
				glickoTwo[i] = (ratings[i] - 1500) / 173.7178;
			}
		}
	}

	private static void print() {
		for (int i = 0; i < ratings.length; i++) {
			System.out.println(names[i] + " , rating : " + ratings[i] + " , expected points: " + points[i] + " , overperformance: " + (score[i] - points[i]));
		}
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println(avg);
		results();
		calculate();
		print();
		iterative();
		print();
	}

	private static void calculate() {
		glickoRating();
		double RD = 100.0 / 173.7178;
		g = 1 / Math.sqrt(1 + 3 * RD * RD / (Math.PI * Math.PI));
		for (Result result1 : result) {
			double diff = (glickoTwo[result1.winnerOne] + glickoTwo[result1.winnerTwo] - (glickoTwo[result1.loserOne] + glickoTwo[result1.loserTwo]));
			points[result1.winnerOne] += 1 / (1 + Math.exp(-g * (diff)));
			points[result1.winnerTwo] += 1 / (1 + Math.exp(-g * (diff)));
			points[result1.loserOne] += 1 / (1 + Math.exp(-g * (-diff)));
			points[result1.loserTwo] += 1 / (1 + Math.exp(-g * (-diff)));
		}
	}

	private static void results() {
		// Erste Ködem-Meisterschaft
		result[0] = new Result(4, 0, 7, 6);
		result[1] = new Result(2, 5, 3, 1);
		result[2] = new Result(3, 0, 4, 5);
		result[3] = new Result(1, 2, 6, 7);
		result[4] = new Result(0, 3, 7, 2);
		result[5] = new Result(4, 1, 6, 5);
		result[6] = new Result(4, 1, 6, 7);
		result[7] = new Result(3, 0, 5, 2);
		result[8] = new Result(0, 1, 5, 4);
		result[9] = new Result(2, 6, 3, 7);
		result[10] = new Result(3, 4, 0, 7);
		result[11] = new Result(1, 5, 2, 6);
		result[12] = new Result(0, 6, 4, 7);
		result[13] = new Result(1, 2, 3, 5);
		result[14] = new Result(0, 1, 5, 6);
		result[15] = new Result(2, 3, 7, 4);
		result[16] = new Result(5, 2, 0, 6);
		result[17] = new Result(1, 4, 7, 3);
		result[18] = new Result(0, 2, 4, 1);
		result[19] = new Result(3, 6, 5, 7);
		// Erste Ködem-WM, Vorrunde
		result[20] = new Result(8, 1, 14, 7);
		result[21] = new Result(9, 2, 6, 11);
		result[22] = new Result(0, 10, 13, 12);
		result[23] = new Result(1, 8, 6, 11);
		result[24] = new Result(12, 13, 7, 14);
		result[25] = new Result(0, 10, 9, 2);
		result[26] = new Result(1, 8, 13, 12);
		result[27] = new Result(0, 10, 6, 11);
		result[28] = new Result(2, 9, 7, 14);
		result[29] = new Result(1, 8, 10, 0);
		result[30] = new Result(2, 9, 12, 13);
		result[31] = new Result(11, 6, 7, 14);
		result[32] = new Result(1, 8, 2, 9);
		result[33] = new Result(0, 10, 14, 7);
		result[34] = new Result(13, 12, 6, 11);
		// Erste Ködem-WM, VF
		result[35] = new Result(11, 6, 12, 13);
		result[36] = new Result(6, 11, 13, 12);
		result[37] = new Result(13, 12, 6, 11);
		result[38] = new Result(2, 9, 14, 7);
		result[39] = new Result(2, 9, 7, 14);
		result[40] = new Result(9, 2, 7, 14);
		// HF
		result[41] = new Result(8, 1, 11, 6);
		result[42] = new Result(8, 1, 6, 11);
		result[43] = new Result(1, 8, 6, 11);
		result[44] = new Result(2, 9, 10, 0);
		result[45] = new Result(2, 9, 0, 10);
		result[46] = new Result(0, 10, 9, 2);
		// Spiel um Platz 5, um Platz 3, Finale
		result[47] = new Result(12, 13, 7, 14);
		result[48] = new Result(12, 13, 14, 7);
		result[49] = new Result(12, 13, 14, 7);
		result[50] = new Result(10, 0, 11, 6);
		result[51] = new Result(10, 0, 11, 6);
		result[52] = new Result(0, 10, 11, 6);
		result[53] = new Result(8, 1, 2, 9);
		result[54] = new Result(1, 8, 2, 9);
		result[55] = new Result(9, 2, 1, 8);
		result[56] = new Result(9, 2, 8, 1);
		result[57] = new Result(8, 1, 2, 9);

		// Ködem-Open 2018
		/**/
		result[58] = new Result(1, 11, 12, 17);
		result[59] = new Result(16, 15, 18, 10);
		result[60] = new Result(1, 17, 15, 18);
		result[61] = new Result(10, 16, 12, 11);
		result[62] = new Result(16, 18, 1, 10);
		result[63] = new Result(15, 12, 11, 17);
		result[64] = new Result(1, 15, 16, 12);
		result[65] = new Result(11, 18, 10, 17);
		result[66] = new Result(1, 16, 11, 15);
		result[67] = new Result(12, 10, 18, 17);
		result[68] = new Result(1, 18, 16, 17);
		result[69] = new Result(12, 15, 10, 11);
		result[70] = new Result(12, 1, 17, 18);
		result[71] = new Result(10, 15, 11, 16);
		result[72] = new Result(1, 17, 11, 10);
		result[73] = new Result(15, 16, 18, 12);
		result[74] = new Result(10, 18, 11, 1);
		result[75] = new Result(16, 12, 15, 17);
		result[76] = new Result(16, 10, 12, 17);
		result[77] = new Result(18, 1, 15, 11);
		result[78] = new Result(15, 1, 10, 12);
		result[79] = new Result(11, 16, 17, 18);
		result[80] = new Result(1, 10, 12, 18);
		result[81] = new Result(17, 16, 11, 15);
		result[82] = new Result(16, 1, 11, 17);
		result[83] = new Result(10, 12, 15, 18);
		result[84] = new Result(1, 12, 16, 10);
		result[85] = new Result(15, 17, 18, 11);
		result[86] = new Result(16, 18, 1, 17);
		result[87] = new Result(10, 15, 11, 12);

		for (int i = 0; i < score.length; i++) {
			score[i] = 0;
			points[i] = 0;
			glickoTwo[i] = 0;
		}
		for (Result result1 : result) {
			score[result1.winnerOne]++;
			score[result1.winnerTwo]++;
		}
	}

	private static void iterative() {
		for (double acc = 1; acc <= 50; acc++) { // slowly increase accuracy
			ACCURACY = 10.0 / acc;
			System.out.println("Acc = " + ACCURACY);
			for (int j = 0; j < 10000; j++) {
				clear();
				calculate();
				for (int i = 0; i < ratings.length; i++) {
					if (score[i] - points[i] > ACCURACY) { // we only update ratings if players significantly over/ underperform as otherwise this might not converge
						ratings[i] += Math.random() * 1;
					} else if (score[i] - points[i] < -ACCURACY) {
						ratings[i] -= Math.random() * 1;
					}
				}
			}
			print();
		}
	}
}
