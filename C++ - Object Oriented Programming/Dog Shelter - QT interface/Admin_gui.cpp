//
// Created by Tudor Tise on 18/05/2021.
//

#include "Admin_gui.h"
#include <QLineEdit>
#include <QFormLayout>
#include <QMessageBox>
#include "fstream"

Admin_gui::Admin_gui(Service& ser) : srv(ser){
    this->show_design();
    this->print_dogs();
    this->connect();
}

void Admin_gui::show_design(){
    this->setWindowTitle("Dogs");


    auto* main = new QVBoxLayout{this};
    auto* adm = new QHBoxLayout {this};
    QLabel* label2 = new QLabel;
    label2->setText("Admin");
    adm->addWidget(label2,0, Qt::AlignCenter);
    main->addLayout(adm);


    this->dogs_list = new QListWidget{};
    main->addWidget(this->dogs_list);

    this->id = new QLineEdit{};
    this->breed = new QLineEdit{};
    this->name = new QLineEdit{};
    this->age = new QLineEdit{};
    this->photo = new QLineEdit{};


    auto* text_boxes = new QFormLayout{ this };
    text_boxes->addRow("Id", this->id);
    text_boxes->addRow("Breed", this->breed);
    text_boxes->addRow("Name", this->name);
    text_boxes->addRow("Age", this->age);
    text_boxes->addRow("Photo", this->photo);

    main->addLayout(text_boxes);


    auto* buttons = new QHBoxLayout {this};
    this->add_admin = new QPushButton{"Add"};
    this->delete_admin = new QPushButton{"Delete"};
    this->update_admin = new QPushButton{"Update"};
    buttons->addWidget(this->add_admin);
    buttons->addWidget(this->delete_admin);
    buttons->addWidget(this->update_admin);

    main->addLayout(buttons);

    auto* delimiter = new QHBoxLayout {this};
    QLabel* label = new QLabel;
    label->setText("User");
    delimiter->addWidget(label,0, Qt::AlignCenter);

    main->addLayout(delimiter);

    auto* user = new QVBoxLayout {this};
    this->see_dog = new QPushButton{"See dog"};
    this->filter_dog = new QPushButton{"Filter"};
    user->addWidget(this->see_dog);
    //user->addWidget(this->filter_dog);
    main->addLayout(user);

    auto* text_boxes2 = new QVBoxLayout{ this };
    this->filter_breed = new QLineEdit{};
    QLabel* label3 = new QLabel;
    label3->setText("Breed filter");
    text_boxes2->addWidget(label3,0, Qt::AlignCenter);
    text_boxes2->addWidget(this->filter_breed);


    this->filter_age = new QLineEdit{};
    filter_age->setValidator( new QIntValidator(0, 10, this) );
    QLabel* label4 = new QLabel;
    label4->setText("Age filter");
    text_boxes2->addWidget(label4,0, Qt::AlignCenter);
    text_boxes2->addWidget(this->filter_age);

    main->addLayout(text_boxes2);

    main->addWidget(this->filter_dog);

    this->adopted_list = new QListWidget{};
    main->addWidget(this->adopted_list);

    auto* saves = new QVBoxLayout{ this };
    this->save_csv = new QPushButton{"CSV"};
    this->save_html = new QPushButton{"HTML"};
    saves->addWidget(this->save_html);
    saves->addWidget(this->save_csv);
    main->addLayout(saves);

}

void Admin_gui::print_dogs(){
    this->dogs_list->clear();

    std::vector<Dog> dogs = srv.getDogsService();

    for (auto& d : dogs)
        this->dogs_list->addItem(QString::fromStdString(d.toString()));
}

void Admin_gui::print_adopted(){
    this->adopted_list->clear();

    std::vector<Dog> dogs = srv.getAdoptedService();

    for (auto& d : dogs)
        this->adopted_list->addItem(QString::fromStdString(d.toString()));
}

void Admin_gui::connect(){
    //admin
    QObject::connect(this->add_admin, &QPushButton::clicked, this, &Admin_gui::add_dog_gui);
    QObject::connect(this->update_admin, &QPushButton::clicked, this, &Admin_gui::update_dog_gui);
    QObject::connect(this->delete_admin, &QPushButton::clicked, this, &Admin_gui::delete_dog_gui);
    QObject::connect(this->see_dog, &QPushButton::clicked, this, &Admin_gui::iterate_dogs_user);
    QObject::connect(this->filter_dog, &QPushButton::clicked, this, &Admin_gui::iterate_filtered_dogs_user);
    QObject::connect(this->save_html, &QPushButton::clicked, this, &Admin_gui::see_adopted_html);
    QObject::connect(this->save_csv, &QPushButton::clicked, this, &Admin_gui::see_adopted_csv);


    QObject::connect(this->dogs_list, &QListWidget::itemSelectionChanged, [this]() {

        int selectedIndex = this->get_selected_admin();

        if (selectedIndex < 0){
            return;
        }

        Dog d = this->srv.get_dog_service_admin(selectedIndex);
        this->id->setText(QString::number(d.get_id()));
        this->name->setText(QString::fromStdString(d.get_name()));
        this->breed->setText(QString::fromStdString(d.get_breed()));
        this->age->setText(QString::number(d.get_age()));
        this->photo->setText(QString::fromStdString(d.get_photograph()));

    });
}

int Admin_gui::get_selected_admin(){
    QModelIndexList index = this->dogs_list->selectionModel()->selectedIndexes();

    if (index.empty()){
        this->id->clear();
        this->name->clear();
        this->breed->clear();
        this->age->clear();
        this->photo->clear();
        return -1;
    }

    int selectedIndex = index.at(0).row();
    return selectedIndex;
}

void Admin_gui::add_dog_gui() {
    std::string name_edit, breed_edit, photo_edit;
    int id_edit, age_edit;

    id_edit = this->id->text().toInt();
    age_edit = this->age->text().toInt();
    name_edit = this->name->text().toStdString();
    breed_edit = this->breed->text().toStdString();
    photo_edit = this->photo->text().toStdString();

    if (this->id->text().isEmpty() || this->age->text().isEmpty() || this->name->text().isEmpty() ||
        this->breed->text().isEmpty() || this->photo->text().isEmpty()){
        QMessageBox::information(this, "Error!", QString::fromStdString("Please fill in all the fields"));
    } else {
        try{
            this->srv.add_dog(id_edit, breed_edit, name_edit,age_edit,photo_edit);
            this->print_dogs();
        }catch(std::exception &exception){
            QMessageBox::information(this, "Error!", QString::fromStdString(exception.what()));
        }
    }
}

void Admin_gui::delete_dog_gui() {
    int to_delete = this->id->text().toInt();
    if (this->id->text().isEmpty()){
        QMessageBox::information(this, "Error!", QString::fromStdString("No id provided"));
    } else {
        try{
            this->srv.delete_dog_service(to_delete);
            this->print_dogs();
        }catch(std::exception &exception){
            QMessageBox::information(this, "Error!", QString::fromStdString(exception.what()));
        }
    }
}

void Admin_gui::update_dog_gui() {
    std::string name_edit, breed_edit, photo_edit;
    int id_edit, age_edit;

    id_edit = this->id->text().toInt();
    age_edit = this->age->text().toInt();
    name_edit = this->name->text().toStdString();
    breed_edit = this->breed->text().toStdString();
    photo_edit = this->photo->text().toStdString();

    try{
        this->srv.update_dog_service(id_edit, breed_edit, name_edit,age_edit,photo_edit);
        this->print_dogs();
    }catch(std::exception &exception){
        QMessageBox::information(this, "Error!", QString::fromStdString(exception.what()));
    }
}

void Admin_gui::iterate_dogs_user() {
    std::vector<Dog>  all_dogs = this->srv.getDogsService();

    QMessageBox msgBox;

    QAbstractButton* pButtonYes = msgBox.addButton(tr("Yes"), QMessageBox::YesRole);
    QAbstractButton* pButtonNo = msgBox.addButton(tr("No"), QMessageBox::NoRole);
    QAbstractButton* pButtonExit = msgBox.addButton(tr("Exit"), QMessageBox::RejectRole);

    while (true){
        for (auto d : all_dogs) {
            QString text = QString::fromStdString(d.toString());
            text.append("\nDo you want to adopt it?");
            msgBox.setText(text);

//        std::string command = "open ";
//        command += d.get_photograph();
//        system(command.c_str());

            msgBox.exec();

            if (msgBox.clickedButton()==pButtonYes){
                this->srv.adopt_dog(d);
                this->srv.delete_dog_service(d.get_id());
                //this->adopted_list->addItem(QString::fromStdString(d.toString()));
                this->print_adopted();
                this->print_dogs();
                if (this->srv.get_size_service() == 0){
                    return;
                }
            } else if (msgBox.clickedButton() == pButtonNo){
                continue;
            } else {
                return;
            }
        }
        all_dogs = this->srv.getDogsService();
    }


}

void Admin_gui::see_adopted_html() {
    std::string htmlString;

    htmlString += "\t<html>\n";
    htmlString += "\t\t<head>\n";
    htmlString += "\t\t</head>\n";
    htmlString += "\t<body>\n";
    htmlString += "\t<table>\n";
    htmlString += "\t<tr>\n";
    htmlString += "\t\t<th>Breed</th>\n";
    htmlString += "\t\t<th>Name</th>\n";
    htmlString += "\t\t<th>Age</th>\n";
    htmlString += "\t\t<th>Photo</th>\n";
    htmlString += "\t</tr>\n";

    std::ofstream file;
    file.open ("dogs.html");

    std::vector<Dog> all_dogs = this->srv.getAdoptedService();
    for (auto d : all_dogs){
        htmlString += d.toHTML();
    }

    htmlString += "\t</table>\n";
    htmlString += "\t</body>\n";
    htmlString += "\t</html>\n";

    file << htmlString;
    file.close();

    system("open dogs.html");
};

void Admin_gui::see_adopted_csv()  {
    std::ofstream file;
    file.open ("dogs.csv");

    std::vector<Dog> all_dogs = this->srv.getAdoptedService();

    for (auto d : all_dogs){
        file << d.toCSV() << std::endl;
    }
    file.close();
    system("open dogs.csv");
}

void Admin_gui::iterate_filtered_dogs_user() {
    std::string breed_to_filter = this->filter_breed->text().toStdString();

    if (this->filter_age->text().isEmpty()){
        QMessageBox::information(this, "Error!", QString::fromStdString("Age cannot be empthy!!"));
    } else {
        int age_to_filter = this->filter_age->text().toInt();
        std::vector<Dog>  all_dogs = this->srv.filterDogsService(breed_to_filter, age_to_filter);

        if (all_dogs.empty()){
            QMessageBox::information(this, "Error!", QString::fromStdString("No dogs found"));
        } else {
            QMessageBox msgBox;

            QAbstractButton* pButtonYes = msgBox.addButton(tr("Yes"), QMessageBox::YesRole);
            QAbstractButton* pButtonNo = msgBox.addButton(tr("No"), QMessageBox::NoRole);
            QAbstractButton* pButtonExit = msgBox.addButton(tr("Exit"), QMessageBox::RejectRole);
            int number_of_dogs = all_dogs.size();
            int counter = 0;

            while (true) {
                for (auto d : all_dogs) {
                    QString text = QString::fromStdString(d.toString());
                    text.append("\nDo you want to adopt it?");
                    msgBox.setText(text);

//        std::string command = "open ";
//        command += d.get_photograph();
//        system(command.c_str());

                    msgBox.exec();

                    if (msgBox.clickedButton() == pButtonYes) {
                        this->srv.adopt_dog(d);
                        this->srv.delete_dog_service(d.get_id());
                        this->print_adopted();
                        this->print_dogs();
                        counter++;
                        if (counter == number_of_dogs){
                            return;
                        }
                    } else if (msgBox.clickedButton() == pButtonNo) {
                        continue;
                    } else {
                        return;
                    }
                }
                all_dogs = this->srv.filterDogsService(breed_to_filter, age_to_filter);
            }
        }

    }
};
