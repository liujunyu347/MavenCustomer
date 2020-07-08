package com.flexon.customer;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

class CustomerDemo{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql:/localhost:3306/customer";

    static final String USER = "root";
    static final String PASS = "yes";

    public static void main(String[] args) {
        System.out.println("Menu:");
        System.out.println("exit: exit the program");
        System.out.println("add new user: add firstName LastName phoneNumber");
        System.out.println("delete existing users(any column name and its value): delete id 4");
        System.out.println("update existing users(any column name and its value): update id 4 phoneNumber 321321321");
        System.out.println();

        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");


            Scanner input = new Scanner(System.in);
            String[] command = new String[]{"", "", "", "", ""};
            while (!command[0].equals("exit")) {
                System.out.print("Please enter a command: ");
                command = input.nextLine().trim().split(" ", 5);
                if (command[0].equals("add")){
                    add(command[1], command[2], command[3]);
                }else if (command[0].equals("delete")){
                    delete(command[1], command[2]);
                }else if (command[0].equals("update")){
                    update(command[1], command[2], command[3], command[4]);
                }else{
                    if (!command[0].equals("exit")){
                        System.out.println("Invalid command");
                    }
                }
            }

            printDB();

        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
                se2.printStackTrace();
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    public static boolean add(String firstName, String lastName, String phoneNumber) throws SQLException {
        if (firstName == null && lastName == null && phoneNumber == null){
            return false;
        }
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO customer(firstName, lastName, phoneNumber) VALUES (?, ?, ?)");
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, phoneNumber);

        ps.executeUpdate();
        System.out.println("INSERT Successfully");
        return true;
    }

    public static boolean delete(String columnName, String value) throws SQLException {
        if (columnName == null && value == null){
            return false;
        }
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement ps = conn.prepareStatement("DELETE FROM customer WHERE " +
                columnName + " = " + value + ";");

        ps.executeUpdate();
        System.out.println("DELETE Successfully");
        return true;
    }

    public static boolean update(String sourceColumn, String sourceValue, String targetColumn, String targetValue)
            throws SQLException {
        if (targetColumn == null && targetValue == null){
            return false;
        }
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement ps = conn.prepareStatement("UPDATE customer SET " +
                sourceColumn + " = '" + sourceValue + "' WHERE " + targetColumn + " = '" + targetValue + "';");

        ps.executeUpdate();
        System.out.println("UPDATE Successfully");
        return true;
    }

    public static void printDB() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt = conn.createStatement();
        String sql;
        sql = "select * from customer;";
        ResultSet rs = stmt.executeQuery(sql);

//        while(rs.next()){
//            int id  = rs.getInt("id");
//            String first = rs.getString("firstName");
//            String last = rs.getString("lastName");
//            String address = rs.getString("phoneNumber");
//
//            System.out.println("ID: " + id);
//            System.out.println(", First: " + first);
//            System.out.println(", Last: " + last);
//            System.out.println(", Phone: " + address);
//        }
        try {
            FileWriter myWriter = new FileWriter("output.txt");
//            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            while(rs.next()) {
                int id = rs.getInt("id");
                String first = rs.getString("firstName");
                String last = rs.getString("lastName");
                String address = rs.getString("phoneNumber");
                myWriter.write("ID: " + id + ", First: " + first + ", Last: " + last + ", Phone: " + address + "\n");
//                System.out.println("ID: " + id);
//                System.out.println(", First: " + first);
//                System.out.println(", Last: " + last);
//                System.out.println(", Phone: " + address);
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        rs.close();
        stmt.close();
        conn.close();
    }

}

//
//class Customer{
//    private final int customerid;
//    private String firstName;
//    private String lastName;
//    private String phoneNumber;
//
//    public Customer(String firstName, String lastName, int customerid, String phoneNumber){
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.customerid = customerid;
//        this.phoneNumber = phoneNumber;
//    }
//
//    public int getCustomerid() {
//        return customerid;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//}