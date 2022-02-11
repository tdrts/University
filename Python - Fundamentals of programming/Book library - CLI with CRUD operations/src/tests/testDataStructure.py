import unittest
from src.IterableDataStructure import *


class testStructure(unittest.TestCase):

    def setUp(self):
        self.structure = IterableDataStructure()

    def test_append(self):
        self.structure.append('abc')
        self.assertEqual(self.structure.__getitem__(0), 'abc')

    def test_len(self):
        self.structure.append('a')
        self.structure.append('b')
        self.structure.append('c')
        self.assertTrue(self.structure.__len__() == 3)

    def test_iter_next(self):
        self.structure.append('a')
        #self.assertRaises(IndexError, self.structure.__next__)

        self.structure.append('a')
        self.structure.append('b')

        self.structure.__next__()
        self.structure.__iter__()

        for x in range(2):
            self.assertEqual(self.structure.__getitem__(x), 'a')

    def test_getlist(self):
        self.structure.append('a')
        self.structure.append('b')
        self.assertEqual(self.structure.get_list(), ['a', 'b'])

    def test_remove(self):
        self.structure.append('a')
        self.structure.append('b')
        self.structure.remove('b')
        self.assertEqual(self.structure.get_list(), ['a'])

    def test_set(self):
        self.structure.append('a')
        self.structure.append('b')
        self.structure.__setitem__(0, 'b')
        self.assertEqual(self.structure.get_list(), ['b', 'b'])


class test_shell_sort(unittest.TestCase):

    def setUp(self):
        self.mylist = [1, 3, 2, 4, 6, 5]

    def sort_function1(self, number1, number2):
        return number1 >= number2

    def sort_function2(self, number1, number2):
        return number1 <= number2

    def testShellSort_MyList_SortedList(self):
        shellSort(self.mylist, self.sort_function1)
        self.assertEqual(self.mylist, [1, 2, 3, 4, 5, 6])

        shellSort(self.mylist, self.sort_function2)
        self.assertEqual(self.mylist, [6, 5, 4, 3, 2, 1])


class test_filter(unittest.TestCase):

    def setUp(self):
        self.my_list = [1, 0, 4, 0]

    def filter_function(self, number):
        return number != 0

    def testFilter_MyList_FilteredList(self):
        new_list = filter(self.my_list, self.filter_function)
        self.assertEqual(new_list, [1, 4])