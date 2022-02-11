from dataclasses import dataclass


@dataclass
class Book:
    __id: str
    author: str
    title: str

    @property
    def id(self):
        return self.__id

    def __str__(self):
        return "id: {} | author: {} | title: {}".format(self.id, self.author, self.title)


@dataclass
class Client:
    __id: str
    name: str

    @property
    def id(self):
        return self.__id

    def __str__(self):
        return "id: {} | name: {}".format(self.id, self.name)


@dataclass
class Rental:
    __id: str
    book_id: str
    client_id: str
    rented_date: str
    returned_date: str = ""

    @property
    def id(self):
        return self.__id

    def __str__(self):
        return "rental id: {} | book id: {} | client id: {} | rent date: {} | return date: {}".format(
            self.id, self.book_id, self.client_id, self.rented_date, self.returned_date)
