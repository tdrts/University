
import unittest
from service.board import *
from service.service import *
from validations import *
from console.console import *


class TestBoard(unittest.TestCase):

    def testGetWon_NewBoard_False(self):
        board = Board()
        self.assertEqual(board.get_won(), False)

    def testGetWon_GameFinished_True(self):
        board = Board()
        board.set_won(True)
        self.assertEqual(board.get_won(), True)

    def testGetWinner_GameFinished_Winner(self):
        board = Board()
        board.set_winner('X')
        self.assertEqual(board.get_winner(), 'X')

    def testIfWon_GameFinished_Winner(self):
        board = Board()
        board.set_board(1, 1, 'X')
        board.set_board(1, 2, 'X')
        board.set_board(1, 3, 'X')
        board.set_board(1, 4, 'X')
        board.if_won()
        self.assertEqual(board.get_winner(), 'X')

    def testIfWon_GameNotFinished_NotWon(self):
        board = Board()
        board.set_board(1, 1, 'X')
        board.set_board(1, 2, '0')
        board.set_board(1, 3, 'X')
        board.set_board(1, 4, 'X')
        board.if_won()
        self.assertEqual(board.get_won(), False)

    def testIfWon_GameFinished_Won(self):
        board = Board()
        board.set_board(5, 1, 'X')
        board.set_board(5, 2, 'X')
        board.set_board(5, 3, 'X')
        board.set_board(5, 4, 'X')
        board.if_won()
        self.assertEqual(board.get_won(), True)


class TestValidations(unittest.TestCase):

    def testColumnValidation_InvalidInput_ErrorMessage(self):
        test_validations = Validations()
        column = 8
        try:
            test_validations.column_validation(column)
            assert (False)
        except InputError as error:
            self.assertEqual(str(error), 'Column out of range!')

    def testColumnValidation_InvalidInput_ErrorMessageException(self):
        test_validations = Validations()
        column = 8
        with self.assertRaises(InputError) as error:
            test_validations.column_validation(column)
        self.assertEqual('Column out of range!', str(error.exception))


if __name__ == "__main__":
    unittest.main()
