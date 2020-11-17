# language: en
@pet
Feature: Add a pet to a customer
  User wants to add a new pet to a customer

  Background:
    Given database contains customers as:
      | id                                   | name        | cpf         | phone       | email                   | status | creationDate        | lastUpdate          |
      | 684e5119-6c38-4b20-9122-00faa9c8b413 | Vitor Carmo | 75837225781 | 1192345678  | vitor.carmo@petz.com    | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 | Karen       | 37856321504 | 11994313242 | karen.carmo@gmail.com   | ACTIVE | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Mariana     | 56122110906 | 11992345678 | mariana.carmo@gmail.com | ACTIVE | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
    And database contains pets as:
      | id                                   | name | type | observation | birthdate           | ownerId                              | ownerName   | ownerPhone  | ownerEmail            | status | creationDate        | lastUpdate          |
      | 1caf959e-6b52-48f6-b049-e2e6f261b8d3 | Toto | DOG  |             | 2018-07-13T00:00:00 | bf01879f-3a17-4b57-95ea-0881a58f7c44 | Karen Carmo | 11994313242 | karen.carmo@gmail.com | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |

  Scenario Outline: Add a new pet to a customer successfully
    Given the next pet creation data is:
      | nextId | <id>                      |
      | now    | 12/11/2020 - 10:00:12 UTC |
    Given the database is "online"
    When the user request to add a pet to customer '<ownerId>' as follows:
      | name        | <name>        |
      | type        | <type>        |
      | observation | <observation> |
      | birthdate   | <birthdate>   |
    Then the service will return 201 status code
    Examples:
      | id                                   | name | type | observation | birthdate           | ownerId                              |
      | 2caf959e-6b52-48f6-b049-e2e6f261b8d2 | Fifi | CAT  |             | 2019-07-29T00:00:00 | e1d40af7-c41d-4dc5-bf70-1da46d328f92 |
      | 3caf959e-6b52-48f6-b049-e2e6f261b8d1 | Rex  | DOG  | crazy dog   | 2010-07-01T00:00:00 | 684e5119-6c38-4b20-9122-00faa9c8b413 |

  Scenario Outline: Try to add a new pet to a customer with bad request
    Given the database is "online"
    When the user request to add a pet to customer '<ownerId>' as follows:
      | name        | <name>        |
      | type        | <type>        |
      | observation | <observation> |
      | birthdate   | <birthdate>   |
    Then the service will return 400 status code
    Examples:
      | name | type | observation | birthdate           | ownerId                              |
      |      | CAT  |             | 2019-07-29T00:00:00 | e1d40af7-c41d-4dc5-bf70-1da46d328f92 |
      | Rex  | DOG  | crazy dog   |                     | 684e5119-6c38-4b20-9122-00faa9c8b413 |

  Scenario: Try to add a new pet to a nonexistent customer
    Given the database is "online"
    When the user request to add a pet to customer '555e5119-1c32-4b20-9122-00faa9c8b222' as follows:
      | name      | Fifi                |
      | type      | CAT                 |
      | birthdate | 2019-07-29T00:00:00 |
    Then the service will return 404 status code

  Scenario: Try to add a new pet to a customer when the database is offline
    Given the database is "offline"
    When the user request to add a pet to customer '684e5119-6c38-4b20-9122-00faa9c8b413' as follows:
      | name      | Fifi                |
      | type      | CAT                 |
      | birthdate | 2019-07-29T00:00:00 |
    Then the service will return 500 status code