package application;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditInfo {

	public static Company company = null;

	// The name of the current logo. This is used to add the logo name to the
	// Company object.
	public static String companyLogo = null;

	// This allows the logo on the page to be changed dynamically. All instances
	// have access to the same object, removing duplicates.
	public static Image logo = null;

	// This string is used to delete the old logo when a new logo is assigned
	public static String previousLogo = null;

	public VBox getPage(Stage stage) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(20, 20, 20, 20));

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(12);
		grid.setHgap(10);
		vbox.getChildren().add(grid);

		grid.getColumnConstraints().add(new ColumnConstraints(150));

		Label nameLabel = new Label("Enter Company Name:");
		GridPane.setConstraints(nameLabel, 0, 0);

		TextField companyName = new TextField();
		companyName.setPromptText("Name");
		companyName.setPrefWidth(200);
		companyName.setMaxWidth(200);
		GridPane.setConstraints(companyName, 1, 0);

		Label emailLabel = new Label("Enter Company Email:");
		GridPane.setConstraints(emailLabel, 0, 1);

		TextField companyEmail = new TextField();
		companyEmail.setPromptText("company.email@email.com");
		companyEmail.setPrefWidth(200);
		companyEmail.setMaxWidth(200);
		GridPane.setConstraints(companyEmail, 1, 1);

		Label phoneLabel = new Label("Enter Company Phone:");
		GridPane.setConstraints(phoneLabel, 0, 2);

		TextField companyPhone = new TextField();
		companyPhone.setPromptText("#");
		companyPhone.setPrefWidth(200);
		companyPhone.setMaxWidth(200);
		GridPane.setConstraints(companyPhone, 1, 2);

		Label faxLabel = new Label("Enter Company Fax:");
		GridPane.setConstraints(faxLabel, 0, 3);

		TextField companyFax = new TextField();
		companyFax.setPromptText("#");
		companyFax.setPrefWidth(200);
		companyFax.setMaxWidth(200);
		GridPane.setConstraints(companyFax, 1, 3);

		Label logoLabel = new Label("Attach Company Logo:");
		GridPane.setConstraints(logoLabel, 0, 4);

		// Choose Directory for Logo
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"),
				new FileChooser.ExtensionFilter("Image Files", "*.jpg"),
				new FileChooser.ExtensionFilter("Image Files", "*.PNG"),
				new FileChooser.ExtensionFilter("Image Files", "*.jpeg"));

		HBox logoSelect = new HBox(10);
		GridPane.setConstraints(logoSelect, 1, 4);
		Label fileDir = new Label();
		Button setDir = new Button("Select Image");
		setDir.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(stage);
			try {
				// Get the selected file directory
				String dir = selectedFile.getAbsolutePath();
				fileDir.setText(dir);

				// Remove the last image view so the images don't overlap
				try {
					ObservableList<Node> childrens = grid.getChildren();
					for (Node node : childrens) {
						if (node instanceof ImageView) {
							grid.getChildren().remove(node);
							break;
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// Add the image to the grid
				logo = new Image("file:////" + dir);
				ImageView imgView = new ImageView(logo);

				// set the current image to previousLogo and then reassign current image
				previousLogo = companyLogo;
				companyLogo = selectedFile.getName();

				// Resize the image if it is too big
				imgView.setPreserveRatio(true);
				if (logo.getHeight() > 128)
					imgView.setFitHeight(150);
				GridPane.setConstraints(imgView, 0, 14);
				grid.getChildren().add(imgView);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		logoSelect.getChildren().addAll(fileDir, setDir);
		Label street = new Label("Street Address:");
		GridPane.setConstraints(street, 0, 7);

		TextField companyStreet = new TextField();
		companyStreet.setPromptText("Street");
		companyStreet.setPrefWidth(200);
		companyStreet.setMaxWidth(200);
		GridPane.setConstraints(companyStreet, 1, 7);

		Label city = new Label("City:");
		GridPane.setConstraints(city, 0, 8);

		TextField companyCity = new TextField();
		companyCity.setPromptText("City");
		companyCity.setPrefWidth(200);
		companyCity.setMaxWidth(200);
		GridPane.setConstraints(companyCity, 1, 8);

		Label state = new Label("State:");
		GridPane.setConstraints(state, 0, 9);

		TextField companyState = new TextField();
		companyState.setPromptText("State");
		companyState.setPrefWidth(200);
		companyState.setMaxWidth(200);
		GridPane.setConstraints(companyState, 1, 9);

		Label zip = new Label("Zip Code:");
		GridPane.setConstraints(zip, 0, 10);

		TextField companyZip = new TextField();
		companyZip.setPromptText("Zip");
		companyZip.setPrefWidth(200);
		companyZip.setMaxWidth(200);
		GridPane.setConstraints(companyZip, 1, 10);

		Label slogan = new Label("Enter Company Slogan:");
		GridPane.setConstraints(slogan, 0, 13);

		TextField companySlogan = new TextField();
		companySlogan.setPromptText("Slogan");
		companySlogan.setPrefWidth(350);
		companySlogan.setMaxWidth(350);
		GridPane.setConstraints(companySlogan, 1, 13);

		Button save = new Button("Save");
		save.setPrefWidth(60);
		save.setOnAction(e -> {

			// Create company object
			company = new Company(companyName.getText(), companyEmail.getText(), companyPhone.getText(),
					companyFax.getText(), companyLogo, companyStreet.getText(), companyCity.getText(),
					companyState.getText(), companyZip.getText(), companySlogan.getText());

			// Make sure all fields are filled in.
			Alert alert = new Alert(AlertType.NONE);

			if (company.getName().equals("")) {
				alert.setContentText("Enter Company Name.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			} else if (company.getEmail().equals("")) {
				alert.setContentText("Enter Company Email.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			} else if (company.getPhone().equals("")) {
				alert.setContentText("Enter Company Phone.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			} else if (company.getFax().equals("")) {
				alert.setContentText("Enter Company Fax.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			} else if (company.getLogo() == null) {
				alert.setContentText("Attach Company Logo.");
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
			} else if (company.getSlogan().equals("")) {
				alert.setContentText("Enter Company Slogan.");
				alert.setAlertType(AlertType.ERROR);
				alert.show();
			}

			// If all fields are filled in
			else {
				ManageData manage = new ManageData();
				manage.upload(company);

				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText("Information Saved");
				alert.show();
			}
		});
		GridPane.setConstraints(save, 0, 16);

		grid.getChildren().addAll(nameLabel, companyName, emailLabel, companyEmail, phoneLabel, companyPhone, faxLabel,
				companyFax, logoLabel, logoSelect, street, companyStreet, city, companyCity, state, companyState, zip,
				companyZip, slogan, companySlogan, save);
		grid.getStyleClass().add("border");

		// Load previous data
		try {
			companyName.setText(company.getName());
			companyEmail.setText(company.getEmail());
			companyPhone.setText(company.getPhone());
			companyFax.setText(company.getFax());
			companyStreet.setText(company.getStreet());
			companyCity.setText(company.getCity());
			companyState.setText(company.getState());
			companyZip.setText(company.getZip());
			companySlogan.setText(company.getSlogan());

			// Logo
			ImageView imgView = new ImageView(logo);
			imgView.setPreserveRatio(true);
			if (logo.getHeight() > 128)
				imgView.setFitHeight(150);
			GridPane.setConstraints(imgView, 0, 14);
			grid.getChildren().add(imgView);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return vbox;
	}

}
