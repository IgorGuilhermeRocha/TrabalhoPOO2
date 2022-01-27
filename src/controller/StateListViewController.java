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
import model.entities.State;
import model.services.EmployeeService;
import model.services.StateService;
import util.Utils;
import view.listeners.DataChangeListener;

public class StateListViewController implements Initializable,DataChangeListener {

	private StateService service;

	private ObservableList<State> list;
	
	@FXML
	private TableView<State> tvStates;
	
	@FXML
	private TableColumn<State,Integer> tcId;
	
	@FXML
	private TableColumn<State,String> tcSigla;
	
	@FXML
	private TableColumn<State,State> tcRemove;
	
	@FXML
	private TableColumn<State,State> tcEdit;
	
	@FXML
	private Button btNovo;
	
	@FXML
	public void onBtNovoAction(ActionEvent event) {
		CreateTelaCadastro(new State(),new Stage(),Utils.getCurrentStage(event));
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setService(new StateService());
		initNodes();
		updateTableView();
	
	}
	
	public void initNodes() {
		tcId.setCellValueFactory(new PropertyValueFactory<>("idState"));
		tcSigla.setCellValueFactory(new PropertyValueFactory<>("initials"));
		Stage stage = (Stage) MainViewController.getScene().getWindow();
		tvStates.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service != null) {
			list = FXCollections.observableArrayList(service.findAll());
			
			}
		tvStates.setItems(list);
		initEditButtons();
		initRemoveButtons();
	}

	public void CreateTelaCadastro(State e,Stage stage, Stage pai) {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CadastroEstadosView.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
		CadastroEstadosViewController controller = loader.getController();
		controller.setState(e);
		
		controller.setService(new StateService());
		controller.subscribeDataChangeListener(this);
		controller.updateFormData();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Cadastro de Estados");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(pai);
		stage.show();

		
	}
	
	private void initEditButtons() {
		tcEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcEdit.setCellFactory(param -> new TableCell<State, State>() {
		 private final Button button = new Button("Editar");
		 @Override
		 protected void updateItem(State obj, boolean empty) {
		 super.updateItem(obj, empty);
		 if (obj == null) {
		 setGraphic(null);
		 return;
		 }
		 setGraphic(button);
		 button.setOnAction(
		 event -> CreateTelaCadastro(
		 obj,new Stage(),Utils.getCurrentStage(event)));
		 }
		 });
		}
	
	private void initRemoveButtons() {
		tcRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcRemove.setCellFactory(param -> new TableCell<State, State>() {
		 private final Button button = new Button("Remover");
		 @Override
		 protected void updateItem(State obj, boolean empty) {
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

	protected void removeEntity(State obj) {
		int i = JOptionPane.showConfirmDialog(null, "Tem certeza ?");
		if( i!= 1) {
		service.delete(obj.getIdState());
		updateTableView();
		}
	}

	public StateService getService() {
		return service;
	}


	public void setService(StateService service) {
		this.service = service;
	}


	public ObservableList<State> getList() {
		return list;
	}


	public void setList(ObservableList<State> list) {
		this.list = list;
	}


	@Override
	public void onDataChange() {
		updateTableView();
		
	}
	
	
}
