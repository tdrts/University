import unittest

class IterableDataStructure:
    def __init__(self):
        self.__index = 0
        self.__list = []

    def __iter__(self):
        return iter(self.__list)

    def __next__(self):
        if self.__index > len(self.__list)+1:
            raise StopIteration
        else:
            self.__index += 1
        return self.__list[self.__index]

    def __len__(self):
        return len(self.__list)

    def __getitem__(self, index):
        return self.__list[index]

    def __setitem__(self, index, value):
        self.__list[index] = value

    def append(self, element):
        self.__list.append(element)

    def remove(self, item):
            self.__list.remove(item)

    def get_list(self):
        return self.__list[:]


def shellSort(given_list, function):
    """
    we start with a gap, and after each iteration we reduce the gap
    """
    length = len(given_list)
    gap = length // 2
    while gap > 0:
        for position in range(gap, length):
            element = given_list[position]
            temporary_position = position
            while temporary_position >= gap and function(given_list[temporary_position - gap], element):
                given_list[temporary_position] = given_list[temporary_position - gap]
                temporary_position = temporary_position - gap
            given_list[temporary_position] = element
        gap = gap // 2


def filter(list, function):
    new_list = []
    for index in range(len(list)):
        if function(list[index]):
            new_list.append(list[index])
    return new_list[:]


