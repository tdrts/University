//
// Created by Tudor Tise on 21/04/2021.
//

#ifndef A67_917TUDORTISE_INPUTEXCEPTION_H
#define A67_917TUDORTISE_INPUTEXCEPTION_H


#include <exception>
#include <string>

class InputException : public std::exception {
private:
    std::string message;
public:
    explicit InputException(std::string message);

    [[nodiscard]] const char *what() const noexcept override;
};

class AlreadyExistsException: public InputException {
public:
    AlreadyExistsException();
};

class NotExistingException: public InputException {
public:
    NotExistingException();
};


#endif //A67_917TUDORTISE_INPUTEXCEPTION_H
