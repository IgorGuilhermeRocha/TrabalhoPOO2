package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.Client;
import model.entities.State;
import model.services.ClientService;
import model.services.StateService;
import util.Utils;
import view.listeners.DataChangeListener;
import view.validations.Validations;

public class CadastroClientesViewController implements Initializable {

	private Client client;

	private StateService state;

	private ClientService service;

	private ObservableList<State> list;

	private List<DataChangeListener> dataChangeListener = new ArrayList<>();


	@FXML
	private Label lNameError;
	@FXML
	private Label lCpfError;
	@FXML
	private Label lBalanceError;
	@FXML
	private Label lStatesError;

	@FXML
	private TextField txtName;
	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtCpf;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtBalance;
	@FXML
	private ComboBox<State> cbStates;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	private void onBtConfirmarAction(ActionEvent event) {
		
		boolean teste =  false;
		
		lNameError.setVisible(false);
		lBalanceError.setVisible(false);
		lCpfError.setVisible(false);
		lStatesError.setVisible(false);
		
		if(Validations.tfIsNull(txtName.getText())) {
			lNameError.setVisible(true);
			teste =  true;
		}
		
		if(Validations.tfIsNull(txtCpf.getText())) {
			lCpfError.setVisible(true);
			teste =  true;
		}
		
		if(Validations.tfIsNull(txtBalance.getText()) || Validations.isNumber(txtBalance.getText())== null) {
			lBalanceError.setVisible(true);
			teste =  true;
		}
		
		if(cbStates.getValue() == null) {
			lStatesError.setVisible(true);
			teste =  true;
		}
		

		if (teste == false) {

			client.setName(txtName.getText());
			client.setLastName(txtLastName.getText());
			client.setCpf(txtCpf.getText());
			client.setPhone(txtPhone.getText());
			client.setState(cbStates.getValue());
			client.setBalance(Float.parseFloat(txtBalance.getText()));

			service.insere(client);
			notifyDataChangeListener();
			Stage s = Utils.getCurrentStage(event);
			s.close();

		}

	}

	

	@FXML
	private void onBtCancelarAction(ActionEvent event) {
		Stage s = Utils.getCurrentStage(event);
		s.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeComboBoxDepartment();
		Validations.setTextFieldMaxLength(txtName, 15);
		Validations.setTextFieldMaxLength(txtLastName, 15);
		Validations.setTextFieldMaxLength(txtCpf, 11);
		Validations.setTextFieldMaxLength(txtPhone, 12);
		updateFormData();
	}

	
	public void updateFormData() {
		if (client != null) {
			txtName.setText(client.getName());
			txtLastName.setText(client.getLastName());
			txtCpf.setText(client.getCpf());
			txtPhone.setText(client.getPhone());
			cbStates.setValue(client.getState());
			txtBalance.setText(Float.toString(client.getBalance()));
		}
	}

	public void loadObjects() {
		List<State> l = state.findAll();
		list = FXCollections.observableArrayList(l);
		cbStates.setItems(list);

	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public StateService getState() {
		return state;
	}

	public void setState(StateService state) {
		this.state = state;
	}

	public ClientService getService() {
		return service;
	}

	public void setService(ClientService service) {
		this.service = service;
	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<State>, ListCell<State>> factory = lv -> new ListCell<State>() {
			@Override
			protected void updateItem(State item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getInitials());
			}
		};
		cbStates.setCellFactory(factory);
		cbStates.setButtonCell(factory.call(null));
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);

	}
	
	private void notifyDataChangeListener() {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChange();
		}

	}

}
