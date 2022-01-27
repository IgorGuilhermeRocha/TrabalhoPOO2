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
import model.services.CategoryService;
import util.Utils;
import view.listeners.DataChangeListener;

public class CategoryListViewController implements Initializable, DataChangeListener {

	private CategoryService service;
	private ObservableList<Category> list;

	@FXML
	TableView<Category> tvCategory;
	@FXML
	TableColumn<Category, Integer> tcIdCategory;
	@FXML
	TableColumn<Category, String> tcName;
	@FXML
	TableColumn<Category, Category> tableColumnEDIT;
	@FXML
	TableColumn<Category, Category> tableColumnREMOVE;

	@FXML
	private Button btNovo;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		CreateTelaCadastro(new Category(), new Stage(), Utils.getCurrentStage(event));
		updateTableView();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setService(new CategoryService());
		initNodes();
		updateTableView();
		initEditButtons();
		initRemoveButtons();

	}

	public void initNodes() {

		tcIdCategory.setCellValueFactory(new PropertyValueFactory<>("id_category"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		Stage stage = (Stage) MainViewController.getScene().getWindow();
		tvCategory.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service != null) {
			if (service.findAll() != null) {
				list = FXCollections.observableArrayList(service.findAll());
				tvCategory.setItems(list);
			}
		}

		initEditButtons();
		initRemoveButtons();
	}

	public void CreateTelaCadastro(Category e, Stage stage, Stage pai) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateCategory.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
		CadastroCategoryViewController controller = loader.getController();
		controller.setCategory(e);
		controller.setService(new CategoryService());

		if (e.getId_category() != null) {
			controller.updateFormData();
		}

		controller.subscribeDataChangeListener(this);

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Cadastro de categorias");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(pai);
		stage.show();

	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Category, Category>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Category obj, boolean empty) {
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

	protected void removeEntity(Category obj) {
		int i = JOptionPane.showConfirmDialog(null, "Tem certeza ?");
		if (i != 1) {
			service.remove(obj.getId_category());
			updateTableView();
		}
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Category, Category>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Category obj, boolean empty) {
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

	@Override
	public void onDataChange() {
		updateTableView();

	}

	public CategoryService getService() {
		return service;
	}

	public void setService(CategoryService service) {
		this.service = service;
	}

}
