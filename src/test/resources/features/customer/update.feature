# language: en
@customer
Feature: Update a Customer
  User wants to update a customer

  Background:
    Given database contains customers as:
      | id                                   | name        | cpf         | phone       | email                   | status   | creationDate        | lastUpdate          |
      | 684e5119-6c38-4b20-9122-00faa9c8b413 | Vitor Carmo | 75837225781 | 1192345678  | vitor.carmo@petz.com    | INACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 | Karen       | 37856321504 | 11994313242 | karen.carmo@gmail.com   | ACTIVE   | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Mariana     | 56122110906 | 11992345678 | mariana.carmo@gmail.com | ACTIVE   | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
    And database contains pets as:
      | id                                   | name | type | observation | birthdate           | ownerId                              | status | creationDate        | lastUpdate          |
      | 1caf959e-6b52-48f6-b049-e2e6f261b8d6 | Toto | DOG  |             | 2018-07-13T00:00:00 | 684e5119-6c38-4b20-9122-00faa9c8b413 | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |

  Scenario Outline: Update successfully a customer
    Given the database is "online"
    When the user request an update of the customer '<id>' as follows:
      | name  | <name>  |
      | phone | <phone> |
      | email | <email> |
    Then the service will return 204 status code
    Examples:
      | id                                   | name          | phone       | email                   |
      | 684e5119-6c38-4b20-9122-00faa9c8b413 | Vitor Carmo   | 1192345678  | vitor_carmo@gmail.com   |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 | Karen Carmo   | 11994313242 | karen_carmo@gmail.com   |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Mariana Carmo | 11999999999 | mariana_carmo@gmail.com |


  Scenario Outline: Try to update a customer with bad request
    Given the database is "online"
    When the user request an update of the customer '<id>' as follows:
      | name  | <name>  |
      | phone | <phone> |
      | email | <email> |
    Then the service will return 400 status code
    Examples:
      | id                                   | name    | phone       | email                  |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 |         | 11994313242 | karen.carmo@gmail.com  |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Karen   | 11992345678 | karen.carmogmail.com   |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Karen   | 992345678   | mariana.carmogmail.com |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Mariana | 11992345678 | mariana.carmogmail.com |

  Scenario: Try to update a customer with nonexistent customer id
    Given the database is "online"
    When the user request an update of the customer '124e5119-6c38-4b20-9122-00faa9c8b201' as follows:
      | name  | Rebeka                 |
      | phone | 1199123456             |
      | email | rebeka.carmo@gmail.com |
    Then the service will return 404 status code

  Scenario: Try to update a customer when the database is offline
    Given the database is "offline"
    When the user request an update of the customer '684e5119-6c38-4b20-9122-00faa9c8b413' as follows:
      | name  | Vitor                 |
      | phone | 1192345678            |
      | email | vitor.carmo@gmail.com |
    Then the service will return 500 status code