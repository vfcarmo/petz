# language: en
@pet
Feature: Delete a pet to a customer
  User wants to delete a pet to a customer

  Background:
    Given database contains customers as:
      | id                                   | name        | cpf         | phone       | email                   | status | creationDate        | lastUpdate          |
      | 684e5119-6c38-4b20-9122-00faa9c8b413 | Vitor Carmo | 75837225781 | 1192345678  | vitor.carmo@petz.com    | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 | Karen       | 37856321504 | 11994313242 | karen.carmo@gmail.com   | ACTIVE | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Mariana     | 56122110906 | 11992345678 | mariana.carmo@gmail.com | ACTIVE | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
    And database contains pets as:
      | id                                   | name | type | observation | birthdate           | ownerId                              | ownerName     | ownerPhone  | ownerEmail              | status | creationDate        | lastUpdate          |
      | 1caf959e-6b52-48f6-b049-e2e6f261b8d3 | Toto | DOG  |             | 2018-07-13T00:00:00 | bf01879f-3a17-4b57-95ea-0881a58f7c44 | Karen Carmo   | 11994313242 | karen.carmo@gmail.com   | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |
      | 2caf959e-6b52-48f6-b049-e2e6f261b8d2 | Fifi | CAT  |             | 2019-07-29T00:00:00 | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Mariana Carmo | 11992345678 | mariana.carmo@gmail.com | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |
      | 3caf959e-6b52-48f6-b049-e2e6f261b8d1 | Rex  | DOG  |             | 2010-07-01T00:00:00 | 684e5119-6c38-4b20-9122-00faa9c8b413 | Vitor Carmo   | 1192345678  | vitor.carmo@petz.com    | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |

  Scenario Outline: Delete a pet from a customer successfully
    Given the database is "online"
    When the user request to deletion of the pet '<id>' of the customer '<ownerId>'
    Then the service will return 204 status code
    Examples:
      | id                                   | ownerId                              |
      | 2caf959e-6b52-48f6-b049-e2e6f261b8d2 | e1d40af7-c41d-4dc5-bf70-1da46d328f92 |
      | 3caf959e-6b52-48f6-b049-e2e6f261b8d1 | 684e5119-6c38-4b20-9122-00faa9c8b413 |

  Scenario Outline: Try to delete a pet without owning it
    Given the database is "online"
    When the user request to deletion of the pet '<id>' of the customer '<customerId>'
    Then the service will return 400 status code
    Examples:
      | id                                   | customerId                           |
      | 1caf959e-6b52-48f6-b049-e2e6f261b8d3 | e1d40af7-c41d-4dc5-bf70-1da46d328f92 |
      | 2caf959e-6b52-48f6-b049-e2e6f261b8d2 | 684e5119-6c38-4b20-9122-00faa9c8b413 |
      | 3caf959e-6b52-48f6-b049-e2e6f261b8d1 | bf01879f-3a17-4b57-95ea-0881a58f7c44 |

  Scenario: Try to delete a pet from a nonexistent customer
    Given the database is "online"
    When the user request to deletion of the pet '1caf959e-6b52-48f6-b049-e2e6f261b8d3' of the customer '555e5119-1c32-4b20-9122-00faa9c8b222'
    Then the service will return 404 status code

  Scenario: Try to delete a pet from a customer when the database is offline
    Given the database is "offline"
    When the user request to deletion of the pet '1caf959e-6b52-48f6-b049-e2e6f261b8d3' of the customer 'bf01879f-3a17-4b57-95ea-0881a58f7c44'
    Then the service will return 500 status code