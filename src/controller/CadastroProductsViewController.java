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
import model.entities.Product;
import model.services.ProductService;
import util.Utils;
import view.listeners.DataChangeListener;
import view.validations.Validations;

public class CadastroProductsViewController implements Initializable {
	
	private Product product;
	private ProductService service;
	private List<DataChangeListener> dataChangeListener =  new ArrayList<>();
	
	@FXML private TextField txtName;
	@FXML private TextField txtPrice;
	@FXML private TextField txtQuantity;
	@FXML private Button btConfirmar;
	@FXML private Button btCancelar;
	
	@FXML private Label lNameError;
	@FXML private Label lPriceError;
	@FXML private Label lQuantityError;
	
	
	

	@FXML
	private void onBtConfirmarAction(ActionEvent event) {
		
		
		
		boolean teste =  false;
		lNameError.setVisible(false);
		lPriceError.setVisible(false);
		lQuantityError.setVisible(false);
		
		
		if(Validations.tfIsNull(txtName.getText())) {
			lNameError.setVisible(true);
			teste = true;
		}
		if(Validations.tfIsNull(txtPrice.getText()) || Validations.isNumber(txtPrice.getText()) == null) {
			lPriceError.setVisible(true);
			teste = true;
			
		}
		if(Validations.tfIsNull(txtQuantity.getText()) || Validations.isInteger(txtQuantity.getText()) == null) {
			lQuantityError.setVisible(true);
			teste = true;
		}
		
		if(teste == false) {
			
			product.setName(txtName.getText());
			product.setPrice(Float.parseFloat(txtPrice.getText()));
			product.setQuantity(Integer.parseInt(txtQuantity.getText()));
			
			service.insere(product);
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
		Validations.setTextFieldMaxLength(txtName, 30);
		
		
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
		if(this.product.getIdProduto() != null) {
			txtName.setText(product.getName());
			txtPrice.setText(Float.toString(product.getPrice()));
			txtQuantity.setText(Integer.toString(product.getQuantity()));
		}
		
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public ProductService getService() {
		return service;
	}



	public void setService(ProductService service) {
		this.service = service;
	}
	
	

}
