import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	public void start(Stage primaryStage) throws Exception {
		
		//set up scene and layout.
		VBox root = new VBox();
		Scene scene = new Scene(root, 300, 600);
		
		//prepare stage's title and scene. then show it.
		primaryStage.setTitle("Clipboard Cache");
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
