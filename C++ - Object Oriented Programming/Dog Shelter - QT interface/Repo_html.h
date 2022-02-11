//
// Created by Tudor Tise on 12/05/2021.
//

#ifndef A67_917TUDORTISE_REPO_HTML_H
#define A67_917TUDORTISE_REPO_HTML_H
#include "Repo.h"

class Repo_html : public Repo{
private:
    std::vector<Dog> dogs_csv;
    std::string file_name_html;
public:
    void add_dog_repo(const Dog&) override;

    std::vector<Dog>  getDogsRepo() const override {return this->dogs_csv;}

    void set_file_name(std::string file_name) override;

    std::string get_file_name() override;

    void write_to_file() override;
};


#endif //A67_917TUDORTISE_REPO_HTML_H
