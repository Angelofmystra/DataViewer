
package dataviewer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MutualDatastore {

    
    public ObservableList<Person> getInitialData(){
        final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person(true, "Jacob",     "Smith",    "jacob.smith@example.com" ),
            new Person(false, "Isabella",  "Johnson",  "isabella.johnson@example.com" ),
            new Person(true, "Ethan",     "Williams", "ethan.williams@example.com" ),
            new Person(true, "Emma",      "Jones",    "emma.jones@example.com" ),
            new Person(false, "Michael",   "Brown",    "michael.brown@example.com" )
        );
        return data;
    }
}
