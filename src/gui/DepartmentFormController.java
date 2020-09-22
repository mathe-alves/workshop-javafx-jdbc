package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{

	//dependencia para o departamento
	private Department entity;
	
	//declaracao dos componentes da tela
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	@FXML
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId); //declarando que o id ser� s� int
		Constraints.setTextFieldMaxLength(txtName, 30); //declarando que ser� 30 caracter no nome
		
	}
	
	//aqui vai jogar nas caixinhas de texto (id e nome) os dados que est�o no objeto entity
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));//converteu int para string
		txtName.setText(entity.getName());
	}

}
