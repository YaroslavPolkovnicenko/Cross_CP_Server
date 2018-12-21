package sample.Server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Employees.*;

import java.util.ArrayList;

public class ServerController {

    @FXML
    private TableView<Employee> table;

    @FXML
    private TableColumn<Employee, String> type;

    @FXML
    private TableColumn<Employee, String> family;

    @FXML
    private TableColumn<Employee, Integer> age;

    @FXML
    private TableColumn<Employee, Double> experience;

    @FXML
    void initialize() {

        ArrayList<Employee> list = new ArrayList<>();

        ServerThread serverThread = new ServerThread(list, this);
        Thread thread = new Thread(serverThread);
        thread.start();
    }

    public void showTable(ArrayList<Employee> arrayList) {

        ObservableList<Employee> list = FXCollections.observableArrayList(arrayList);

        type.setCellValueFactory(new PropertyValueFactory<Employee, String>("type"));
        family.setCellValueFactory(new PropertyValueFactory<Employee, String>("family"));
        age.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("age"));
        experience.setCellValueFactory(new PropertyValueFactory<Employee, Double>("experience"));

        table.setItems(list);
    }
}