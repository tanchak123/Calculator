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

    private String setResult(String value) {
        int count = 0;
        value = value.replaceAll(" ", "");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            stringBuilder.append(value.charAt(i));
            if ((i + 1) % 3 == 0) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public class Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long result = 0;
            JButton button = (JButton) e.getSource();
            Character buttonText = button.getText().charAt(0);
            String fieldText = textField.getText().replaceAll(" ", "");
            if (buttonText.equals('C')) {
                textField.setText("");
                return;
            } else if (buttonText.equals('=')) {
                textField.setText(getResult(fieldText));
            } else {
                textField.setText(setResult(textField.getText() + button.getText()));
            }
        }
    }

    private String getResult(String fieldText) {
        StringBuilder sb = new StringBuilder();
        StringBuilder chars = new StringBuilder();
        for (int i = 0; i < fieldText.length(); i++) {
            if (!Character.isDigit(fieldText.charAt(i))) {
                sb.append(" ");
                chars.append(fieldText.charAt(i));
                continue;
            }
            sb.append(fieldText.charAt(i));
        }
        String[] nums = sb.toString().split(" ");
        String[] character = chars.toString().split("");
        long result = Long.parseLong(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            switch (character[i - 1]) {
                case "+":
                    result += Long.parseLong(nums[i]);
                    break;
                case "-":
                    result -= Long.parseLong(nums[i]);
                    break;
                case "*":
                    result *= Long.parseLong(nums[i]);
                    break;
                case "÷":
                    result /= Long.parseLong(nums[i]);
                    break;
                case "%":
                    result %= Long.parseLong(nums[i]);
                    break;
                case "^":
                    result = (long) Math.pow(result, Long.parseLong(nums[i]));
                    break;
                case ",":
                    result = Long.parseLong(result + "," + nums[i]);
                    break;
                default:
                    break;
            }
        }
        return String.valueOf(result);
    }

    private int getI(int i, String fieldText) {
        while (i < fieldText.length()
                && Character.isDigit(fieldText.charAt(i))) {
            i++;
        }
        return i - 1;
    }
}
