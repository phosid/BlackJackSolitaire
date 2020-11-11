import java.util.Scanner;

public class BlackjackSolitaire {
	
	private String[][] board;
	private Deck deck;
	private String[] discard;
	private int discardsUsed = 0;
	private int openSlots = 16;
	Scanner input;


	
	// Initializes board, deck, discard pile, and other class variables
	// Begins the game
	public void play() {
		board = createNewBoard();
		discard = new String[4];
		deck = new Deck();
		deck.newDeck();
		input = new Scanner(System.in);
		startTurns();
		
	}
	
	
	// Creates and sets a new empty board
	// With Columns 1, 2, 3, 4, 5 with corresponding number of Rows 2, 4, 4, 4, 2
	private String[][] createNewBoard() {
		
		String[][] board = new String[][]{ 
			new String[]{"1", "6"}, 
			new String[]{"2", "7", "11", "14"}, 
			new String[]{"3", "8", "12", "15"}, 
			new String[]{"4", "9", "13", "16"}, 
			new String[]{"5", "10"}
		};
		return board;
	}
	
	// Loop function that processes each turn of the game
	//   Ends when there are no open slots left
	private void startTurns(){
		System.out.println("Welcome to Blackjack Solitaire!");
		displayBoard();

		while(openSlots > 0) {
			// Draw a card
			Card card = deck.drawCard();
			System.out.println();
			System.out.println("You drew: " + card.getDisplayName());
			
			
			// While loops, keeps asking user to enter input until it is valid
			boolean moveComplete = false;
			
			while(!moveComplete) {
				// Asks user to enter a prompt
				String move = promptUser();

				// Ask user to enter a space to place card, then plays the card
				if(move.toUpperCase().equals("P")) {
					moveComplete = playCard(card);
				}
				
				// Add card to discard pile
				if(move.toUpperCase().equals("D")) {
					moveComplete = discardCard(card);
				}
			}

			// Displays the board at the end of the turn
			displayBoard();
			
			// Displays the discarded cards in a line at the end of the turn
			if(discardsUsed > 1) {
				System.out.print("Discarded Cards: ");
				for(String x : discard) {
					if(x != null) {
						System.out.print(" " + x + " ");
					}
				}
				System.out.println();
			}

		}
		
		// Game finished message
		// Prints score
		System.out.println("The game has finished!");
	    int score = getScore(board);
		System.out.println("Your score is: " + score);
	}
	
	
	public String promptUser() {
		// Ask user to play or discard
		if(discardsUsed < 4) {
			System.out.println("Enter 'P' to play card  or 'D' to discard:");
		}
		else {
			System.out.println("Enter 'P' to play card:");
		}
		
		String move = input.nextLine();
		
		return move;
	}
	
	
	public boolean playCard(Card card){
		boolean success = false;
		
		// Place the card using the location
		System.out.println("Enter the number to place your card that position:");
		String inputPosition = input.nextLine();
		
		// Finds the board slot that has the number that matches the
		//    user input and places the card display name there
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if (board[i][j].equals(inputPosition)){
					board[i][j] = card.getDisplayName();
					success = true;
				}
			}
		}
		
		if(!success) {
			System.out.println("Please enter a valid position");
		}

		// Decrease the number of available open slots
		openSlots--;
		
		return success;
	}
	

	public boolean discardCard(Card card) {
		boolean success = false;

		// Check if there are open discard slots and discards the card
		if(discardsUsed < 4) {
			// Places card in the discard pile
			discard[discardsUsed] = card.getDisplayName();
			discardsUsed++;
			success = true;
		}
		// Handles if there are no open discart slots
		else {
			System.out.println("All discards used, invalid option");
			success = false;
		}
		
		return success;
	}
	
	
	private void displayBoard(){
		System.out.println("Current Board: ");

		// loop through the rows of the board
		for(int i = 0; i < 4; i++) {
			
			// For each column print out the row value that has index 'i'
			//   check if its exists first to handle variable length columns
			for(int j = 0; j < 5; j++) {
				
				if(board[j].length > i) {
					// Prints each card on the board
					System.out.print(" " + board[j][i] + " ");
				}
				else {
					// Spacer
					System.out.print("  ");
				}
			}
			// Whitespace
			System.out.println();
			
		}
		// Whitespace
		System.out.println();
	}
	
	
	// Calculates the score - pass in a completed board
	public int getScore(String[][] board){

		int score = 0;
		
		// Iterate over columns
		for(int i = 0; i < board.length; i++) {
			score += calculateSubTotal(board[i]);
		}
		
		// Iterate over rows
		for(int i = 0; i < 4; i++) {
			String[] rowArray = new String[5];
			for(int j = 0; j < 5; j++) {
				if(board[j].length > i) {
					rowArray[j] = board[j][i];
				}
			}
			score += calculateSubTotal(rowArray);
		}
		
		return score;
	}
	
	
	public int calculateSubTotal(String[] arr) {
				
		// Keeps track of aces that have a special scoring rule
		int[] acePositions = new int[4];
		int numberOfAces = 0;
		
		int[] cardPointsArray = new int[arr.length];
		
		// Loops through all the card in the row or column 
		for(int i = 0; i < arr.length ; i++) {
			// Empty check single rows are irregularly sized
			if(arr[i] != null) {
				int cardPoints = getCardPoints(arr[i]);
				
				if (cardPoints == 11) {
					acePositions[numberOfAces] = i;
					numberOfAces++;			
				}
				cardPointsArray[i] = cardPoints;
			}
				
		}
		
		int total = 0; 
		int score = 0;
		
		// handle if there are aces, check if 1 or 11 will give a better score and use that
		if (numberOfAces > 0) {
			// for every ace there is in the set
			for(int k = 0; k < numberOfAces; k++) {
				
				// Get the total where Ace is 11
				for(int j = 0; j < cardPointsArray.length; j++) {
					total += cardPointsArray[j];
				}
				
				// Get the total where Ace is 1
				int index = acePositions[k];
				cardPointsArray[index] = 1;
				
				int secondTotal = 0;
				for(int j = 0; j < cardPointsArray.length; j++) {
					secondTotal += cardPointsArray[j];
				}
				
				// Gets the game score from both the point totals
				score = getScoreFromTotal(total, arr.length);
				int secondScore = getScoreFromTotal(secondTotal, arr.length);		
				
				// Return the better score
				if(score > secondScore) {
					return score;
				}
				else {
					return secondScore;
				}
			}
		}
		
		else {
			// Simply passes the total to the scoring function if there are no Aces
			for(int j = 0; j < cardPointsArray.length; j++) {
				total += cardPointsArray[j];
			}
			score = getScoreFromTotal(total, arr.length);
		}
	
		return score;
		
	}
	
	// Gets the game score earned from the card point totals
	// Based on the games rules
	private int getScoreFromTotal(int total, int length) {
		
		// More Than 21 - 0 Points
		if(total > 21) {
			return 0;
		}
		// 16 Or Less - 1 Point
		if(total < 17) {
			return 1;
		}
		// Blackjack - 21 Points
		if(total == 21 & length == 2) {
			return 10;
		}

		// Others
		if(total == 21 & length > 2) {
		return 7;
	}
		if(total == 20) {
			return 5;
		}
		if(total == 19) {
			return 4;
		}
		if(total == 18) {
			return 3;
		}
		if(total == 17) {
			return 2;
		}
		return 0;
	}


	public int getCardPoints(String cardName) {
		
		// Removes the suit (last character), to get the rank of the card
		String rank = cardName.substring(0, cardName.length() - 1);
		
		// King, Queen, Jack - 10 points
		if(rank.equals("K") || rank.equals("Q") ||rank.equals("J")) {
			return 10;
		}
		
		// Ace can either be 1 or 11
		// This will be handled wherever this function is implemented
		if(rank.equals("A")) {
			return 11;
		}
		
		// Return the primitive int value of the number string
		else {
			return Integer.parseInt(rank);
		}

		
	}

}
