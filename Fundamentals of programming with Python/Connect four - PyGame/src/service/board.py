from validations.validations import InputError


class FinishedGame(Exception):
    pass


class Board:
    def __init__(self):
        self.__board = [['_' for column in range(7)] for line in range(6)]
        self.__free_space = [5] * 7
        self.__players = {'user': 'X',
                          'computer': '0'}
        self.__finished = False
        self.__winner = None

    def get_won(self):
        """
        :return: the status of the game either true if game is finished or false otherwise
        """
        return self.__finished

    def get_winner(self):
        """
        :return: the winner of the game meaning either the computer or the player
        """
        return self.__winner

    def set_won(self, who_won):
        self.__finished = who_won

    def set_winner(self, player):
        self.__winner = player

    def set_board(self, line, column, player_choice):
        self.__board[line][column] = player_choice

    def get_board(self):
        return self.__board

    def get_free_space(self):
        return self.__free_space

    def get_free_space_column(self, column):
        return self.__free_space[column]

    def three_on_column(self):
        """
        checks if there are 3 consecutive pieces on a column and if the above space is free
        :return: None if there are no such pieces
                column number where to put the piece to block the player
        """
        for column in range(7):
            for line in range(4):
                if self.__board[line][column] == self.__board[line+1][column]:
                    if self.__board[line+1][column] == self.__board[line+2][column]:
                        if self.__board[line][column] != '_':
                            if self.get_free_space_column(column) == line-1:
                                return column
        return None

    def three_on_second_diagonal(self):
        """
        checks if there are 3 consecutive pieces on the second diagonal and if the above/ below space is free
        :return: None if there are no such pieces
                column number where to put the piece to block the player
        """
        for line in range(3):
            for column in range(4):
                if self.__board[line+1][column+1] == 'X':
                    if self.__board[line+1][column+1] == self.__board[line+2][column+2]:
                        if self.__board[line+2][column+2] == self.__board[line+3][column+3]:
                            if self.get_free_space_column(column) == line:
                                return column
        for line in range(3):
            for column in range(4):
                if self.__board[line][column] == 'X':
                    if self.__board[line][column] == self.__board[line+1][column+1]:
                        if self.__board[line+1][column+1] == self.__board[line+2][column+2]:
                            if self.get_free_space_column(column+3) == line+3:
                                return column+3
        return None

    def three_on_first_diagonal(self):
        """
        checks if there are 3 consecutive pieces on the first diagonal and if the above/ below space is free
        :return: None if there are no such pieces
                column number where to put the piece to block the player
        """
        for line in range(3):
            for column in range(3, 7):
                if self.__board[line+1][column-1] == 'X':
                    if self.__board[line+1][column-1] == self.__board[line+2][column-2]:
                        if self.__board[line+2][column-2] == self.__board[line+3][column-3]:
                            if self.get_free_space_column(column) == line:
                                return column
        for line in range(3):
            for column in range(3, 7):
                if self.__board[line][column] == 'X':
                    if self.__board[line][column] == self.__board[line+1][column-1]:
                        if self.__board[line+1][column-1] == self.__board[line+2][column-2]:
                            if self.get_free_space_column(column-3) == line+3:
                                return column-3
        return None

    def consecutive2_line(self):
        """
        checks if there are 2 consecutive pieces on a line and if the above space is free
        :return: None if there are no such pieces
                column number where to put the piece to block the player
        """
        for line in range(6):
            for column in range(6):
                if self.__board[line][column] == self.__board[line][column + 1] and self.__board[line][column + 1] != '_':
                    if column == 0 and (self.get_free_space_column(column + 2) == line):
                        return column + 2
                    elif (column != 0) and (self.get_free_space_column(column - 1) == line):
                        return column - 1
                    elif (column == 5) and (self.get_free_space_column(column - 1) == line):
                        return column + 3
                    elif (column != 5) and self.get_free_space_column(column + 2) == line:
                        return column + 2
        return None

    def consecutive2_column(self):
        """
        checks if there are 2 consecutive pieces on a column and if the above space is free
        :return: None if there are no such pieces
                column number where to put the piece to block the player
        """
        for column in range(7):
            for line in range(4):
                if self.__board[line][column] == self.__board[line + 1][column] and self.__board[line+2][column] == '_':
                    if self.get_free_space_column(column) > 0:
                        return column
        return None

    def choose_column(self):
        """
        choosing where to place the new piece in order to block the user
        :return: column on which to move
        """
        column = 0
        while self.get_free_space_column(column) == -1:
            column = column+1
        if self.three_on_column() is not None:
            return self.three_on_column()
        elif self.three_on_first_diagonal() is not None:
            return self.three_on_first_diagonal()
        elif self.three_on_second_diagonal() is not None:
            return self.three_on_second_diagonal()
        elif self.consecutive2_line() is not None:
            return self.consecutive2_line()
        elif self.consecutive2_column() is not None:
            return self.consecutive2_column()
        return column

    def if_won(self):
        """
        Checks if the game is finished by verifying all combinations on lines, columns, and diagonals
        If no player is winner, then it is a draw
        """
        for line in range(3):
            for column in range(7):
                if self.__board[line][column] != '_':
                    if self.__board[line][column] == self.__board[line + 1][column] and self.__board[line + 1][column] == self.__board[line + 2][column] and self.__board[line + 2][column] == \
                            self.__board[line + 3][column]:
                        winner = self.__board[line][column]
                        self.set_won(True)
                        self.set_winner(winner)
                        return

        for column in range(4):
            for line in range(6):
                if self.__board[line][column] != '_':
                    if self.__board[line][column] == self.__board[line][column + 1] and self.__board[line][column + 1] == self.__board[line][column + 2] and self.__board[line][column + 2] == \
                            self.__board[line][column + 3]:
                        winner = self.__board[line][column]
                        self.set_won(True)
                        self.set_winner(winner)
                        return

        for line in range(3):
            for column in range(4):
                if self.__board[line][column] != '_':
                    if self.__board[line][column] == self.__board[line + 1][column + 1] and self.__board[line + 1][column + 1] == self.__board[line + 2][column + 2]:
                        if self.__board[line + 2][column + 2] == self.__board[line + 3][column + 3]:
                            winner = self.__board[line][column]
                            self.set_won(True)
                            self.set_winner(winner)
                            return

        for line in range(4, 6):
            for column in range(4):
                if self.__board[line][column] != '_':
                    if self.__board[line][column] == self.__board[line - 1][column + 1]:
                        if self.__board[line - 1][column + 1] == self.__board[line - 2][column + 2]:
                            if self.__board[line - 2][column + 2] == self.__board[line - 3][column + 3]:
                                winner = self.__board[line][column]
                                self.set_won(True)
                                self.set_winner(winner)
                                return

        draw = True
        for line in range(6):
            for column in range(7):
                if self.__board[line][column] == '_':
                    draw = False
                    return

        if draw:
            raise FinishedGame('It is a draw!')

    def piece(self, column, player):
        """
        places a piece
        checks if the game ended after every piece placed
        :param column: the column where the piece should be placed. column number for player and empty space for computer
        :param player: 'user'/'computer')
        :return: places the piece and shows status of the game if needed
        """
        if not self.__finished:
            if player == 'user':
                if int(self.get_free_space_column(column)) >= 0:
                    if self.__board[self.get_free_space_column(column)][column] == '_':
                        self.set_board(self.get_free_space_column(column), column, self.__players[player])
                        self.__free_space[column] = self.__free_space[column]-1
                else:
                    raise InputError("Column is full!")
            elif player == 'computer':
                while True:
                    column = self.choose_column()
                    if self.__board[self.get_free_space_column(column)][column] == '_':
                        self.set_board(self.get_free_space_column(column), column, self.__players[player])
                        self.__free_space[column] = self.__free_space[column]-1
                        self.if_won()
                        return
            self.if_won()
        elif self.__finished:
            raise FinishedGame(self.__winner+' won!')


