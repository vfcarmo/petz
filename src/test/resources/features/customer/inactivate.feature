# language: en
@customer
Feature: Inactivate a customer by id
  User wants to inactivate a customer by id

  Background:
    Given database contains customers as:
      | id                                   | name        | cpf         | phone       | email                   | status   | creationDate        | lastUpdate          |
      | 684e5119-6c38-4b20-9122-00faa9c8b413 | Vitor Carmo | 75837225781 | 1192345678  | vitor.carmo@petz.com    | INACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 | Karen       | 37856321504 | 11994313242 | karen.carmo@gmail.com   | ACTIVE   | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Mariana     | 56122110906 | 11992345678 | mariana.carmo@gmail.com | ACTIVE   | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
    And database contains pets as:
      | id                                   | name | type | observation | birthdate           | ownerId                              | status | creationDate        | lastUpdate          |
      | 1caf959e-6b52-48f6-b049-e2e6f261b8d6 | Toto | DOG  |             | 2018-07-13T00:00:00 | 684e5119-6c38-4b20-9122-00faa9c8b413 | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |

  Scenario Outline: Inactivate a customer successfully
    Given the database is "online"
    When the user request inactivation of the customer '<customerId>'
    Then the service will return 204 status code
    Examples:
      | customerId                           |
      | 684e5119-6c38-4b20-9122-00faa9c8b413 |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 |

  Scenario Outline: Try to inactivate a customer with nonexistent customer id
    Given the database is "online"
    When the user request inactivation of the customer '<customerId>'
    Then the service will return 404 status code
    Examples:
      | customerId                           |
      | 0e17458c-5309-4156-823a-4c99e228f1c2 |
      | 3a567109-71e0-465d-83c4-0e6e2c93ca35 |

  Scenario: Try to inactivate a customer when the database is offline
    Given the database is "offline"
    When the user request inactivation of the customer '684e5119-6c38-4b20-9122-00faa9c8b413'
    Then the service will return 500 status code