//
// Created by Tudor Tise on 23/03/2021.
//

#include "Dog.h"
#include "string"
#include <vector>
#include <sstream>
#include "algorithm"

Dog::Dog() : id(), breed(""), name(""), age(), photograph("") {}

Dog::Dog(int id, const std::string& breed, const std::string& name, int age, const std::string& photograph){
    this->id = id;
    this->breed = breed;
    this->name = name;
    this->age = age;
    this->photograph = photograph;
}


int Dog::get_id() {
    return this->id;
}

std::string Dog::get_breed() const {
    return this->breed;
}

std::string Dog::get_name() {
    return this->name;
}

int Dog::get_age() const{
    return this->age;
}

std::string Dog::get_photograph() {
    return this->photograph;
}

std::string Dog::toString() {
    return "ID:" + std::to_string(this->get_id()) + " | Breed:" + this->get_breed() + " | Name:" + this->get_name() + " | Age:" + std::to_string(this->get_age()) + " | Photo:" + this->get_photograph();
}

void Dog::set_breed(const std::string& new_breed) {
    this->breed = new_breed;
}

void Dog::set_name(const std::string& new_name) {
    this->name = new_name;
}

void Dog::set_age(int new_age) {
    this->age = new_age;
}

void Dog::set_photograph(const std::string& new_photograph) {
    this->photograph = new_photograph;
}

std::ostream &operator<<(std::ostream &output, const Dog &d) {
    output << d.id << "," << d.get_breed()
           << "," << d.name << "," << d.age << "," <<
              d.photograph;
    return output;
}

std::istream &operator>>(std::istream &input, Dog &d) {
    std::vector<std::string> tokens;
    std::string token;

    std::string line ;
    getline(input,line);
    std::stringstream tokenStream(line);

    while (getline(tokenStream, token, ',')){
        tokens.push_back(token);
    }

    if (tokens.size() != 5){
        return input;
    } else {
        d.id = stoi(tokens[0]);
        d.breed = tokens[1];
        d.name = tokens[2];
        d.age = stoi(tokens[3]);
        d.photograph = tokens[4];
    }
    return input;
}

std::string Dog::toCSV() {
    std::string csv;

    csv += this->get_breed() + ",";
    csv += this->get_name() + ",";
    csv +=  std::to_string(this->get_age()) + ",";
    csv += this->get_photograph();

    return csv;
}

std::string Dog::toHTML() {
    std::string html;

    html =  "\t<tr>\n";
    html +=  "\t\t<td>" + this->get_breed() + "</td>\n";
    html +=  "\t\t<td>" + this->get_name() + "</td>\n";
    html +=  "\t\t<td>" + std::to_string(this->get_age()) + "</td>\n";
    html +=  "\t\t<td><a href=\"" + this->get_photograph() + "\">Link</a></td>\n";

    return html;
}




