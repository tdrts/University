import string
import random
from src.service.undoredo import UndoRedo
from src.domain.validators import *
from src.repository.repo import *


class Console:
    def __init__(self):
        self.__service = UndoRedo(BookRepository(), ClientRepository(), RentalRepository())

    @staticmethod
    def print_options(self):
        print("")
        print(" \tAvailable options:")
        print("====manage books====")
        print("1. add book")
        print("2. list book")
        print("3. update  book")
        print("4. remove book")
        print("====manage clients====")
        print("5. add client")
        print("6. list clients")
        print("7. update client")
        print("8. remove clients")
        print("====manage rentals====")
        print("9. Rent a book")
        print("10. Return a book")
        print("11. List rentals")
        print("====Search=====")
        print("12. Search books by id")
        print("13. Search books by author")
        print("14. Search books by title")
        print("15. Search client by id")
        print("16. Search client by name")
        print("====Statistics=====")
        print("17. Most rented books")
        print("18. Most active clients")
        print("19. Most rented author")
        print("=========")
        print("20. Undo")
        print("21. Redo")
        print("")

    @staticmethod
    def add_books_at_startup(self):
        """
        adds 10 random books at startup
        """
        letters = string.ascii_lowercase
        for i in range(0, 4):
            title = ''.join(random.choice(letters) for j in range(5))
            author = ''.join(random.choice(letters) for j in range(5))
            self.__service.add_book(i, title, author)

    @staticmethod
    def add_clients_at_startup(self):
        """
        adds 10 random clients at startup
        """
        letters = string.ascii_lowercase
        for i in range(0, 4):
            name = ''.join(random.choice(letters) for j in range(2))
            self.__service.add_client(i, name)

    def __ui_add_book(self):
        id = input("Please enter the id of the book you want to add: ")

        try:
            id_int = int(id)
        except ValueError as ve:
            raise ValueError("Book id must be an integer")

        author = input("Please enter the author of the book you want to add: ")
        title = input("Please enter the title of the book you want to add: ")
        self.__service.add_book(id_int, title, author)

    def __ui_delete_book(self):
        id = input("Please enter the id of the book you want to remove: ")

        try:
            id_int = int(id)
        except ValueError as ve:
            raise ValueError("Book id must be an integer")

        self.__service.delete_book(id_int)

    def ui_list_book(self):
        print("Listing all the books: ")
        for x in self.__service.get_all_books():
            print(x)

    def __ui_update_book(self):
        book_id = input("Please enter the id of the book you want to update: ")

        try:
            id_int = int(book_id)
        except ValueError as ve:
            raise ValueError("Book id must be an integer")

        author = input("Please enter the author of the book you want to update: ")
        title = input("Please enter the title of the book you want to update: ")
        self.__service.update_book(id_int, author, title)

    def __ui_add_client(self):
        id = input("Please enter the id of the client you want to add: ")

        try:
            id_int = int(id)
        except ValueError as ve:
            raise ValueError("Client id must be an integer")

        name = input("Please enter the name of the client you want to add: ")
        self.__service.add_client(id_int, name)

    def __ui_delete_client(self):
        id = input("Please enter the id of the client you want to remove: ")

        try:
            id_int = int(id)
        except ValueError as ve:
            raise ValueError("Client id must be an integer")

        self.__service.delete_client(id_int)

    def ui_list_client(self):
        print("Listing all the clients: ")
        for x in self.__service.get_all_clients():
            print(x)

    def __ui_update_client(self):
        id = input("Please enter the id of the client you want to update: ")

        try:
            id_int = int(id)
        except ValueError as ve:
            raise ValueError("Client id must be an integer")

        name = input("Please enter the name of the client you want to update: ")
        self.__service.update_client(id_int, name)

    def __ui_rent_book(self):
        try:
            rental_id = int(input("Please enter the id of the rental: "))
            book_id = int(input("Please enter the id of the book you want to rent out: "))
            client_id = int(input("Please enter the id of the client who wants to rent the book: "))
            rent_day = int(input("rent day"))
            if rent_day < 1 or rent_day > 31:
                raise DateError("Day can only be between 1 and 31")
            rent_month = int(input("rent month"))
            if rent_month < 1 or rent_month > 12:
                raise DateError("Month can only be between 1 and 12")
            rent_year = int(input("rent year"))
            if rent_year < 1800 or rent_year > 2200:
                raise DateError("Year can only be between 1800 and 2200")
        except ValueError as ve:
            raise ValueError("Wrong format. Should be integers")

        rent_date = str(rent_day) + '.' + str(rent_month) + '.' + str(rent_year)
        self.__service.rent_book(rental_id, book_id, client_id, rent_date)

    def __ui_return_book(self):

        try:
            book_id = int(input("Please enter the id of the book you want to be returned: "))
            return_day = int(input("rent day"))
            if return_day < 1 or return_day > 31:
                raise DateError("Day can only be between 1 and 31")

            return_month = int(input("rent month"))

            if return_month < 1 or return_month > 12:
                raise DateError("Month can only be between 1 and 12")

            return_year = int(input("rent year"))

            if return_year < 1800 or return_year > 2200:
                raise DateError("Year can only be between 1800 and 2200")
        except ValueError as ve:
            raise ValueError("Wrong format. Should be integers")

        return_date = str(return_day) + '.' + str(return_month) + '.' + str(return_year)
        self.__service.return_book(book_id, return_date)

    def ui_list_rentals(self):
        print("Listing all the rentals: ")
        for x in self.__service.get_all_rentals():
            print(x)

    def ui_search_book_id(self):
        book_id = input("write the id you want to search for: ")
        try:
            id_int = int(book_id)
        except ValueError as ve:
            raise ValueError("Book id must be an integer")

        result = self.__service.search_book_by_id(id_int)
        if not result:
            print("No books with the given id")
        else:
            for each in result:
                print(each)

    def ui_search_book_title(self):
        book_title = input("write the title you want to search for: ")

        result = self.__service.search_book_by_title(book_title.lower())
        if not result:
            print("No books with the given title")
        else:
            for each in result:
                print(each)

    def ui_search_book_author(self):
        book_author = input("write the author you want to search for: ")

        result = self.__service.search_by_author(book_author.lower())
        if not result:
            print("No books with the given author")
        else:
            for each in result:
                print(each)

    def ui_search_client_id(self):
        client_id = input("write the id you want to search for: ")
        try:
            id_int = int(client_id)
        except ValueError as ve:
            raise ValueError("client id must be an integer")

        result = self.__service.search_client_by_id(id_int)
        if not result:
            print("No client with the given id")
        else:
            for each in result:
                print(each)

    def ui_search_client_name(self):
        client_name = input("write the name you want to search for: ")

        result = self.__service.search_by_name(client_name.lower())
        if not result:
            print("No client with the given name")
        else:
            for each in result:
                print(each)

    def ui_most_rented_books(self):
        print("Most rented books")
        for x in self.__service.most_rented_books():
            print(x)

    def ui_most_active_clients(self):
        print("Most active clients")
        for x in self.__service.most_active_clients():
            print(x)

    def ui_most_rented_author(self):
        print("Most rented author")
        for x in self.__service.most_rented_author():
            print(x)

    def ui_undo(self):
        self.__service.undo()
        print("Undo done!")

    def ui_redo(self):
        self.__service.redo()
        print("Redo done")

    def run(self):
        self.add_books_at_startup(self)
        self.add_clients_at_startup(self)
        while True:
            self.print_options(self)
            command = {1: self.__ui_add_book, 2: self.ui_list_book, 3: self.__ui_update_book, 4: self.__ui_delete_book,
                       5: self.__ui_add_client, 6: self.ui_list_client, 7: self.__ui_update_client, 8: self.__ui_delete_client,
                       9: self.__ui_rent_book, 10: self.__ui_return_book, 11:self.ui_list_rentals, 12: self.ui_search_book_id,
                       13: self.ui_search_book_author, 14: self.ui_search_book_title, 15: self.ui_search_client_id,
                       16: self.ui_search_client_name, 17: self.ui_most_rented_books, 18: self.ui_most_active_clients,
                       19: self.ui_most_rented_author, 20: self.ui_undo, 21: self.ui_redo}
            cmd = input("What operation do you want to perform?")

            if cmd == 'exit':
                break

            try:
                if cmd.isdigit():
                    command[int(cmd)]()
                else:
                    print("operation must be a number")
            except KeyError as ke:
                print('option', ke, ' does not exist')
            except DateError as de:
                print('date error exception caught:', de)
            except ValueError as ve:
                print('exception caught: ', ve)
            except IndexError as ie:
                print('exception caught: ', ie)
            except ExistingElement as ee:
                print('exception caught: ', ee)
            except NotExistingElement as ne:
                print('exception caught: ', ne)
            except UndoError as ue:
                print(ue)
            except RedoError as re:
                print(re)
