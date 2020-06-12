import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Panel extends JPanel {
    private static JTextField textField = new JTextField(12);
    private static JButton b1;
    private static Font font = new Font("SanSerif", Font.BOLD, 20);
    private Handler handler = new Handler();
    private ArrayList<JButton> buttons = new ArrayList<>();

    public Panel() {
        int x = 10;
        int y = 100;
        add(textField);
        textField.setEditable(false);
        textField.setFont(font.deriveFont(Font.BOLD,30));
        textField.setBounds(10,10, 200, 80);
        b1 = new JButton(Colums.DELETE.getS());
        buttons.add(b1);
        b1.setFont(font.deriveFont(Font.BOLD, 30));
        b1.addActionListener(handler);
        add(b1);
        b1.setBounds(210, 10, 100, 80);
        setLayout(null);
        createBottom("0",110, 250);
        createBottom(Colums.PLUS.getS(), 10, 250);
        createBottom(Colums.MINUS.getS(), 210, 250);
        createBottom(Colums.MULTIPLY.getS(), 10, 300);
        createBottom(Colums.DIVIDE.getS(), 110, 300);
        createBottom(Colums.DEGREE.getS(), 210, 300);
        createBottom(Colums.DIVIDER.getS(), 10, 350);
        createBottom(Colums.FRACTION.getS(), 110, 350);
        createBottom(Colums.CLEAR.getS(), 210, 350);
        createBottom(Colums.EQUAL.getS(), 10, 400, 300, 50);
        int count = 1;
        while (count <= 9) {
            createBottom(String.valueOf(count), x, y, 100, 50);
            count++;
            x += 100;
            if (x % 310 == 0) {
                y += 50;
                x = 10;
                continue;
            }
        }
    }

    private void createBottom(String colum, int x, int y) {
        b1 = new JButton(colum);
        buttons.add(b1);
        b1.setFont(font);
        b1.addActionListener(handler);
        add(b1);
        b1.setBounds(x, y, 100, 50);
    }

    private void createBottom(String colum, int x, int y, int w, int h) {
        b1 = new JButton(colum);
        buttons.add(b1);
        b1.setFont(font);
        b1.addActionListener(handler);
        add(b1);
        b1.setBounds(x, y, w, h);
    }

    public class Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            Character buttonText = button.getText().charAt(0);
            String fieldText = textField.getText().replaceAll(" ", "");
            if (buttonText.equals('C')) {
                textField.setText("");
                return;
            } else if (buttonText.equals('=')) {
                textField.setText(setSpace(getResult(fieldText)));
            } else if (buttonText.equals(Colums.FRACTION.getC())) {
                for (Colums colum : Colums.values()) {
                    if (fieldText.charAt(fieldText.length() - 1) == colum.getC()) {
                        textField.setText(setSpace(fieldText));
                        return;
                    }
                }
                StringBuilder sb = new StringBuilder();
                boolean helper = false;
                for (Character character : fieldText.toCharArray()) {
                    if (Character.isDigit(character)) {
                        sb.append(character);
                        continue;
                    }
                    if (character == '.' && helper == false) {
                        helper = true;
                        sb.append(character);
                        continue;
                    }
                    for (Colums colum : Colums.values()) {
                        if (character == colum.getC()) {
                            helper = false;
                            sb.append(character);
                            break;
                        }
                    }
                }
                if (!helper) {
                    sb.append(buttonText);
                }
                textField.setText(setSpace(sb.toString()));
            } else if (buttonText.equals(Colums.DELETE.getC())){
                if (fieldText.length() <= 1) {
                    textField.setText("");
                    return;
                }
                textField.setText(setSpace(fieldText.substring(0, fieldText.length() - 1)));
            } else {
                if (fieldText.length() > 1 && !Character.isDigit(buttonText) && !Character.isDigit(fieldText.charAt(fieldText.length() - 1))) {
                    textField.setText(fieldText.substring(0, fieldText.length() - 1) + " " + buttonText);
                    return;
                }
                if (fieldText.length() < 1 && buttonText != '-' && !Character.isDigit(buttonText)) {
                    textField.setText("");
                    return;
                }
                textField.setText(setSpace(fieldText + button.getText()));
            }
        }
    }

    private String getResult(String fieldText) {
        StringBuilder sb = new StringBuilder();
        StringBuilder chars = new StringBuilder();
        boolean start = false;
        for (int i = 0; i < fieldText.length(); i++) {
            if (!Character.isDigit(fieldText.charAt(i))
                    && start) {
                if (fieldText.charAt(i) == '.') {
                    sb.append(fieldText.charAt(i));
                    continue;
                }
                sb.append(" ");
                chars.append(fieldText.charAt(i));
                start = false;
                continue;
            }
            sb.append(fieldText.charAt(i));
            start = true;
        }
        String[] nums = sb.toString().split(" ");
        String[] character = chars.toString().split("");
        double result = Double.parseDouble(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            switch (character[i - 1]) {
                case "+":
                    result += Double.parseDouble(nums[i]);
                    break;
                case "-":
                    result -= Double.parseDouble(nums[i]);
                    break;
                case "*":
                    result *= Double.parseDouble(nums[i]);
                    break;
                case "÷":
                    result /= Double.parseDouble(nums[i]);
                    break;
                case "%":
                    result %= Double.parseDouble(nums[i]);
                    break;
                case "^":
                    result = Math.pow(result, Double.parseDouble(nums[i]));
                    break;
                default:
                    break;
            }
        }
        return isDouble(String.valueOf(result));
    }

    private String setSpace(String value) {
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = value.length() - 1; i >= 0; i--) {
            if (!Character.isDigit(value.charAt(i))) {
                if (value.charAt(i) == '.') {
                    stringBuilder.insert(0,value.charAt(i));
                    count = 0;
                    continue;
                }
                stringBuilder.insert(0, " " + value.charAt(i) + ' ');
                count = 0;
                continue;
            }
            if (count == 3) {
                stringBuilder.insert(0, ' ');
                count = 0;
            }
            stringBuilder.insert(0, value.charAt(i));
            count++;
        }
        return stringBuilder.length() > 1 ? stringBuilder.charAt(1) == Colums.MINUS.getC() ? Colums.MINUS.getS()
                + stringBuilder.toString().substring(3, stringBuilder.length())
                : stringBuilder.toString()
                : stringBuilder.toString();
    }

    private String isDouble(String string) {
        if (string.charAt(string.length() - 1) == '0') {
            return string.substring(0, string.length() - 2);
        }
        return string;
    }

}
