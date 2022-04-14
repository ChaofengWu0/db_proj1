//
// Created by ll on 2022/4/11.
//


#include "vector"
#include "iostream"
#include "assert.h"
#include "iomanip"
#include "fstream"
#include "ostream"
#include <cstring>
#include "sstream"

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

void delete_();

void update_();

double select_(vector<order *> &, vector<order *> &);

bool check_for_now_order(order *,
                         const int order_number_cons[],
                         const string &product_code_cons,
                         const string &product_model_cons,
                         const int quantity_cons[],
                         const string &contract_number_cons,
                         const string &salesman_number_cons
);

bool check_for_input_code(const string &str);

double print_sel(vector<order *> &selected);

void save(vector<order *> &);

int main() {
    // 用来装所有order的表
    vector<order *> orders;
    const string data_address_origin = R"(C:\Users\ll\Desktop\University\dataBase\proj\db_proj1\_.csv)";
    insert_(orders, data_address_origin, "order_table_origin", true);
    // 在这里，已经把所有的存在csv中的数据转换成了对象存放在orders这个vector中

    //// 这两行是用来insert数据的

    cout << "The file has been opened and it already has 20000 orders in it\n"
            "Please choose the operation\n"
            "i: insert\n"
            "d: delete\n"
            "u: update\n"
            "s: select\n"
            "save: save\n"
            "q:   quit\n";
    clock_t start_time = clock();
    string choice;
    int total_insert_data = 0;
    while ((choice = cin.get()) != "q") {
        cin.get();
        if (choice == "save") {
            save(orders);
            clock_t end_time = clock();
            double consume_time = (double) (end_time - start_time) / CLOCKS_PER_SEC;
//            printf("insert %d data,it consume %f s", total_data, consume_time);
            break;
        }
        switch (choice.at(0)) {
            case 'i': {
                // 这里暂时没有让用户自定义,只能insert指定目录的数据
                const string data_address_add = R"(C:\Users\ll\Desktop\University\dataBase\proj\db_proj1\order_table_toadd.csv)";
                total_insert_data += insert_(orders, data_address_add, "order_table_toadd", false);
                break;
            }
            case 'd': {

                break;
            }
            case 'u': {
                break;
            }
            case 's': {
                vector<order *> selected;
                double time_for_sel = select_(orders, selected);
                double time_for_print = print_sel(selected);
                printf("This is the time for select %f s\n"
                       "This is the time for print%f s\n"
                       "This is the total time %f s\n", time_for_sel, time_for_print, time_for_sel + time_for_print);
                break;
            }
            default: {
                cout <<
                     "Please input in right format\n"
                     "i: insert\n"
                     "d: delete\n"
                     "u: update\n"
                     "s: select\n"
                     "save: save\n"
                     "q:   quit\n";
            }
        }

        //// insert解决
//        cout << "please input the " << endl;
    }
}

double print_sel(vector<order *> &selected) {
    bool is_break = false;
    clock_t start_time = clock();
    for (auto now_order: selected) {
        if (now_order == nullptr) {
            is_break = true;
            cout << "This is the end of this select operation" << endl;
            break;
        }
        cout << now_order->order_number << "," << now_order->product_code << "," << now_order->product_model
             << "," << now_order->quantity << "," << now_order->contract_number << ","
             << now_order->salesman_number << "," << now_order->estimated_date << "," <<
             now_order->lodgement_date << endl;
    }
    if (selected.empty()) cout << "There's no order match the constrains you input" << endl;
    if (!is_break) cout << "This is the end of this select operation" << endl;
    clock_t end_time = clock();
    return (double) (end_time - start_time) / CLOCKS_PER_SEC;
}

double select_(vector<order *> &src, vector<order *> &selected) {
    int order_number_cons[2]{-1, -1};
    string product_code_cons;
    string product_model_cons;
    int quantity_cons[2]{-1, -1};
    string contract_number_cons;
    string salesman_number_cons;
    // order_number limit算完了
    printf("Input two numbers representing the range of order_num you want to select(0,%d)\n"
           "Or you don't want this limit,"
           "then please input -1\n", src.size());
    cin >> order_number_cons[0];
    if (order_number_cons[0] != -1) {
        cin >> order_number_cons[1];
    }
    // product_code_cons
    printf("Please input the 7-length product code you want to select(a mix of capital letters and numbers)\n"
           "Or you don't want any constrains please input null\n");
    string temp_code;
    cin >> temp_code;
    if (temp_code != "null") {
        if (temp_code.size() != 7) {
            cout << "Please make sure that the input is 7 chars in length\n";
            abort();
        }
        if (!check_for_input_code(temp_code)) {
            cout << "Please make sure that the content of input meets the requirement\n";
            abort();
        }
        assert(temp_code.size() == 7);
        assert(check_for_input_code(temp_code));
    }
    product_code_cons = temp_code;
    // product_model
    printf("Please input the product model you want to select\n"
           "Or you don't want any constrains please input null\n");
    string temp_model;
    cin >> temp_model;
    product_model_cons = temp_model;
    // quantity
    printf("Input two numbers representing the range of quantity you want to select.\n"
           "Or you don't want this limit,then please input -1\n");
    cin >> quantity_cons[0];
    if (quantity_cons[0] != -1) {
        cin >> quantity_cons[1];
    }
    // contract_num
    printf("Please input the contract number you want to select\n"
           "Or you don't want any constrains please input null\n");
    cin >> contract_number_cons;
    // salesman_number
    printf("Please input the salesman number you want to select\n"
           "Or you don't want any constrains please input null\n");
    cin >> salesman_number_cons;

    clock_t start_time = clock();
    for (auto now_order: src) {
        if (check_for_now_order(now_order, order_number_cons, product_code_cons, product_model_cons, quantity_cons,
                                contract_number_cons, salesman_number_cons)) {
            selected.push_back(now_order);
        }
    }
    clock_t end_time = clock();
    return (double) (end_time - start_time) / CLOCKS_PER_SEC;
}

bool check_for_now_order(order *now_order, const int order_number_cons[],
                         const string &product_code_cons,
                         const string &product_model_cons,
                         const int quantity_cons[],
                         const string &contract_number_cons,
                         const string &salesman_number_cons
) {
    if (!now_order) {
        printf("null pointer occurs at function %s at line %d \n", __FUNCTION__, __LINE__);
        abort();
    }
    if (product_code_cons != "null" && product_code_cons != now_order->product_code) return false;
    if (product_model_cons != "null" && product_model_cons != now_order->product_model)return false;
    if (contract_number_cons != "null" && contract_number_cons != now_order->contract_number) return false;
    if (salesman_number_cons != "null" && salesman_number_cons != now_order->salesman_number) return false;
    int now_order_number = now_order->order_number;
    int now_quantity = now_order->quantity;
    if (order_number_cons[0] != -1 &&
        (now_order_number < order_number_cons[0] || now_order_number > order_number_cons[1]))
        return false;
    if (quantity_cons[0] != -1 &&
        (now_quantity < quantity_cons[0] || now_quantity > quantity_cons[1]))
        return false;
    return true;
}

bool check_for_input_code(const string &str) {
    for (char i: str) {
        if ((i >= 65 && i <= 90) || (i >= 48 && i <= 57)) {
        } else return false;
    }
    return true;
}

int insert_(vector<order *> &table, const string &address, string name, bool is_origin) {
    int cnt = table.size();
    string now_data;
    ifstream get_content;
    get_content.open(address, ios::in);
    if (!get_content.is_open()) {
        printf("wrong in %d,because of the file %s can't open\n", __LINE__, &name);
        abort();
    }
    int cnt_for_data = 0;
    while (getline(get_content, now_data)) {
        cnt_for_data++;
        string data[8];
        stringstream split_data(now_data);
        getline(split_data, data[0], ',');
        getline(split_data, data[1], ',');
        getline(split_data, data[2], ',');
        getline(split_data, data[3], ',');
        getline(split_data, data[4], ',');
        getline(split_data, data[5], ',');
        getline(split_data, data[6], ',');
        getline(split_data, data[7], ',');
        int order_num, quantity;
        quantity = atoi(data[3].c_str());
        if (is_origin) {
            // 如果是最初的表，那就说明order_num就按照表里的来，虽然可能会有脏数据，但是这个地方没有处理，因为proj1保证了干净数据
            order_num = atoi(data[0].c_str());
        } else {
            // 如果不是最初的表，那么order_num就应该每次自增
            order_num = cnt++;
        }
        table.push_back(new order(order_num, data[1], data[2], quantity, data[4], data[5], data[6], data[7]));
    }
    get_content.close();
    return cnt_for_data;
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
