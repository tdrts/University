//
// Created by Tudor Tise on 23/03/2021.
//

#ifndef LAB4_5_SERVICE_H
#define LAB4_5_SERVICE_H

#include "Repo.h"

class Service {
private:
    Repo repo;
    Repo& adoption;

public:
    //constructor
    Service(Repo admin, Repo& user);

    //reads dogs from file
    void read_data_file();

    //returns the dynamic array
    std::vector<Dog>  getDogsService() const;

    //returns the dynamic array filtered
//    std::vector<Dog>  filterDogsService(std::string breed, int age);
    std::vector<Dog>  filterDogsService(std::string &breed, int age);

    //ads a new dog
    //return -1 if id already exists, 1 otherwise
    void add_dog(int id, const std::string& breed, const std::string& name, int age, const std::string& photograph);
    int search_for_duplicate_id(int id);

    //deletes a dog by id
    //return -1 if id doesnt exists, 1 otherwise
    void delete_dog_service(int id);

    void delete_dog_by_index(int index);

    //updates a dog identified by id
    void update_dog_service(int id, const std::string &breed, const std::string &name, int age, const std::string &photograph);

    //returns the size of the repo
    int get_size_service();

    //add a dog to the adoption list
    void adopt_dog(const Dog &d);

    //return the list of adopted dogs
    std::vector<Dog> getAdoptedService() const;

    //return the size of the adopted list
    int get_size_adopted();

    std::string get_file_name_service();

    Dog get_dog_service_admin(int position) const;

};


#endif //LAB4_5_SERVICE_H
