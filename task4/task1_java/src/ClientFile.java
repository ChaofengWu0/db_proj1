import java.io.*;
import java.util.Random;

public class ClientFile {
    //尝试把增删改查都在这里实现
    public static void main(String[] args) throws IOException {
        OrderNode head = new OrderNode();
        OrderNode cur = new OrderNode();
        int count = 0;
        //插入20000行数据入内存
        BufferedReader br = new BufferedReader(new FileReader("cs3072_public_order1.csv"));
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
        //ps：此时cur等于末尾了
        //狭义增删改查实现：
        //直接从第二个表中读出

        long start = System.currentTimeMillis();
//        增(目前写入的时间比其他操作都还要久)，直接从第二个表中添加10000条
//        BufferedReader br2=new BufferedReader(new FileReader("cs3072_public_order2.csv"));
//        String line2;
//        OrderNode cur2=cur;
//        count=0;
//        while(count<=10000&&(line2 = br2.readLine()) != null){
//            String[] parts=line2.split(",");
//            int order_number=Integer.parseInt(parts[0]);
//            String product_code=parts[1];
//            String product_model=parts[2];
//            int quantity=Integer.parseInt(parts[3]);
//            String contract_number=parts[4];
//            String salesman_number=parts[5];
//            String estimated_date=parts[6];
//            String lodgement_date=parts[7];
//            OrderNode newNode=new OrderNode(cur2,null,order_number,product_code,product_model,quantity,contract_number,salesman_number,estimated_date,lodgement_date);
//            cur2.next=newNode;
//            cur2=newNode;
//            count++;
//        }
//        由于删除后5000项对于链表来说太多简单，因此生成5000个随机数删除（且故意不排序一个个找了删）
        int[]ran=createRan(20000,5000);
        for (int i = 0; i < ran.length; i++) {
            head=delete(head,ran[i]);
        }
        //改,随机选择5000条数据随机修改为第二张表中的5000条数据
//        int[] ran = createRan(20000, 5000);
//        int[] ran2 = createRan(30000, 5000);//30000以内的随机数
//        String line2;
//        for (int i = 0; i < ran.length; i++) {
//            OrderNode cur2 = head;
//            while (cur2.order_number != ran[i]) {
//                cur2 = cur2.next;//找到需要修改的节点
//            }
//            OrderNode pre;
//            OrderNode next;
//            int order_number = 0;
//            String product_code = "";
//            String product_model="";
//            int quantity=0;
//            String contract_number="";
//            String salesman_number="";
//            String estimated_date="";
//            String lodgement_date="";
//            BufferedReader br2=new BufferedReader(new FileReader("cs3072_public_order2.csv"));
//            while ((line2 = br2.readLine()) != null) {
//                String[] parts = line2.split(",");
//                order_number = Integer.parseInt(parts[0]);
//                product_code = parts[1];
//                product_model = parts[2];
//                quantity = Integer.parseInt(parts[3]);
//                contract_number = parts[4];
//                salesman_number = parts[5];
//                estimated_date = parts[6];
//                lodgement_date = parts[7];
//                if(order_number==ran2[i]+20000){
//                    break;
//                }
//            }
//            cur2.order_number=order_number;
//            cur2.product_code=product_code;
//            cur2.product_model=product_model;
//            cur2.quantity=quantity;
//            cur2.contract_number=contract_number;
//            cur2.salesman_number=salesman_number;
//            cur2.estimated_date=estimated_date;
//            cur2.lodgement_date=lodgement_date;
//        }
        //查,随机查5000条(注意，查询的时候不需要写入磁盘)
//        int[]ran=createRan(20000,5000);
//        for (int i = 0; i < ran.length; i++) {
//            OrderNode cur2=head;
//            while(cur2.order_number!=ran[i]){
//                cur2=cur2.next;
//            }
//            System.out.println("order_number:"+cur2.order_number +" product_code:"+ cur2.product_code+
//                    " product_model:"+cur2.product_model+" quantity:"+cur2.quantity+
//                    " contract_number:"+cur2.contract_number+" salesman_number:"+cur2.salesman_number+
//                    " estimated_date:"+cur2.estimated_date+" lodgement_date:"+cur2.lodgement_date+"\n");
//        }
        write(head);
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println(time);
    }

    private static OrderNode delete(OrderNode head, int i) {
        OrderNode cur = head;
        while (cur != null && cur.order_number != i) {
            cur = cur.next;
        }
        if (cur.order_number == i) {
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

    public static int[] createRan(int total, int num) {//生成total以内的不重复的5000个随机数
        Random r = new Random();
        int[] ran = new int[num];
        for (int i = 0; i < num; i++) {
            ran[i] = (total / num) * i + 1 + r.nextInt(total / num);
        }
        return ran;
    }

    public static void write(OrderNode head) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("order_output"));
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
