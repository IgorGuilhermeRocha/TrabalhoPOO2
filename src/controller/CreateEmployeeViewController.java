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
import model.services.EmployeeService;
import util.Utils;
import view.listeners.DataChangeListener;
import view.validations.Validations;

public class CreateEmployeeViewController implements Initializable {

	private Employee employee;
	private EmployeeService service;

	@FXML private TextField tfEmail;
	@FXML private PasswordField pfPassword;
	@FXML private Label errorEmail;
	@FXML private Label errorPassword;
	@FXML private Button btConfirmar;
	@FXML private Button btCancelar;

	private List<DataChangeListener> dataChangeListener = new ArrayList<>();

	@FXML
	private void onBtConfirmarAction(ActionEvent event) {

		boolean teste = false;
		errorEmail.setVisible(false);
		errorPassword.setVisible(false);

		if (Validations.tfIsNull(tfEmail.getText())) {
			errorEmail.setVisible(true);
			teste = true;
		}
		if (Validations.tfIsNull(pfPassword.getText())) {
			errorPassword.setVisible(true);
			teste = true;
		}
		if (teste == false) {

			employee.setEmail(tfEmail.getText());
			employee.setPassword(pfPassword.getText());
			service.insere(employee);
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
		Validations.setTextFieldMaxLength(tfEmail, 30);
		Validations.setTextFieldMaxLength(pfPassword, 12);

	}

	
	public void loadEmployee() {
		if (this.employee != null) {
			if (this.employee.getId() != null) {
				this.tfEmail.setText(this.employee.getEmail());
				this.pfPassword.setText(this.employee.getPassword());
			}
		}
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmployeeService getService() {
		return service;
	}

	public void setService(EmployeeService service) {
		this.service = service;
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
