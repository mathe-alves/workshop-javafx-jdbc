package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable{

	//dependencia para o departamento
	private Department entity;
	
	private DepartmentService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);//vai adicionar na lista apos o save
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
		entity = getFormData(); //vai ser responsavel por pegar os dados na caixa do form e instanciar um dp 
		service.saveOrUpdate(entity);//salvou no db
		notifyDataChangeListeners();//vai notificar o db
		Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		}
	
	//vai executar a interface
	private void notifyDataChangeListeners() {
		for( DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private Department getFormData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();//aqui serve para fechar após o clique
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId); //declarando que o id será só int
		Constraints.setTextFieldMaxLength(txtName, 30); //declarando que será 30 caracter no nome
		
	}
	
	//aqui vai jogar nas caixinhas de texto (id e nome) os dados que estão no objeto entity
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));//converteu int para string
		txtName.setText(entity.getName());
	}

}
