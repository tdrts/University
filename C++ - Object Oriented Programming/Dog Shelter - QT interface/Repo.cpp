//
// Created by Tudor Tise on 23/03/2021.
//

#include "Repo.h"
#include <fstream>

void Repo::read_from_file_repo() {
    std::ifstream file;
    Dog d;
    file.open("dogs.txt");
    while (file >> d){
            add_dog_repo(d);
    }
}

void Repo::add_dog_repo(const Dog &d) {
    this->dogs.push_back(d);
}

void Repo::delete_from_repo(int pos) {
    this->dogs.erase(this->dogs.begin() + pos);
}

void Repo::update_in_repo(int pos,  Dog d) {
    this->dogs.at(pos)= d;
}

int Repo::get_size_repo() {
    return this->dogs.size();
}

void Repo::write_to_file() {
    std::ofstream file;
    file.open ("dogs.txt");
    std::vector<Dog> all_dogs = getDogsRepo();
    for (auto d : all_dogs){
        file << d << std::endl;
    }
    file.close();
}

void Repo::set_file_name(std::string file_name) {
    return;
}

std::string Repo::get_file_name() {
    return "";
}

Dog Repo::get_dog_by_position(int position) const{
    if (position < 0 || position >= dogs.size())
        throw std::runtime_error("Invalid position");
    return dogs[position];
}

void Repo::delete_by_index(int index) {
    this->dogs.push_back(dogs[index]);
}
