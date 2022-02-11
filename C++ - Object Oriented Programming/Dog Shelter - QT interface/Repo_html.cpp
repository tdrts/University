//
// Created by Tudor Tise on 12/05/2021.
//

#include "Repo_html.h"
#include "fstream"

void Repo_html::add_dog_repo(const Dog &d) {
    this->dogs_csv.push_back(d);
    this->write_to_file();
}

void Repo_html::set_file_name(std::string file_name) {
    this->file_name_html = file_name;
}

std::string Repo_html::get_file_name() {
    return this->file_name_html;
}

void Repo_html::write_to_file() {
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
    file.open (this->get_file_name());

    std::vector<Dog> all_dogs = this->getDogsRepo();
    for (auto d : all_dogs){
        htmlString += d.toHTML();
    }

    htmlString += "\t</table>\n";
    htmlString += "\t</body>\n";
    htmlString += "\t</html>\n";

    file << htmlString;
    file.close();
}
