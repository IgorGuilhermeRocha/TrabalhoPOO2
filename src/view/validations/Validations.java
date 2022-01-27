package view.validations;

import javafx.scene.control.TextField;

public class Validations {

	public static boolean tfIsNull(String texto) {
		if(texto == null || texto.trim().equals("")) {
			return true;
		}
		return false;
		
	}
	public static Integer isInteger(String text) {
		int n;
		try {
			n = Integer.parseInt(text);
			if(n < 0) {
				return null;
			}
		}catch(NumberFormatException e) {
			return null;
		}
		return n;
	}
	public static Float isNumber(String text) {
		float n;
		try {
			 n =  Float.parseFloat(text);
			if(n < 0) {
				return null;
			}
		}catch(NumberFormatException e) {
			return null;
		}
		return n;
	}
	
	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && newValue.length() > max) {
	        	txt.setText(oldValue);
	        }
	    });
	}
}
