package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {
	
	private static Scene scene;
	
	@FXML private MenuItem mComprar;
	
	@FXML private MenuItem mClient;
	
	@FXML private MenuItem mEmployee;
	
	@FXML private MenuItem mAboutWork;
	
	@FXML private MenuItem mConfirm;
	
	@FXML private MenuItem mCategory;
	
	@FXML private MenuItem mProduct;

	
	@FXML
	public void onMComprarAction() {
		loadView("/view/ComprasView.fxml");
	}
	
	@FXML
	public void OnMProductAction() {
		
		loadView("/view/ProductListView.fxml");
		
	}
	
	@FXML
	public void OnMConfirmAction() {
		
		loadView("/view/LogoutView.fxml");
		
	}
	@FXML
	public void OnMCategoryAction() {
		loadView("/view/CategoryListView.fxml");
	}
	
	@FXML
	public void onMClientAction() {
		loadView("/view/ClientListView.fxml");
		
	}
	
	@FXML
	public void onMStateAction() {
		loadView("/view/StateListView.fxml");
	}
	
	@FXML
	public void onMEmployeeAction() {
		
		loadView("/view/EmployeeListView.fxml");
		
	}
	
	@FXML
	public void onMAboutWorkAction() {
		loadView("/view/AboutWorkView.fxml");
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void loadView(String url)  {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
		VBox vBox = null;
		try {
			vBox = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = getScene();
		VBox mainVbox = (VBox)((ScrollPane) scene.getRoot()).getContent();
		
		Node mainMenu = mainVbox.getChildren().get(0);
		mainVbox.getChildren().clear();
		mainVbox.getChildren().add(mainMenu);
		mainVbox.getChildren().addAll(vBox.getChildren());
		
		
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		MainViewController.scene = scene;
	}
	
	
	
	
	
	

}
