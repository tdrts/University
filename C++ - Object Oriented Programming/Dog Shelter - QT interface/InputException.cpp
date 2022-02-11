//
// Created by Tudor Tise on 21/04/2021.
//

#include "InputException.h"

#include <utility>

InputException::InputException(std::string message) : message(std::move(message)) {}

const char *InputException::what() const noexcept {
    return this->message.c_str();
}

AlreadyExistsException::AlreadyExistsException() : InputException("element already exists\n") {}

NotExistingException::NotExistingException() : InputException("element does not exist\n") {}