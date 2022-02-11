from src.ui.console import *
from src.service.service import Library

books_repo = BookRepository()
clients_repo = ClientRepository()
rentals_repo = RentalRepository()

library = Library(books_repo, clients_repo, rentals_repo)

console = Console()

console.run()
