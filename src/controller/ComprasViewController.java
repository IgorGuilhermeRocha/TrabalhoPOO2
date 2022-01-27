package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Client;
import model.entities.Product;
import model.entities.Purcharses;
import model.services.ClientService;
import model.services.ProductService;
import model.services.PurcharsesService;
import view.validations.Validations;

public class ComprasViewController implements Initializable {

	private ProductService productService;
	private ClientService clientService;
	private PurcharsesService purcharsesService;
	private boolean checkClient;
	private boolean checkProduct;

	private Client client;
	private Product product;

	@FXML
	private TextField txtIdClient;
	@FXML
	private TextField txtIdProduct;
	@FXML
	private TextField txtQuantity;

	@FXML
	private Button btConfirmarClient;
	@FXML
	private Button btConfirmarProduct;
	@FXML
	private Button btConfirmarCompra;

	@FXML
	private Label lNameClient;
	@FXML
	private Label lCpfClient;
	@FXML
	private Label lBalanceClient;

	@FXML
	private Label lNameProduct;

	@FXML
	private Label lPriceProduct;

	@FXML
	private Label lQuantityProduct;

	@FXML
	private Label lIdClientError;

	@FXML
	private Label lQuantityError;

	@FXML
	private Label lIdProductError;

	@FXML
	public void confirmarClient() {
		lIdClientError.setVisible(false);
		lNameClient.setVisible(false);
		lCpfClient.setVisible(false);
		lBalanceClient.setVisible(false);

		if (Validations.tfIsNull(txtIdClient.getText()) || Validations.isInteger(txtIdClient.getText()) == null) {
			lIdClientError.setVisible(true);
		} else {
			client = clientService.find(Integer.parseInt(txtIdClient.getText()));
			if (client != null) {
				if (client.getId_client() != null) {
					lNameClient.setText(client.getName());
					lCpfClient.setText(client.getCpf());
					lBalanceClient.setText(Float.toString(client.getBalance()));
					lNameClient.setVisible(true);
					lCpfClient.setVisible(true);
					lBalanceClient.setVisible(true);
					checkClient = true;

				} else {
					lIdClientError.setVisible(true);
					checkClient = false;
				}
			} else {
				lIdClientError.setVisible(true);
				checkClient = false;
			}
		}
	}

	@FXML
	public void comprar() {
		if (checkClient == true && checkProduct == true) {
			lQuantityError.setVisible(false);
			if (Validations.tfIsNull(txtQuantity.getText()) || Validations.isInteger(txtQuantity.getText()) == null) {
				lQuantityError.setVisible(true);
			} else {
				if (Integer.parseInt(txtQuantity.getText()) <= product.getQuantity()) {
					int n = Integer.parseInt(txtQuantity.getText());
					float s = product.getPrice();
					if(n <= 0) {
						JOptionPane.showMessageDialog(null, "Quantidade inválida");
						return;
					}
					if (client.getBalance() >= n * s) {
						product.setQuantity(product.getQuantity() - Integer.parseInt(txtQuantity.getText()));
						client.setBalance(client.getBalance() - n * s);
						clientService.insere(client);
						productService.insere(product);
						Purcharses p =  new Purcharses();
						p.setClient(client);
						p.setProduct(product);
						p.setTotalPrice(n * s);
						purcharsesService.insert(p);
						client = new Client();
						product =  new Product();
						checkClient = false;
						checkProduct = false;
						updateLabels();
						JOptionPane.showMessageDialog(null, "Compra feita com sucesso");
					}else{
						JOptionPane.showMessageDialog(null, "Dinheiro insuficiente");
					}
				} else {
					lQuantityError.setVisible(true);
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "Cliente/Produto inválido(s) ");
		}

	}

	@FXML
	public void confirmarProduct() {
		lIdProductError.setVisible(false);
		lNameProduct.setVisible(false);
		lPriceProduct.setVisible(false);
		lQuantityProduct.setVisible(false);

		if (Validations.tfIsNull(txtIdProduct.getText()) || Validations.isInteger(txtIdProduct.getText()) == null) {
			lIdProductError.setVisible(true);
		} else {
			product = productService.find(Integer.parseInt(txtIdProduct.getText()));
			if (product != null) {
				if (product.getIdProduto() != null) {
					lNameProduct.setText(product.getName());
					lPriceProduct.setText(Float.toString(product.getPrice()));
					lQuantityProduct.setText(Integer.toString(product.getQuantity()));
					lNameProduct.setVisible(true);
					lQuantityProduct.setVisible(true);
					lPriceProduct.setVisible(true);
					checkProduct = true;

				} else {
					lIdProductError.setVisible(true);
					checkProduct = false;
				}
			} else {
				lIdProductError.setVisible(true);
				checkProduct = false;
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setProductService(new ProductService());
		setClientService(new ClientService());
		setPurcharsesService(new PurcharsesService());

	}
	
	public void updateLabels() {
		txtIdClient.setText("");
		txtIdProduct.setText("");
		txtQuantity.setText("");
		lNameClient.setText("");
		lCpfClient.setText("");
		lBalanceClient.setText("");
		lNameProduct.setText("");
		lQuantityProduct.setText("");
		lPriceProduct.setText("");
		
	}
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public PurcharsesService getPurcharsesService() {
		return purcharsesService;
	}

	public void setPurcharsesService(PurcharsesService purcharsesService) {
		this.purcharsesService = purcharsesService;
	}

}
