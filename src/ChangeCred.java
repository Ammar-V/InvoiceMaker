package application;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChangeCred {

	public VBox getPage() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(20));

		VBox box = new VBox(10);
		box.setPadding(new Insets(20));
		box.getStyleClass().add("border");
		vbox.getChildren().add(box);

		Label label1 = new Label("Change Username/Password");
		label1.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
		box.getChildren().add(label1);

		Label userLabel = new Label("Username:");
		box.getChildren().add(userLabel);

		TextField username = new TextField();
		username.setMinWidth(150);
		username.setMaxWidth(150);
		username.setPromptText("Username");
		username.setText(Cred.userCurrent);
		box.getChildren().add(username);

		Label passLabel = new Label("Password:");
		box.getChildren().add(passLabel);

		PasswordField password = new PasswordField();
		password.setMinWidth(150);
		password.setMaxWidth(150);
		password.setPromptText("Password");
		password.setText(Cred.passCurrent);
		box.getChildren().add(password);

		Button save = new Button("  Save  ");
		box.getChildren().add(save);

		save.setOnAction(e -> {
			if (!username.getText().equals("") && !password.getText().equals("")) {
				Cred.save(username.getText(), password.getText());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Credentials Saved.");
				alert.show();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Invalid Username/Password.");
				alert.show();
			}
		});

		return vbox;
	}

}
