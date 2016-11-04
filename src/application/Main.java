package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.MainViewController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private static Connection connection;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initRootLayout();
		showMainView();
	}

	public static void main(String[] args) {
		launch(args);
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getDbConnection(){
		return connection;
	}

	public static void setUpConnection(String server, String port, String username, String password, String dbname){
		try {
	         Class.forName("org.postgresql.Driver");
	         String url = "jdbc:postgresql://" + server + ":" + port + "/" + dbname;
	         connection = DriverManager
	            .getConnection(url,
	            username, password);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
	    }
	}


	public void initRootLayout() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/RootLayout.fxml"));
		try {
			rootLayout = (BorderPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void showMainView() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/MainView.fxml"));
		try {
			Parent mainView = (Parent) loader.load();
			rootLayout.setCenter(mainView);
			MainViewController mainViewController = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
