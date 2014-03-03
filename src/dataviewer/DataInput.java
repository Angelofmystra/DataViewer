
package dataviewer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
 
/**
 * A simple table that uses cell factories to add a control to a table
 * column and to enable editing of first/last name and email.
 *
 * @see javafx.scene.control.TableCell
 * @see javafx.scene.control.TableColumn
 * @see javafx.scene.control.TablePosition
 * @see javafx.scene.control.TableRow
 * @see javafx.scene.control.TableView
 */
public class DataInput {
    Group root;
    DataOutput dataoutput;
    private TableColumn invitedCol;
    private TableColumn firstNameCol;
    private TableColumn lastNameCol;
    private TableColumn emailCol;
    
    public DataInput(DataOutput dout){
        root = new Group();
        this.dataoutput = dout;
        
        //"Invited" column
        TableColumn invitedCol = new TableColumn<Person, Boolean>();
        invitedCol.setText("Invited");
        invitedCol.setMinWidth(50);
        invitedCol.setCellValueFactory(new PropertyValueFactory("invited"));
        invitedCol.setCellFactory(new Callback<TableColumn<Person, Boolean>, TableCell<Person, Boolean>>() {
 
            public TableCell<Person, Boolean> call(TableColumn<Person, Boolean> p) {
                return new CheckBoxTableCell<Person, Boolean>();
            }
        });
        //"First Name" column
        firstNameCol = new TableColumn();
        firstNameCol.setText("First");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        //"Last Name" column
        lastNameCol = new TableColumn();
        lastNameCol.setText("Last");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        //"Email" column
        emailCol = new TableColumn();
        emailCol.setText("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory("email"));
 
        //Set cell factory for cells that allow editing
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
 
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        emailCol.setCellFactory(cellFactory);
        firstNameCol.setCellFactory(cellFactory);
        lastNameCol.setCellFactory(cellFactory);
 
        //Set handler to update ObservableList properties. Applicable if cell is edited
        updateObservableListProperties(emailCol, firstNameCol, lastNameCol);
 
        TableView tableView = new TableView();
        MutualDatastore mutualdatastore = new MutualDatastore();
        tableView.setItems(mutualdatastore.getInitialData());
        //Enabling editing
        tableView.setEditable(true);
        tableView.getColumns().addAll(invitedCol, firstNameCol, lastNameCol, emailCol);
        root.getChildren().add(tableView); 
    }
 
  private void updateObservableListProperties(TableColumn emailCol, TableColumn firstNameCol,
            TableColumn lastNameCol) {
        //Modifying the email property in the ObservableList
        emailCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {           
            @Override public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
                //dataoutput.getEmail().setEditable(true);
                dataoutput.getTable().getItems().set(t.getTablePosition().getRow(), t.getNewValue());
                //dataoutput.getEmail().setEditable(false);
            }  
        });
        //Modifying the firstName property in the ObservableList
        firstNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {          
            @Override public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setFirstName(t.getNewValue());
            }
        });
        //Modifying the lastName property in the ObservableList
        lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {           
            @Override public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setLastName(t.getNewValue());
            }
        });
    }    
     

 
    //CheckBoxTableCell for creating a CheckBox in a table cell
    public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {
        private final CheckBox checkBox;
        private ObservableValue<T> ov;
 
        public CheckBoxTableCell() {
            this.checkBox = new CheckBox();
            this.checkBox.setAlignment(Pos.CENTER);
 
            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        } 
         
        @Override public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(checkBox);
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
            }
        }
    }
 
    // EditingCell - for editing capability in a TableCell
    public static class EditingCell extends TableCell<Person, String> {
        private TextField textField;
 
        public EditingCell() {
        }
        
        @Override public void startEdit() {
            super.startEdit();
 
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
        
        @Override public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(null);
        }
        
        @Override public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {                
                @Override public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
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
}