class InputError(Exception):
    pass


class Validations:

    def column_validation(self, column):
        """
        check if given column is in range of the board
        :param column: given column
        :return: error if column not in range
        """
        error = ''

        column = int(column)
        if (column < 1) or (column > 7):
            error += 'Column out of range!'

        if len(error) > 0:
            raise InputError(error)





