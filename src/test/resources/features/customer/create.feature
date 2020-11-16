# language: en
@customer
Feature: Insert a Customer
  User wants to create a new customer

  Background:
    Given database contains customers as:
      | id                                   | name        | cpf         | phone      | email                | status | creationDate        | lastUpdate          |
      | 684e5119-6c38-4b20-9122-00faa9c8b413 | Vitor Carmo | 75837225781 | 1192345678 | vitor.carmo@petz.com | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |
    And database contains pets as:
      | id                                   | name | type | observation | birthdate           | ownerId                              | status | creationDate        | lastUpdate          |
      | 1caf959e-6b52-48f6-b049-e2e6f261b8d6 | Toto | DOG  |             | 2000-07-13T00:00:00 | 684e5119-6c38-4b20-9122-00faa9c8b413 | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |

  Scenario Outline: Create successfully a customer
    Given the next customer creation data is:
      | nextId | <id>                      |
      | now    | 12/11/2020 - 10:00:12 UTC |
    And the user prepare a request to create a customer with details as:
      | name  | <name>  |
      | cpf   | <cpf>   |
      | phone | <phone> |
      | email | <email> |
    And the database is "online"
    When the user request a creation to this customer
    Then the service will return 200 status code
    And the database is called to insert this customer:
      | id   | name   | cpf   | phone   | email   | status | creationDate        | lastUpdate          |
      | <id> | <name> | <cpf> | <phone> | <email> | ACTIVE | 2020-11-12T10:00:12 | 2020-11-12T10:00:12 |
    Examples:
      | id                                   | name    | cpf         | phone       | email                   |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 | Karen   | 37856321504 | 11994313242 | karen.kake@gmail.com    |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Mariana | 56122110906 | 11992345678 | mariana.carmo@gmail.com |