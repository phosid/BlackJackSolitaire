
public class Card {
	String rank; // numbers or faces ex: (10, A, J, 2)
	String suit; // suit of the card ex: (H, D, S, C)
	
	Card(String rankInput, String suitInput){
		rank = rankInput;
		suit = suitInput;
		
	}
	
	public String getDisplayName(){
		return suit + rank;
	}
		
	
}
