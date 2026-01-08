import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
public class budgetmanager extends JFrame implements ActionListener {
JButton addIncomeBtn, addExpenseBtn, showBalanceBtn , showgraph;
JTextArea displayArea;
File budgetFile ;
String username;
public budgetmanager(String username) {
this.username = username;
budgetFile = new File(username + "_budget.txt");
setSize(500, 400);
setLayout(new BorderLayout());
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
JPanel topPanel = new JPanel();
addIncomeBtn = new JButton("Add Income");
addExpenseBtn = new JButton("Add Expense");
showBalanceBtn = new JButton("Show Balance");
showgraph = new JButton("show graph");
addIncomeBtn.addActionListener(this);
addExpenseBtn.addActionListener(this);
showBalanceBtn.addActionListener(this);
showgraph.addActionListener(this);
topPanel.add(addIncomeBtn);
topPanel.add(addExpenseBtn);
topPanel.add(showBalanceBtn);
topPanel.add(showgraph);
displayArea = new JTextArea();
displayArea.setEditable(false);
add(topPanel, BorderLayout.NORTH);
add(new JScrollPane(displayArea), BorderLayout.CENTER);
setVisible(true);
}
public void actionPerformed(ActionEvent ae) {
if (ae.getSource() == addIncomeBtn) {
String amtStr = JOptionPane.showInputDialog(this, "Enter income amount:");
if (amtStr != null && !amtStr.isEmpty()) {
try {
double amt = Double.parseDouble(amtStr);
try (FileWriter fw = new FileWriter(budgetFile, true)) {
fw.write("Income," + amt + "\n");
}

displayArea.append("Added: Income," + amt + "\n");
} catch (NumberFormatException e) {
JOptionPane.showMessageDialog(this, "Invalid amount");
} catch (IOException e) {
JOptionPane.showMessageDialog(this, "Error writing income!");
}
}
} else if (ae.getSource() == addExpenseBtn) {
String amtStr = JOptionPane.showInputDialog(this, "Enter expense amount:");
if (amtStr != null && !amtStr.trim().isEmpty()) {
try {
double amt = Double.parseDouble(amtStr);
try (FileWriter fw = new FileWriter(budgetFile, true)) {
fw.write("Expense," + amt + "\n");
}
displayArea.append("Added: Expense," + amt + "\n");
} catch (NumberFormatException e) {
JOptionPane.showMessageDialog(this, "Invalid amount");
} catch (IOException e) {
JOptionPane.showMessageDialog(this, "Error writing expense!");
}
}
} else if (ae.getSource() == showBalanceBtn) {
showBalance();
}
else if (ae.getSource() == showgraph)
{
  showgraphfun();
}
}
void showBalance() {
double income = 0, expense = 0;
try (BufferedReader br = new BufferedReader(new FileReader(budgetFile))) {
String line;
while ((line = br.readLine()) != null) {
String[] parts = line.split(",");
if (parts[0].equals("Income")) {
income += Double.parseDouble(parts[1]);
} else if (parts[0].equals("Expense")) {
expense += Double.parseDouble(parts[1]);
}
}
} catch (IOException e) {
JOptionPane.showMessageDialog(this, "No records yet!");
}
double balance = income - expense;
displayArea.append("Income: " + income + " | Expense: " + expense + " | Balance: " +
balance + "\n");

}
void showgraphfun() {
    double income = 0, expense = 0;
    


       try (BufferedReader br = new BufferedReader(new FileReader(budgetFile))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equalsIgnoreCase("Income")) {
                income += Double.parseDouble(parts[1]);
            } else if (parts[0].equalsIgnoreCase("Expense")) {
                expense += Double.parseDouble(parts[1]);
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "No data found!");
        return;
    }

    double total = income + expense;
    if (total == 0) {
        JOptionPane.showMessageDialog(this, "No records to show graph!");
        return;
    }
    final double finIncome = income;
    final double finExpense = expense;

    JFrame graphFrame = new JFrame("Income vs Expense Graph");
    graphFrame.setSize(400, 400);
    graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel graphPanel = new JPanel() {
        protected void paintComponent(Graphics g) {
	   JOptionPane.showMessageDialog(this, "Green bar is income and red bar is for expense");
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();

            int barWidth = 100;
            int maxBarHeight = 200;

            double maxValue = Math.max(finIncome, finExpense);
            int incomeBarHeight = (int) ((finIncome / maxValue) * maxBarHeight);
            int expenseBarHeight = (int) ((finExpense / maxValue) * maxBarHeight);

            
            g.setColor(Color.GREEN);
            g.fillRect(100, height - incomeBarHeight - 100, barWidth, incomeBarHeight);
     
             
            g.setColor(Color.RED);
            g.fillRect(250, height - expenseBarHeight - 100, barWidth, expenseBarHeight);
	   
                   
	 }
    };

    graphFrame.add(graphPanel);
    graphFrame.setVisible(true);
}

}