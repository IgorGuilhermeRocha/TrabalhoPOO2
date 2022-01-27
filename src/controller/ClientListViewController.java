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
import model.entities.Client;
import model.entities.State;
import model.services.ClientService;
import model.services.StateService;
import util.Utils;
import view.listeners.DataChangeListener;

public class ClientListViewController implements Initializable, DataChangeListener {
	
	private ClientService service;
	
	private ObservableList<Client> list;
	
	@FXML
	private TableView<Client> tvClients;
	@FXML
	private TableColumn<Client,Integer> tcId;
	@FXML
	private TableColumn<Client,String> tcName;
	@FXML
	private TableColumn<Client,String> tcLastName;
	@FXML
	private TableColumn<Client,String> tcCPF;
	@FXML
	private TableColumn<Client,String> tcPhone;
	@FXML
	private TableColumn<Client,State> tcState;
	@FXML
	private TableColumn<Client,Float> tcBalance;


	
	@FXML
	private TableColumn<Client,Client> tableColumnEDIT;
	
	@FXML
	private TableColumn<Client,Client> tableColumnREMOVE;
	
	
	@FXML
	private Button btNovo;
	
	
	
	@FXML
	public void onBtNovoAction(ActionEvent event) {
		CreateTelaCadastro(new Client(),new Stage(),Utils.getCurrentStage(event));
		updateTableView();
	}
	
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setService(new ClientService());
		initNodes();
		updateTableView();
		
	}
	
	public void initNodes() {
		
		tcId.setCellValueFactory(new PropertyValueFactory<>("Id_client"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tcCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		tcState.setCellValueFactory(new PropertyValueFactory<>("state"));
		tcBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
		Stage stage = (Stage) MainViewController.getScene().getWindow();
		tvClients.prefHeightProperty().bind(stage.heightProperty());
	}




	public void updateTableView() {
		if(service != null) {
		list = FXCollections.observableArrayList(service.findAll());
		}
		tvClients.setItems(list);
		initEditButtons();
		initRemoveButtons();
	}
	
	public ClientService getService() {
		return service;
	}



	public void setService(ClientService service) {
		this.service = service;
	}
	
	public void CreateTelaCadastro(Client e,Stage stage, Stage pai) {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ClientsFormView.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
		CadastroClientesViewController controller = loader.getController();
		controller.setClient(e);
		controller.setService(new ClientService());
		controller.setState(new StateService());
		if(e.getId_client() != null) {
			controller.updateFormData();
		}
		controller.subscribeDataChangeListener(this);
		controller.loadObjects();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Cadastro de Clientes");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(pai);
		stage.show();

		
	}



	
	
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Client, Client>() {
		 private final Button button = new Button("Remover");
		 @Override
		 protected void updateItem(Client obj, boolean empty) {
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

	protected void removeEntity(Client obj) {
		int i = JOptionPane.showConfirmDialog(null, "Tem certeza ?");
		if( i!= 1) {
		service.remove(obj.getId_client());
		updateTableView();
		}
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Client, Client>() {
		 private final Button button = new Button("Editar");
		 @Override
		 protected void updateItem(Client obj, boolean empty) {
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

	@Override
	public void onDataChange() {
		updateTableView();
		
	}
	


}
