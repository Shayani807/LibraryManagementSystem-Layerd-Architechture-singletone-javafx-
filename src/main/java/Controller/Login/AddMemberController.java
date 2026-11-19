package com.library.controller;

import bo.MemberBO;
import bo.impl.MemberBOImpl;
import dto.MemberDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class AddMemberController {

    @FXML private TextField memberIdField;
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextArea addressArea;
    @FXML private Label messageLabel;

    private final MemberBO memberBO = new MemberBOImpl();

    @FXML
    private void handleRegisterMember() {
        System.out.println("REGISTER BUTTON CLICKED!");

        String id = memberIdField.getText().trim();
        String name = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressArea.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            messageLabel.setText("Member ID and Full Name are required!");
            messageLabel.setStyle("-fx-text-fill: red;");
            new Alert(Alert.AlertType.WARNING, "Member ID and Full Name are required!").show();
            return;
        }

        try {
            MemberDTO dto = new MemberDTO(
                    id,
                    name,
                    email,
                    phone,
                    address,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            boolean saved = memberBO.saveMember(dto);

            if (saved) {
                messageLabel.setText("Member Added Successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");
                new Alert(Alert.AlertType.INFORMATION, "Member Added Successfully!").show();
                handleClear();
            } else {
                messageLabel.setText("Failed to add member! Check console.");
                messageLabel.setStyle("-fx-text-fill: red;");
                new Alert(Alert.AlertType.ERROR, "Failed to add member!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    private void handleClear() {
        memberIdField.clear();
        fullNameField.clear();
        emailField.clear();
        phoneField.clear();
        addressArea.clear();
        messageLabel.setText("");
        memberIdField.requestFocus();
    }
}