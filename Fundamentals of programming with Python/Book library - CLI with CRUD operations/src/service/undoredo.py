from src.service.service import Library
from src.domain.validators import UndoError, RedoError
import copy


class UndoRedo(Library):
    def __init__(self, books_repo, clients_repo, rentals_repo):
        super().__init__(books_repo, clients_repo, rentals_repo)
        self.undo_list = []
        self.redo_list = []

    def add_to_history(self, wanted_function):
        self.undo_list.append(wanted_function)
        self.redo_list.clear()

    def undo(self):
        if len(self.undo_list) == 0:
            raise UndoError("Nothing to undo!")
        top = self.undo_list[-1]

        self.undo_list.pop(-1)
        top[0][0](*top[0][1:])

        self.redo_list.append(top)

    def redo(self):
        if len(self.redo_list) == 0:
            raise RedoError("Nothing to redo!")
        top = self.redo_list[-1]

        self.redo_list.pop(-1)
        top[1][0](*top[1][1:])

        self.undo_list.append(top)

    def add_book(self, book_id, book_title, book_author):
        to_add = (super().delete_book, book_id), (super().add_book, book_id, book_title, book_author)
        super().add_book(book_id, book_title, book_author)
        self.add_to_history(to_add)

    def add_book_and_rents(self, book, rents):
        super().add_book(book.id, book.author, book.title)
        for i in rents:
            if i.book_id == book.id:
                super().rent_book(i.id, i.book_id, i.client_id, i.rented_date)
                super().return_book(i.book_id, i.returned_date)

    def delete_book(self, book_id):
        rents = copy.deepcopy(list(super().get_all_rentals()))
        to_add = ((self.add_book_and_rents, super().get_book(book_id), rents), (super().delete_book, book_id))
        super().delete_book(book_id)
        self.add_to_history(to_add)

    def update_book(self, book_id, book_title, book_author):
        to_add = ((super().update_book, book_id, super().get_book(book_id).title, super().get_book(book_id).author),
                  (super().update_book, book_id, book_title, book_author))
        super().update_book(book_id, book_title, book_author)
        self.add_to_history(to_add)

    def add_client(self, client_id, name):
        to_add = (super().delete_client, client_id), (super().add_client, client_id, name)
        super().add_client(client_id, name)
        self.add_to_history(to_add)

    def add_client_and_rents(self, client, rents):
        super().add_client(client.id, client.name)
        for i in rents:
            if i.client_id == client.id:
                super().rent_book(i.id, i.book_id, i.client_id, i.rented_date)
                super().return_book(i.book_id, i.returned_date)

    def delete_client(self, client_id):
        rents = copy.deepcopy(list(super().get_all_rentals()))
        to_add = ((self.add_client_and_rents, super().get_client(client_id), rents), (super().delete_client, client_id))
        super().delete_client(client_id)
        self.add_to_history(to_add)

    def update_client(self, client_id, name):
        to_add = ((super().update_client, client_id, super().get_client(client_id).name),
                  (super().update_client, client_id, name))
        super().update_client(client_id, name)
        self.add_to_history(to_add)

    def rent_book(self, rental_id, book_id, client_id, rent_date):
        to_add = ((super().delete_rent, rental_id), (super().rent_book, rental_id, book_id, client_id, rent_date))
        super().rent_book(rental_id, book_id, client_id, rent_date)
        self.add_to_history(to_add)

    def return_book(self, book_id, return_date):
        to_add = ((super().delete_return, book_id), (super().return_book, book_id, return_date))
        super().return_book(book_id, return_date)
        self.add_to_history(to_add)