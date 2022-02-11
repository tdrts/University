from src.console.console import Console
from src.service.board import Board
from src.validations.validations import Validations
from src.service.service import Service

board = Board()
validations = Validations()
service = Service(board, validations)
console = Console(service)

console.run()
