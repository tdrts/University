//
// Created by Tudor Tise on 23/03/2021.
//

#include "Service.h"
#include <string.h>
#include <algorithm>
#include <numeric>
#include "InputException.h"
#include "Validator.h"


void Service::read_data_file() {
    this->repo.read_from_file_repo();
}

std::vector<Dog>  Service::getDogsService() const {
    return this->repo.getDogsRepo();
}

void Service::add_dog(int id, const std::string &breed, const std::string &name, int age, const std::string &photograph) {
    if (this->search_for_duplicate_id(id) != -1){
        throw AlreadyExistsException();
    } else {
        Dog d = Dog(id, breed, name, age, photograph);
        Validator::appropriateAge(d);
        Validator::appropriateID(d);
        this->repo.add_dog_repo(d);
        this->repo.write_to_file();
    }
}

int Service::search_for_duplicate_id(int id) {
    std::vector<Dog>  v = getDogsService();
    int i = 0;
    for (auto d : v){
        if (d.get_id() == id)
            return i;
        i++;
    }
    return -1;
}

void Service::delete_dog_service(int id) {
    if (this->search_for_duplicate_id(id) != -1){
        this->repo.delete_from_repo(this->search_for_duplicate_id(id));
        this->repo.write_to_file();
    } else {
        throw NotExistingException();
    }
}

void Service::update_dog_service(int id, const std::string &breed, const std::string &name, int age, const std::string &photograph) {
    int pos = search_for_duplicate_id(id);
    if (pos != -1){
        Dog d = Dog(id, breed, name,age,photograph);
        Validator::appropriateAge(d);
        this->repo.update_in_repo(pos, d);
        this->repo.write_to_file();
    } else{
        throw NotExistingException();
    }

}

int Service::get_size_service() {
    return this->repo.get_size_repo();
}

std::vector<Dog>  Service::filterDogsService(std::string &breed, int age) {
    std::vector<Dog>  new_v;

    std::vector<Dog>  v = getDogsService();
    auto checks_age = [age](const Dog& d) { return d.get_age() < age;};
    auto checks_breed = [breed, age](const Dog& d) { return (d.get_breed()==breed) && (d.get_age() < age);};

    if (!breed.empty()){
        copy_if(v.begin(), v.end(), std::back_inserter(new_v), checks_breed);
    } else{
        copy_if(v.begin(), v.end(), std::back_inserter(new_v), checks_age);
    }

    return new_v;
}

void Service::adopt_dog(const Dog &d) {
    this->adoption.add_dog_repo(d);
}

std::vector<Dog> Service::getAdoptedService() const {
    return this->adoption.getDogsRepo();
}

int Service::get_size_adopted() {
    return this->adoption.get_size_repo();
}

Service::Service(Repo admin, Repo &user) : repo{admin}, adoption{user} {}

std::string Service::get_file_name_service() {
    return this->adoption.get_file_name();
}

Dog Service::get_dog_service_admin(int position) const{
    return this->repo.get_dog_by_position(position);
}

void Service::delete_dog_by_index(int index) {
    this->repo.delete_by_index(index);
}
