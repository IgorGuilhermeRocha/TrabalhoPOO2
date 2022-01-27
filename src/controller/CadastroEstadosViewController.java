package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Employee;
import model.entities.State;
import model.services.EmployeeService;
import model.services.StateService;
import util.Utils;
import view.listeners.DataChangeListener;
import view.validations.Validations;

public class CadastroEstadosViewController implements Initializable{

	private State state;
	
	private StateService service;
	
	private List<DataChangeListener> dataChangeListener =  new ArrayList<>();
	
	@FXML private Label lSiglaError;
	@FXML private TextField tfSigla;
	@FXML private Button btConfirmar;
	@FXML private Button btCancelar;
	
	@FXML
	private void onBtConfirmarAction(ActionEvent event) {
		
		
		
		boolean teste =  false;
		lSiglaError.setVisible(false);
		
		if(Validations.tfIsNull(tfSigla.getText())) {
			lSiglaError.setVisible(true);
			teste =  true;
		}
	
		if(teste == false) {
			
			state.setInitials(tfSigla.getText());
			service.insere(state);
			notifyDataChangeListener();
			Stage s = Utils.getCurrentStage(event);
			s.close();
			
		}
		
		
	}
	
	private void notifyDataChangeListener() {
		for(DataChangeListener listener : dataChangeListener) {
			listener.onDataChange();
		}
		
	}
	public void updateFormData() {
		if(this.state.getIdState() != null) {
			tfSigla.setText(state.getInitials());
		}
	}

	@FXML
	private void onBtCancelarAction(ActionEvent event) {
		Stage s = Utils.getCurrentStage(event);
		s.close();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Validations.setTextFieldMaxLength(tfSigla, 2);
		if(state != null) {
			tfSigla.setText(state.getInitials());
		}
	 
			
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);
		
	}


	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public StateService getService() {
		return service;
	}

	public void setService(StateService service) {
		this.service = service;
	}
	
	
	
	
	
}
