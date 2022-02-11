//
// Created by Tudor Tise on 18/05/2021.
//

#ifndef A89_917TUDORTISE_ADMIN_GUI_H
#define A89_917TUDORTISE_ADMIN_GUI_H
#include <QWidget>
#include <QPushButton>
#include <QHBoxLayout>
#include <QLabel>
#include <QListWidget>
#include "Service.h"

class Admin_gui : public QWidget{
private:
    Service& srv;
//adm
    QListWidget* dogs_list;

    QPushButton* add_admin;
    QPushButton* delete_admin;
    QPushButton* update_admin;

    QLineEdit* id;
    QLineEdit* breed;
    QLineEdit* name;
    QLineEdit* age;
    QLineEdit* photo;

//usr
    QPushButton* see_dog;
    QPushButton* filter_dog;


    QPushButton* save_html;
    QPushButton* save_csv;

    QLineEdit* filter_breed;
    QLineEdit* filter_age;

    QListWidget* adopted_list;

public:

    Admin_gui(Service& ser);
    void show_design();

    //admin
    void print_dogs();
    int get_selected_admin();
    void connect();
    void add_dog_gui();
    void delete_dog_gui();
    void update_dog_gui();


    //user
    void iterate_dogs_user();
    void iterate_filtered_dogs_user();
    void print_adopted();
    void see_adopted_html();
    void see_adopted_csv();
};


#endif //A89_917TUDORTISE_ADMIN_GUI_H
