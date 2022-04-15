import csv
import random

file = open(r"random_data_500W.csv", "w", encoding="utf-8", newline='')
writer = csv.writer(file)
iterator = 1
total_data_num = 5000000
data = []
while iterator < total_data_num:
    order_number = iterator
    # code
    cnt_for_code = 7
    product_code = ''
    while cnt_for_code > 0:
        cnt_for_code = cnt_for_code - 1
        # c = ''
        b = random.randint(0, 2)
        # b = 0则是字母
        if b == 0:
            d = random.randint(65, 90)
        else:
            d = random.randint(48, 57)
        # c = chr(d)
        product_code = product_code + chr(d)
    cnt_for_model = random.randint(4, 30)
    # model
    product_model = ''
    while cnt_for_model > 0:
        cnt_for_model = cnt_for_model - 1
        b = random.randint(0, 9)
        c = ''
        d = ''
        # b = 0则是字母
        if b != 0:
            if b == 1:
                d = random.randint(65, 90)
            else:
                d = random.randint(97, 122)
        else:
            d = random.randint(48, 57)
        c = chr(d)
        product_model = product_model + c

    # quantity
    quantity = random.randint(1, 1000)
    # contract_number
    contract_number = "CSE"
    cnt_for_contract = 6
    while cnt_for_contract > 0:
        cnt_for_contract = cnt_for_contract - 1
        d = random.randint(48, 57)
        contract_number += chr(d)
    # salesman_number
    salesman_number = random.randint(10000000, 99999999)
    # estimated_date
    estimated_date = ''
    year_e = random.randint(2000, 2030)
    estimated_date = estimated_date + str(year_e)
    estimated_date = estimated_date + '-'
    # 月
    month_e = random.randint(1, 12)
    if month_e < 10:
        estimated_date = estimated_date + '0'
    estimated_date = estimated_date + str(month_e)
    estimated_date = estimated_date + '-'
    # 日
    if month_e == 2:
        if year_e % 4 == 0:
            day_e = random.randint(1, 29)
            if day_e < 10:
                estimated_date = estimated_date + '0'
            estimated_date = estimated_date + str(day_e)
        else:
            day_e = random.randint(1, 28)
            if day_e < 10:
                estimated_date = estimated_date + '0'
            estimated_date = estimated_date + str(day_e)
    elif month_e == 1 or month_e == 3 or month_e == 5 or month_e == 7 or month_e == 8 or month_e == 10 or month_e == 12:
        day_e = random.randint(1, 31)
        if day_e < 10:
            estimated_date = estimated_date + '0'
        estimated_date = estimated_date + str(day_e)
    else:
        day_e = random.randint(1, 31)
        if day_e < 10:
            estimated_date = estimated_date + '0'
        estimated_date = estimated_date + str(day_e)

    # lodgement_date
    lodgement_date = ''
    year_l = random.randint(year_e + 1, 2099)
    lodgement_date = lodgement_date + str(year_l)
    lodgement_date = lodgement_date + '-'
    # if year_l < year_e:
    # 月
    month = random.randint(1, 12)
    if month < 10:
        lodgement_date = lodgement_date + '0'
    lodgement_date = lodgement_date + str(month)
    lodgement_date = lodgement_date + '-'
    # 日
    if month == 2:
        if year_e % 4 == 0:
            day = random.randint(1, 29)
            if day < 10:
                lodgement_date = lodgement_date + '0'
            lodgement_date = lodgement_date + str(day)
        else:
            day = random.randint(1, 28)
            if day < 10:
                lodgement_date = lodgement_date + '0'
            lodgement_date = lodgement_date + str(day)
    elif month == 1 or month == 3 or month == 5 or month == 7 or month == 8 or month == 10 or month == 12:
        day = random.randint(1, 31)
        if day < 10:
            lodgement_date = lodgement_date + '0'
        lodgement_date = lodgement_date + str(day)
    else:
        day = random.randint(1, 31)
        if day < 10:
            lodgement_date = lodgement_date + '0'
        lodgement_date = lodgement_date + str(day)
    # else:
    # print(order_number, product_code, product_model)

    data = [str(order_number), product_code, product_model, str(quantity), contract_number, salesman_number,
            estimated_date, lodgement_date]
    iterator = iterator + 1
    writer.writerow(data)
print("The program is over")
file.close()
