import java.util.ArrayList;

public class ElimateLeftRecursion {

	public static void main(String[] args) {

		/*
		 * Notes: -CFG input format is {"A,B,C,D"} meaning (A->B|C|D). -The String "eps"
		 * means "epsilon". -To test the testcases just uncomment each testCase alone
		 * for duplicate variables, then run the code and the output will be printed in
		 * the console.
		 */

		// *TestCase 1 (Lecture '3' slide '35')*
		// String[] setOfVariables = { "A", "S" };
		// String[] setOfTerminals = { "a", "b", "c", "d " };
		// String[] nonEmptyProductions = { "S,Aa,b", "A,Ac,Sd,eps" };
		// char startVariable = 'S';
		//
		// CFG C = new CFG(setOfVariables, setOfTerminals, nonEmptyProductions,
		// startVariable);
		// eliminateLeftRecursion(C);

		// *TestCase 2 (Practice Assignment problem 4-1 "a")*
		// String[] setOfVariables = { "S" };
		// String[] setOfTerminals = { "a", "b" };
		// String[] nonEmptyProductions = { "S,Sa,b" };
		// char startVariable = 'S';
		//
		// CFG C = new CFG(setOfVariables, setOfTerminals, nonEmptyProductions,
		// startVariable);
		// eliminateLeftRecursion(C);

		// *TestCase 3 (Practice Assignment problem 4-1 "b")*
		// String[] setOfVariables = { "S" };
		// String[] setOfTerminals = { "a", "b", "c", "d" };
		// String[] nonEmptyProductions = { "S,Sab,cd" };
		// char startVariable = 'S';
		//
		// CFG C = new CFG(setOfVariables, setOfTerminals, nonEmptyProductions,
		// startVariable);
		// eliminateLeftRecursion(C);

		// *TestCase 4 (Practice Assignment problem 4-1 "c")*
		// String[] setOfVariables = { "S" };
		// String[] setOfTerminals = { "u", "*", "(", ")", "a" };
		// String[] nonEmptyProductions = { "S,SuS,SS,S*,(S),a" };
		// char startVariable = 'S';
		//
		// CFG C = new CFG(setOfVariables, setOfTerminals, nonEmptyProductions,
		// startVariable);
		// eliminateLeftRecursion(C);

		// *TestCase 6 (Practice Assignment problem 4-1 "e")*
		// String[] setOfVariables = { "A", "T" };
		// String[] setOfTerminals = { "0", "1" };
		// String[] nonEmptyProductions = { "A,0,T1", "T,1,A0" };
		// char startVariable = 'A';
		//
		// CFG C = new CFG(setOfVariables, setOfTerminals, nonEmptyProductions,
		// startVariable);
		// eliminateLeftRecursion(C);

		// *TestCase 7 (Practice Assignment problem 4-1 "f ")*
		// String[] setOfVariables = { "A", "B" , "C" };
		// String[] setOfTerminals = { "a", "b" };
		// String[] nonEmptyProductions = { "A,BC" , "B,Bb,eps" , "C,AC,a" };
		// char startVariable = 'A';
		//
		// CFG C = new CFG(setOfVariables, setOfTerminals, nonEmptyProductions,
		// startVariable);
		//
		// eliminateLeftRecursion(C);

	}

	public static void eliminateLeftRecursion(CFG C) {

		System.out.println("* Original CFG *");
		for (int i = 0; i < C.nonEmptyProductions.length; i++) {
			System.out.println(C.nonEmptyProductions[i]);
		}

		C = eliminateEpsProductions(C);

		ArrayList<String> nonTerminals = new ArrayList<>();
		ArrayList<String> nonEmptyProdsUpdate = new ArrayList<>();

		for (int i = 0; i < C.nonEmptyProductions.length; i++) {
			nonEmptyProdsUpdate.add(C.nonEmptyProductions[i]);
		}

		if ((C.nonEmptyProductions.length == 1) && (checkImmediateLeftRecursion(C.nonEmptyProductions[0]) == true)) {
			String[] newRules = eliminateDirectLeftRecursion(C.nonEmptyProductions[0]);
			nonEmptyProdsUpdate.clear();
			nonEmptyProdsUpdate.add(newRules[0]);
			nonEmptyProdsUpdate.add(newRules[1]);
		}

		else {

			// inserting all non terminals in an arraylist called "nonTerminal"
			for (int i = 0; i < C.nonEmptyProductions.length; i++) {
				nonTerminals.add((C.nonEmptyProductions[i].charAt(0) + ""));
			}

			for (int i = 0; i < nonTerminals.size(); i++) {
				for (int j = 0; j < i; j++) {
					String Ai = nonTerminals.get(i);
					String Aj = nonTerminals.get(j);
					String[] AiRule = {};
					String[] AjRule = {};
					String temp = "";

					// saving the rule of 'Aj'
					for (int k = 0; k < C.nonEmptyProductions.length; k++) {
						if ((C.nonEmptyProductions[k].charAt(0) + "").equals(Aj)
								&& (C.nonEmptyProductions[k].charAt(1)) == ',') {
							AjRule = C.nonEmptyProductions[k].split(",");
						}

						// saving the rule of 'Ai'
						if ((C.nonEmptyProductions[k].charAt(0) + "").equals(Ai)
								&& (C.nonEmptyProductions[k].charAt(1)) == ',') {
							AiRule = C.nonEmptyProductions[k].split(",");

							for (int l = 1; l < AiRule.length; l++) {
								if ((AiRule[l].charAt(0) + "").equals(Aj)) {
									String product = AiRule[l].substring(1, AiRule[l].length());

									for (int m = 1; m < AjRule.length; m++) {
										if (m == AjRule.length - 1) {
											temp = temp + AjRule[m] + product;
										} else {
											temp = temp + AjRule[m] + product + ",";
										}
									}

									AiRule[l] = temp;
								}

							}

							String newRule = "";
							for (int l = 0; l < AiRule.length; l++) {
								if (l == AiRule.length - 1) {
									newRule = newRule + AiRule[l];
								} else {
									newRule = newRule + AiRule[l] + ",";
								}
							}

							C.nonEmptyProductions[k] = newRule;

							if (checkImmediateLeftRecursion(C.nonEmptyProductions[k]) == true) {
								String[] newRules = eliminateDirectLeftRecursion(C.nonEmptyProductions[k]);
								nonEmptyProdsUpdate.remove(k);
								nonEmptyProdsUpdate.add(k, newRules[0]);
								nonEmptyProdsUpdate.add(k + 1, newRules[1]);
								k++;

							} else {
								nonEmptyProdsUpdate.remove(k);
								nonEmptyProdsUpdate.add(k, newRule);
							}

							C.nonEmptyProductions = new String[nonEmptyProdsUpdate.size()];
							for (int c = 0; c < C.nonEmptyProductions.length; c++) {
								C.nonEmptyProductions[c] = nonEmptyProdsUpdate.get(c);
							}
						}

					}
				}
			}

		}

		System.out.println();
		System.out.println("* CFG after LeftRecursionElimination *");
		for (int i = 0; i < nonEmptyProdsUpdate.size(); i++) {
			System.out.println(nonEmptyProdsUpdate.get(i));
		}
	}

	public static boolean checkImmediateLeftRecursion(String Rules) {
		String[] rules = Rules.split(",");
		String startVariable = rules[0];
		boolean isLeftRecursive = false;

		for (int i = 1; i < rules.length; i++) {
			char leftMost = rules[i].charAt(0);

			if ((leftMost + "").equals(startVariable)) {
				isLeftRecursive = true;
			}
		}

		return isLeftRecursive;

	}

	public static String[] eliminateDirectLeftRecursion(String rule) {

		String[] ruleAnatomy = rule.split(",");
		ArrayList<String> alpha = new ArrayList<>();
		ArrayList<String> beta = new ArrayList<>();

		// seperate alphas from betas
		for (int i = 1; i < ruleAnatomy.length; i++) {

			String leftMost = ruleAnatomy[i].charAt(0) + "";
			if (leftMost.equals(ruleAnatomy[0])) {
				String alph = ruleAnatomy[i].substring(1, ruleAnatomy[i].length());
				alpha.add(alph);
			}

			else {
				beta.add(ruleAnatomy[i]);
			}

		}

		// initializing new variables and new rules
		String newVariable = ruleAnatomy[0] + "'";
		ArrayList<String> newRule1 = new ArrayList<>();
		ArrayList<String> newRule2 = new ArrayList<>();
		newRule1.add(ruleAnatomy[0] + "");
		newRule2.add(newVariable);

		// Creating new rules after eliminating DLR
		for (int i = 0; i < beta.size(); i++) {
			newRule1.add(beta.get(i) + newVariable);
		}

		for (int i = 0; i < alpha.size(); i++) {
			newRule2.add(alpha.get(i) + newVariable);
		}

		newRule2.add("eps");

		// Converting to String
		String Rule1 = "";
		for (int i = 0; i < newRule1.size(); i++) {
			if (Rule1.isEmpty()) {
				Rule1 = Rule1 + newRule1.get(i);
			} else {
				Rule1 = Rule1 + "," + newRule1.get(i);
			}

		}

		String Rule2 = "";
		for (int i = 0; i < newRule2.size(); i++) {
			if (Rule2.isEmpty()) {
				Rule2 = Rule2 + newRule2.get(i);
			} else {
				Rule2 = Rule2 + "," + newRule2.get(i);
			}

		}

		String[] newRules = { Rule1, Rule2 };
		return newRules;

	}

	public static CFG eliminateEpsProductions(CFG C) {

		ArrayList<String> nonProdsUpdate = new ArrayList<>();
		for (int i = 0; i < C.nonEmptyProductions.length; i++) {
			nonProdsUpdate.add(C.nonEmptyProductions[i]);
		}

		for (int i = 0; i < C.nonEmptyProductions.length; i++) {
			String[] ruleAnatomy = C.nonEmptyProductions[i].split(",");
			String[] ruleAnatomy1 = {};
			String rule = "";
			if ((ruleAnatomy[(ruleAnatomy.length) - 1]).equals("eps")) {
				String currentNonTerminal = ruleAnatomy[0];
				for (int j = 0; j < C.nonEmptyProductions.length; j++) {
					ruleAnatomy1 = C.nonEmptyProductions[j].split(",");
					for (int k = 1; k < ruleAnatomy1.length; k++) {
						StringBuilder sb = new StringBuilder(ruleAnatomy1[k]);

						for (int l = 0; l < sb.length(); l++) {
							if ((sb.charAt(l) + "").equals(currentNonTerminal)) {
								sb = sb.deleteCharAt(l);
								ruleAnatomy1[k] = ruleAnatomy1[k] + "," + new String(sb);

								rule = "";
								for (int l2 = 0; l2 < (ruleAnatomy1.length); l2++) {
									if (l2 == (ruleAnatomy1.length) - 1) {
										rule = rule + ruleAnatomy1[l2];
									} else {
										rule = rule + ruleAnatomy1[l2] + ",";
									}
								}

								nonProdsUpdate.remove(j);
								nonProdsUpdate.add(j, rule);
								C.nonEmptyProductions[j] = rule;

							}
						}

					}

				}
			}
		}

		for (int i = 0; i < C.nonEmptyProductions.length; i++) {
			String[] rule = C.nonEmptyProductions[i].split(",");
			if ((rule[rule.length - 1]).equals("eps")) {
				rule[rule.length - 1] = "";

				C.nonEmptyProductions[i] = "";
				for (int j = 0; j < rule.length - 1; j++) {
					if (j == rule.length - 2) {
						C.nonEmptyProductions[i] = C.nonEmptyProductions[i] + rule[j];
					} else {
						C.nonEmptyProductions[i] = C.nonEmptyProductions[i] + rule[j] + ",";
					}
				}
			}
		}

		CFG newC = new CFG(C.setOfVariables, C.setOfTerminals, C.nonEmptyProductions, C.startVariable);

		return newC;

	}
}
