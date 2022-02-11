from src.service.service import *
from src.repository.repo import *


import unittest

from src.service.undoredo import UndoRedo


class Tests(unittest.TestCase):

    def setUp(self):
        self.library = UndoRedo(BookRepository(), ClientRepository(), RentalRepository())

    def tearDown(self):
        del self.library

    def test_add_book(self):
        self.library.add_book(1, "Carte", "Martin")

        self.assertEqual(len(self.library.get_all_books()), 1)
        self.assertRaises(ExistingElement, self.library.add_book, 1, "Book", "Martina")

    def test_delete_book(self):
        self.library.add_book(1, "Carte", "Martin")
        self.assertEqual(len(self.library.get_all_books()), 1)

        self.library.delete_book(1)
        self.assertEqual(len(self.library.get_all_books()), 0)

        self.assertRaises(NotExistingElement, self.library.delete_book, 1)

    # def test_update_book(self):
    #     books_repo = BookRepository()
    #
    #     self.library.add_book(1, "Carte", "Martin")
    #
    #     self.assertRaises(NotExistingElement, self.library.update_book, 2, "ala", "bala")
    #
    #     self.library.update_book(1, "Kate", "Book")
    #     self.assertEqual(len(self.library.get_all_books()), 1)
    #     for x in books_repo.get_all():
    #         self.assertEqual(int(x.id), 1)
    #         self.assertEqual(x.title, "Book")
    #         self.assertEqual(x.author, "Kate")

    def test_get_all_books(self):
        self.library.add_book(1, "Carte", "Martin")
        self.assertEqual(len(self.library.get_all_books()), 1)

        self.library.add_book(2, "Carte", "Martin")
        self.library.add_book(3, "Carte", "Martin")
        self.assertEqual(len(self.library.get_all_books()), 3)

    def test_add_client(self):
        self.library.add_client(1, "Martin")

        self.assertEqual(len(self.library.get_all_clients()), 1)
        self.assertRaises(ExistingElement, self.library.add_client, 1, "Martina")

    def test_delete_client(self):
        self.library.add_client(1, "Martin")
        self.assertEqual(len(self.library.get_all_clients()), 1)

        self.library.delete_client(1)
        self.assertEqual(len(self.library.get_all_clients()), 0)
        self.assertRaises(NotExistingElement, self.library.delete_client, 1)

    # def test_update_client(self):
    #
    #     clients_repo = ClientRepository()
    #
    #     self.library.add_client(1, "Martin")
    #
    #     self.assertEqual(len(self.library.get_all_clients()), 1)
    #
    #     self.assertRaises(NotExistingElement, self.library.update_client, 2, "Kate")
    #
    #     self.library.update_client(1, "Kate")
    #     self.assertEqual(len(self.library.get_all_clients()), 1)
    #
    #     for x in clients_repo.get_all():
    #         self.assertEqual(int(x.id), 1)
    #         self.assertEqual(x.name, "Kate")

    def test_get_all_clients(self):
        self.library.add_client(1, "Martin")
        self.assertEqual(len(self.library.get_all_clients()), 1)

        self.library.add_client(2, "Martina")
        self.library.add_client(3, "Martinb")
        self.assertEqual(len(self.library.get_all_clients()), 3)

    def test_rent_book(self):
        self.library.add_book(1, "tudor ", "carte")
        self.library.add_book(2, "alex ", "carte2")
        self.library.add_client(1, "maria")
        self.library.add_client(2, "mariaaa")

        self.assertRaises(NotExistingElement, self.library.rent_book, 1, 3, 1, "2.3.2000)")
        self.assertRaises(NotExistingElement, self.library.rent_book, 2, 1, 3, "2.3.2000")

        self.library.rent_book(3, 2, 2, "2.3.2000)")
        self.assertRaises(ExistingElement, self.library.rent_book, 3, 2, 2, "2.3.2000")
        self.assertRaises(ExistingElement, self.library.rent_book, 4, 2, 1, "2.3.2000")

    def test_return_book(self):

        self.library.add_book(1, "tudor ", "carte")
        self.library.add_book(2, "alex ", "carte2")
        self.library.add_client(1, "maria")
        self.library.add_client(2, "mariaaa")

        self.assertRaises(NotExistingElement, self.library.return_book, 3, "2.3.2000")

        self.library.rent_book(1, 1, 1, "1.1.2000")
        self.library.return_book(1, "2.3.2000")

        for rental in self.library.get_all_rentals():
            self.assertEqual(rental.returned_date, "2.3.2000")

    def test_get_all_rentals(self):

        self.library.add_book(1, "tudor ", "carte")
        self.library.add_client(1, "maria")

        self.library.rent_book(1, 1, 1, "12.03.2000")
        self.assertEqual(len(self.library.get_all_rentals()),1)

        self.library.add_book(2, "alex ", "carte2")
        self.library.add_client(2, "mariaaa")
        self.library.rent_book(2, 2, 2, "13.03.2000")

        self.assertEqual(len(self.library.get_all_rentals()), 2)

    def test_search_by_id_book(self):
        self.library.add_book(1, "tudor ", "carte")
        self.library.add_book(2, "tudort ", "carte2")
        self.library.add_book(10, "tudor ", "carte3")
        self.library.add_book(11, "tudor ", "carte3")

        self.assertEqual(len(self.library.search_book_by_id(1)), 3)

    def test_search_by_id_title(self):
        self.library.add_book(1, "carte", "tudor ")
        self.library.add_book(2, "carte2", "tudort ")
        self.library.add_book(10, "carte3", "tudr ")

        self.assertEqual(len(self.library.search_book_by_title("carte")), 3)

    def test_search_by_id_author(self):
        self.library.add_book(1, "carte", "tudor ")
        self.library.add_book(2, "carte2", "tudort ")
        self.library.add_book(10, "carte3", "tudr ")

        self.assertEqual(len(self.library.search_by_author("tud")), 3)

    def test_search_by_id_client(self):
        self.library.add_client(1, "Martin")
        self.assertEqual(len(self.library.search_client_by_id(1)), 1)

        self.library.add_client(21, "Martina")
        self.library.add_client(3, "Martinb")
        self.assertEqual(len(self.library.search_client_by_id(1)), 2)

    def test_search_by_name(self):
        self.library.add_client(1, "Martin")
        self.assertEqual(len(self.library.search_by_name("rtin")), 1)

        self.library.add_client(21, "Martina")
        self.library.add_client(3, "Martinb")
        self.assertEqual(len(self.library.search_by_name("Martina")), 1)

    def test_most_rented_books(self):

        self.library.add_book(1, "tudor ", "carte")
        self.library.add_book(2, "alex ", "book")
        self.library.add_client(1, "maria")
        self.library.add_client(2, "ana")

        self.library.rent_book(1, 1, 1, "12.03.2000")
        self.library.return_book(1, "21.3.2000")

        self.library.rent_book(2, 1, 2, "23.03.2000")
        self.library.return_book(1, "25.3.2000")

        self.library.rent_book(3, 2, 2, "23.03.2000")
        self.library.return_book(2, "25.3.2000")

        self.assertEqual(self.library.most_rented_books()[0].id, 1)
        self.assertEqual(self.library.most_rented_books()[2].id, 2)

    def test_most_active_clients(self):
        self.library.add_book(1, "tudor ", "carte")
        self.library.add_book(2, "alex ", "book")
        self.library.add_book(3, "mara ", "livre")

        self.library.add_client(1, "maria")
        self.library.add_client(2, "ana")

        self.library.rent_book(1, 1, 1, "12.03.2000")
        self.library.return_book(1, "21.3.2000")

        self.library.rent_book(2, 1, 2, "23.03.2000")
        self.library.return_book(1, "27.3.2000")

        self.library.rent_book(3, 3, 2, "23.03.2000")
        self.library.return_book(3, "26.3.2000")

        self.assertEqual(self.library.most_active_clients()[0].id, 2)
        self.assertEqual(self.library.most_active_clients()[2].id, 1)

    def test_most_rented_author(self):

        self.library.add_book(1, "carte", "tudor")
        self.library.add_book(2, "book", "alex")
        self.library.add_book(3, "livre", "alex")

        self.library.add_client(1, "maria")
        self.library.add_client(2, "ana")

        self.library.rent_book(1, 1, 1, "12.03.2000")
        self.library.return_book(1, "21.3.2000")

        self.library.rent_book(2, 2, 2, "23.03.2000")
        self.library.return_book(2, "25.3.2000")

        self.library.rent_book(3, 3, 2, "23.03.2000")
        self.library.return_book(3, "25.3.2000")

        self.assertEqual(self.library.most_rented_author()[0], "alex")
        self.assertEqual(self.library.most_rented_author()[2], "tudor")

    def test_undo(self):
        self.library.add_book(1, "carte", "tudor")
        self.library.add_book(2, "book", "alex")
        self.library.add_book(3, "livre", "alex")


        self.assertEqual(len(self.library.get_all_books()), 3)
        self.library.undo()

        self.assertEqual(len(self.library.get_all_books()), 2)
        self.library.undo()

        self.assertEqual(len(self.library.get_all_books()), 1)
        self.library.undo()

        self.assertEqual(len(self.library.get_all_books()), 0)

        self.library.add_book(2, "book", "alex")
        self.library.add_client(1, "alex")
        self.library.rent_book(3, 2, 1, "2.3.2000)")
        self.assertEqual(len(self.library.get_all_rentals()), 1)

        self.library.undo()
        self.assertEqual(len(self.library.get_all_rentals()), 0)

    def test_redo(self):
        self.library.add_book(1, "carte", "tudor")
        self.library.add_book(2, "book", "alex")
        self.library.add_client(1, "alex")
        self.library.undo()
        self.library.undo()
        self.library.undo()


        self.assertEqual(len(self.library.get_all_books()), 0)
        self.assertEqual(len(self.library.get_all_clients()), 0)

        self.library.redo()
        self.assertEqual(len(self.library.get_all_books()), 1)

        self.library.redo()
        self.assertEqual(len(self.library.get_all_books()), 2)

        self.library.redo()
        self.assertEqual(len(self.library.get_all_clients()), 1)

        self.library.add_book(3, "book", "alex")
        self.library.add_client(3, "alex")
        self.library.rent_book(3, 3, 3, "2.3.2000)")
        self.assertEqual(len(self.library.get_all_rentals()), 1)

        self.library.undo()
        self.assertEqual(len(self.library.get_all_rentals()), 0)

        self.library.redo()
        self.assertEqual(len(self.library.get_all_rentals()), 1)

