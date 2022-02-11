//
// Created by Tudor Tise on 23/03/2021.
//

#ifndef LAB4_5_UI_H
#define LAB4_5_UI_H

#include "Service.h"


class Ui {
private:
    Service& service;

public:
    //Ui() = default;
    Ui(Service& srv);
    void print_menu_admin();
    void print_menu_user();
    void print_menu_main();
    void run_admin();
    void run_main_menu();
    void run_user();
    void list_dogs_ui();
    void list_dogs_ui_user();
    void list_adoption_list();
    void filter_dogs_ui_user();
    void add_dog_ui();
    void delete_dog_ui();
    void update_dog_ui();

    //void write_to_file();
};


#endif //LAB4_5_UI_H
