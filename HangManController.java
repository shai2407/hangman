import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class HangManController{

	private GraphicsContext gc;
	private HangMan h1;
	private Button[] btns;

	final int letterToLine = 13;
	final String abc = "abcdefghijklmnopqrstuvwxyz";
	final int SIZE = 13;

	final int xTOP = 200;
	final int xWire = 250;
	final int xDiagonal = 380;
	final int xColumn = 450;

	final int yTOP = 10;
	final int yDiagonal = 50;
	final int yWire = 60;
	final int yBottomColumn = 299;

	final int bottomLength = 50; 


	@FXML
	private Label bank;

	@FXML
	private Canvas canv;

	@FXML
	private GridPane grid;

	@FXML
	private Label wd;

	@FXML
	void newGame(ActionEvent event) {
		gc.clearRect(xTOP, yTOP, xColumn, yBottomColumn);
		initialize();
	}


	public void initialize(){
		// initialize variables
		gc = canv.getGraphicsContext2D();
		h1 = new HangMan();
		h1.getRandomeWord();
		update();

		// draw gallows
		gc.strokeLine(xTOP,yTOP,xColumn,yTOP); // top
		gc.strokeLine(xColumn,yTOP,xColumn,yBottomColumn); // body
		gc.strokeLine(xColumn - bottomLength ,yBottomColumn,xColumn + bottomLength,yBottomColumn); // bottom
		gc.strokeLine(xDiagonal,yTOP,xColumn,yDiagonal); // diagonal
		gc.strokeLine(xWire,yTOP,xWire,yWire); // wire
		// create button of each letter
		btns = new Button[abc.length()];
		for(int i = 0 ; i<abc.length();i++) {
			btns[i] = new Button(""+abc.charAt(i));
			btns[i].setStyle("-fx-font-size:21");
			btns[i].setPrefSize(grid.getPrefWidth()/ SIZE ,grid.getPrefHeight()/ SIZE);
			grid.add(btns[i], i%SIZE, i/SIZE);
			btns[i].setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					handleButton(event);
				}
			});
		}

	}

	private void handleButton(ActionEvent event){
		// update guess after choosing a letter
		Button b = (Button)event.getSource();
		char letter = b.getText().charAt(0);
		h1.fillGuess(letter);
		update();
		b.setText("");
		b.setVisible(false);
	}

	private void update(){
		// update guess, bank, and draw the hangman. check if game ends
		wd.setText(h1.getGuess());
		bank.setText(h1.getBank());
		h1.draw(gc);
		if(h1.isFinished() != 0) {
			for(int j = 0 ; j<abc.length();j++) {
				btns[j].setText("");
				btns[j].setVisible(false);
			}
			if(h1.isFinished() == 1) {
				bank.setText("You Won!");
			}
			if(h1.isFinished() == -1) {
				bank.setText("Hanged!" + " word was: " + h1.getWord());
			}
		}
	}
}