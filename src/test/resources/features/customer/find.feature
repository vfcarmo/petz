# language: en
@customer
Feature: Find a customer by id
  User wants to retrieve details about a customer

  Background:
    Given database contains customers as:
      | id                                   | name        | cpf         | phone       | email                   | status | creationDate        | lastUpdate          |
      | 684e5119-6c38-4b20-9122-00faa9c8b413 | Vitor Carmo | 75837225781 | 1192345678  | vitor.carmo@petz.com    | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 | Karen       | 37856321504 | 11994313242 | karen.carmo@gmail.com   | ACTIVE | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | Mariana     | 56122110906 | 11992345678 | mariana.carmo@gmail.com | ACTIVE | 2020-12-10T18:33:10 | 2020-12-10T18:33:10 |
    And database contains pets as:
      | id                                   | name | type | observation | birthdate           | ownerId                              | status | creationDate        | lastUpdate          |
      | 1caf959e-6b52-48f6-b049-e2e6f261b8d6 | Toto | DOG  |             | 2000-07-13T00:00:00 | 684e5119-6c38-4b20-9122-00faa9c8b413 | ACTIVE | 2020-11-10T18:33:10 | 2020-11-10T18:33:10 |

  Scenario Outline: Retrieve a customer successfully
    Given the database is "online"
    When the user request details about the customer '<customerId>'
    Then the service will return 200 status code
    And the service will reply this customer: '<response>'
    Examples:
      | customerId                           | response                                                                                                                                                                                                                                         |
      | 684e5119-6c38-4b20-9122-00faa9c8b413 | {"id":"684e5119-6c38-4b20-9122-00faa9c8b413","name":"Vitor Carmo","cpf":"75837225781","phone":"1192345678","email":"vitor.carmo@petz.com","status":"ACTIVE","creationDate":"2020-11-10T19:33:10-02:00","lastUpdate":"2020-11-10T19:33:10-02:00"} |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 | {"id":"bf01879f-3a17-4b57-95ea-0881a58f7c44","name":"Karen","cpf":"37856321504","phone":"11994313242","email":"karen.carmo@gmail.com","status":"ACTIVE","creationDate":"2020-12-10T19:33:10-02:00","lastUpdate":"2020-12-10T19:33:10-02:00"}     |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | {"id":"e1d40af7-c41d-4dc5-bf70-1da46d328f92","name":"Mariana","cpf":"56122110906","phone":"11992345678","email":"mariana.carmo@gmail.com","status":"ACTIVE","creationDate":"2020-12-10T19:33:10-02:00","lastUpdate":"2020-12-10T19:33:10-02:00"} |

  Scenario: Try to retrieve a customer when the database is offline
    Given the database is "offline"
    When the user request details about the customer '<customerId>'
    Then the service will return 500 status code