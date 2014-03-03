/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataviewer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DataViewer extends Application {   
    DataOutput dataoutput = new DataOutput();
    DataInput datainput = new DataInput(dataoutput);
    @Override
    public void start(Stage primaryStage) {
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
        Group root = new Group();
        final TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        tabPane.setPrefSize(400, 400);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        final Tab tab1 = new Tab();
        tab1.setText("Input");
        tab1.setContent(addInput());
        final Tab tab2 = new Tab();
        tab2.setText("View");
        tab2.setContent(addViewer());
        tabPane.getTabs().addAll(tab1, tab2);
        root.getChildren().add(tabPane);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public Group addInput(){
        
        return datainput.get();
    }
    
    /*
    This function creates the initial viewer that is to be added to the TabbedPane. I have chosen to do it like this such that the code to make the table view initially is separated from the main code to make it easier to manage.
    */
           
    public Group addViewer(){
        
        return dataoutput.get(); // This function call returns the initial table. It is to be added to the TabbedPane as a child. 
    }
    
}
