import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.japierre.ClipboardCache.entity.Model;

public class Main extends Application {
	
	Model model;
	
	//set up scene and layout.
	VBox setupRoot = new VBox(8);
	VBox cacheRoot = new VBox();
	
	//make view for setup
	Scene setupView = new Scene(setupRoot, 400, 100);
	Label label = new Label("Number of elements in cache:");
	Label errorMessage = new Label("Please input a number.");
	TextField field = new TextField();
	HBox labelTextfieldContainer = new HBox(8);
	
	//make view for cache
	Scene cacheView = new Scene(cacheRoot, 300, 600);
	
	public void start(Stage primaryStage) throws Exception {
		
		//first start making the setup view. give it the field and label
		Button button = new Button("Confirm");
		labelTextfieldContainer.getChildren().add(label);
		labelTextfieldContainer.getChildren().add(field);
		labelTextfieldContainer.setHgrow(field, Priority.ALWAYS);
		
		setupRoot.getChildren().add(labelTextfieldContainer);
		setupRoot.getChildren().add(button);
		setupRoot.getChildren().add(errorMessage);
		setupRoot.setAlignment(Pos.CENTER);
		
		//shouldn't see error message yet.
		errorMessage.setVisible(false);
		
		//give the button an event handler
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			//when the button is pressed, switch to the other view
			public void handle(ActionEvent arg0) {
				
				String input = field.getText();
				
				try {
					
					int size = Integer.valueOf(input);
					model = new Model(size);
					primaryStage.setScene(cacheView);
					primaryStage.show();
					
				} catch(NumberFormatException e) {
					errorMessage.setVisible(true);
				}
				
			}
			
		});
		
		//prepare stage's title and scene. then show it.
		primaryStage.setTitle("Clipboard Cache");
		primaryStage.setScene(setupView);
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
