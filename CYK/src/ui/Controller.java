package ui;

import java.util.NoSuchElementException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.System;

public class Controller {

	// --------------------------------------------------------------------------------

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab CFGTab;

	@FXML
	private Tab resultTab;

	@FXML
	private Pane tabPane1;
	
	@FXML
	private Pane tabPane2;
	
	// --------------------------------------------------------------------------------

	@FXML
	private VBox CFGPane;
	
	@FXML
	private VBox CYKPane;
	
	// --------------------------------------------------------------------------------

	@FXML
	private TextField WTextField;

	@FXML
	private Button stringButton;

	@FXML
	private Button addRowButton;

	@FXML
	private Button runButton;

	@FXML
	private Button resetButton;

	// --------------------------------------------------------------------------------

	public static final String LAMBDA = "λ";

	private System cyk;

	// --------------------------------------------------------------------------------

	@FXML
	public void initialize() {

		WTextField.setDisable(true);

		resultTab.setDisable(true);

		addRowButton.setDisable(true);

		runButton.setDisable(true);

		CFGPane.setDisable(true);

		resetButton.setDisable(true);

		newRow();

	}
	
	// --------------------------------------------------------------------------------

	private void newRow(){

		HBox box = new HBox();
		
		CFGPane.getChildren().add(box);

		box.prefWidth(685d);
		
		box.prefHeight(50d);
		
		box.setAlignment(Pos.CENTER);
		
		box.setSpacing(3d);

		TextField informationTextField = new TextField();
		
		box.getChildren().add(informationTextField);
		
		informationTextField.setPrefSize(80d, 30d);
		
		informationTextField.setAlignment(Pos.CENTER);
		
		informationTextField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		informationTextField.setFont(Font.font("System", FontWeight.BOLD, 14d));

		TextField emptyTextField = new TextField();
		
		box.getChildren().add(emptyTextField);
		
		emptyTextField.setPrefSize(80d, 30d);
		
		emptyTextField.setAlignment(Pos.CENTER);
		
		emptyTextField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		emptyTextField.setFont(Font.font("System", FontWeight.BOLD, 14d));
		
		emptyTextField.setText("->");
		
		emptyTextField.setEditable(false);

		TextField resultTextField = new TextField();
		
		box.getChildren().add(resultTextField);
		
		resultTextField.setPrefSize(505d, 30d);
		
		resultTextField.setAlignment(Pos.CENTER_LEFT);
		
		resultTextField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		resultTextField.setFont(Font.font("System", 14d));

		informationTextField.setOnKeyPressed(event -> {
			
			if(event.getCode()== KeyCode.ENTER && !informationTextField.getText().equals("")){
				
				resultTextField.setText(resultTextField.getText() + LAMBDA);
				
			}
			
		});
		
	}
	
	// --------------------------------------------------------------------------------

	@FXML
	public void addRow(ActionEvent event) {

		newRow();

	}
	
	// --------------------------------------------------------------------------------

	@FXML
	public void addString(ActionEvent event) {

		TextInputDialog informationInputDialog = new TextInputDialog();
		
		informationInputDialog.setHeaderText(null);
		
		informationInputDialog.setTitle("Important information");
		
		informationInputDialog.setContentText("Enter a value for W");
		
		Optional<String> inputUserInformation = informationInputDialog.showAndWait();

		try{
			
			if(!inputUserInformation.get().isEmpty()){
				
				WTextField.setText(inputUserInformation.get());
				
			}

		}catch (NoSuchElementException e){

		}

		WTextField.setAlignment(Pos.CENTER);

		addRowButton.setDisable(false);

		runButton.setDisable(false);

		CFGPane.setDisable(false);

		stringButton.setDisable(true);

	}
	
	// --------------------------------------------------------------------------------

	@FXML
	public void reset(ActionEvent event) {

		CFGPane.getChildren().clear();
		
		CYKPane.getChildren().clear();

		WTextField.setText("");
		
		this.cyk = null;

		newRow();
		
		resultTab.setDisable(true);

		CYKPane.setDisable(false);

		tabPane.getSelectionModel().select(CFGTab);

		CFGPane.setDisable(true);

		stringButton.setDisable(false);

		resetButton.setDisable(true);

	}
	
	// --------------------------------------------------------------------------------

	@FXML
	public void runSystem(ActionEvent event) {

		CYKPane.getChildren().clear();

		this.cyk = new System(informationToMatrix(), WTextField.getText().length());
		
		this.cyk.addValueMap();
		
		this.cyk.addColumn(WTextField.getText());
		
		this.cyk.CYKAlgorithm(WTextField.getText());

		showMessage();
		
		generateMatrix();

		resultTab.setDisable(false);
		
		tabPane.getSelectionModel().select(resultTab);

		stringButton.setDisable(true);

		addRowButton.setDisable(true);

		runButton.setDisable(true);

		CFGPane.setDisable(true);

		resetButton.setDisable(false);

	}
	
	// --------------------------------------------------------------------------------

	private String[][] informationToMatrix() {
		
		String[][] information = new String[CFGPane.getChildren().size()][2];

		for (int a = 0; a < CFGPane.getChildren().size(); a ++) {
			
			HBox box = (HBox) CFGPane.getChildren().get(a);

			TextField tf1 = (TextField) box.getChildren().get(0);
			
			TextField tf2 = (TextField) box.getChildren().get(2);

			information[a][0] = tf1.getText();
			
			information[a][1] = tf2.getText();
			
		}
		
		return information;
		
	}
	
	// --------------------------------------------------------------------------------

	private void showMessage() {

		TextField tf = new TextField();
		
		CYKPane.getChildren().add(tf);

		tf.setAlignment(Pos.CENTER);
		
		tf.setEditable(false);
		
		tf.setPrefSize(698, 30d);

		tf.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		tf.setFont(Font.font("System", FontWeight.BOLD, 14));

		if (this.cyk.containsFeature()) {
			
			tf.setText("The input string is generated by the GIC");
			
			tf.setBackground( new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));

		} else {
			
			tf.setText("The string entered is not generated by the GIC");
			
			tf.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
		
		}
		
	}
	
	// --------------------------------------------------------------------------------

	private void generateMatrix() {
		
		HBox box1 = new HBox();
		
		VBox box2 = new VBox();

		box1.setSpacing(3d);
		
		box1.setAlignment(Pos.CENTER);
		
		box2.getChildren().add(box1);

		Label userInformation1 = new Label();
		
		userInformation1.setPrefHeight(30);
		
		userInformation1.setPrefWidth(40);
		
		box1.getChildren().add(userInformation1);

		for (int a = 0; a < WTextField.getText().length(); a ++) {
			
			Label userInformation2 = new Label();
			
			userInformation2.setAlignment(Pos.CENTER);
			
			userInformation2.setPrefHeight(30);
			
			userInformation2.setPrefWidth(40);

			userInformation2.setText("j= " + (a + 1));
			
			box1.getChildren().add(userInformation2);
			
		}
		
		printMatrix(box2);
		
	}
	
	// --------------------------------------------------------------------------------

	private void printMatrix(VBox box) {
		
		GridPane pane = new GridPane();
		
		String[][] rmatrix = this.cyk.getResultMatrix();

		pane.setHgap(3);
		
		pane.setVgap(3);
		
		pane.setAlignment(Pos.CENTER);

		for (int a = 0; a < rmatrix.length; a ++) {

			Label informationUser1 = new Label("i= " + (a + 1));
			
			informationUser1.setAlignment(Pos.CENTER);
			
			informationUser1.setPrefHeight(30);
			
			informationUser1.setPrefWidth(40);
			
			pane.add(informationUser1, 0, (a + 1));

			for (int b = 0; b < rmatrix[0].length; b ++) {

				Label informationUser2 = new Label();
				
				informationUser2.setAlignment(Pos.CENTER);
				
				informationUser2.setPrefHeight(30);
				
				informationUser2.setPrefWidth(40);
				
				informationUser2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

				if (rmatrix[a][b] != null) {
					
					informationUser2.setText("{" + rmatrix[a][b] + "}");
					
				} else {
					
					informationUser2.setText("");
					
				}
				
				pane.add(informationUser2, (b + 1), (a + 1));
				
			}
			
		}
		
		box.getChildren().add(pane);
		
		this.CYKPane.getChildren().add(box);
		
	}
	
	// --------------------------------------------------------------------------------

}
