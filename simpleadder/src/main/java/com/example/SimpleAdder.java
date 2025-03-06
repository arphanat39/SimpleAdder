package com.example;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleAdder extends Application {
    private static Random random = new Random(); // ตัวแปรสำหรับสร้างเลขสุ่ม

    private TextField textFieldA; // ช่องสำหรับใส่ค่า A
    private TextField textFieldB; // ช่องสำหรับใส่ค่า B
    private Label labelA; // ป้ายแสดงผลค่าของ A
    private Label labelB; // ป้ายแสดงผลค่าของ B
    private Label outputLabel; // ป้ายแสดงผลลัพธ์การคำนวณ
    private Label warningLabel; // ป้ายแสดงข้อความเตือนเมื่อมีข้อผิดพลาด
    private Node outputRow; // แถวที่แสดงผลการคำนวณ
    private ComboBox<String> operator; // ช่องเลือกเครื่องหมายทางคณิตศาสตร์ เช่น +, -, *, /
    private Label operatorLabel; // ป้ายแสดงเครื่องหมายการคำนวณ

    public static void main(String[] args) {
        launch(args); // เริ่มโปรแกรม JavaFX
    }

    @Override
    public void start(Stage stage) {
        var scene = new Scene(createMainView(), 500, 200); // กำหนดขนาดหน้าต่างและสร้าง Scene หลัก
        stage.setScene(scene);
        stage.setTitle("Simple Adder"); // ตั้งชื่อหน้าต่าง
        stage.show(); // แสดงหน้าต่าง
    }

    private Region createMainView() {
        var view = new BorderPane(); // ใช้ BorderPane สำหรับจัด Layout หลัก
        view.getStylesheets().add(getClass().getResource("/css/simple-adder.css").toExternalForm()); // เพิ่มสไตล์จากไฟล์ CSS
        view.setTop(createHeading()); // แสดงหัวข้อโปรแกรมที่ด้านบน
        view.setCenter(createCenterContent()); // แสดงส่วนการป้อนค่าและผลลัพธ์ตรงกลาง
        view.setBottom(createButtonRow()); // แสดงปุ่มที่ด้านล่าง
        return view;
    }

    private Node createHeading() {
        var heading = new Label("Simple Adder"); // ป้ายหัวข้อของโปรแกรม
        HBox.setHgrow(heading, Priority.ALWAYS);
        heading.setMaxWidth(Double.MAX_VALUE);
        heading.setAlignment(Pos.CENTER); // จัดตำแหน่งให้อยู่ตรงกลาง
        heading.getStyleClass().add("heading-label");
        return heading;
    }

    private Node createCenterContent() {
        var inputRow = createInputRow(); // สร้างแถวสำหรับการป้อนค่า A และ B
        var outputPane = createOutputPane(); // สร้างพื้นที่สำหรับแสดงผลลัพธ์
        var centerContent = new VBox(20, inputRow, outputPane); // จัดการจัดวางในแนวตั้งด้วย VBox
        centerContent.setPadding(new Insets(20)); // เพิ่มระยะห่างระหว่างขอบ
        centerContent.setAlignment(Pos.CENTER); // จัดตำแหน่งให้อยู่ตรงกลาง
        return centerContent;
    }

    private Node createInputRow() {
        textFieldA = new TextField("0"); // ช่องป้อนค่าของ A เริ่มต้นด้วยค่า 0
        textFieldB = new TextField("0"); // ช่องป้อนค่าของ B เริ่มต้นด้วยค่า 0
    
        operator = new ComboBox<>(); // ตัวเลือกสำหรับเครื่องหมายทางคณิตศาสตร์
        operator.getItems().addAll("+", "-", "*", "/"); // เพิ่มตัวเลือกเครื่องหมาย +, -, *, /
        operator.setValue("+"); // ตั้งค่าเริ่มต้นเป็นเครื่องหมายบวก
    
        var inputRow = new HBox(10, new Label("A:"), textFieldA, operator, new Label("B:"), textFieldB); // จัดเรียงในแนวนอน
        inputRow.setAlignment(Pos.CENTER);
        return inputRow;
    }

    private Node createOutputPane() {
        outputRow = createOutputRow(); // แถวแสดงผลลัพธ์
        var warningLabel = createWarningLabel(); // ป้ายแสดงข้อความเตือน
        warningLabel.setVisible(false); // ซ่อนป้ายเตือนในตอนเริ่มต้น
        var outputPane = new StackPane(outputRow, warningLabel); // ใช้ StackPane ในการจัดการแสดงผล
        return outputPane;
    }

    private Node createOutputRow() {
        labelA = new Label("0"); // ป้ายแสดงค่าของ A
        labelB = new Label("0"); // ป้ายแสดงค่าของ B
        operatorLabel = new Label(operator.getValue()); // ป้ายแสดงเครื่องหมายการคำนวณ เริ่มต้นด้วยค่าในตัวเลือก
        outputLabel = new Label("0"); // ป้ายแสดงผลลัพธ์การคำนวณ เริ่มต้นเป็น 0
        outputLabel.setStyle("-fx-text-fill: blue;"); // กำหนดสีของข้อความผลลัพธ์เป็นสีฟ้า
        var outputRow = new HBox(10, labelA, operatorLabel, labelB, new Label("="), outputLabel); // จัดเรียงในแนวนอน
        outputRow.setAlignment(Pos.CENTER); // จัดตำแหน่งให้อยู่ตรงกลาง
        return outputRow;
    }

    private Node createWarningLabel() {
        warningLabel = new Label("Invalid input format."); // ข้อความเตือนเมื่อรูปแบบการป้อนค่าผิด
        warningLabel.getStyleClass().add("warning");
        return warningLabel;
    }

    private Node createButtonRow() {
        var buttonRow = new HBox(20, createRandomizeButton(), createAddButton()); // จัดเรียงปุ่ม Randomize และ Calculate ในแนวนอน
        buttonRow.setPadding(new Insets(0, 0, 20, 0));
        buttonRow.setAlignment(Pos.CENTER); // จัดตำแหน่งให้อยู่ตรงกลาง
        return buttonRow;
    }

    private Node createRandomizeButton() {
        var randomizeButton = new Button("Randomize"); // ปุ่มสำหรับสุ่มค่าของ A และ B
        randomizeButton.getStyleClass().add("custom-button");
        randomizeButton.setOnAction(evt -> {
            textFieldA.setText(String.valueOf(rangeRandomInt(-1000, 1000))); // สุ่มค่า A ในช่วง -1000 ถึง 1000
            textFieldB.setText(String.valueOf(rangeRandomInt(-1000, 1000))); // สุ่มค่า B ในช่วง -1000 ถึง 1000
        });
        return randomizeButton;
    }

    private int rangeRandomInt(int start, int end) {
        return random.nextInt(end - start) + start; // ฟังก์ชันสำหรับสุ่มตัวเลขในช่วง start ถึง end
    }

    private Node createAddButton() {
        var addButton = new Button("Calculate"); // ปุ่มคำนวณผลลัพธ์
        addButton.getStyleClass().add("custom-button");
        addButton.setOnAction(evt -> {
            String valueA = textFieldA.getText(); // รับค่าจากช่องป้อนค่า A
            String valueB = textFieldB.getText(); // รับค่าจากช่องป้อนค่า B
            labelA.setText(valueA); // แสดงค่า A ที่ป้อน
            labelB.setText(valueB); // แสดงค่า B ที่ป้อน
            String operatorread = operator.getValue(); // อ่านค่าเครื่องหมายการคำนวณจาก ComboBox
            operatorLabel.setText(operatorread); // อัปเดตป้ายแสดงเครื่องหมายการคำนวณ

            try {
                Float a = Float.valueOf(valueA); // แปลงค่าของ A เป็นจำนวนแบบทศนิยม
                Float b = Float.valueOf(valueB); // แปลงค่าของ B เป็นจำนวนแบบทศนิยม
                switch (operatorread) { // ตรวจสอบว่าเป็นการคำนวณแบบไหน
                    case "+" -> outputLabel.setText(String.valueOf(a + b)); // บวก
                    case "-" -> outputLabel.setText(String.valueOf(a - b)); // ลบ
                    case "*" -> outputLabel.setText(String.valueOf(a * b)); // คูณ
                    case "/" -> { 
                        if (b != 0) {
                            outputLabel.setText(String.valueOf(a / b)); // หาร ถ้า B ไม่เป็น 0
                        } else {
                            throw new ArithmeticException("Divide by zero."); // ข้อผิดพลาดเมื่อหารด้วย 0
                        }
                    }
                }
                showOutput(); // แสดงผลลัพธ์
            } catch (NumberFormatException e) { // ข้อผิดพลาดถ้าใส่ค่าไม่ถูกต้อง
                showWarning(); // แสดงข้อความเตือน
            } catch (ArithmeticException e) { // ข้อผิดพลาดถ้าหารด้วย 0
                warningLabel.setText("Divide by zero"); //   set warningLabel ->Divide by zero
                showWarning();//แสดงข้อความเตือน
            }
        });
        return addButton;
    }

    private void showOutput() {
        outputRow.setVisible(true);
        warningLabel.setVisible(false);
    }

    private void showWarning() {
        outputRow.setVisible(false);
        warningLabel.setVisible(true);
    }
}
