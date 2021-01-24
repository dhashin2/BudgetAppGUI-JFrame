package weSave;

import static weSave.weSave.DB_PASSWD;
import static weSave.weSave.DB_URL;
import static weSave.weSave.DB_USER;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserView extends javax.swing.JFrame {
    private static DBBean db = new DBBean();
    private static ResultSet resultSet;
    
    public UserView() {
        initComponents();
        getContentPane().setBackground(Color.LIGHT_GRAY);
        LoginFrame lg = new LoginFrame();
        String Username = lg.getUsername();
        
        
        setResizable(false);
        setTitle("weSave - Welcome " + Username.toLowerCase());
        pack();
        setLocationRelativeTo(null);
        
        setBalance();
        setMonthlyIncome();
        setMonthlyExpense();
        setMonthlyBalance();
        
    }
    
    //TO DISPLAY THE TOTAL BALANCE IN A USER'S ACCOUNT IN THEIR USER HOME PAGE
    public void setBalance(){
        LoginFrame lg = new LoginFrame();
        String Username = lg.getUsername();
        Connection con = null;
        String iQuery = "SELECT SUM(AMOUNT) FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Income')";
        String eQuery = "SELECT SUM(AMOUNT) FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Expense')";
        String checkQuery = "SELECT * FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Income')";
        String checkQuery2 = "SELECT * FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Expense')";
        
        try {
            con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
        }
        catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            
        resultSet = db.doQuery(checkQuery, con);
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        boolean incomeEntriesAvailable;
        if(resultSet.next() == false){
            incomeEntriesAvailable = false;
        }
        else{
            incomeEntriesAvailable = true;
        }
        
        resultSet = db.doQuery(checkQuery2, con);
        boolean expenseEntriesAvailable;
        if(resultSet.next() == false){
            expenseEntriesAvailable = false;
        }
        else{
            expenseEntriesAvailable = true;
        }
        
        if(incomeEntriesAvailable && expenseEntriesAvailable){
            resultSet = db.doQuery(iQuery, con);
            resultSet.next();
            String sumOfIncome = resultSet.getString("1");
            resultSet.close();
            resultSet = db.doQuery(eQuery, con);
            resultSet.next();
            String sumOfExpense = resultSet.getString("1");
            resultSet.close();
            double income = Double.parseDouble(sumOfIncome);
            double expense = Double.parseDouble(sumOfExpense);
            double total = (income - expense);
            double roundOff = Math.round(total * 100.0) / 100.0;
            String totalString = myFormat.format(roundOff);
            jTextField1.setText(totalString);
            jTextField1.setEditable(false); 
        
        }else if(incomeEntriesAvailable && !expenseEntriesAvailable){
            resultSet = db.doQuery(iQuery, con);
            resultSet.next();
            String sumOfIncomeAlone = resultSet.getString("1");
            double sumOfIncome = Double.parseDouble(sumOfIncomeAlone);
            String sumOfIncomeAloneTotal = myFormat.format(sumOfIncome);
            jTextField1.setText(sumOfIncomeAloneTotal);
            jTextField1.setEditable(false);
        
        }else if(!incomeEntriesAvailable && expenseEntriesAvailable){
            resultSet = db.doQuery(eQuery, con);
            resultSet.next();
            String sumOfExpenseAlone = resultSet.getString("1");
            double sumOfExpense = Double.parseDouble(sumOfExpenseAlone);
            String sumOfExpenseAloneFinal = myFormat.format(sumOfExpense);
            
            jTextField1.setText("-"+sumOfExpenseAloneFinal);
            jTextField1.setEditable(false);
        }else{
            jTextField1.setText("0");
            jTextField1.setEditable(false);
        }
        }catch(Exception e){
            
        }
    }
    
    public void setMonthlyBalance(){
        LoginFrame lg = new LoginFrame();
        String Username = lg.getUsername();
        Connection con = null;
        String iQuery = "SELECT SUM(AMOUNT) FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Income') AND MONTH = "
                + "('"+java.time.LocalDate.now().getMonth().toString().toUpperCase()+"') AND YEARS = "+java.time.LocalDate.now().getYear()+"";
        String eQuery = "SELECT SUM(AMOUNT) FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Expense') AND MONTH = "
                + "('"+java.time.LocalDate.now().getMonth().toString().toUpperCase()+"') AND YEARS = "+java.time.LocalDate.now().getYear()+"";
        String checkQuery = "SELECT * FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Income')";
        String checkQuery2 = "SELECT * FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Expense')";
        try {
            con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
        }
        catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        try{
        resultSet = db.doQuery(checkQuery, con);
        boolean incomeEntriesAvailable;
        if(resultSet.next() == false){
            incomeEntriesAvailable = false;
        }
        else{
            incomeEntriesAvailable = true;
        }
        
        resultSet = db.doQuery(checkQuery2, con);
        boolean expenseEntriesAvailable;
        if(resultSet.next() == false){
            expenseEntriesAvailable = false;
        }
        else{
            expenseEntriesAvailable = true;
        }
        
        if(incomeEntriesAvailable && expenseEntriesAvailable){
            resultSet = db.doQuery(iQuery, con);
            resultSet.next();
            String sumOfIncome = resultSet.getString("1");
            resultSet.close();
            resultSet = db.doQuery(eQuery, con);
            resultSet.next();
            String sumOfExpense = resultSet.getString("1");
            resultSet.close();
            double income = Double.parseDouble(sumOfIncome);
            double expense = Double.parseDouble(sumOfExpense);
            double total = (income - expense);
            double roundOff = Math.round(total * 100.0) / 100.0;
            String totalString = myFormat.format(roundOff); 
            jTextField4.setText(totalString);
            jTextField4.setEditable(false); 
        }else{
            jTextField4.setText("0");
            jTextField4.setEditable(false);
        }
        }catch(Exception e){
            
        }
    }
    
    public void setMonthlyIncome(){
        LoginFrame lg = new LoginFrame();
        String Username = lg.getUsername();
        Connection con = null;
        String query = "SELECT SUM(AMOUNT) FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Income') AND MONTH = "
                + "('"+java.time.LocalDate.now().getMonth().toString().toUpperCase()+"') AND YEARS = "+java.time.LocalDate.now().getYear()+"";
        String checkQuery = "SELECT * FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Income')";
        try {
            con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
        }
        catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        resultSet = db.doQuery(checkQuery, con);
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        boolean entriesAvailable;
        try {
            if(resultSet.next() == false){
                entriesAvailable = false;
            }
            else{
                entriesAvailable = true;
            }
            
            if(entriesAvailable){
                resultSet = db.doQuery(query, con);
                resultSet.next();
                String sumOfIncome = resultSet.getString("1");
                double income = Double.parseDouble(sumOfIncome);         
                double roundOff = Math.round(income * 100.0) / 100.0;
                String totalString = myFormat.format(roundOff); 
                jTextField2.setText(totalString);
                jTextField2.setEditable(false);
            }else{
                jTextField2.setText("0");
                jTextField2.setEditable(false);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setMonthlyExpense(){
        LoginFrame lg = new LoginFrame();
        String Username = lg.getUsername();
        Connection con = null;
        String query = "SELECT SUM(AMOUNT) FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Expense') AND MONTH = "
                + "('"+java.time.LocalDate.now().getMonth().toString().toUpperCase()+"') AND YEARS = "+java.time.LocalDate.now().getYear()+"";
        String checkQuery = "SELECT * FROM TRANSACTIONS WHERE USERNAME = ('"+Username+"') AND TYPE = ('Expense')";
        try {
            con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
        }
        catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        resultSet = db.doQuery(checkQuery, con);
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        boolean entriesAvailable;
        try {
            if(resultSet.next() == false){
                entriesAvailable = false;
            }
            else{
                entriesAvailable = true;
            }
            
            if(entriesAvailable){
                resultSet = db.doQuery(query, con);
                resultSet.next();
                String sumOfIncome = resultSet.getString("1");
                double income = Double.parseDouble(sumOfIncome);         
                double roundOff = Math.round(income * 100.0) / 100.0;
                String totalString = myFormat.format(roundOff);
                jTextField3.setText(totalString);
                jTextField3.setEditable(false);
            }else{
                jTextField3.setText("0");
                jTextField3.setEditable(false);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
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

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        AddIncome = new javax.swing.JButton();
        AddExpense = new javax.swing.JButton();
        ViewTransactions = new javax.swing.JButton();
        Logout = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(java.awt.Color.lightGray);

        AddIncome.setBackground(new java.awt.Color(102, 102, 102));
        AddIncome.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        AddIncome.setForeground(new java.awt.Color(255, 255, 255));
        AddIncome.setText("Add Income");
        AddIncome.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        AddIncome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddIncomeActionPerformed(evt);
            }
        });

        AddExpense.setBackground(new java.awt.Color(102, 102, 102));
        AddExpense.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        AddExpense.setForeground(new java.awt.Color(255, 255, 255));
        AddExpense.setText("Add Expense");
        AddExpense.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        AddExpense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddExpenseActionPerformed(evt);
            }
        });

        ViewTransactions.setBackground(new java.awt.Color(102, 102, 102));
        ViewTransactions.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ViewTransactions.setForeground(new java.awt.Color(255, 255, 255));
        ViewTransactions.setText("View Transactions");
        ViewTransactions.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ViewTransactions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewTransactionsActionPerformed(evt);
            }
        });

        Logout.setBackground(new java.awt.Color(102, 102, 102));
        Logout.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        Logout.setForeground(new java.awt.Color(255, 255, 255));
        Logout.setText("Logout");
        Logout.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AddExpense, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ViewTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(AddIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AddExpense, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ViewTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Incomes");

        jTextField2.setBackground(new java.awt.Color(102, 102, 102));
        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 153, 51));
        jTextField2.setText("400000");
        jTextField2.setBorder(null);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Expenses");

        jTextField3.setBackground(new java.awt.Color(102, 102, 102));
        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 153, 51));
        jTextField3.setText("400000");
        jTextField3.setBorder(null);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.setBackground(new java.awt.Color(102, 102, 102));
        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 153, 51));
        jTextField4.setText("400000");
        jTextField4.setBorder(null);
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Balance");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTextField3)
                    .addComponent(jTextField4)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jTextField1.setBackground(new java.awt.Color(102, 102, 102));
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 153, 51));
        jTextField1.setText("400000");
        jTextField1.setBorder(null);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Monthly Balance");

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total Balance");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //ADD INCOME JFRAME BUTTON
    private void AddIncomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddIncomeActionPerformed
        AddIncome addIncome = new AddIncome();
        dispose();
        addIncome.setVisible(true);   
    }//GEN-LAST:event_AddIncomeActionPerformed

    //ADD EXPENSE BUTTON
    private void AddExpenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddExpenseActionPerformed
        AddExpense addExpense = new AddExpense();
        dispose();
        addExpense.setVisible(true);
    }//GEN-LAST:event_AddExpenseActionPerformed

    //VIEW TRANSACTIONS BUTTON
    private void ViewTransactionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewTransactionsActionPerformed
        ViewTransactions viewTransactions = new ViewTransactions();
        dispose();
        viewTransactions.setVisible(true);
    }//GEN-LAST:event_ViewTransactionsActionPerformed

    //LOGOUT
    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        dispose();
        LoginFrame lg = new LoginFrame();
        lg.setVisible(true);
        lg.clearUsername();
    }//GEN-LAST:event_LogoutActionPerformed
    
    //DISPLAY TOTAL VALUE IN ACCOUNT
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
    
    //DISPLAY TOTAL INCOME FOR THE MONTH
    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    //DISPLAY TOTAL EXPENSE FOR THE MONTH
    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    //DISPLAY BALANCE FOR THE MONTH
    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

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
            java.util.logging.Logger.getLogger(UserView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddExpense;
    private javax.swing.JButton AddIncome;
    private javax.swing.JButton Logout;
    private javax.swing.JButton ViewTransactions;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
