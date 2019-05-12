import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	VBox cachePane;
	Button toFrontButton;
	
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
					
					//make cache, set up cache scene.
					int size = Integer.valueOf(input);
					model = new Model(size);
					primaryStage.setScene(cacheView);
					
					cachePane = new VBox(8);
					toFrontButton = new Button("Copy to clipboard");
					cacheRoot.getChildren().addAll(cachePane,toFrontButton);
					cacheRoot.setAlignment(Pos.CENTER);
					
					//set up size for the cache pane
					cachePane.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(0),null)));
					cachePane.setMinSize(270, 500);
					cachePane.setMaxSize(270,550);
					
					primaryStage.show();
				
				//if the input wasn't an int, don't switch but show the error message
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
