import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        textField.setBounds(10,10, 300, 80);
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
                if (textField.getText().contains(".")) {
                    textField.setText(textField.getText());
                    return;
                }
                textField.setText(textField.getText() + '.');
            } else {
                textField.setText(setSpace(textField.getText() + button.getText()));
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
                    result = (double) Math.pow(result, Double.parseDouble(nums[i]));
                    break;
                default:
                    break;
            }
        }
        return isDouble(String.valueOf(result));
    }

    private String setSpace(String value) {
        int count = 0;
        int sbIndex = 0;
        value = value.replaceAll(" ", "");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            stringBuilder.append(value.charAt(i));
            sbIndex++;
            if (!Character.isDigit(value.charAt(i))) {
                if (value.charAt(i) == '.') {
                    count = 0;
                    continue;
                }
                if (count % 3 != 0) {
                    stringBuilder.insert(sbIndex - 1, ' ');
                    sbIndex++;
                }
                stringBuilder.append(" ");
                count = 0;
                sbIndex++;
                continue;
            }
            count++;
            if ((count) % 3 == 0) {
                stringBuilder.append(" ");
                sbIndex++;
            }
        }
        return stringBuilder.charAt(0) == Colums.MINUS.getC() ? Colums.MINUS.getS()
                + stringBuilder.toString().substring(2, stringBuilder.length())
            : stringBuilder.toString();
    }

    private String isDouble(String string) {
        if (string.charAt(string.length() - 1) == '0') {
            return string.substring(0, string.length() - 2);
        }
        return string;
    }

}
