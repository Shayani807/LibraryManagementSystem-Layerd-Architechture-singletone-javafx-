package Controller.Login;

import Util.HibernateUtil;
import bo.IssuedBookBO;
import bo.impl.IssuedBookBOImpl;
import dto.IssueDTO;
import entity.Book;
import entity.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueBookController {

    @FXML private ComboBox<String> memberComboBox;
    @FXML private ComboBox<String> bookComboBox;
    @FXML private TextField memberField;
    @FXML private TextField bookField;
    @FXML private DatePicker issueDatePicker;
    @FXML private DatePicker dueDatePicker;
    @FXML private Label statusLabel;
    @FXML private Label messageLabel;

    private final IssuedBookBO issuedBookBO = new IssuedBookBOImpl();
    private final Map<String, String> memberMap = new HashMap<>();
    private final Map<String, String> bookMap = new HashMap<>();

    @FXML
    public void initialize() {
        issueDatePicker.setValue(LocalDate.now());
        dueDatePicker.setValue(LocalDate.now().plusDays(14));
        loadMembers();
        loadBooks();

        memberComboBox.setOnAction(e -> {
            String sel = memberComboBox.getValue();
            if (sel != null) memberField.setText(memberMap.get(sel));
        });
        bookComboBox.setOnAction(e -> {
            String sel = bookComboBox.getValue();
            if (sel != null) bookField.setText(bookMap.get(sel));
        });
    }

    private void loadMembers() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Member> members = session.createQuery("FROM Member", Member.class).list();
            for (Member m : members) {
                String display = m.getMemberId() + " - " + m.getFullName();
                list.add(display);
                memberMap.put(display, m.getMemberId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        memberComboBox.setItems(list);
    }

    private void loadBooks() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Book> books = session.createQuery("FROM Book", Book.class).list();
            for (Book b : books) {
                String display = b.getIsbn() + " - " + b.getTitle();
                list.add(display);
                bookMap.put(display, b.getIsbn());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bookComboBox.setItems(list);
    }

    @FXML
    private void handleIssueBook() {
        String memberSel = memberComboBox.getValue();
        String bookSel = bookComboBox.getValue();
        if (memberSel == null || bookSel == null) {
            messageLabel.setText("Please select Member and Book!");
            return;
        }
        IssueDTO dto = new IssueDTO(
                0,
                memberMap.get(memberSel),
                bookMap.get(bookSel),
                "",
                issueDatePicker.getValue(),
                dueDatePicker.getValue(),
                null,
                "BORROWED"
        );
        boolean ok = issuedBookBO.issueBook(dto);
        if (ok) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Book Issued Successfully!");
            alert.setContentText("Member: " + dto.getMemberId() + "\nBook: " + dto.getIsbn());
            alert.showAndWait();
            handleClear();
        } else {
            messageLabel.setText("Issue Failed! Book may be already issued or not available.");
        }
    }

    @FXML
    private void handleClear() {
        memberComboBox.setValue(null);
        bookComboBox.setValue(null);
        memberField.clear();
        bookField.clear();
        issueDatePicker.setValue(LocalDate.now());
        dueDatePicker.setValue(LocalDate.now().plusDays(14));
        messageLabel.setText("");
        if (statusLabel != null) statusLabel.setText("");
    }
}