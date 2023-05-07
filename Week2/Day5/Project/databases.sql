CREATE TABLE `books` (
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `genre` varchar(255) NOT NULL,
  `is_available` tinyint(1) NOT NULL
);

CREATE TABLE `borrowed_books` (
  `book_title` varchar(255) NOT NULL,
  `borrower_name` varchar(255) NOT NULL,
  `due_date` date NOT NULL
);

CREATE TABLE `borrowers` (
  `name` varchar(255) NOT NULL
);