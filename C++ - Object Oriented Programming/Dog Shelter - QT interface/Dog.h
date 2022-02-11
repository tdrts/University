//
// Created by Tudor Tise on 23/03/2021.
//

#ifndef LAB4_5_DOG_H
#define LAB4_5_DOG_H

#include "iostream"


class Dog {
private:
    int id;
    std::string breed;
    std::string name;
    int age;
    std::string photograph;


public:
    Dog();
    Dog(int id, const std::string& breed, const std::string& name, int age, const std::string& photograph);

    //getters
    int get_id();
    std::string get_breed() const;
    std::string get_name();
    int get_age() const;
    std::string get_photograph();

    //function used to convert the object to a printable string
    std::string toString();

    std::string toCSV();
    std::string toHTML();

    //setters
    void set_breed(const std::string& new_breed);
    void set_name(const std::string& new_name);
    void set_age(int new_age);
    void set_photograph(const std::string& new_photograph);

    friend std::ostream &operator<<( std::ostream &output, const Dog &D );
    friend std::istream &operator>>( std::istream  &input, Dog &D );

    };
#endif //LAB4_5_DOG_H
