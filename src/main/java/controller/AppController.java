package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import model.Database;
import model.Human;
import model.HumanRepository;
import view.AppView;
import view.HumanFX;

public class AppController implements Initializable {

    @FXML private TableView<HumanFX> tableView;
    @FXML private TableColumn<HumanFX, Integer> idCol;
    @FXML private TableColumn<HumanFX, String>  fullNameCol;
    @FXML private TableColumn<HumanFX, Double>  incomeCol;
    @FXML private TableColumn<HumanFX, String>  birthdayCol;

    @FXML private TextField idField;
    @FXML private TextField fullNameField;
    @FXML private TextField incomeField;
    @FXML private TextField birthdayField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button clearSearchButton;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final Database database = new Database("human.db");
    private static final HumanRepository repo = new HumanRepository(database);
    private AppView appView;

    public AppController() {
        this.appView = new AppView();
    }

    public static void main(String[] args) {
        new AppController().appView.run(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        incomeCol.setCellValueFactory(new PropertyValueFactory<>("income"));
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(loadAll());
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> onRowSelected(newVal));
    }

    private ObservableList<HumanFX> loadAll() {
        ObservableList<HumanFX> list = FXCollections.observableArrayList();
        List<Human> fromDb = repo.loadAll();
        for (Human h : fromDb) list.add(toFX(h));
        return list;
    }

    private HumanFX toFX(Human h) {
        String birthday = h.getBirthday() != null ? sdf.format(h.getBirthday()) : "";
        return new HumanFX(h.getId(), h.getFullName(), h.getIncome(), birthday);
    }

    private void setFieldsDisabled(boolean disabled) {
        fullNameField.setDisable(disabled);
        incomeField.setDisable(disabled);
        birthdayField.setDisable(disabled);
    }

    private void setButtons(boolean add, boolean update, boolean delete,
                            boolean cancel, boolean save) {
        addButton.setDisable(add);
        updateButton.setDisable(update);
        deleteButton.setDisable(delete);
        cancelButton.setDisable(cancel);
        saveButton.setDisable(save);
    }

    private void clearFields() {
        idField.clear();
        fullNameField.clear();
        incomeField.clear();
        birthdayField.clear();
    }

    @FXML
    public void onAddButtonAction() {
        tableView.getSelectionModel().clearSelection();
        setFieldsDisabled(false);
        setButtons(true, true, true, false, false);
        clearFields();
    }

    @FXML
    public void onCancelButtonAction() {
        setFieldsDisabled(true);
        setButtons(false, true, true, true, true);
        clearFields();
        tableView.getSelectionModel().clearSelection();
    }

    @FXML
    public void onSaveButtonAction() {
        try {
            Human h = new Human();
            h.setFullName(fullNameField.getText());
            h.setIncome(Double.parseDouble(incomeField.getText()));
            h.setBirthday(sdf.parse(birthdayField.getText()));
            Human saved = repo.create(h);
            HumanFX fx = toFX(saved);
            tableView.getItems().add(fx);
            tableView.getSelectionModel().select(fx);
            setFieldsDisabled(true);
            setButtons(false, true, true, true, true);
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao salvar: " + e.getMessage()).show();
        }
    }

    @FXML
    public void onUpdateButtonAction() {
        HumanFX selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try {
            Human h = repo.loadFromId(selected.getId());
            h.setFullName(fullNameField.getText());
            h.setIncome(Double.parseDouble(incomeField.getText()));
            h.setBirthday(sdf.parse(birthdayField.getText()));
            repo.update(h);
            selected.setFullName(h.getFullName());
            selected.setIncome(h.getIncome());
            selected.setBirthday(sdf.format(h.getBirthday()));
            tableView.refresh();
            setFieldsDisabled(true);
            setButtons(false, true, true, true, true);
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao atualizar: " + e.getMessage()).show();
        }
    }

    @FXML
    public void onDeleteButtonAction() {
        HumanFX selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        Human h = repo.loadFromId(selected.getId());
        repo.delete(h);
        tableView.getItems().remove(selected);
        clearFields();
        setButtons(false, true, true, true, true);
    }

    private void onRowSelected(HumanFX selected) {
        if (selected == null) return;
        idField.setText(String.valueOf(selected.getId()));
        fullNameField.setText(selected.getFullName());
        incomeField.setText(String.valueOf(selected.getIncome()));
        birthdayField.setText(selected.getBirthday());
        setButtons(false, false, false, true, true);
    }

    @FXML
    public void onSearchButtonAction() {
        String term = searchField.getText().trim();
        if (term.isEmpty()) {
            tableView.setItems(loadAll());
            return;
        }
        ObservableList<HumanFX> results = FXCollections.observableArrayList();
        for (Human h : repo.searchByName(term)) {
            results.add(toFX(h));
        }
        tableView.setItems(results);
        clearFields();
        setButtons(false, true, true, true, true);
    }

    @FXML
    public void onClearSearchButtonAction() {
        searchField.clear();
        tableView.setItems(loadAll());
    }
}