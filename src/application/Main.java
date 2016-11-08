package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import graph.Graph;
import graph.RandomLayout;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import services.QueryService.DBConnection;
import services.QueryService.QueryService;
import view.MainViewController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private static DBConnection dbConnection;
	private Parent mainView;
	private Graph graph;
    private MainViewController mainViewController;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initRootLayout();
		showMainView();
        //showGraph(new ArrayList<>(Arrays.asList("Tuan Anh", "Viet Anh")));
	}

	public static void main(String[] args) {
		launch(args);
		dbConnection.close();
	}

	public static DBConnection getDbConnection(){
		return dbConnection;
	}

	public static void setUpConnection(String server, String port, String username, String password, String dbname){
		if(Objects.isNull(dbConnection)){
			dbConnection = new DBConnection(server, Integer.valueOf(port), dbname,username, password);
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
		if (mainView == null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/MainView.fxml"));
			try {
				mainView = loader.load();
                mainViewController = (MainViewController) loader.getController();
                mainViewController.setApplication(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		rootLayout.setCenter(mainView);
	}

	public void showGraph(ArrayList<String> authors) {
        //int[][] colaborations = new int[2][2];
		int[][] colaborations = QueryService.getCollaborationMatrix(authors);
		graph = new Graph(this, authors, colaborations);
		rootLayout.setCenter(graph.getScrollPane());
		RandomLayout layout = new RandomLayout(graph);
		layout.execute();
	}
}
