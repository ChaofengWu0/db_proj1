//
// Created by ll on 2022/4/11.
//


#include "vector"
#include "iostream"
#include "assert.h"
#include "iomanip"
#include "fstream"
#include "ostream"
#include "sstream"
#include "time.h"

using namespace std;

struct order {
    int order_number;
    string product_code;
    string product_model;
    int quantity;
    string contract_number;
    string salesman_number;
    string estimated_date;
    string lodgement_date;

    order(int orderNumber, string productCode, string productModel, int quantity,
          string contractNumber, string salesmanNumber, string estimatedDate,
          string lodgementDate)
            : order_number(orderNumber), product_code(std::move(productCode)), quantity(quantity),
              product_model(std::move(productModel)), contract_number(std::move(contractNumber)),
              salesman_number(std::move(salesmanNumber)), estimated_date(std::move(estimatedDate)),
              lodgement_date(std::move(lodgementDate)) {}

    order() = default;

    order(order &order1) = delete;

    order &operator=(order &) = delete;
};

int insert_(vector<order *> &, const string &, string, bool);

void delete_(vector<order *> &);

void update_();

void select_();

void save(vector<order *> &);

int main() {
    // 用来装所有order的表
    vector<order *> orders;
    const string data_address_origin = R"(C:\Users\ll\Desktop\University\dataBase\proj\db_proj1\order_table_origin.csv)";
    insert_(orders, data_address_origin, "order_table_origin", true);
    // 在这里，已经把所有的存在csv中的数据转换成了对象存放在orders这个vector中

    // 这两行是用来insert数据的
    {
//        clock_t start_time = clock();
//        const string data_address_add = R"(C:\Users\ll\Desktop\University\dataBase\proj\db_proj1\order_table_toadd.csv)";
//        int total_data = insert_(orders, data_address_add, "order_table_toadd", false);
//        clock_t end_time = clock();
//        double consume_time = (double) (end_time - start_time) / CLOCKS_PER_SEC;
//        printf("insert %d data,it consume %ds", total_data, consume_time);
    }

    // insert解决
    
    // 


//    save(orders);
}

int insert(vector<order *> &table, const string &address, string name, bool is_origin) {
    int cnt = table.size();
    string now_data;
    ifstream get_content;
    get_content.open(address, ios::in);
    if (!get_content.is_open()) {
        printf("wrong in %d,because of the file %s can't open\n", __LINE__, &name);
        abort();
    }
    get_content.close();
    vector<string> temp;
    while (getline(get_content, now_data)) {
        temp.push_back(now_data);
    }
    string data[8];
    for (auto &iterator: temp) {
        stringstream split_data(iterator);
        getline(split_data, data[0], ',');
        getline(split_data, data[1], ',');
        getline(split_data, data[2], ',');
        getline(split_data, data[3], ',');
        getline(split_data, data[4], ',');
        getline(split_data, data[5], ',');
        getline(split_data, data[6], ',');
        getline(split_data, data[7], ',');
        int order_num, quantity;
        stringstream cvt;
        cvt << data[3];
        cvt >> quantity;
        if (is_origin) {
            // 如果是最初的表，那就说明order_num就按照表里的来，虽然可能会有脏数据，但是这个地方没有处理，因为proj1保证了干净数据
            cvt << data[0];
            cvt >> order_num;
        } else {
            // 如果不是最初的表，那么order_num就应该每次自增
            order_num = cnt++;
        }
        table.push_back(new order(order_num, data[1], data[2], quantity, data[4], data[5], data[6], data[7]));
    }
    return temp.size();
}

void save(vector<order *> &final_table) {
    ofstream write;
    write.open(R"(C:\Users\ll\Desktop\University\dataBase\proj\db_proj1\order_table_origin.csv)", ios::out);
    if (!write.is_open()) {
        cout << "wrong! The file can't be opened" << endl;
        abort();
    }
    int cnt = 1;
    for (auto now_order: final_table) {
        write << cnt++ << "," << now_order->product_code << "," << now_order->product_model << ","
              << now_order->quantity << "," << now_order->contract_number << "," << now_order->salesman_number << ","
              << now_order->estimated_date << "," << now_order->lodgement_date << endl;
    }
    write.close();
}








