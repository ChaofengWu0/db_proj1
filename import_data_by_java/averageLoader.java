import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

public class AverageLoader {
//    static class pro_sales {
//        String product_code;
//        String salesman_number;
//
//        public pro_sales(String product_code, String salesman_number) {
//            this.product_code = product_code;
//            this.salesman_number = salesman_number;
//        }
//
//        public pro_sales() {
//        }
//    }

    private static URL propertyURL = AverageLoader.class
            .getResource("/loader.cnf");

    private static Connection con = null;
    //    private static PreparedStatement stmt = null;
    private static PreparedStatement stmt0 = null;
    private static PreparedStatement stmt1 = null;
    private static PreparedStatement stmt2 = null;
    private static PreparedStatement stmt3 = null;
    private static PreparedStatement stmt4 = null;
    private static PreparedStatement stmt5 = null;
    private static PreparedStatement stmt6 = null;
    private static boolean verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
//                if (stmt != null) stmt.close();

                if (stmt0 != null) stmt0.close();

                if (stmt1 != null) stmt1.close();

                if (stmt2 != null) stmt2.close();

                if (stmt3 != null) stmt3.close();

                if (stmt4 != null) stmt4.close();

                if (stmt5 != null) stmt5.close();

                if (stmt6 != null) stmt6.close();

                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }


    private static void loadData_for_client(String client_enterprise, String supply_center, String country, String
            city, String industry
            , HashMap<String, Integer> map_for_table_cli)
            throws SQLException {
        if (con != null) {
            try {
                //导入数据
                stmt0 = con.prepareStatement("insert into client(client_enterprise,supply_center,country,city,industry)"
                        + " values(?,?,?,?,?)");
            } catch (SQLException e) {
                System.err.println("Insert statement failed");
                System.err.println(e.getMessage());
                closeDB();
                System.exit(1);
            }
            if (map_for_table_cli.get(client_enterprise) == null) {
                map_for_table_cli.put(client_enterprise, 1);
                stmt0.setString(1, client_enterprise);
                stmt0.setString(2, supply_center);
                stmt0.setString(3, country);
                stmt0.setString(4, city);
                stmt0.setString(5, industry);
//                stmt.addBatch();
                stmt0.executeUpdate();
            }
        }
    }


    private static void loadData_for_contract(String contract_number, String contract_date, String
            estimated_delivery_date,
                                              String lodgement_date, String director, String
                                                      client_enterprise, HashMap<String, Integer> map_for_table_con)
            throws SQLException {
        if (con != null) {
            try {
                //导入数据
                stmt1 = con.prepareStatement("insert into contract(contract_number,contract_date,estimated_date,lodgement_date" +
                        ",director,client_enterprise)"
                        + " values(?,?,?,?,?,?)");
            } catch (SQLException e) {
                System.err.println("Insert statement failed");
                System.err.println(e.getMessage());
                closeDB();
                System.exit(1);
            }
            if (map_for_table_con.get(contract_number) == null) {
                map_for_table_con.put(contract_number, 1);
                stmt1.setString(1, contract_number);
                stmt1.setString(2, contract_date);
                stmt1.setString(3, estimated_delivery_date);
                stmt1.setString(4, lodgement_date);
                stmt1.setString(5, director);
                stmt1.setString(6, client_enterprise);
//                stmt.addBatch();
                stmt1.executeUpdate();
            }
        }
    }


    private static void loadData_for_contract_model(String contract_number, String product_model, int quantity)
            throws SQLException {
        if (con != null) {
            try {
                //导入数据
                stmt2 = con.prepareStatement("insert into contract_model(contract_number,product_model,quantity)"
                        + " values(?,?,?)");
            } catch (SQLException e) {
                System.err.println("Insert statement failed");
                System.err.println(e.getMessage());
                closeDB();
                System.exit(1);
            }
            stmt2.setString(1, contract_number);
            stmt2.setString(2, product_model);
            stmt2.setInt(3, quantity);
//            stmt.addBatch();
            stmt2.executeUpdate();
        }
    }


    private static void loadData_for_model(String product_model, int unit_price, String
            product_code, HashMap<String, Integer> map_for_table_model)
            throws SQLException {
        if (con != null) {
            try {
                //导入数据
                stmt3 = con.prepareStatement("insert into model(product_model,unit_price,product_code)"
                        + " values(?,?,?)");
            } catch (SQLException e) {
                System.err.println("Insert statement failed");
                System.err.println(e.getMessage());
                closeDB();
                System.exit(1);
            }
            if (map_for_table_model.get(product_model) == null) {
                map_for_table_model.put(product_model, 1);
                stmt3.setString(1, product_model);
                stmt3.setInt(2, unit_price);
                stmt3.setString(3, product_code);
//                stmt.addBatch();
                stmt3.executeUpdate();
            }
        }
    }


    private static void loadData_for_product(String product_code, String
            product_name, HashMap<String, Integer> map_for_table_pro)
            throws SQLException {
        if (con != null) {
            try {
                //导入数据
                stmt4 = con.prepareStatement("insert into product(product_code,product_name)"
                        + " values(?,?)");
            } catch (SQLException e) {
                System.err.println("Insert statement failed");
                System.err.println(e.getMessage());
                closeDB();
                System.exit(1);
            }
            if (map_for_table_pro.get(product_code) == null) {
                map_for_table_pro.put(product_code, 1);
                stmt4.setString(1, product_code);
                stmt4.setString(2, product_name);
                stmt4.executeUpdate();
//                stmt.addBatch();
            }
        }
    }


    private static void loadData_for_product_salesman(String product_code, String
            salesman_number, HashMap<String, Integer> map_for_relation_proSale, int[] test)
            throws SQLException {
        if (con != null) {
            try {
                //导入数据
                stmt5 = con.prepareStatement("insert into product_salesman(product_code,salesman_number)"
                        + " values(?,?)");
            } catch (SQLException e) {
                System.err.println("Insert statement failed");
                System.err.println(e.getMessage());
                closeDB();
                System.exit(1);
            }
            String temp = product_code + salesman_number;
            // 此处和其他判别重复的方法不一样，需要判两次
            // 一次是看这个map包含还是不包含product_code,不包含可以直接加入表中
            // 另一次是如果此map包含product_code，还要判断product_code和salesman_number这一对值是否在map中，不包含此元组则可以加入表中
            if (map_for_relation_proSale.get(temp) == null) {
                map_for_relation_proSale.put(temp, 1);
                stmt5.setString(1, product_code);
                stmt5.setString(2, salesman_number);
                stmt5.executeUpdate();
//                stmt.addBatch();
            }
        }
    }


    private static void loadData_for_salesman(String salesman_number, String salesman_name, String gender,
                                              int age, String mobile_phone, HashMap<String, Integer> map_for_table_sale)
            throws SQLException {
        if (con != null) {
            try {
                //导入数据
                stmt6 = con.prepareStatement("insert into salesman(salesman_number,salesman_name,gender,age,mobile_phone)"
                        + " values(?,?,?,?,?)");
            } catch (SQLException e) {
                System.err.println("Insert statement failed");
                System.err.println(e.getMessage());
                closeDB();
                System.exit(1);
            }
            if (map_for_table_sale.get(salesman_number) == null) {
                map_for_table_sale.put(salesman_number, 1);
                stmt6.setString(1, salesman_number);
                stmt6.setString(2, salesman_name);
                stmt6.setString(3, gender);
                stmt6.setInt(4, age);
                stmt6.setString(5, mobile_phone);
                stmt6.executeUpdate();
//                stmt6.addBatch();
            }
        }
    }


    public static void main(String[] args) {
        String fileName = null;
        boolean verbose = false;

        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
                switch (args[0]) {
                    case "-v":
                        verbose = true;
                        break;
                    default:
                        System.err.println("Usage: java [-v] AverageLoader filename");
                        System.exit(1);
                }
                fileName = args[1];
                break;
            default:
                System.err.println("Usage: java [-v] AverageLoader filename");
                System.exit(1);
        }

        if (propertyURL == null) {
            System.err.println("No configuration file (loader.cnf) found");
            System.exit(1);
        }
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "u99999999");
        defprop.put("password", "99999999");
        defprop.put("database", "postgres");
        Properties prop = new Properties(defprop);
        try (BufferedReader conf
                     = new BufferedReader(new FileReader(propertyURL.getPath()))) {
            prop.load(conf);
        } catch (IOException e) {
            // Ignore
            System.err.println("No configuration file (loader.cnf) found");
        }
        try (BufferedReader infile
                     = new BufferedReader(new FileReader(fileName))) {
            long start;
            long end;
            String line;
            String[] parts;


            String contract_number;
            String client_enterprise;
            String supply_center;
            String country;
            String city;
            String industry;
            String product_code;
            String product_name;
            String product_model;
            int unit_price;
            int quantity;
            String contract_date;
            String estimated_delivery_date;
            String lodgement_date;
            String director;
            String salesman;
            String salesman_number;
            String gender;
            int age;
            String mobile_phone;


            // 这里除了第五个map之外，都是string，integer的hashmap，integer置为1的时候，说明在这个hashmap中，当前搜索的string已经出现过，就不能再进行添加操作，否则会违反
            // 主键unique的性质
            HashMap<String, Integer> map_for_table_con = new HashMap<>();
            HashMap<String, Integer> map_for_table_cli = new HashMap<>();
            HashMap<String, Integer> map_for_table_model = new HashMap<>();
            HashMap<String, Integer> map_for_table_pro = new HashMap<>();
            // 由于这个hashmap要用于product_salesman这个表的数据填充，所以需要辨别两个属性，i.e.(product_code和salesman_number)，
            // 所以我用了一个hashmap作为key
            HashMap<String, Integer> map_for_relation_ProSale = new HashMap<>();
            HashMap<String, Integer> map_for_table_sale = new HashMap<>();


            int cnt = 0;
            // Empty target table
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            Statement statement;
            if (con != null) {
                statement = con.createStatement();
                statement.execute("truncate table contract,client,contract_model,model,product,product_salesman,salesman");
                statement.close();
            }
            closeDB();
            //
            start = System.currentTimeMillis();
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            infile.readLine();
            int[] test = new int[]{1};
            while ((line = infile.readLine()) != null) {
                parts = line.split(",");
                if (parts.length > 1) {


                    contract_number = parts[0];
                    client_enterprise = parts[1];
                    supply_center = parts[2];
                    country = parts[3];
                    city = parts[4];
                    industry = parts[5];
                    product_code = parts[6];
                    product_name = parts[7];
                    product_model = parts[8];
                    unit_price = Integer.parseInt(parts[9]);
                    quantity = Integer.parseInt(parts[10]);
                    contract_date = parts[11];
                    estimated_delivery_date = parts[12];
                    lodgement_date = parts[13];
                    director = parts[14];
                    salesman = parts[15];
                    salesman_number = parts[16];
                    gender = parts[17];
                    age = Integer.parseInt(parts[18]);
                    mobile_phone = parts[19];

                    loadData_for_client(client_enterprise, supply_center, country, city, industry, map_for_table_cli);
                    loadData_for_contract(contract_number, contract_date, estimated_delivery_date, lodgement_date, director
                            , client_enterprise, map_for_table_con);
                    loadData_for_salesman(salesman_number, salesman, gender, age, mobile_phone, map_for_table_sale);
                    loadData_for_product(product_code, product_name, map_for_table_pro);
                    loadData_for_product_salesman(product_code, salesman_number, map_for_relation_ProSale, test);
                    loadData_for_model(product_model, unit_price, product_code, map_for_table_model);
                    loadData_for_contract_model(contract_number, product_model, quantity);


                    cnt++;
                }
            }
            con.commit();
            closeDB();
            end = System.currentTimeMillis();
            System.out.println(cnt + " records successfully loaded");
            System.out.println("Loading speed : "
                    + (cnt * 1000) / (end - start)
                    + " records/s");
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        }
        closeDB();
    }
}

