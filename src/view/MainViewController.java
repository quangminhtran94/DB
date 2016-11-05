package view;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


import application.Author;
import application.Main;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import services.QueryService.QueryService;

public class MainViewController {
	private List<Author> authors = new ArrayList<Author>();

	private ObservableList<Author> masterData;

	private FilteredList<Author> filteredData;

	private List<Author> choosenAuthors = new ArrayList<Author>();

	@FXML
	private TableView<Author> authorView;

	@FXML
	private TableColumn<Author, String> idCol;

	@FXML
	private TableColumn<Author, String> nameCol;

	@FXML
	private TextField serverTxt;

	@FXML
	private TextField portTxt;

	@FXML
	private TextField usernameTxt;

	@FXML
	private TextField dbnameTxt;

	@FXML
	private PasswordField passwordTxt;

	@FXML
	private TextField filterTxt;


	@FXML
	private Button connectBtn;

	@FXML
	private Button generateBtn;

	@FXML
	private Button addBtn;

	@FXML
	private Button removeBtn;

	@FXML
	private ListView<Author> choosenListView;


	@FXML
	private void initialize(){

		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Author choosenAuthor = authorView.getSelectionModel().getSelectedItem();
				choosenAuthors.add(choosenAuthor);
				choosenListView.setItems(FXCollections.observableArrayList(choosenAuthors));
			}
		});

		removeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Author choosenAuthor = choosenListView.getSelectionModel().getSelectedItem();
				choosenAuthors.remove(choosenAuthor);
				choosenListView.setItems(FXCollections.observableArrayList(choosenAuthors));
			}
		});

		filterTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(author -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(author.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (author.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            authorView.setItems(filteredData);
        });

		connectBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String server = serverTxt.getText();
				String port = portTxt.getText();
				String dbname = dbnameTxt.getText();
				String username = usernameTxt.getText();
				String password = passwordTxt.getText();
				Main.setUpConnection(server, port, username, password, dbname);
				getAuthors();
				authorView.setItems(masterData);
				idCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getId())));
				nameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
			}
		});

		generateBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				//TODO: click to generate graph
				ArrayList<String> names = new ArrayList<String>();
				for(int i = 0; i < choosenAuthors.size(); i++){
					names.add(choosenAuthors.get(i).getName());
				}
				int[][] result = QueryService.getCollaborationMatrix(names);
				System.out.println("TESTING");
			}
		});
	}



	private void getAuthors(){
		Connection connection = Main.getDbConnection().getConnection();
		Statement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM author;" );
			while (rs.next()) {
				int id = rs.getInt("id");
				String  name = rs.getString("name");
				this.authors.add(new Author(id, name));
			}
			rs.close();
			stmt.close();
			masterData =  FXCollections.observableArrayList(authors);
			filteredData = new FilteredList<>(masterData, p -> true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
