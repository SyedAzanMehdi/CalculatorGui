import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame implements ActionListener {

    private JTextField display;
    private JPanel buttonPanel;
    private StringBuilder currentInput = new StringBuilder();
    private double result = 0;
    private String lastOperator = "";
    private boolean startNewNumber = true;

    public CalculatorGUI() {
        setTitle("Calculator");
        setSize(350, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
                "C", "", "", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "=", ""
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 22));
            if (!text.equals("")) {
                btn.addActionListener(this);
            } else {
                btn.setEnabled(false);
            }
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("Calculator - Made by  AZAN MEHDI", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 14));
        footer.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        add(footer, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789.".contains(cmd)) {
            if (startNewNumber) {
                currentInput.setLength(0);
                startNewNumber = false;
            }
            if (cmd.equals(".") && currentInput.toString().contains(".")) {
                return; // Avoid multiple decimals
            }
            currentInput.append(cmd);
            display.setText(currentInput.toString());

        } else if (cmd.equals("C")) {
            currentInput.setLength(0);
            display.setText("");
            result = 0;
            lastOperator = "";
            startNewNumber = true;

        } else if (cmd.equals("=")) {
            calculate(Double.parseDouble(currentInput.toString()));
            display.setText(String.valueOf(result));
            lastOperator = "";
            startNewNumber = true;

        } else
        {
            if (!lastOperator.isEmpty()) {
                calculate(Double.parseDouble(currentInput.toString()));
                display.setText(String.valueOf(result));
            } else {
                result = Double.parseDouble(currentInput.toString());
            }
            lastOperator = cmd;
            startNewNumber = true;
        }
    }

    private void calculate(double num) {
        switch (lastOperator) {
            case "+":
                result += num;
                break;
            case "-":
                result -= num;
                break;
            case "*":
                result *= num;
                break;
            case "/":
                if (num == 0) {
                    JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                result /= num;
                break;
            default:
                result = num;
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorGUI calc = new CalculatorGUI();
            calc.setVisible(true);
        });
    }
}
