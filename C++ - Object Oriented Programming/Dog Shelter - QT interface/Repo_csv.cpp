//
// Created by Tudor Tise on 12/05/2021.
//

#include "Repo_csv.h"
#include <fstream>

void Repo_csv::add_dog_repo(const Dog &d) {
    this->dogs_csv.push_back(d);
    this->write_to_file();
}

void Repo_csv::set_file_name(std::string file_name) {
    this->file_name_csv = file_name;
}

std::string Repo_csv::get_file_name() {
    return this->file_name_csv;
}

void Repo_csv::write_to_file() {
    std::ofstream file;
    file.open (this->get_file_name());

    std::vector<Dog> all_dogs = this->getDogsRepo();

    for (auto d : all_dogs){
        file << d.toCSV() << std::endl;
    }

    file.close();
}

