package application;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AddShipper {
	ManageData manage = new ManageData();
	public static ObservableList<Company> shippers = FXCollections.observableArrayList();
	public static boolean adding = false;

	public VBox getPage() {

		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(20, 20, 20, 20));

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * ---------------Code to add a contact----------------
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(12);
		grid.setHgap(10);
		vbox.getChildren().add(grid);

		grid.getColumnConstraints().add(new ColumnConstraints(150));

		Label label = new Label("Add New Shipper:");
		label.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
		GridPane.setConstraints(label, 0, 0);

		Label nameLabel = new Label("Enter Company Name:");
		GridPane.setConstraints(nameLabel, 0, 1);

		TextField companyName = new TextField();
		companyName.setPromptText("Name");
		companyName.setPrefWidth(200);
		companyName.setMaxWidth(200);
		GridPane.setConstraints(companyName, 1, 1);

		Label emailLabel = new Label("Enter Company Email:");
		GridPane.setConstraints(emailLabel, 0, 2);

		TextField companyEmail = new TextField();
		companyEmail.setPromptText("company.email@email.com");
		companyEmail.setPrefWidth(200);
		companyEmail.setMaxWidth(200);
		GridPane.setConstraints(companyEmail, 1, 2);

		Label phoneLabel = new Label("Enter Company Phone:");
		GridPane.setConstraints(phoneLabel, 0, 3);

		TextField companyPhone = new TextField();
		companyPhone.setPromptText("#");
		companyPhone.setPrefWidth(200);
		companyPhone.setMaxWidth(200);
		GridPane.setConstraints(companyPhone, 1, 3);

		Label faxLabel = new Label("Enter Company Fax:");
		GridPane.setConstraints(faxLabel, 0, 4);

		TextField companyFax = new TextField();
		companyFax.setPromptText("#");
		companyFax.setPrefWidth(200);
		companyFax.setMaxWidth(200);
		GridPane.setConstraints(companyFax, 1, 4);

		Label street = new Label("Street Address:");
		GridPane.setConstraints(street, 0, 6);

		TextField companyStreet = new TextField();
		companyStreet.setPromptText("Street");
		companyStreet.setPrefWidth(200);
		companyStreet.setMaxWidth(200);
		GridPane.setConstraints(companyStreet, 1, 6);

		Label city = new Label("City:");
		GridPane.setConstraints(city, 0, 7);

		TextField companyCity = new TextField();
		companyCity.setPromptText("City");
		companyCity.setPrefWidth(200);
		companyCity.setMaxWidth(200);
		GridPane.setConstraints(companyCity, 1, 7);

		Label state = new Label("State:");
		GridPane.setConstraints(state, 0, 8);

		TextField companyState = new TextField();
		companyState.setPromptText("State");
		companyState.setPrefWidth(200);
		companyState.setMaxWidth(200);
		GridPane.setConstraints(companyState, 1, 8);

		Label zip = new Label("Zip Code:");
		GridPane.setConstraints(zip, 0, 9);

		TextField companyZip = new TextField();
		companyZip.setPromptText("Zip");
		companyZip.setPrefWidth(200);
		companyZip.setMaxWidth(200);
		GridPane.setConstraints(companyZip, 1, 9);

		Button add = new Button("Add Shipper");
		add.setPrefWidth(100);
		add.setOnAction(e -> {

			// Create company object
			Company company = new Company(companyName.getText(), companyEmail.getText(), companyPhone.getText(),
					companyFax.getText(), null, companyStreet.getText(), companyCity.getText(), companyState.getText(),
					companyZip.getText(), null);

			// Make sure all fields are filled in.
			Alert alert = new Alert(AlertType.NONE);

			if (company.getName().equals("")) {
				alert.setContentText("Enter Company Name.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			} else if (company.getPhone().equals("")) {
				alert.setContentText("Enter Company Phone.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			} else if (company.getStreet().equals("")) {
				alert.setContentText("Enter Company Street.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			} else if (company.getCity().equals("")) {
				alert.setContentText("Enter Company City.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			} else if (company.getState().equals("")) {
				alert.setContentText("Enter Company State.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			} else if (company.getZip().equals("")) {
				alert.setContentText("Enter Company Zip Code.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			}

			// If all fields are filled in
			else {
				adding = true;
				shippers.add(company);
				companyName.clear();
				companyEmail.clear();
				companyPhone.clear();
				companyFax.clear();
				companyStreet.clear();
				companyCity.clear();
				companyState.clear();
				companyZip.clear();

				// Add contact to directory
				manage.saveShipper();

				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setContentText("Shipper Added.");
				alert1.show();
			}
		});
		GridPane.setConstraints(add, 0, 10);

		grid.getChildren().addAll(label, nameLabel, companyName, emailLabel, companyEmail, phoneLabel, companyPhone,
				faxLabel, companyFax, street, companyStreet, city, companyCity, state, companyState, zip, companyZip,
				add);
		grid.getStyleClass().add("border");

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * ---------------Code to delete an existing shipper----------------
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		GridPane grid2 = new GridPane();
		grid2.setPadding(new Insets(20, 20, 20, 20));
		grid2.setVgap(12);
		grid2.setHgap(15);
		vbox.getChildren().add(grid2);

		Label deleteLabel = new Label("Select Shipper:");
		deleteLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold");

		GridPane.setConstraints(deleteLabel, 0, 0);

		ChoiceBox<String> list = new ChoiceBox<>();
		// populating list with contacts available to delete
		for (Company comp : shippers) {
			list.getItems().add(comp.getName());
		}
		list.setMinWidth(360);
		list.setMaxWidth(360);
		GridPane.setConstraints(list, 0, 1);

		// If a contact is added, update the ChoiceBox. Make sure contact is being
		// added, not deleted.
		shippers.addListener((ListChangeListener.Change<? extends Company> e) -> {
			if (adding) {
				list.getItems().add(shippers.get(shippers.size() - 1).getName());
			}
		});
		Button deleteButton = new Button("Delete Shipper");
		deleteButton.setMinWidth(120);
		GridPane.setConstraints(deleteButton, 1, 1);

		GridPane info = new GridPane();
		info.setVgap(10);
		info.setHgap(12);
		GridPane.setConstraints(info, 0, 3);

		// Showing information about selected contact

		Label delEmailLabel = new Label("Email: ");
		GridPane.setConstraints(delEmailLabel, 0, 0);

		Label delEmail = new Label();
		GridPane.setConstraints(delEmail, 1, 0);

		Label delPhoneLabel = new Label("Phone: ");
		GridPane.setConstraints(delPhoneLabel, 0, 1);

		Label delPhone = new Label();
		GridPane.setConstraints(delPhone, 1, 1);

		Label delFaxLabel = new Label("Fax: ");
		GridPane.setConstraints(delFaxLabel, 0, 2);

		Label delFax = new Label();
		GridPane.setConstraints(delFax, 1, 2);

		Label delAddressLabel = new Label("Address: ");
		GridPane.setConstraints(delAddressLabel, 0, 3);

		Label delAddress = new Label();
		GridPane.setConstraints(delAddress, 1, 3);

		list.setOnAction(e -> {
			String selected = list.getSelectionModel().getSelectedItem();

			for (Company comp : shippers) {
				if (comp.getName().equals(selected)) {
					delEmail.setText(comp.getEmail());
					delPhone.setText(comp.getPhone());
					String addressOutput = comp.getStreet() + ", " + comp.getCity() + ", " + comp.getState() + ", "
							+ comp.getZip();
					delFax.setText(comp.getFax());
					delAddress.setText(addressOutput);
					break;
				}
			}
		});

		deleteButton.setOnAction(e -> {
			try {
				String delItem = list.getSelectionModel().getSelectedItem();
				// Remove from ChoiceBox
				if (delItem.length() > 0) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.OK) {
						// Remove item from ChoiceBox in Create Invoice page
						CreateInvoice.shipperList.getItems().remove(list.getSelectionModel().getSelectedIndex());
						// Remove item from ChoiceBox in Shippers page
						list.getItems().remove(list.getSelectionModel().getSelectedIndex());

						// Remove from shippers list
						adding = false;
						for (Company comp : shippers) {
							if (comp.getName().equals(delItem)) {

								// Delete from directory and ArrayList
								manage.deleteShipper(comp);
								shippers.remove(comp);
								break;
							}
						}
						// Reset the labels
						delEmail.setText("");
						delPhone.setText("");
						delFax.setText("");
						delAddress.setText("");

					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		info.getChildren().addAll(delEmailLabel, delEmail, delPhoneLabel, delPhone, delFaxLabel, delFax,
				delAddressLabel, delAddress);
		grid2.getChildren().addAll(deleteLabel, list, deleteButton, info);
		grid2.getStyleClass().add("border");

		return vbox;

	}
}
