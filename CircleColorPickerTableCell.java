package ppa.component;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;


public class CircleColorPickerTableCell<T> extends TableCell<T, Circle> {

    
    protected Circle circle = new Circle(10.0);
    protected ColorPicker picker = new ColorPicker();

    public CircleColorPickerTableCell() {
        setAlignment(Pos.CENTER);
        circle.setDisable(true);
        //circle.setAllowIndeterminate(true);
        //picker.setAlignment(Pos.CENTER);
    }

    @Override
    protected void updateItem(Circle value, boolean isEmpty) {
        super.updateItem(value, isEmpty);
        if (isEmpty) {
            setText(null);
            setGraphic(null);
        } else {      	
            setGraphic(value);
        }
    }

    @Override
    public void startEdit() {
    	super.startEdit();
        EventHandler<KeyEvent> handler = key -> {
            if (key.getCode() == KeyCode.ENTER) {
                commitEdit(circle);
            } else if (key.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        };
        onKeyPressedProperty().setValue(handler);
        circle.onKeyPressedProperty().setValue(handler); 
    	setGraphic(picker);
    }

    @Override
    public void commitEdit(Circle newValue) {
        super.commitEdit(newValue);
        cleanupEditor();
    }

    private void cleanupEditor() {
    	picker.setDisable(false);
    	circle.setFill(picker.getValue());
    	setGraphic(circle);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        cleanupEditor();
    }
}
