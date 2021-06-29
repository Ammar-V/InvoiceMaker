package application;

import java.io.File;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateInvoice {

	Alert alert = new Alert(AlertType.NONE);

	public static ObservableList<Company> contacts = AddContact.contacts;
	public static ObservableList<Company> shippers = AddShipper.shippers;
	public static ObservableList<Company> receivers = AddReceiver.receivers;
	public static ChoiceBox<String> brokerList = new ChoiceBox<>();
	public static ChoiceBox<String> shipperList = new ChoiceBox<>();
	public static ChoiceBox<String> receiverList = new ChoiceBox<>();
	TableView<InvoiceItem> table;

	@SuppressWarnings("unchecked")
	public VBox getPage(Stage primaryStage) {

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20, 20, 20, 20));

		GridPane grid = new GridPane();
		// grid.setGridLinesVisible(true);
		grid.getColumnConstraints().add(new ColumnConstraints(130));
		grid.getColumnConstraints().add(new ColumnConstraints(200));
		grid.getColumnConstraints().add(new ColumnConstraints(101));
		grid.getColumnConstraints().add(new ColumnConstraints(130));
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(12);
		grid.setHgap(25);
		grid.getStyleClass().add("border");
		vbox.getChildren().add(grid);

		/*
		 * 
		 * SELECT BROKER
		 * 
		 */

		Label selectBroker = new Label("Select Broker:");
		GridPane.setConstraints(selectBroker, 0, 0);

		// populating ChoiceBox list with contacts available to create an invoice for
		for (Company comp : contacts) {
			brokerList.getItems().add(comp.getName());
		}
		brokerList.setMinWidth(200);
		GridPane.setConstraints(brokerList, 1, 0);
		GridPane.setColumnSpan(brokerList, 3);
		// If a broker is added, update the ChoiceBox. Make sure broker is being
		// added, not deleted.
		contacts.addListener((ListChangeListener.Change<? extends Company> e) -> {
			if (AddContact.adding) {
				brokerList.getItems().add(contacts.get(contacts.size() - 1).getName());
			}
		});

		/*
		 * 
		 * SELECT SHIPPER
		 * 
		 */

		Label selectShipper = new Label("Select Shipper:");
		GridPane.setConstraints(selectShipper, 0, 1);

		// populating ChoiceBox list with contacts available to create an invoice for
		for (Company comp : shippers) {
			shipperList.getItems().add(comp.getName());
		}
		shipperList.setMinWidth(200);
		GridPane.setConstraints(shipperList, 1, 1);
		GridPane.setColumnSpan(shipperList, 3);
		// If a contact is added, update the ChoiceBox. Make sure contact is being
		// added, not deleted.
		shippers.addListener((ListChangeListener.Change<? extends Company> e) -> {
			if (AddShipper.adding) {
				shipperList.getItems().add(shippers.get(shippers.size() - 1).getName());
			}
		});

		/*
		 * 
		 * SELECT RECEIVER
		 * 
		 */

		Label selectReceiver = new Label("Select Receiver:");
		GridPane.setConstraints(selectReceiver, 0, 2);

		// populating ChoiceBox list with contacts available to create an invoice for
		for (Company comp : receivers) {
			receiverList.getItems().add(comp.getName());
		}
		receiverList.setMinWidth(200);
		GridPane.setConstraints(receiverList, 1, 2);
		GridPane.setColumnSpan(receiverList, 3);
		// If a contact is added, update the ChoiceBox. Make sure contact is being
		// added, not deleted.
		receivers.addListener((ListChangeListener.Change<? extends Company> e) -> {
			if (AddReceiver.adding) {
				receiverList.getItems().add(receivers.get(receivers.size() - 1).getName());
			}
		});

		Label dateLabel = new Label("Today's Date:");
		GridPane.setConstraints(dateLabel, 0, 3);

		DatePicker today = new DatePicker();
		today.setMinWidth(200);
		GridPane.setConstraints(today, 1, 3);

		Label dueLabel = new Label("Due Date:");
		GridPane.setConstraints(dueLabel, 0, 4);

		DatePicker due = new DatePicker();
		due.setMinWidth(200);
		GridPane.setConstraints(due, 1, 4);

		/*
		 * 
		 * MORE INVOICE LOGISTICS
		 * 
		 */

		Label bolLabel = new Label("BOL Number:");
		GridPane.setConstraints(bolLabel, 2, 0);

		TextField bolNumber = new TextField();
		bolNumber.setPromptText("#");
		bolNumber.setMinWidth(125);
		GridPane.setConstraints(bolNumber, 3, 0);

		Label carrierLabel = new Label("Carrier Pronumber:");
		GridPane.setConstraints(carrierLabel, 2, 1);

		TextField carrierPro = new TextField();
		carrierPro.setPromptText("#");
		carrierPro.setMinWidth(125);
		GridPane.setConstraints(carrierPro, 3, 1);

		Label brokerLabel = new Label("Broker Pronumber:");
		GridPane.setConstraints(brokerLabel, 2, 2);

		TextField brokerPro = new TextField();
		brokerPro.setPromptText("#");
		brokerPro.setMinWidth(125);
		GridPane.setConstraints(brokerPro, 3, 2);

		Label viaLabel = new Label("Shipped Via:");
		GridPane.setConstraints(viaLabel, 2, 3);

		TextField shippedVia = new TextField();
		shippedVia.setPromptText("");
		shippedVia.setMinWidth(125);
		GridPane.setConstraints(shippedVia, 3, 3);

		Label termsLabel = new Label("Pay Terms:");
		GridPane.setConstraints(termsLabel, 2, 4);

		Spinner<Integer> payTerms = new Spinner<>(0, Integer.MAX_VALUE, 5);
		payTerms.setEditable(true);
		payTerms.setMinWidth(125);
		GridPane.setConstraints(payTerms, 3, 4);

		/*
		 * 
		 * 
		 * 
		 * 
		 * ----------Invoice Items Table----------
		 * 
		 * 
		 * 
		 * 
		 */
		TableColumn<InvoiceItem, String> description = new TableColumn<>("Description");
		description.setMinWidth(511);
		description.setCellValueFactory(new PropertyValueFactory<>("description"));

		TableColumn<InvoiceItem, String> price = new TableColumn<>("Price ($)");
		price.setMinWidth(130);
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		price.setStyle("-fx-alignment: center");

		table = new TableView<>();
		table.getColumns().addAll(description, price);
		table.setMinWidth(636);
		table.setPlaceholder(new Label("No Invoice Items"));
		GridPane.setConstraints(table, 0, 5);

		// Penalty for late dues
		CheckBox checkPenalty = new CheckBox("Overdue Penalty (%)");
		GridPane.setColumnSpan(checkPenalty, 2);
		GridPane.setConstraints(checkPenalty, 0, 6);

		Spinner<Integer> penalty = new Spinner<>(0, 100, 2);
		penalty.setMaxWidth(130);
		penalty.setEditable(true);
		penalty.setDisable(true);
		checkPenalty.selectedProperty().addListener((obs, o, n) -> {
			if (checkPenalty.isSelected())
				penalty.setDisable(false);
			else
				penalty.setDisable(true);
		});

		/*
		 * 
		 * 
		 * Manual Invoice Number input
		 * 
		 * 
		 */

		HBox invoiceNumBox = new HBox(25);
		GridPane.setColumnSpan(invoiceNumBox, 3);
		GridPane.setConstraints(invoiceNumBox, 1, 6);

		// The penalty spinner is added in this hbox to visually fit it on the screen.
		invoiceNumBox.getChildren().add(penalty);

		CheckBox checkINum = new CheckBox("Manual Invoice Number");
		invoiceNumBox.getChildren().add(checkINum);

		TextField invoiceNum = new TextField();
		invoiceNum.setPromptText("#");
		invoiceNum.setDisable(true);
		invoiceNumBox.getChildren().add(invoiceNum);
		checkINum.selectedProperty().addListener((obs, o, n) -> {
			if (checkINum.isSelected())
				invoiceNum.setDisable(false);
			else
				invoiceNum.setDisable(true);
		});

		Button addButton = new Button("  Add Invoice Item  ");
		GridPane.setColumnSpan(addButton, 2);
		GridPane.setConstraints(addButton, 0, 7);
		addButton.setOnAction(e -> {
			addNewItem(primaryStage);
		});

		Button deleteButton = new Button("  Delete Invoice Item  ");
		GridPane.setConstraints(deleteButton, 1, 7);
		deleteButton.setOnAction(e -> {
			InvoiceItem selected = table.getSelectionModel().getSelectedItem();
			if (selected != null) {
				alert.setAlertType(AlertType.CONFIRMATION);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.OK) {
					for (InvoiceItem item : data) {
						if (item == selected) {
							data.remove(item);
							table.setItems(data);

							// Disable the button and remove focus from table
							deleteButton.setDisable(true);
							table.getSelectionModel().clearSelection();
							break;
						}
					}
				}
			}
		});
		deleteButton.setDisable(true);

		// If the table is not focused (not selected) then disable deleteButton
		table.focusedProperty().addListener((obs, old, n) -> {
			// Make sure to check if the user is trying to use the delete button. If they
			// are, don't disable yet.
			if (!table.isFocused() && !deleteButton.isFocused()) {
				deleteButton.setDisable(true);
				table.getSelectionModel().clearSelection();
			}
		});

		// When a row is selected, enable the delete button
		table.getSelectionModel().selectedItemProperty().addListener((obs, old, n) -> {
			if (n != null) {
				deleteButton.setDisable(false);
			}
		});

		Button create = new Button("  Create Invoice  ");
		create.setMinWidth(130);
		GridPane.setConstraints(create, 3, 7);
		create.setOnAction(e -> {
			try {
				String broker = brokerList.getSelectionModel().getSelectedItem();
				String shipper = shipperList.getSelectionModel().getSelectedItem();
				String receiver = receiverList.getSelectionModel().getSelectedItem();

				// Check fields aren't empty
				broker.substring(0, 1);
				shipper.substring(0, 1);
				receiver.substring(0, 1);
				today.getValue().getYear();
				due.getValue().getYear();
				if (!data.isEmpty()) {

					// Choose save file name
					FileChooser saveName = new FileChooser();
					saveName.setTitle("Save");
					saveName.getExtensionFilters().add(new FileChooser.ExtensionFilter("pdf Files", "*.pdf"));
					File saveFile = saveName.showSaveDialog(primaryStage);
					if (saveFile != null) {

						// Penalty
						if (checkPenalty.isSelected())
							CreatePDF.penalty = penalty.getValue().intValue();

						// Invoice Num
						if (checkINum.isSelected())
							CreatePDF.inputInvoiceNum = invoiceNum.getText();
						// Create the pdf
						CreatePDF pdf = new CreatePDF();
						pdf.create(broker, shipper, receiver, today.getValue(), due.getValue(), data,
								saveFile.getAbsolutePath(), bolNumber.getText(), carrierPro.getText(),
								brokerPro.getText(), shippedVia.getText(), payTerms.getValue().intValue());
					}
				} else {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Add An Invoice Item.");
					alert.show();
				}
			} catch (Exception e1) {

				// TODO Auto-generated catch block
				e1.printStackTrace();
				alert.setAlertType(AlertType.ERROR);
				alert.setContentText("Fill In All The Fields Properly.");
				alert.show();
			}
		});

		grid.getChildren().addAll(selectBroker, brokerList, selectShipper, shipperList, selectReceiver, receiverList,
				dateLabel, today, dueLabel, due, bolLabel, bolNumber, carrierLabel, carrierPro, brokerLabel, brokerPro,
				viaLabel, shippedVia, termsLabel, payTerms, table, addButton, deleteButton, create, checkPenalty,
				invoiceNumBox);
		return vbox;
	}

	// Data list for the table
	public static ObservableList<InvoiceItem> data = FXCollections.observableArrayList();

	// Window for adding a new invoice item
	public void addNewItem(Stage primaryStage) {
		Stage stage = new Stage();
		stage.setTitle("New Invoice Item");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));

		Label label = new Label("Description:");
		vbox.getChildren().add(label);

		TextArea text = new TextArea();
		text.setWrapText(true);
		text.setMinHeight(150);
		text.setMinWidth(300);
		vbox.getChildren().add(text);

		HBox hbox = new HBox(20);
		vbox.getChildren().add(hbox);

		Label priceLabel = new Label("Price ($):");
		hbox.getChildren().add(priceLabel);

		TextField price = new TextField();
		price.setMinWidth(120);
		hbox.getChildren().add(price);

		Button done = new Button("  Add Item  ");
		hbox.getChildren().add(done);
		done.setPrefWidth(105);
		done.setOnAction(e -> {

			try {
				// Check if description is empty
				if (text.getText().replaceAll(" ", "").equals(""))
					throw new Exception();

				try {
					data.add(new InvoiceItem(wrap(text.getText()), Double.parseDouble(price.getText())));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				table.setItems(data);
				stage.close();
			} catch (NumberFormatException e1) {
				alert.setAlertType(AlertType.ERROR);
				alert.setContentText("Invalid Price.");
				alert.show();
			} catch (Exception a) {
				alert.setAlertType(AlertType.ERROR);
				alert.setContentText("Invalid Description.");
				alert.show();
			}

		});

		Scene scene = new Scene(vbox, 380, 300);
		stage.setScene(scene);
		stage.show();

		// Center this window on the previous window
		stage.setX(primaryStage.getX() + ((primaryStage.getWidth() / 2)) - 190);
		stage.setY(primaryStage.getY() + ((primaryStage.getHeight() / 2)) - 150);
	}

	// Simulate Text Wrap
	public String wrap(String str) {
		if (str.length() < 80) {
			return str;
		} else {
			Scanner scan = new Scanner(str);
			// Helps keep track of when to substring from
			int index = 0;
			String segment = "";

			// Make sure the string isn't empty, and the current string being built isn't
			// larger than 80 in length
			while (scan.hasNext() && segment.length() < 80) {
				String word = scan.next();
				// Make sure that the addition of the next word plus a space isn't larger than
				// 80 in length
				if ((segment.length() + word.length() + " ").length() < 80) {
					segment += word + " ";
					index += 1 + word.length();
				}

			}
			scan.close();
			// Catch exception when already at end of string
			try {
				return segment + "\n" + wrap(str.substring(index));
			} catch (Exception e) {
				return segment + "\n";
			}
		}
	}

}
