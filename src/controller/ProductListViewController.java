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
import model.entities.Category;
import model.entities.Client;
import model.entities.Product;
import model.services.ProductService;
import model.services.StateService;
import util.Utils;
import view.listeners.DataChangeListener;

public class ProductListViewController implements Initializable, DataChangeListener {

	private ProductService service;

	private ObservableList<Product> list;

	@FXML
	private TableView<Product> tvProducts;
	@FXML
	private TableColumn<Product, Integer> tcId;
	@FXML
	private TableColumn<Product, String> tcName;
	@FXML
	private TableColumn<Product, Float> tcPrice;
	@FXML
	private TableColumn<Product, Integer> tcQuantity;

	@FXML
	private TableColumn<Product, Product> tableColumnEDIT;

	@FXML
	private TableColumn<Product, Product> tableColumnREMOVE;

	@FXML
	private Button btNovo;
	
	@FXML
	public void onBtNovoAction(ActionEvent event) {
		CreateTelaCadastro(new Product(), new Stage(), Utils.getCurrentStage(event));
		updateTableView();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setService(new ProductService());
		initNodes();
		updateTableView();

	}

	public void updateTableView() {
		if (service != null) {
			if (service.findAll() != null) {
				list = FXCollections.observableArrayList(service.findAll());
				tvProducts.setItems(list);
			}
		}

		initEditButtons();
		initRemoveButtons();
	}	
	
	public void CreateTelaCadastro(Product e,Stage stage, Stage pai) {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CadastroProductsView.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
		CadastroProductsViewController controller = loader.getController();

		controller.setService(new ProductService());
		controller.setProduct(e);
		
		if(e.getIdProduto() != null) {
			controller.updateFormData();
		}
		controller.subscribeDataChangeListener(this);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Cadastro de Produtos");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(pai);
		stage.show();

		
	}
	
	public void initNodes() {
		tcId.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		Stage stage = (Stage) MainViewController.getScene().getWindow();
		tvProducts.prefHeightProperty().bind(stage.heightProperty());
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Product, Product>() {
		 private final Button button = new Button("Remover");
		 @Override
		 protected void updateItem(Product obj, boolean empty) {
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

	protected void removeEntity(Product obj) {
		int i = JOptionPane.showConfirmDialog(null, "Tem certeza ?");
		if( i!= 1) {
		service.remove(obj.getIdProduto());
		updateTableView();
		}
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Product, Product>() {
		 private final Button button = new Button("Editar");
		 @Override
		 protected void updateItem(Product obj, boolean empty) {
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

	public ProductService getService() {
		return service;
	}

	public void setService(ProductService service) {
		this.service = service;
	}


	

}
