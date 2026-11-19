package Controller.Login;

import bo.IssuedBookBO;
import bo.impl.IssuedBookBOImpl;
import dto.IssueDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnBookController {

    @FXML private ComboBox<String> borrowedBookComboBox;
    @FXML private TextField memberField;
    @FXML private TextField bookField;
    @FXML private DatePicker borrowedDatePicker;
    @FXML private DatePicker dueDatePicker;
    @FXML private DatePicker returnDatePicker;
    @FXML private Label statusLabel;
    @FXML private Label messageLabel;

    private final IssuedBookBO issuedBookBO = new IssuedBookBOImpl();
    private final Map<String, IssueDTO> borrowedMap = new HashMap<>();

    @FXML
    public void initialize() {
        returnDatePicker.setValue(LocalDate.now());
        loadBorrowedBooks();

        borrowedBookComboBox.setOnAction(e -> {
            String selected = borrowedBookComboBox.getValue();
            if (selected != null) {
                IssueDTO dto = borrowedMap.get(selected);
                if (dto != null) {
                    memberField.setText(dto.getMemberId());


                    String bookText = dto.getTitle() != null ? dto.getIsbn() + " - " + dto.getTitle() : dto.getIsbn();
                    bookField.setText(bookText);

                    borrowedDatePicker.setValue(dto.getIssueDate());
                    dueDatePicker.setValue(dto.getDueDate());

                    if (dto.getDueDate() != null && LocalDate.now().isAfter(dto.getDueDate())) {
                        statusLabel.setText("OVERDUE");
                        statusLabel.setStyle("-fx-text-fill: red;");
                    } else {
                        statusLabel.setText("ON TIME");
                        statusLabel.setStyle("-fx-text-fill: green;");
                    }
                    messageLabel.setText("");
                }
            }
        });
    }

    private void loadBorrowedBooks() {
        ObservableList<String> list = FXCollections.observableArrayList();
        borrowedMap.clear();
        List<IssueDTO> all = issuedBookBO.getAllIssuedBooks();
        for (IssueDTO dto : all) {
            if ("BORROWED".equals(dto.getStatus())) {
                String title = dto.getTitle() != null ? dto.getTitle() : dto.getIsbn();
                String display = dto.getIssueId() + " - " + title + " (" + dto.getMemberId() + ")";
                list.add(display);
                borrowedMap.put(display, dto);
            }
        }
        borrowedBookComboBox.setItems(list);
    }

    @FXML
    private void handleReturnBook() {
        String selected = borrowedBookComboBox.getValue();
        if (selected == null) {
            messageLabel.setText("Please select a borrowed book!");
            return;
        }
        IssueDTO dto = borrowedMap.get(selected);

        boolean ok = issuedBookBO.returnBook(dto.getIssueId());
        if (ok) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Book Returned Successfully!");
            alert.setContentText("Book: " + dto.getIsbn() + "\nMember: " + dto.getMemberId());
            alert.showAndWait();
            handleClear();
        } else {
            messageLabel.setText("Return Failed!");
        }
    }

    @FXML
    private void handleClear() {
        borrowedBookComboBox.setValue(null);
        memberField.clear();
        bookField.clear();
        borrowedDatePicker.setValue(null);
        dueDatePicker.setValue(null);
        returnDatePicker.setValue(LocalDate.now());
        statusLabel.setText("Not Checked");
        statusLabel.setStyle("-fx-text-fill: black;");
        messageLabel.setText("");
        loadBorrowedBooks();
    }
}