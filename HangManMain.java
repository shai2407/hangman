import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HangManMain extends Application{ 

	public void start(Stage stage) throws Exception{ 
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("HangMan.fxml")); 
		Scene scene = new Scene(root); 
		stage.setTitle("HangManMain"); 
		stage.setScene(scene); 
		stage.show(); 
	} 

	public static void main(String[] args){ 
		launch(args); 
		System.out.println();
	} 
}