public class OrderNode {
    OrderNode pre;
    OrderNode next;
    int order_number;
    String product_code;
    String product_model;
    int quantity;
    String contract_number;
    String salesman_number;
    String estimated_date;
    String lodgement_date;

    public OrderNode() {
    }

    public OrderNode(OrderNode pre, OrderNode next, int order_number, String product_code, String product_model, int quantity, String contract_number, String salesman_number, String estimated_date, String lodgement_date) {
        this.pre = pre;
        this.next = next;
        this.order_number = order_number;
        this.product_code = product_code;
        this.product_model = product_model;
        this.quantity = quantity;
        this.contract_number = contract_number;
        this.salesman_number = salesman_number;
        this.estimated_date = estimated_date;
        this.lodgement_date = lodgement_date;
    }
}
