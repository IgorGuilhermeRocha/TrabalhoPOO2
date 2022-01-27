package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.services.EmployeeService;
import view.validations.Validations;

public class LoginViewController implements Initializable{
	
	private EmployeeService service;
	private static  Stage myStage;
	
	@FXML private ImageView javaImg;
	@FXML private ImageView banco;
	@FXML private TextField tfEmail;
	@FXML private PasswordField pfPassword;
	@FXML private Label lErro;
	@FXML private Button btConfirmar;
	
	
	public void onBtConfirmarAction() throws IOException {
		if(service == null) {
			setService();
		}
		
		lErro.setVisible(false);
		
		String errors ="";
		if( Validations.tfIsNull(tfEmail.getText())== true){
			errors+="Email não foi preenchido\n";
		
		}
		
		if(Validations.tfIsNull(pfPassword.getText()) == true) {
			errors+="Senha não foi preenchida";
		}
		if(!errors.equals("")){
			JOptionPane.showMessageDialog(null,"Erro: \n"+errors, null,JOptionPane.WARNING_MESSAGE);
		}else {
			
		if(!service.login(tfEmail.getText(), pfPassword.getText()) == true) {
			myStage.close();
			loadMainView(new Stage());
		}else {
			lErro.setVisible(true);
		}
		}
	
	}
	
	
	public void loadMainView(Stage stage) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
		ScrollPane scrollPane = loader.load();
		
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		
		Scene scene = new Scene(scrollPane);
		stage.setScene(scene);
		stage.setTitle("Tela Principal");
		MainViewController.setScene(scene);
		stage.show();
	}
	
	
	public EmployeeService getService() {
		return service;
	}
	
	public void setService() {
		this.service = new EmployeeService();
	}
	
	public TextField getTfEmail() {
		return tfEmail;
	}
	
	public void setTfEmail(TextField tfEmail) {
		this.tfEmail = tfEmail;
	}
	
	public PasswordField getPfPassword() {
		return pfPassword;
	}
	
	public void setPfPassword(PasswordField pfPassword) {
		this.pfPassword = pfPassword;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Validations.setTextFieldMaxLength(tfEmail, 30);
		Validations.setTextFieldMaxLength(pfPassword, 12);
		javaImg.setImage(new Image(getClass().getResourceAsStream("java.png")));
		banco.setImage(new Image(getClass().getResourceAsStream("post.png")));
		tfEmail.setText("");
		pfPassword.setText("");
		
		
	}


	public static Stage getMyStage() {
		return myStage;
	}


	public static void setMyStage(Stage myStage) {
		LoginViewController.myStage = myStage;
	}
	
	
	

}
