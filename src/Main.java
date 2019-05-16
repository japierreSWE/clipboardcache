import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

import com.japierre.ClipboardCache.controller.ClipboardChangeController;
import com.japierre.ClipboardCache.controller.PushController;
import com.japierre.ClipboardCache.entity.Model;

public class Main extends Application {
	
	Model model;
	Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	//set up scene and layout.
	VBox setupRoot = new VBox(8);
	VBox cacheRoot = new VBox(8);
	
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
	ArrayList<HBox> cacheLabelContainers;
	ArrayList<Label> cacheLabels;
	Label selected;
	
	/** Make the pane in the cache view display the cache */
	private void displayCache() {
		
		//first, clear the pane and reset the labels.
		cachePane.getChildren().clear();
		cacheLabels.clear();
		int count = 1;
		
		//set up the labels based on the cache's state.
		for(String str : model.getCache()) {
			cacheLabels.add(new Label(Integer.toString(count) + ". " + str));
			++count;
		}
		
		//then, add them to the pane.
		for(Label label : cacheLabels) {
			label.setFont(new Font("Arial", 20));
			//cachePane.getChildren().add(label);
			
			HBox container = new HBox();
			container.getChildren().add(label);
			cacheLabelContainers.add(container);
			cachePane.getChildren().add(container);
			
			container.setBorder(new Border(new BorderStroke(null, null, Color.LIGHTGREY, null, null, null, BorderStrokeStyle.SOLID, null, null, null, null)));
			
			//if the label gets clicked, that's the selected one.
			container.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				public void handle(MouseEvent event) {
					
					HBox node = (HBox)event.getSource();
					selected = (Label)node.getChildren().get(0);
					
					for(Label label : cacheLabels) {
						label.setBackground(null);
					}
					
					selected.setBackground(new Background(new BackgroundFill(Color.YELLOW,new CornerRadii(0),null)));
					
				}
				
			});
			
		}
		
	}
	
	/** Prepares the event listener that runs when the clipboard changes */
	private void setupClipboardListener() {

		
		
		//check the clipboard every 250 milliseconds.
		Timeline clipboardChecker = new Timeline(new KeyFrame(Duration.millis(250), new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				Transferable obj = cb.getContents(this);

				//only do the following if the clipboard data is a string.
				if(obj.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					
					//get the string, try to add it to cache, then show the cache.
					try {
						String contents = (String)obj.getTransferData(DataFlavor.stringFlavor);
						boolean changed = new ClipboardChangeController().add(model, contents);
						
						if(changed) displayCache();

					} catch (UnsupportedFlavorException e1) {

						e1.printStackTrace();
					} catch (IOException e1) {

						e1.printStackTrace();
					}

				}

			}

		}));
		
		clipboardChecker.setCycleCount(Timeline.INDEFINITE);
		clipboardChecker.play();
		//turn on this recurring event.
	}
		
	
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
					
					cachePane = new VBox(0);
					toFrontButton = new Button("Copy to clipboard");
					cacheRoot.getChildren().addAll(cachePane,toFrontButton);
					cacheRoot.setAlignment(Pos.CENTER);
					cacheLabels = new ArrayList<Label>();
					cacheLabelContainers = new ArrayList<HBox>();
					
					//set up size for the cache pane and give it a white background
					cachePane.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(0),null)));
					cachePane.setMinSize(270, 500);
					cachePane.setMaxSize(270,550);
					
					toFrontButton.setOnAction(new EventHandler<ActionEvent> () {
						
						public void handle(ActionEvent e) {
							
							boolean pushed = new PushController().push(model, selected);
							String text = selected.getText();
							int indexOfFirstSpace = text.indexOf(" ");
							//each label's text starts with "number. "
							//so, the text coming after the space is an
							//element of the cache.
							
							//get it, remove it, then put it in front.
							String element = text.substring(indexOfFirstSpace + 1);
							cb.setContents(new StringSelection(element), null);
							
							if(pushed) displayCache();
							
						}
						
					});
					
					primaryStage.show();
					
					//we also need to make the clipboard listener now that
					//we're in this mode.
					setupClipboardListener();
				
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
