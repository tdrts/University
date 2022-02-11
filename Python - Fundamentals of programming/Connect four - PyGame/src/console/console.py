from src.validations.validations import InputError, Validations
from src.service.board import FinishedGame
from validations import validations


class Console:

    def __init__(self, service):
        self.__service = service

    def get_board(self):
        return self.__service.get_board()

    def print_board(self):
        for line in range(6):
            for column in range(7):
                print(self.get_board()[line][column], end=' ')
            print()
        print()

    def read_number(self):
        """
        read column number
        :return: number between 1-7 or value error if invalid type
        """
        number = input('Enter column:')
        while True:
            try:
                number = int(number)
                return number
            except ValueError:
                number = input('Invalid numerical type! Try again:')

    def piece(self):
        """
        executing a turn by the user
        """
        column = self.read_number()
        self.__service.piece(column, 'user')

    def computer_turn(self):
        """
        executing a turn by the computer
        """
        self.__service.piece('', 'computer')

    def run(self):
        self.print_board()
        while not self.__service.get_won():
            try:
                self.piece()
                self.print_board()
                if not self.__service.get_won():
                    self.computer_turn()
                    self.print_board()

            except FinishedGame as gameended:
                print(gameended)
                return
            except ValueError as valuerror:
                print(valuerror)
            except validations.InputError as inputerror:
                print(inputerror)
            except InputError as inputerror:
                print(inputerror)

        print(self.__service.get_winner() + ' won!')

