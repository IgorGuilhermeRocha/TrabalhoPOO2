package controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.Utils;

public class LogoutViewController {
	
	@FXML
	private Button btSair;
	
	@FXML
	private void onBtSair(ActionEvent event) {
		Utils.getCurrentStage(event).close();
		Main m =  new Main();	
		m.start(new Stage());
	}

}
