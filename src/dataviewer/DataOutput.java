/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataviewer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author bertrandbrompton
 */
public class DataOutput {
    private Group root;
    private TableColumn invitedCol;
    private TableColumn firstNameCol;
    private TableColumn lastNameCol;
    private TableColumn emailCol;
    private TableView tableView;
    
    public DataOutput(){
            
        root = new Group();
        
        
        invitedCol = new TableColumn<Person, Boolean>();
        invitedCol.setText("Invited");
        invitedCol.setMinWidth(50);
        invitedCol.setCellValueFactory(new PropertyValueFactory("invited"));
//        invitedCol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {
// 
//            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) {
//                return new DataInput.CheckBoxTableCell<Person, Boolean>();
//            }
//        });
        invitedCol.setEditable(false);
        //invitedCol.setStyle("-fx-opacity: 1");
        
        firstNameCol = new TableColumn();
        firstNameCol.setText("First");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        lastNameCol = new TableColumn();
        lastNameCol.setText("Last");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        emailCol = new TableColumn();
        emailCol.setText("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));
        tableView = new TableView();
        
        MutualDatastore mutualdatastore = new MutualDatastore();
        tableView.setItems(mutualdatastore.getInitialData());
        tableView.getColumns().addAll(invitedCol, firstNameCol, lastNameCol, emailCol);
        root.getChildren().add(tableView);
        
    }
    
    public Group get(){
        return root;
    }
    public TableColumn getInvited(){
        return invitedCol;
    }
    public TableColumn getFirstName(){
        return firstNameCol;       
    }
    public TableColumn getlastName(){
        return lastNameCol;
    }
    public TableColumn getEmail(){
        return emailCol;
    }
    public TableView getTable(){
        return tableView;
    }
}
