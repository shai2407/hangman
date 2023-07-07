import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.canvas.GraphicsContext;


public class HangMan{

	private String word;
	private int mistakes;
	private String guess;
	private String bank;

	final int NUM_OF_MISTAKES = 9;
	final int NUM_OF_LETTERS = 26;
	GraphicsContext gc;
	FileReader fr;

	HangMan(){
		word = "";
		mistakes = 0;
		guess = "_";
		bank = "";
	}

	//gets a random word from file 
	public void getRandomeWord(){
		// count lines in file
		int lines = 0;
		try{
			Scanner s = new Scanner (new File("myFile.txt"));
			while(s.hasNext())
			{
				lines++;
				s.nextLine();
			}
			s.close();
		}
		catch(IOException e) {
			System.out.println("Error");
		}
		// choose random word from file
		int randomLine = ThreadLocalRandom.current().nextInt(0, lines);
		try{
			Scanner f = new Scanner (new File("myFile.txt"));
			for(int i = 0; i < randomLine; i++)
			{
				f.nextLine();
			}
			word += f.nextLine();
			f.close();
		}catch(IOException e) {
			System.out.println("Error");
		}
		// update guess
		for(int i = 0; i < word.length()-1; i++)
			guess += " _";
	}

	public int getMistakes(){
		return mistakes;
	}

	public String getWord(){
		return word;
	}
	public String getGuess(){
		return guess;
	}

	public String getBank(){
		return bank;
	}

	final int xWire = 250;
	final int yWire = 60;
	final int headRadius = 25;
	final int armLen = 40;
	final int legLen = 30;

	// draw the hangman according to num of mistakes
	public void draw(GraphicsContext gc){
		if(getMistakes() == 1) {
			//draw head
			gc.strokeOval(225, 60, 50, 50);
		}
		if(getMistakes() == 2) {
			//draw body
			gc.strokeLine(xWire,110,xWire,200);
		}
		if(getMistakes() == 3) {
			//draw right arm
			gc.strokeLine(xWire,150,xWire + armLen,190);
		}
		if(getMistakes() == 4) {
			//draw left arm 
			gc.strokeLine(xWire,150,xWire - armLen,190);
		}
		if(getMistakes() == 5) {
			//draw left leg 
			gc.strokeLine(xWire,200,xWire + legLen,260);
		}
		if(getMistakes() == 6) {
			//draw right leg 
			gc.strokeLine(xWire,200,xWire - legLen,260);
		}
		if(getMistakes() == 7) {
			//draw face
			gc.strokeLine(xWire - 10,yWire + 35,xWire + 10,yWire + 35); // mouth			
		}
		if(getMistakes() == 8) {
			// right eye
			gc.strokeLine(xWire + 5, yWire + 12, xWire + 10, yWire + 17);
			gc.strokeLine(xWire + 5, yWire + 17, xWire + 10, yWire + 12);
		}
		if(getMistakes() == 9) {
			// left eye
			gc.strokeLine(xWire - 10, yWire + 12, xWire - 5, yWire + 17);
			gc.strokeLine(xWire - 5, yWire + 12, xWire - 10, yWire + 17);
		}
	}	

	// won - 1, lose - -1, still playing - 0
	public int isFinished(){
		if(getMistakes() == NUM_OF_MISTAKES)
			return -1;
		for(int i = 0; i < guess.length(); i++){
			if(guess.charAt(i) == '_')
				return 0;
		}
		return 1;
	}

	// update guess after choosing a letter
	public void fillGuess(char letter){
		boolean corrLetter = false;
		int fitToGuess = 2;
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i) == letter){
				corrLetter = true;
				guess = guess.substring(0,fitToGuess*i) + letter + guess.substring(fitToGuess*i+1) ;
			}
		}
		if(!corrLetter){
			bank += " " + letter;
			mistakes++;
		}
	}
}