package budgetappgui;

import static budgetappgui.BudgetAppGUI.DB_PASSWD;
import static budgetappgui.BudgetAppGUI.DB_URL;
import static budgetappgui.BudgetAppGUI.DB_USER;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ViewTransactions extends javax.swing.JFrame {
    private static DBBean db = new DBBean();
    private static ResultSet resultSet;
    
    public ViewTransactions() {
        initComponents();
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setTitle("weSave - Transactions");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        
        jComboBox4.removeAllItems();
        jComboBox3.removeAllItems();
        jComboBox2.removeAllItems();
        jComboBox1.removeAllItems();
        jComboBox1.addItem("Food and Beverage");
        jComboBox1.addItem("Bills");
        jComboBox1.addItem("Investment");
        jComboBox1.addItem("Loan");
        jComboBox1.addItem("Salary");
        jComboBox1.addItem("Gift");
        jComboBox1.addItem("Other");
        
        jComboBox2.addItem("January");
        jComboBox2.addItem("February");
        jComboBox2.addItem("March");
        jComboBox2.addItem("April");
        jComboBox2.addItem("May");
        jComboBox2.addItem("June");
        jComboBox2.addItem("July");
        jComboBox2.addItem("August");
        jComboBox2.addItem("September");
        jComboBox2.addItem("October");
        jComboBox2.addItem("November");
        jComboBox2.addItem("December");
        
        jComboBox4.addItem("Income");
        jComboBox4.addItem("Expense");
        
        for(int i = 2019; i < 2100; i++){
            int x = i;
            x++;
            jComboBox3.addItem(String.valueOf(x));
        }
        showTransactions();
    }
    
    public ArrayList<Transactions> transactionList(){
        ArrayList<Transactions> transactionsList = new ArrayList<>();
        LoginFrame lg = new LoginFrame();
        String Username = lg.getUsername();
        
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
            String query1 = "Select * from Transactions where username = '"+Username+"'";
            resultSet = db.doQuery(query1, con);
            
            Transactions transactions;
            
            while(resultSet.next()){
                transactions = new Transactions(resultSet.getInt("TransactionID"), resultSet.getDate("Datetime"), 
                        resultSet.getString("Username"),resultSet.getString("Transactionname"),resultSet.getString("Type"), 
                        resultSet.getDouble("Amount"), resultSet.getString("Category"),resultSet.getString("Month"),resultSet.getInt("Years"));
                transactionsList.add(transactions);
            }
        }
        catch (Exception e) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, e);
        }
        return transactionsList;
    }
    
    public ArrayList<Transactions> sortedTransactionList(){
        ArrayList<Transactions> sortedTransactionsList = new ArrayList<>();
        LoginFrame lg = new LoginFrame();
        String Username = lg.getUsername();
        String selectedOption = jComboBox1.getSelectedItem().toString().trim();
        
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
            String query1 = "Select * from Transactions where username = '"+Username+"' AND CATEGORY = '"+selectedOption+"'";
            resultSet = db.doQuery(query1, con);
            
            Transactions transactions;
            
            while(resultSet.next()){
                transactions = new Transactions(resultSet.getInt("TransactionID"), resultSet.getDate("Datetime"), 
                        resultSet.getString("Username"),resultSet.getString("Transactionname"),resultSet.getString("Type"), 
                        resultSet.getDouble("Amount"), resultSet.getString("Category"),resultSet.getString("Month"),resultSet.getInt("Years"));
                sortedTransactionsList.add(transactions);
            }
        }
        catch (Exception e) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, e);
        }
        return sortedTransactionsList;
    }
    
    public ArrayList<Transactions> sortedByMonthTransactionList(){
        ArrayList<Transactions> sortedByMonthTransactionsList = new ArrayList<>();
        LoginFrame lg = new LoginFrame();
        String Username = lg.getUsername();
        String selectedOption1 = jComboBox2.getSelectedItem().toString().trim().toUpperCase();
        String selectedOption2 = jComboBox3.getSelectedItem().toString().trim();
        
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
            String query1 = "Select * from Transactions where username = '"+Username+"' AND MONTH = '"+selectedOption1+"' AND YEARS = "+Integer.parseInt(selectedOption2)+"";
            resultSet = db.doQuery(query1, con);
            
            Transactions transactions;
            
            while(resultSet.next()){
                transactions = new Transactions(resultSet.getInt("TransactionID"), resultSet.getDate("Datetime"), 
                        resultSet.getString("Username"),resultSet.getString("Transactionname"),resultSet.getString("Type"), 
                        resultSet.getDouble("Amount"), resultSet.getString("Category"),resultSet.getString("Month"),resultSet.getInt("Years"));
                sortedByMonthTransactionsList.add(transactions);
            }
        }
        catch (Exception e) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, e);
        }
        return sortedByMonthTransactionsList;
    }
    
    public ArrayList<Transactions> sortedByTypeTransactionList(){
        ArrayList<Transactions> sortedByTypeTransactionsList = new ArrayList<>();
        LoginFrame lg = new LoginFrame();
        String Username = lg.getUsername();
        String selectedOption1 = jComboBox4.getSelectedItem().toString().trim();
        
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
            String query1 = "Select * from Transactions where username = '"+Username+"' AND TYPE = ('"+selectedOption1+"')";
            resultSet = db.doQuery(query1, con);
            
            Transactions transactions;
            
            while(resultSet.next()){
                transactions = new Transactions(resultSet.getInt("TransactionID"), resultSet.getDate("Datetime"), 
                        resultSet.getString("Username"),resultSet.getString("Transactionname"),resultSet.getString("Type"), 
                        resultSet.getDouble("Amount"), resultSet.getString("Category"),resultSet.getString("Month"),resultSet.getInt("Years"));
                sortedByTypeTransactionsList.add(transactions);
            }
        }
        catch (Exception e) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, e);
        }
        return sortedByTypeTransactionsList;
    }
    
    
    public void showTransactions(){
        ArrayList<Transactions> list = transactionList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        Object[] row = new Object[6];
        for(int i=0; i<list.size(); i++){
            row[0] = list.get(i).getDate();
            row[1] = list.get(i).getTransaction();
            row[2] = list.get(i).getType();
            row[3] = list.get(i).getCategory();
            row[4] = list.get(i).getAmount();
            model.addRow(row);
        }
    }
    
    public void showSortedTransactions(){
        ArrayList<Transactions> list = sortedTransactionList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        for(int i=0; i<list.size(); i++){
            row[0] = list.get(i).getDate();
            row[1] = list.get(i).getTransaction();
            row[2] = list.get(i).getType();
            row[3] = list.get(i).getCategory();
            row[4] = list.get(i).getAmount();
            model.addRow(row);
        }
    }
    
    public void showSortedByMonthTransactions(){
        ArrayList<Transactions> list = sortedByMonthTransactionList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        for(int i=0; i<list.size(); i++){
            row[0] = list.get(i).getDate();
            row[1] = list.get(i).getTransaction();
            row[2] = list.get(i).getType();
            row[3] = list.get(i).getCategory();
            row[4] = list.get(i).getAmount();
            model.addRow(row);
        }
    }
    
    public void showSortedByTypeTransactions(){
        ArrayList<Transactions> list = sortedByTypeTransactionList();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        for(int i=0; i<list.size(); i++){
            row[0] = list.get(i).getDate();
            row[1] = list.get(i).getTransaction();
            row[2] = list.get(i).getType();
            row[3] = list.get(i).getCategory();
            row[4] = list.get(i).getAmount();
            model.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setBackground(new java.awt.Color(204, 204, 204));
        jTable1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Transaction", "Type", "Category", "Amount"
            }
        ));
        jTable1.setSelectionBackground(new java.awt.Color(102, 102, 102));
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Go Back");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setBackground(new java.awt.Color(253, 253, 253));
        jComboBox1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food and Beverage", "Bills", "Investment", "Loan", "Other", "Salary", "Gift" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Filter");
        jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox2.setBackground(new java.awt.Color(253, 253, 253));
        jComboBox2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jComboBox3.setBackground(new java.awt.Color(253, 253, 253));
        jComboBox3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setText("Filter by Category");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setText("Filter By Month");

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Filter");
        jButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(102, 102, 102));
        jButton4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Clear");
        jButton4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox4.setBackground(new java.awt.Color(253, 253, 253));
        jComboBox4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setText("Filter by Type");

        jButton5.setBackground(new java.awt.Color(102, 102, 102));
        jButton5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Filter");
        jButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4))
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //GO BACK TO USERVIEW BUTTON
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        UserView userview = new UserView();
        userview.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        
    }//GEN-LAST:event_jComboBox1ActionPerformed
    
    //FILTER BY CATEGORY BUTTON
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        showSortedTransactions();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    //FILTER BY MONTH AND YEAR BUTTON
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        showSortedByMonthTransactions();
    }//GEN-LAST:event_jButton3ActionPerformed

    //CLEAR FILTER
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        showTransactions();
    }//GEN-LAST:event_jButton4ActionPerformed

    //FILTER BY TYPE BUTTON
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        showSortedByTypeTransactions();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewTransactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewTransactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewTransactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewTransactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewTransactions().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
