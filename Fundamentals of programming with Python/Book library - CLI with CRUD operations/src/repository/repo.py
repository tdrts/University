from src.domain.validators import DataException
from src.IterableDataStructure import IterableDataStructure

class RepositoryError(DataException):
    pass


class BookRepository:
    def __init__(self):
        self.__entities = IterableDataStructure()

    def duplicate_id(self, id):
        """
        Checks if the id already exists
        :param id: wanted it to be verified
        :return: True if it already existing else False
        """
        for x in self.get_all():
            if int(x.id) == int(id):
                return True
        return False

    def delete_by_id(self, entity_id):
        """
        Delete book by id
        :param entity_id: wanted id of the book to be deleted
        """
        for book in self.get_all():
            if int(book.id) == int(entity_id):
                self.__entities.remove(book)

    def add(self, entity):
        """
        adds a new book to the repo
        :param entity: book object to be added
        """
        self.__entities.append(entity)

    def update(self, entity_id, new_entity):
        """
        replaces the old book with a new one
        :param entity_id: id of the book we want to update
        :param new_entity: the new book data
        """
        self.__entities[entity_id] = new_entity

    def get_all(self):
        """
        :return: all existing books
        """
        return self.__entities[:]

    def find_by_id(self, entity_id):
        """
        Finds book with given id
        :param entity_id: id of the wanted book
        :return: book with the given id
        """
        for entity in self.__entities.get_list():
            if entity_id == entity.id:
                print(entity)
                return entity
        return None


class ClientRepository:
    def __init__(self):
        self.__entities = IterableDataStructure()

    def duplicate_id(self, id):
        """
            Checks if the id already exists
            :param id: wanted it to be verified
            :return: True if it already existing else False
        """
        for x in self.get_all():
            if int(x.id) == int(id):
                return True
        return False

    def delete_by_id(self, entity_id):
        """
            Delete client by id
            :param entity_id: wanted id of the client to be deleted
        """
        for client in self.get_all():
            if int(client.id) == int(entity_id):
                self.__entities.remove(client)

    def add(self, entity):
        """
            adds a new client to the repo
            :param entity: client object to be added
        """
        self.__entities.append(entity)

    def update(self, entity_id, new_entity):
        """
                replaces the old client data with a new one
                :param entity_id: id of the client we want to update
                :param new_entity: the new client data
        """
        self.__entities[entity_id] = new_entity

    def get_all(self):
        """
            :return: all existing clients
        """
        return self.__entities[:]

    def find_by_id(self, entity_id):
        """
            Finds client with given id
            :param entity_id: id of the wanted client
            :return: client with the given id
        """
        for entity in self.__entities.get_list():
            if entity_id == entity.id:
                return entity
        return None


class RentalRepository:
    def __init__(self):
        self.__entities = IterableDataStructure()

    def duplicate_id(self, id):
        """
            Checks if the id already exists
            :param id: wanted it to be verified
            :return: True if it already existing else False
        """
        for x in self.get_all():
            if int(x.id) == int(id):
                return True
        return False

    def delete_by_id(self, entity_id):
        """
            Delete rental by id
            :param entity_id: wanted id of the rental to be deleted
        """
        for rental in self.get_all():
            if int(rental.id) == int(entity_id):
                self.__entities.remove(rental)

    def add(self, entity):
        """
            adds a new rental to the repo
            :param entity: rental object to be added
        """
        self.__entities.append(entity)

    def get_all(self):
        """
            :return: all existing rentals
        """
        return self.__entities[:]

    def find_by_id(self, entity_id):
        """
        Finds rent with given id
        :param entity_id: id of the wanted book
        :return: rent with the given id
        """
        for entity in self.__entities.get_list():
            if entity_id == entity.id:
                return entity
        return None
