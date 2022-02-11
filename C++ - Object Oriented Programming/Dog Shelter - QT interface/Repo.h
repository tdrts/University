//
// Created by Tudor Tise on 23/03/2021.
//

#ifndef LAB4_5_REPO_H
#define LAB4_5_REPO_H

#include <iostream>
#include <vector>
#include "Dog.h"

class Repo {
private:
    //DynamicVector<Dog>  dogs;
    std::vector<Dog> dogs;
public:
    //constructor
    Repo() = default;

    //reads dogs from file
    void read_from_file_repo();

    //returns the dynamic_vector
    virtual std::vector<Dog>  getDogsRepo() const {return this->dogs;}

    //ads a new dog to the repo
    virtual void add_dog_repo(const Dog&);

    //deletes a dog by id
    void delete_from_repo(int);

    void delete_by_index(int);

    //updates a dog by id with proprieties given in the new Dog parameter
    void update_in_repo(int, Dog);

    //returns the current size of the repo
    int get_size_repo();

    virtual void write_to_file();

    virtual void set_file_name(std::string file_name);

    virtual std::string get_file_name();

    Dog get_dog_by_position(int position) const;
};


#endif //LAB4_5_REPO_H
