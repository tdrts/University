//
// Created by Tudor Tise on 21/04/2021.
//

#include "Validator.h"
#include "InputException.h"

void Validator::appropriateAge(const Dog& d) {
    if (d.get_age() < 0){
        throw InputException("Cannot have a negative age");
    }
    if (d.get_age() > 20){
        throw InputException("Dog cannot be that old");
    }
}

void Validator::appropriateID(Dog &d) {
    if (d.get_id() < 0 ){
        throw InputException("Cannot have a negative id");
    }
}
