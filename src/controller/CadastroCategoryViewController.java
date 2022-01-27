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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Category;
import model.services.CategoryService;
import util.Utils;
import view.listeners.DataChangeListener;
import view.validations.Validations;

public class CadastroCategoryViewController implements Initializable {
	
	private CategoryService service;
	private Category category;
	private List<DataChangeListener> dataChangeListener = new ArrayList<>();
	
	@FXML private TextField txtName;
	@FXML private Label lNameError;
	@FXML private Button btConfirmar;
	@FXML private Button btCancelar;
	
	@FXML
	private void onBtConfirmarAction(ActionEvent event) {
		lNameError.setVisible(false);
		
		if(Validations.tfIsNull(txtName.getText())) {
			lNameError.setVisible(true);
		}else {
			category.setName(txtName.getText());
			service.insert(category);
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
	
	
	public CategoryService getService() {
		return service;
	}
	
	public void setService(CategoryService service) {
		this.service = service;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Validations.setTextFieldMaxLength(this.txtName, 15);
		
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);

	}
	
	public void notifyDataChangeListener() {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChange();
		}

	}
	public void updateFormData(){
		if(this.category.getId_category() != null) {
			this.txtName.setText(this.category.getName());
		}
	}
	

}
