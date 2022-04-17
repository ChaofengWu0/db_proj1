import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ClientFile {
    //尝试把增删改查都在这里实现
    static OrderNode last = new OrderNode();
    //查重
    static HashMap<Integer,Integer>dict=new HashMap<>();
    public static void main(String[] args) throws IOException {
        OrderNode head = new OrderNode();
        OrderNode cur = new OrderNode();
        Scanner in = new Scanner(System.in);
        int count = 0;
        //如果是测试insert，就插入20000行数据入内存，测试其他的就要插入更多
        System.out.println("input the initial file:");
        String file=in.next();
        BufferedReader br = new BufferedReader(new FileReader(file));
        System.out.print("What do you want to do next?(insert,delete,update,select)\n");
        String operation = in.next();
        long start = System.currentTimeMillis();//读入内存的时间也要算
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            int order_number = Integer.parseInt(parts[0]);
            String product_code = parts[1];
            String product_model = parts[2];
            int quantity = Integer.parseInt(parts[3]);
            String contract_number = parts[4];
            String salesman_number = parts[5];
            String estimated_date = parts[6];
            String lodgement_date = parts[7];
            if (count == 0) {
                head = new OrderNode(null, null, order_number, product_code, product_model, quantity, contract_number, salesman_number, estimated_date, lodgement_date);
                cur = head;
                count++;
            } else {
                OrderNode newNode = new OrderNode(cur, null, order_number, product_code, product_model, quantity, contract_number, salesman_number, estimated_date, lodgement_date);
                cur.next = newNode;
                cur = newNode;
                count++;
            }
        }
        last = cur;
        //ps：此时cur等于末尾了
        //狭义增删改查实现：
        //直接从第二个表中读出
//        增
        if (operation.equals("insert")) {
            System.out.print("Please input the file of data you want to insert:\n");
            BufferedReader br2 = new BufferedReader(new FileReader(in.next()));
            String line2;
            while ((line2 = br2.readLine()) != null) {
                insert(line2);
            }
//            OrderNode cur2 = cur;
//            while ((line2 = br2.readLine()) != null) {
//                String[] parts = line2.split(",");
//                int order_number = Integer.parseInt(parts[0]);
//                String product_code = parts[1];
//                String product_model = parts[2];
//                int quantity = Integer.parseInt(parts[3]);
//                String contract_number = parts[4];
//                String salesman_number = parts[5];
//                String estimated_date = parts[6];
//                String lodgement_date = parts[7];
//                OrderNode newNode = new OrderNode(cur2, null, order_number, product_code, product_model, quantity, contract_number, salesman_number, estimated_date, lodgement_date);
//                cur2.next = newNode;
//                cur2 = newNode;
//            }
            System.out.print("please input the file name of the output:\n");
            write(head, in.next());
        }
//        删
        else if (operation.equals("delete")) {
            System.out.print("please input how many data you want to delete:\n");
            int count2 = in.nextInt();
            for (int i = 1; i <= count2; i++) {//file与DBMS不同，测试的时候不需要加上1W,10W,100W,500W
                head = delete(head, i);
            }
            System.out.print("please input the file name of the output:\n");
            write(head, in.next());
        }
        //改
        else if (operation.equals("update")) {
            System.out.print("please input how many data you want to update:\n");
            int count2 = in.nextInt();
            System.out.print("please input the file of the new data:\n");
            String dataUpdate = in.next();
            String line2;
            BufferedReader br2 = new BufferedReader(new FileReader(dataUpdate));
            for (int i = 1; i <= count2; i++) {
                System.out.println(i);
                OrderNode pre;
                OrderNode next;
                int order_number = 0;
                String product_code = "";
                String product_model = "";
                int quantity = 0;
                String contract_number = "";
                String salesman_number = "";
                String estimated_date = "";
                String lodgement_date = "";
                line2 = br2.readLine();
                String[] parts = line2.split(",");
                order_number = Integer.parseInt(parts[0]);
                product_code = parts[1];
                product_model = parts[2];
                quantity = Integer.parseInt(parts[3]);
                contract_number = parts[4];
                salesman_number = parts[5];
                estimated_date = parts[6];
                lodgement_date = parts[7];
                head = update(head, i, order_number, product_code, product_model, quantity, contract_number, salesman_number, estimated_date, lodgement_date);
            }
            System.out.print("please input the file name of the output:\n");
            write(head, in.next());
        }
        //查
        else if (operation.equals("select")) {
            System.out.print("please input the scale of your select:\n");
            int lo = in.nextInt();
            int hi = in.nextInt();
            select(head, lo, hi);
//            for (int i = lo; i <= hi; i++) {
//                OrderNode cur2 = head;
//                while (cur2.order_number != i) {
//                    cur2 = cur2.next;
//                }
//                System.out.println("order_number:" + cur2.order_number + " product_code:" + cur2.product_code +
//                        " product_model:" + cur2.product_model + " quantity:" + cur2.quantity +
//                        " contract_number:" + cur2.contract_number + " salesman_number:" + cur2.salesman_number +
//                        " estimated_date:" + cur2.estimated_date + " lodgement_date:" + cur2.lodgement_date + "\n");
//            }
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time);
    }

    private static void select(OrderNode head, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            OrderNode cur2 = head;
            while (cur2!=null&&cur2.order_number != i) {
                cur2 = cur2.next;
            }
            if(cur2!=null){

            System.out.println("order_number:" + cur2.order_number + " product_code:" + cur2.product_code +
                    " product_model:" + cur2.product_model + " quantity:" + cur2.quantity +
                    " contract_number:" + cur2.contract_number + " salesman_number:" + cur2.salesman_number +
                    " estimated_date:" + cur2.estimated_date + " lodgement_date:" + cur2.lodgement_date + "\n");
            }
        }
    }

    private static void insert(String line2) {
        String[] parts = line2.split(",");
        int order_number = Integer.parseInt(parts[0]);
        String product_code = parts[1];
        String product_model = parts[2];
        int quantity = Integer.parseInt(parts[3]);
        String contract_number = parts[4];
        String salesman_number = parts[5];
        String estimated_date = parts[6];
        String lodgement_date = parts[7];
        if(dict.containsKey(order_number)){
            return;
        }
        OrderNode newNode = new OrderNode(last, null, order_number, product_code, product_model, quantity, contract_number, salesman_number, estimated_date, lodgement_date);
        last.next = newNode;
        last = newNode;
        dict.put(order_number,1);
    }

    private static OrderNode update(OrderNode head, int i, int order_number, String product_code, String product_model, int quantity, String contract_number, String salesman_number, String estimated_date, String lodgement_date) {
        if(dict.containsKey(order_number)){
            return head;
        }
        OrderNode cur = head;
        while (cur != null && cur.order_number != i) {
            cur = cur.next;
        }
        if (cur !=null) {
            dict.remove(cur.order_number);
            dict.put(order_number,1);
            if (cur.equals(head)) {
                head.order_number = order_number;
                head.product_code = product_code;
                head.product_model = product_model;
                head.quantity = quantity;
                head.contract_number = contract_number;
                head.salesman_number = salesman_number;
                head.estimated_date = estimated_date;
                head.lodgement_date = lodgement_date;
            } else {
                cur.order_number = order_number;
                cur.product_code = product_code;
                cur.product_model = product_model;
                cur.quantity = quantity;
                cur.contract_number = contract_number;
                cur.salesman_number = salesman_number;
                cur.estimated_date = estimated_date;
                cur.lodgement_date = lodgement_date;
            }
        }

        return head;
    }

    private static OrderNode delete(OrderNode head, int i) {
        OrderNode cur = head;
        while (cur != null && cur.order_number != i) {
            cur = cur.next;
        }
        if (cur !=null) {
            dict.remove(cur.order_number);
            if (cur.equals(head)) {
                head = head.next;
                head.pre = null;
            } else {
                cur.pre.next = cur.next;
                if (cur.next != null) {
                    cur.next.pre = cur.pre;
                }
            }
        }
        return head;
    }

//    public static int[] createRan(int total, int num) {//生成total以内的不重复的5000个随机数
//        Random r = new Random();
//        int[] ran = new int[num];
//        for (int i = 0; i < num; i++) {
//            ran[i] = (total / num) * i + 1 + r.nextInt(total / num);
//        }
//        return ran;
//    }

    public static void write(OrderNode head, String file) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            while (head != null) {//写入product
                bw.write(head.order_number + "," + head.product_code + "," + head.product_model + "," + head.quantity + "," + head.contract_number + "," + head.salesman_number + "," + head.estimated_date + "," + head.lodgement_date + "\n");
                head = head.next;
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//Original
//    public static void main(String[] args) {
//        try {
//            DataManipulation dm = new DataFactory().createDataManipulation(args[0]);//edit configuration设置为file(如果要用database就写database)
//            dm.openDatasource();
////            dm.addOneMovie("流浪地球;cn;2019;127");
//            System.out.println(dm.allContinentNames());
//            System.out.println(dm.continentsWithCountryCount());
//            System.out.println(dm.FullInformationOfMoviesRuntime(65, 75));
////            System.out.println(dm.findMovieById(10));
//            dm.closeDatasource();
//        } catch (IllegalArgumentException e) {
//            System.err.println(e.getMessage());
//        }
//    }
