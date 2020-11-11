import java.util.Arrays;
import java.util.Collections;

public class Deck {
	
	Card[] deck = new Card[52]; // Array that holds all the cards randomly
	int topCardIndex = 51;

	
	public void newDeck() {
		
		// Creates all of the cards and puts them in the deck
		String[] suits = new String[] {"H", "D", "S", "C"};
		String[] ranks = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		
		int index = 0;
		for(String suit : suits) {
			for(String rank : ranks) {
				Card card = new Card(suit, rank);
				deck[index] = card;
				index++;
			}
		}
		
		// Shuffles the array of cards as a list using Collections shuffle method
		Collections.shuffle(Arrays.asList(deck));

	}
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		deck.newDeck();
		
	}
	
	public Card drawCard() {
		
		// Gets the card from the "top of the deck" and lowers the number of drawable cards
		Card card = deck[topCardIndex];
		topCardIndex--;
		
		return card;
		
	}
	
		
}

