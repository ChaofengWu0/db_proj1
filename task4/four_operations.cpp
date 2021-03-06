//
// Created by ll on 2022/4/11.
//

#include "vector"
#include "iostream"
#include "assert.h"
#include "fstream"
#include "ostream"
#include "sstream"
#include "queue"

using namespace std;

struct order {
    int order_number{};
    string product_code;
    string product_model;
    int quantity{};
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

void get_data(const string &now_data, string data[], int &order_num, int &quantity, bool is_origin, int &cnt);

void print_info(bool);

int insert_(vector<order *> &, const string &, string, bool, double &);

int delete_(vector<order *> &, double &);

int update_(vector<order *> &, double &, bool);

int select_(vector<order *> &, vector<order *> &, double &);

bool check_for_now_order(order *,
                         const int order_number_cons[],
                         const string &product_code_cons,
                         const string &product_model_cons,
                         const int quantity_cons[],
                         const string &contract_number_cons,
                         const string &salesman_number_cons);

bool check_for_code(const string &);

bool check_for_salesman_number(const string &);

bool check_for_contract(const string &);

int print_sel(vector<order *> &selected, double &);

void save(vector<order *> &, double &);

int main() {
    // ???????????????order??????
    vector<order *> orders;
    const string data_address_origin = R"(C:\Users\ll\Desktop\University\dataBase\proj\db_proj1\cpp\base_12W.csv)";
    double no_use = 0;
    double time_for_del = 0;
    double time_for_insert = 0;
    double time_for_update = 0;
//    double time_for_print = 0;
    double time_for_print_this = 0;
    double time_for_sel = 0;
    double time_for_save = 0;
    int total_insert = 0;
    int total_delete = 0;
    int total_update = 0;
    int total_select = 0;

    insert_(orders, data_address_origin, "order_table_origin", true, no_use);
    // ????????????????????????????????????csv???????????????????????????????????????orders??????vector???
//    cout << "The file has been opened and it already has 20000 orders in it\n";
    print_info(true);
    string choice;
    int pr = 0;
    while ((choice = cin.get()) != "q") {
        cin.get();
        if (choice.size() != 4) {
            switch (choice[0]) {
                case 'i': {
                    pr = 1;
                    break;
                }
                case 'd': {
                    pr = 2;
                    break;
                }
                case 'u': {
                    pr = 3;
                    break;
                }
                case 's': {
                    pr = 4;
                    break;
                }
            }
        }
        if (choice == "c") {
            switch (pr) {
                // ???
                case 1: {
                    save(orders, time_for_save);
                    printf("The total number of insert is %d which cost %f s\n", total_insert,
                           time_for_insert + time_for_save);
                    break;
                }
                    // ???
                case 2: {
                    save(orders, time_for_save);
                    printf("The total number of delete is %d which cost %f s\n", total_delete,
                           time_for_del + time_for_save + no_use);
                    break;
                }
                    // ???
                case 3: {
                    save(orders, time_for_save);
                    printf("The total number of update is %d which cost %f s\n", total_update,
                           time_for_update + time_for_save + no_use);
                    break;
                }
                    // ???
                case 4: {
                    printf("The total number of select is %d which cost %f s\n", total_select,
                           time_for_sel);
                    printf("The corresponding time to output the data cost %f s\n",
                           time_for_sel + time_for_print_this + no_use);
                    break;
                }
            }
            break;
        }
        switch (choice.at(0)) {
            case 'i': {
                // ????????????????????????????????????,??????insert?????????????????????
                const string data_address_add = R"(C:\Users\ll\Desktop\University\dataBase\proj\db_proj1\cpp\random_data_1W.csv)";
                total_insert += insert_(orders, data_address_add, "order_table_toadd", false, time_for_insert);
                print_info(true);
                break;
            }
            case 'd': {
                double temp_del_time = 0.0;
                total_delete += delete_(orders, temp_del_time);
                time_for_del += temp_del_time;
                print_info(true);
                break;
            }
            case 'u': {
                double temp_update_time = 0.0;
                total_update += update_(orders, temp_update_time, false);
                time_for_update += temp_update_time;
                print_info(true);
                break;
            }
            case 's': {
                vector<order *> selected;
                time_for_sel = 0;
                select_(orders, selected, time_for_sel);
                print_sel(selected, time_for_print_this);
                print_info(true);
                break;
            }
            default: {
                print_info(false);
            }
        }
    }

    cout << "You have exited the program\nBye!" << endl;
}

void print_info(bool temp) {
    if (!temp) {
        cout << "Please input in right format\n"
                "i: insert\n"
                "d: delete\n"
                "u: update\n"
                "s: select\n"
                "c: commit\n"
                "q:   quit\n";
    } else {
        cout << "Please choose the operation\n"
                "i: insert\n"
                "d: delete\n"
                "u: update\n"
                "s: select\n"
                "c: commit\n"
                "q:   quit\n";
    }
}

bool check_for_now_order(order *now_order, const int order_number_cons[],
                         const string &product_code_cons,
                         const string &product_model_cons,
                         const int quantity_cons[],
                         const string &contract_number_cons,
                         const string &salesman_number_cons) {
    if (!now_order) {
        printf("null pointer occurs at function %s at line %d \n", __FUNCTION__, __LINE__);
        abort();
    }
    if (product_code_cons != "null" && product_code_cons != now_order->product_code)
        return false;
    if (product_model_cons != "null" && product_model_cons != now_order->product_model)
        return false;
    if (contract_number_cons != "null" && contract_number_cons != now_order->contract_number)
        return false;
    if (salesman_number_cons != "null" && salesman_number_cons != now_order->salesman_number)
        return false;
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

void get_data(const string &now_data, string data[], int &order_num, int &quantity, bool is_origin, int &cnt) {
//    string data[8];
    stringstream split_data(now_data);
    getline(split_data, data[0],
            ',');
    getline(split_data, data[1],
            ',');
    getline(split_data, data[2],
            ',');
    getline(split_data, data[3],
            ',');
    getline(split_data, data[4],
            ',');
    getline(split_data, data[5],
            ',');
    getline(split_data, data[6],
            ',');
    getline(split_data, data[7],
            ',');
//    int order_num, quantity;
    quantity = atoi(data[3].c_str());
    if (is_origin) {
        // ????????????????????????????????????order_num?????????????????????????????????????????????????????????????????????????????????????????????proj1?????????????????????
        order_num = atoi(data[0].c_str());
    } else {
        // ?????????????????????????????????order_num?????????????????????
        order_num = cnt++;
    }
}

int insert_(vector<order *> &table, const string &address, string name, bool is_origin, double &time_for_insert) {
    clock_t start_time = clock();
    int cnt = table.size();
    string now_data;
    ifstream get_content;
    get_content.open(address, ios::in);
    if (!get_content.is_open()) {
        printf("wrong in %d,because of the file %s can't open\n", __LINE__, &name);
        abort();
    }
    int cnt_for_data = 0;
    while (
            getline(get_content, now_data)) {
        cnt_for_data++;
        int order_num, quantity;
        string data[8];
        get_data(now_data, data, order_num, quantity, is_origin, cnt);
        table.push_back(new order(order_num, data[1], data[2],
                                  quantity, data[4], data[5],
                                  data[6], data[7]));
    }
    get_content.close();
    clock_t end_time = clock();
    time_for_insert += (double) (end_time - start_time) / CLOCKS_PER_SEC;
    return cnt_for_data;
}

int delete_(vector<order *> &src, double &del_time) {
    cout << "Please select the orders you want to delete\n"
         << endl;
    double select_time = 0;
    vector<order *> temp;
    select_(src, temp, select_time);
    queue<order *> selected_not_want;
    for (auto &i: temp) {
        selected_not_want.push(i);
    }
    vector<order *> final_one;
    for (auto &element: src) {
        if (element == selected_not_want.front()) {
            selected_not_want.pop();
            delete element;
        } else
            final_one.push_back(element);
    }
    int ans = src.size() - final_one.size();
    src.clear();
    int cnt_ = 1;
    for (auto &element: final_one) {
        element->order_number = cnt_++;
        src.push_back(element);
    }
    return ans;
}

int update_(vector<order *> &src, double &time_for_update, bool customize) {
    vector<order *> wanted;
    clock_t start_time;
    select_(src, wanted, time_for_update);
    // wanted???????????????????????????order,?????????????????????????????????
    int num;
    string code;
    string model;
    int quantity;
    string contract;
    string salesman_num;
    string estimated_date;
    string lodgement_date;
    if (customize) {
        cout << "Please input the changes you want.You need to input your thoughts in order\n"
                "for the first and forth elements,if you don't want to change them,you can input a number<0\n"
                "for other elements,if you don't want to change them,you can input \"null\" \n"
                "The order is:\n"
                "num  code  model  quantity  contract  salesman_number  estimated_date  loadgement_date"
             << endl;
        cin >> num >> code >> model >> quantity >> contract >> salesman_num >> estimated_date >> lodgement_date;
        start_time = clock();
        for (auto &element: wanted) {
            if (num >= 0)
                element->order_number = num;

            if (code != "null" && check_for_code(code))
                element->product_code = code;
            else if (code != "null")
                abort();

            if (model != "null")
                element->product_model = model;
            if (quantity >= 0)
                element->quantity = quantity;

            if (contract != "null" && check_for_contract(contract))
                element->contract_number = contract;
            else if (contract != "null")
                abort();

            if (salesman_num != "null" && check_for_salesman_number(salesman_num))
                element->salesman_number = salesman_num;
            else if (salesman_num != "null")
                abort();

            if (estimated_date != "null")
                element->estimated_date = estimated_date;
            if (lodgement_date != "null")
                element->lodgement_date = lodgement_date;
        }
        cin.get();
    } else {
        start_time = clock();
        string update_address = R"(C:\Users\ll\Desktop\University\dataBase\proj\db_proj1\cpp\random_data_500W.csv)";
        ifstream read;
        string name = "random_data";
        read.open(update_address, ios::in);
        if (!read.is_open()) {
            printf("wrong in %d,because of the file %s can't open\n", __LINE__, &name);
            abort();
        }
        string now_data;
        int cnt = 1;
        while (getline(read, now_data)) {
            int order_num;
            string data[8];
            get_data(now_data, data, order_num, quantity, false, cnt);
            src[cnt - 2]->product_code = data[1];
            src[cnt - 2]->product_model = data[2];
            src[cnt - 2]->quantity = quantity;
            src[cnt - 2]->contract_number = data[4];
            src[cnt - 2]->salesman_number = data[5];
            src[cnt - 2]->estimated_date = data[6];
            src[cnt - 2]->lodgement_date = data[7];
        }
    }


    clock_t end_time = clock();
    time_for_update = (double) (end_time - start_time) / CLOCKS_PER_SEC;
    return wanted.size();

}

bool check_for_contract(const string &str) {
    if (str.size() != 10)
        return false;
    for (int i = 0; i < str.size(); ++i) {
        if (i == 0 && str[0] != 'C')
            return false;
        if (i == 1 && str[1] != 'S')
            return false;
        if (i == 2 && str[2] != 'E')
            return false;
        if (str[i] > 57 || str[i] < 48)
            return false;
    }
    return true;
}

bool check_for_code(const string &str) {
    if (str.size() != 7)
        return false;
    for (char i: str) {
        if ((i >= 65 && i <= 90) || (i >= 48 && i <= 57)) {
        } else
            return false;
    }
    return true;
}

bool check_for_salesman_number(const string &str) {
    if (str.size() != 8)
        return false;
    for (auto &i: str) {
        if (i > 57 || i < 48)
            return false;
    }
    return true;
}

int print_sel(vector<order *> &selected, double &print_time) {
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
             << now_order->salesman_number << "," << now_order->estimated_date << "," << now_order->lodgement_date
             << endl;
    }
    if (selected.empty())
        cout << "There's no order match the constrains you input" << endl;
    if (!is_break)
        cout << "This is the end of this select operation" << endl;
    clock_t end_time = clock();
    print_time = (double) (end_time - start_time) / CLOCKS_PER_SEC;
    return selected.size();
}

int select_(vector<order *> &src, vector<order *> &selected, double &sel_time) {
    int order_number_cons[2]{-1, -1};
    string product_code_cons;
    string product_model_cons;
    int quantity_cons[2]{-1, -1};
    string contract_number_cons;
    string salesman_number_cons;

    // order_number limit?????????
    printf("Input two numbers representing the range of order_num you want to select(0,%d)\n"
           "Or you don't want this limit,"
           "then please input -1\n",
           src.size());
    cin >> order_number_cons[0];
    if (order_number_cons[0] != -1) {
        cin >> order_number_cons[1];
    }
    // product_code_cons
    printf("Please input the 7-length product code you want to select(a mix of capital letters and numbers)\n"
           "Or you don't want any constrains please input null\n");
    string temp_code;
    cin >> temp_code;
    if (temp_code != "null" && !check_for_code(temp_code))
        abort();

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
    if (contract_number_cons != "null" && !check_for_contract(contract_number_cons))
        abort();

    // salesman_number
    printf("Please input the salesman number you want to select\n"
           "Or you don't want any constrains please input null\n");
    cin >> salesman_number_cons;
    if (salesman_number_cons != "null" && !check_for_salesman_number(salesman_number_cons))
        abort();
    cin.get();
    clock_t start_time = clock();
    for (auto now_order: src) {
        if (check_for_now_order(now_order, order_number_cons, product_code_cons, product_model_cons, quantity_cons,
                                contract_number_cons, salesman_number_cons)) {
            selected.push_back(now_order);
        }
    }
    clock_t end_time = clock();
    sel_time = (double) (end_time - start_time) / CLOCKS_PER_SEC;
    return selected.size();
}

void save(vector<order *> &final_table, double &time_for_save) {
    ofstream write;
    clock_t start_time = clock();
    write.open(R"(C:\Users\ll\Desktop\University\dataBase\proj\db_proj1\cpp\temp.csv)", ios::out);
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
    clock_t end_time = clock();
    time_for_save = (double) (end_time - start_time) / CLOCKS_PER_SEC;
}
