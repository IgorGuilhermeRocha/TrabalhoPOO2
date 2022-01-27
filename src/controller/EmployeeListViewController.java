package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Employee;
import model.services.EmployeeService;
import util.Utils;
import view.listeners.DataChangeListener;

public class EmployeeListViewController implements Initializable, DataChangeListener {

	private EmployeeService service;

	private ObservableList<Employee> list;

	@FXML
	private TableView<Employee> tvEmployees;
	@FXML
	private TableColumn<Employee, Integer> tcId;
	@FXML
	private TableColumn<Employee, String> tcEmail;
	@FXML
	private TableColumn<Employee, String> tcSenha;
	@FXML
	private TableColumn<Employee, Employee> tableColumnEDIT;
	@FXML
	private TableColumn<Employee, Employee> tableColumnREMOVE;
	@FXML
	private Button btNovo;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		CreateTelaCadastro(new Employee(), new Stage(), Utils.getCurrentStage(event));
		updateTableView();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setService(new EmployeeService());
		initNodes();
		updateTableView();

	}

	public void CreateTelaCadastro(Employee e, Stage stage, Stage pai) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateEmployeeView.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
		CreateEmployeeViewController controller = loader.getController();
		controller.setEmployee(e);
		controller.loadEmployee();
		controller.setService(new EmployeeService());
		controller.subscribeDataChangeListener(this);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Cadastro de Funcionários");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(pai);
		stage.show();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Employee, Employee>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Employee obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> CreateTelaCadastro(obj, new Stage(), Utils.getCurrentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Employee, Employee>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Employee obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}

		});
	}

	protected void removeEntity(Employee obj) {
		int i = JOptionPane.showConfirmDialog(null, "Tem certeza ?");
		if (i != 1) {
			service.delete(obj.getId());
			updateTableView();
		}
	}

	public void initNodes() {
		tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tcSenha.setCellValueFactory(new PropertyValueFactory<>("password"));

		Stage stage = (Stage) MainViewController.getScene().getWindow();
		tvEmployees.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service != null) {
			list = FXCollections.observableArrayList(service.findAll());
		}
		tvEmployees.setItems(list);
		initEditButtons();
		initRemoveButtons();
	}

	public EmployeeService getService() {
		return service;
	}

	public void setService(EmployeeService service) {
		this.service = service;
	}

	@Override
	public void onDataChange() {
		updateTableView();

	}

}
