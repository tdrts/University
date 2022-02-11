from src.domain.entities import Book, Rental, Client
from src.domain.validators import ExistingElement, NotExistingElement
from collections import Counter
from datetime import datetime
from src.IterableDataStructure import *


class Library:
    def __init__(self, books_repo, client_repo, rentals_repo):
        self.__books = books_repo
        self.__clients = client_repo
        self.__rentals = rentals_repo

    def add_book(self, id, title, author):
        """
        Adds a new book
        :param id: integer id
        :param title: title of the book
        :param author: author of the book

        If book already exists it throws a ValueError
        """
        if self.__books.duplicate_id(id):
            raise ExistingElement("Book Id already exists. Please add a different one")
        else:
            new_book = Book(id, author, title)
            self.__books.add(new_book)

    def delete_book(self, id):
        """
        Deletes a book by id and the associated rentals with that book
        :param id: integer id

        If book id does not exists it throws a ValueError
        """
        if not self.__books.duplicate_id(id):
            raise NotExistingElement("Book Id does not exists. Please chose an existing one")
        else:
            self.__books.delete_by_id(id)
            to_delete = []
            for x in self.__rentals.get_all():
                if int(x.book_id) == id:
                    to_delete.append(x.id)

            for y in to_delete:
                self.__rentals.delete_by_id(y)

    def get_book(self, book_id):
        book = self.__books.find_by_id(int(book_id))
        if book is None:
            raise NotExistingElement("Could't find the book id")
        return book

    def update_book(self, id, author, title):
        """
        Updates the author and title of a book
        :param id: integer book id
        :param author: new author name
        :param title: new title name

        If book id does not exists it throws a ValueError
        """
        if not self.__books.duplicate_id(id):
            raise NotExistingElement("Book Id does not exists. Please chose an existing one")
        else:
            new_book = Book(id, author, title)
            self.__books.update(id, new_book)

    def get_all_books(self):
        """
        :return: all available books
        """
        return self.__books.get_all()

    def search_book_by_id(self, id):
        """
        Returns all books with given id partial string matching
        :param id: id of the wanted book
        :return: all books with the given id partial matching
        """
        books = self.get_all_books()
        return filter(books, lambda x: str(x.id).find(str(id)) > -1)

    def search_book_by_title(self, title):
        """
            Returns all books with given title partial string matching
        :param title: title of the wanted book
        :return: all books with the given title partial matching and case insensitive
        """
        books = self.get_all_books()
        return filter(books, lambda x: str(x.title).find(str(title)) > -1)

    def search_by_author(self, author):
        """
            Returns all books with given author partial string matching
            :param author: author of the wanted book
            :return: all books with the given author partial matching and case insensitive
        """
        books = self.get_all_books()
        return filter(books, lambda x: str(x.author).find(str(author)) > -1)

    def add_client(self, id, name):
        """
        Adds a new client
        :param id: integer client id
        :param name: name of the client

        If client id already exists it throws a value error
        """
        if self.__clients.duplicate_id(id):
            raise ExistingElement("Client Id already exists. Please add a different one")
        else:
            new_client = Client(id, name)
            self.__clients.add(new_client)

    def delete_client(self, id):
        """
        Deletes a client by id and all the rentals associated to that client
        :param id: integer id of the wanted deleted book

        If client id does not exist it throws a value error
        """
        if not self.__clients.duplicate_id(id):
            raise NotExistingElement("Client Id does not exists. Please chose an existing one")
        else:
            self.__clients.delete_by_id(id)
            to_delete = []
            for x in self.__rentals.get_all():
                if int(x.client_id) == id:
                    to_delete.append(x.id)

            for y in to_delete:
                self.__rentals.delete_by_id(y)

    def update_client(self, id, name):
        """
        Updates the name of a client
        :param id: integer id to identify the wanted client
        :param name: new client name

        If client id does not exist it throws a value error
        """
        if not self.__clients.duplicate_id(id):
            raise NotExistingElement("Client Id does not exists. Please chose an existing one")
        else:
            new_client = Client(id, name)
            self.__clients.update(id, new_client)

    def get_client(self, client_id):
        client = self.__clients.find_by_id(int(client_id))
        if client is None:
            raise NotExistingElement("Could't find the client id")
        return client

    def get_all_clients(self):
        """
            :return: all available clients
        """
        return self.__clients.get_all()

    def search_client_by_id(self, id):
        """
            Return all clients with the given id
        :param id: id wanted of wanted clients
        :return: all clients with the given id partial string matching
        """
        clients = self.get_all_clients()
        return filter(clients, lambda x: str(x.id).find(str(id)) > -1)

    def search_by_name(self, name):
        """
            Returns all clients with given name partial string matching
            :param name: name of the wanted book
            :return: all clients with the given name partial matching and case insensitive
        """
        clients = self.get_all_clients()
        return filter(clients, lambda x: str(x.name).find(str(name)) > -1)

    def rent_book(self, rental_id, book_id, client_id, rent_date):
        """
        Ads a new rented book
        :param rental_id: id of the rent
        :param book_id: id of the wanted book
        :param client_id: id of the person that rents the book
        :param rent_date: return date which is empthy at the beginning

        Function checks the validity of the ids.
        If a book is already on the rentals it will check wether of not it has been returned.
        """
        if self.__rentals.duplicate_id(rental_id):
            raise ExistingElement("Rental Id already exists. Please chose another one")

        if not self.__clients.duplicate_id(client_id):
            raise NotExistingElement("Client Id does not exists. Please chose an existing one")

        if not self.__books.duplicate_id(book_id):
            raise NotExistingElement("Book Id does not exists. Please chose an existing one")

        for x in self.__rentals.get_all():
            if (x.book_id == book_id) and (x.returned_date == ""):
                raise ExistingElement("Book already rented! Please chose another one")

        rental = Rental(rental_id, book_id, client_id, rent_date)
        self.__rentals.add(rental)

    def get_rent(self, rent_id):
        rent = self.__rentals.find_by_id(int(rent_id))
        if rent is None:
            raise NotExistingElement("Could't find rent the id")
        return rent

    def delete_rent(self, id):
        """
        Deletes a client by id and all the rentals associated to that client
        :param id: integer id of the wanted deleted book

        If client id does not exist it throws a value error
        """
        if not self.__rentals.duplicate_id(id):
            raise NotExistingElement("Rental Id does not exists. Please chose an existing one")
        else:
            self.__rentals.delete_by_id(id)

    def delete_return(self, book_id):
        for rental in self.__rentals.get_all():
            if rental.book_id == book_id:
                rental.returned_date = ""

    def return_book(self, book_id, return_date):
        """
        Ads the return date of a rental
        :param book_id: book that has been rented
        :param return_date: date on which the book has been returnd

        It checks if the book has been rented or not
        """
        is_rented = False
        for x in self.__rentals.get_all():
            if (x.book_id == book_id) and (x.returned_date == ""):
                is_rented = True

        if not is_rented:
            raise NotExistingElement("Book not rented! Please chose an existing one")

        for rental in self.__rentals.get_all():
            if rental.book_id == book_id:
                rental.returned_date = return_date

    def get_all_rentals(self):
        """
        :return: all available rentals
        """
        return self.__rentals.get_all()

    def most_rented_books(self):
        """
        Computing the most number of books that have been rented
        :return: a list having the books in the desceding order of the number of rents
        """
        all_rentals = self.get_all_rentals()
        array_of_book_id = []
        for each in all_rentals:
            array_of_book_id.append(each.book_id)
        array_of_book_id = sorted(array_of_book_id, key=array_of_book_id.count, reverse=True)

        books = []
        for i in array_of_book_id:
            for x in self.__books.get_all():
                if i == x.id:
                    books.append(x)
        return books

    @staticmethod
    def days_between(d1, d2):
        """
        Function to compute the number of days between 2 give dates
        :param d1: first date
        :param d2: second date
        :return: number of days between the 2 dates
        """
        d1 = datetime.strptime(d1, "%d.%m.%Y")
        d2 = datetime.strptime(d2, "%d.%m.%Y")
        return abs((d2 - d1).days)

    def most_active_clients(self):
        """
        Computing the most number of clients that have been renting books
        :return: a list having the clients in the desceding order of the number of rents
        """
        all_rentals = self.get_all_rentals()
        array_of_client_days = []
        for each in all_rentals:
            array_of_client_days.append(self.days_between(each.rented_date, each.returned_date))
        array_of_client_days = sorted(array_of_client_days, key=array_of_client_days.count, reverse=True)

        clients_id = []
        for i in array_of_client_days:
            for x in all_rentals:
                if i == self.days_between(x.rented_date, x.returned_date):
                    clients_id.append(x.client_id)
        clients_id = sorted(clients_id, key = clients_id.count,reverse=True)

        clients = []
        for j in clients_id:
            for i in self.__clients.get_all():
                if i.id == j:
                    clients.append(i)

        return clients

    def most_rented_author(self):
        """
        Computing the most number of author whose books that have been rented
        :return: a list having the authors names in the desceding order of the number of rents
        """
        all_rentals = self.get_all_rentals()
        array_of_books = []
        for each in all_rentals:
            array_of_books.append(each.book_id)

        authors = []
        for i in array_of_books:
            for x in self.__books.get_all():
                if i == x.id:
                    authors.append(x.author)
        new_authors = sorted(authors, key=authors.count, reverse=True)

        return new_authors


