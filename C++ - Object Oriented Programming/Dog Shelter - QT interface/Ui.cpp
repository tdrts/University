//
// Created by Tudor Tise on 23/03/2021.
//

#include "Ui.h"
#include "string"
#include <fstream>
#include "Validator.h"

void Ui::print_menu_admin() {
    std::cout << "\n1. Add a dog" << std::endl;
    std::cout << "2. Update a dog" << std::endl;
    std::cout << "3. Remove a dog" << std::endl;
    std::cout << "4. See all dogs" << std::endl;
    std::cout << "0. Return\n" << std::endl;
}


void Ui::print_menu_user() {
    std::cout << "\n1. See dogs" << std::endl;
    std::cout << "2. See dogs of a given breed, having an age less than a given number" << std::endl;
    std::cout << "3. See adoption list" << std::endl;
    std::cout << "0. Return\n" << std::endl;
}

void Ui::print_menu_main() {
    std::cout << "\n1. Admin mode" << std::endl;
    std::cout << "2. User mode" << std::endl;
    std::cout << "0. Exit\n" << std::endl;
}

void Ui::run_user() {
    std::string option;

    while (true){
        print_menu_user();
        std::cout << "What op do you want? ";
        std::cin >> option;
        if (option == "1") {
            std::cout << "See all dogs "<< std::endl;
            list_dogs_ui_user();
        } else if (option == "2"){
            std::cout << "Filtered dogs "<< std::endl;
            filter_dogs_ui_user();
        } else if (option == "3"){
            std::cout << "Adoption list"<< std::endl;
            list_adoption_list();
        } else if (option == "0"){
            return;
        } else {
            std::cout << "Option not defined"<< std::endl;
        }
    }
}

void Ui::run_admin() {
    std::string option;

    while (true){
        try{
            print_menu_admin();
            std::cout << "What op do you want? ";
            std::cin >> option;
            if (option == "1") {
                this->add_dog_ui();
            } else if (option == "2"){
                this->update_dog_ui();
            } else if (option == "3"){
                this->delete_dog_ui();
            } else if (option == "4"){
                this->list_dogs_ui();
            } else if (option == "0"){
                return;
            } else {
                std::cout << "Option not defined"<< std::endl;
            }
        } catch (std::exception &exception){
            std::cout << exception.what();;
        }
    }
}

void Ui::run_main_menu() {
    this->service.read_data_file();
    std::string option;
    while (true){
        print_menu_main();
        std::cout << "What op do you want? ";
        std::cin >> option;
        if (option == "1") {
            std::cout << "Admin mode"<< std::endl;
            run_admin();
        } else if (option == "2"){
            std::cout << "User mode"<< std::endl;
            run_user();
        } else if (option == "0"){
            return;
        } else {
            std::cout << "Option not defined"<< std::endl;
        }
    }
}

void Ui::list_dogs_ui() {
    std::cout << "Printing the dogs" << std::endl;
    std::vector<Dog>  all_dogs = this->service.getDogsService();
    for (auto d : all_dogs){
        std::cout << d << std::endl;
    }
    //write_to_file();
}

void Ui::add_dog_ui() {
    std::cout << "You opted to add a new dog" << std::endl;
    std::string read_chars;

    int id = -1, age = -1;
    std::string breed, name, link;
    while (true){
        std::cout << "enter a number for the id:" << std::endl;
        std::cin >> read_chars;
        try {
            id = stoi(read_chars);
        } catch (std::invalid_argument &X){
            std::cout << "Please write a valid number for the ID" << std::endl;
        }
        if (id != -1){
            break;
        }
    }

    while (true){
        std::cout << "enter the breed of the dog:" << std::endl;
        std::cin >> read_chars;

        if (read_chars.empty()){
            std::cout << "cannot be empthy" << std::endl;
        } else {
            breed = read_chars;
            break;
        }
    }

    while (true){
        std::cout << "enter the name of the dog:" << std::endl;
        std::cin >> read_chars;

        if (read_chars.empty()){
            std::cout << "cannot be empthy" << std::endl;
        } else {
            name = read_chars;
            break;
        }
    }

    while (true){
        std::cout << "enter a number for the age:" << std::endl;
        std::cin >> read_chars;
        try {
            age = stoi(read_chars);
        } catch (std::invalid_argument &X){
            std::cout << "Please write a valid number for the age" << std::endl;
        }
        if (age != -1){
            break;
        }
    }

    while (true){
        std::cout << "enter the photo link of the dog:" << std::endl;
        std::cin >> read_chars;

        if (read_chars.empty()){
            std::cout << "cannot be empty" << std::endl;
        } else {
            link = read_chars;
            break;
        }
    }

    this->service.add_dog(id, breed, name, age, link);
    //write_to_file();
}

void Ui::delete_dog_ui() {
    int id = -1;
    std::string read_chars;
    while (true){
        std::cout << "enter the id that you want to delete:" << std::endl;
        std::cin >> read_chars;
        try {
            id = stoi(read_chars);
        } catch (std::invalid_argument &X){
            std::cout << "Please write a valid number for the ID" << std::endl;
        }
        if (id != -1){
                this->service.delete_dog_service(id);
        }
        //write_to_file();
        break;
        }
}

void Ui::update_dog_ui() {
    int id = -1, age = -1;
    std::string breed, name, link;
    std::string read_chars;
    while (true){
        std::cout << "enter the id that you want to update:" << std::endl;
        std::cin >> read_chars;
        try {
            id = stoi(read_chars);
        } catch (std::invalid_argument &X){
            std::cout << "Please write an existing  ID" << std::endl;
        }
        if (id != -1){
                break;
        }
    }

    while (true){
        std::cout << "enter the breed of the dog:" << std::endl;
        std::cin >> read_chars;

        if (read_chars.empty()){
            std::cout << "cannot be empthy" << std::endl;
        } else {
            breed = read_chars;
            break;
        }
    }

    while (true){
        std::cout << "enter the name of the dog:" << std::endl;
        std::cin >> read_chars;

        if (read_chars.empty()){
            std::cout << "cannot be empthy" << std::endl;
        } else {
            name = read_chars;
            break;
        }
    }

    while (true){
        std::cout << "enter a number for the age:" << std::endl;
        std::cin >> read_chars;
        try {
            age = stoi(read_chars);
        } catch (std::invalid_argument &X){
            std::cout << "Please write a valid number for the age" << std::endl;
        }
        if (age != -1){
            break;
        }
    }

    while (true){
        std::cout << "enter the photo link of the dog:" << std::endl;
        std::cin >> read_chars;

        if (read_chars.empty()){
            std::cout << "cannot be empty" << std::endl;
        } else {
            link = read_chars;
            break;
        }
    }

    this->service.update_dog_service(id, breed, name, age, link);
    //write_to_file();
}

void Ui::list_dogs_ui_user() {
    std::vector<Dog>  all_dogs = this->service.getDogsService();
    int i = 0;
    for (auto d : all_dogs){
        std::cout << d << std::endl;
        i++;
//        std::string command = "open ";
//        command += d.get_photograph();
//        system(command.c_str());

        std::string option;
        while (true){
            std::cout << "\n 1.Adopt \n 2.Go to next \n 0.Exit \nWhat op do you want? ";
            std::cin >> option;
            if (option == "1") {
                std::cout << "Adopted"<< std::endl;
                this->service.adopt_dog(d);
                this->service.delete_dog_service(d.get_id());
                //write_to_file();
                break;
            } else if (option == "2"){
                std::cout << "Going to next"<< std::endl;
                break;
            } else if (option == "0"){
                return;
            } else {
                std::cout << "Option not defined"<< std::endl;
            }
        }
        if (i == all_dogs.size())
            list_dogs_ui_user();

    }
}

void Ui::filter_dogs_ui_user() {
    std::string breed;
    std::cout << "enter the breed of the dog: ";

    //clear buffer
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

    std::getline(std::cin, breed);

    std::string read_chars;
    int age;
    std::cout << "enter a number for the age:";
    std::cin >> read_chars;
    while (true){
        try {
            age = stoi(read_chars);
            break;
        } catch (std::invalid_argument &X){
            std::cout << "Please write a valid number for the age" << std::endl;
        }
    }

    while (true){
        std::vector<Dog>  v = this->service.filterDogsService(breed, age);
        int size_v = v.size();
        if (size_v == 0){
            break;
        }
        int i = 0;
        for (auto d: v){
            std::cout << d << std::endl;

//        std::string command = "open ";
//        command += d.get_photograph();
//        system(command.c_str());

            std::string option;
            while (true){
                std::cout << "\n 1.Adopt \n 2.Go to next \n 0.Exit \nWhat op do you want? ";
                std::cin >> option;
                if (option == "1") {
                    std::cout << "Adopted"<< std::endl;
                    this->service.adopt_dog(d);
                    this->service.delete_dog_service(d.get_id());
                    //write_to_file();
                    size_v--;
                    if (size_v == 0){
                        return;
                    }
                    break;
                } else if (option == "2"){
                    std::cout << "Going to next"<< std::endl;
                    break;
                } else if (option == "0"){
                    return;
                } else {
                    std::cout << "Option not defined"<< std::endl;
                }
            }
            if (i == v.size() - 1)
                break;
        }
    }


}

void Ui::list_adoption_list() {
    std::cout << "Printing the adopted dogs" << std::endl;
    std::string command = "open ";
    command += this->service.get_file_name_service();
    system(command.c_str());
//    std::vector<Dog> all_adopted = this->service.getAdoptedService();
//    for (auto d : all_adopted){
//        std::cout << d << std::endl;
//    }
}

Ui::Ui(Service &srv) : service{srv}{}

//void Ui::write_to_file() {
//    std::ofstream file;
//    file.open ("dogs.txt");
//    std::vector<Dog> all_dogs = this->service.getDogsService();
//    for (auto d : all_dogs){
//        file << d << std::endl;
//    }
//    file.close();
//}









