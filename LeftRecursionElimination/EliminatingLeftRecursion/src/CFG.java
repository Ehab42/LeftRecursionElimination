
public class CFG {

	String[] setOfVariables;
	String[] setOfTerminals;
	String[] nonEmptyProductions;
	char startVariable;
	
	public CFG(String[] setOfVariables,String[] setOfTerminals,String[] nonEmptyProductions,char startVariable) {
		
		this.setOfVariables = setOfVariables;
		this.setOfTerminals = setOfTerminals;
		this.nonEmptyProductions = nonEmptyProductions;
		this.startVariable = startVariable;
	}
	
	
}
