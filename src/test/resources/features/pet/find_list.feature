# language: en
@pet
Feature: Find all pets
  User wants to retrieve the list of pets

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

  Scenario Outline: Retrieve a pet list successfully
    Given the database is "online"
    When the user request pets of the customer '<customerId>':
      | page | <page>          |
      | size | <size>          |
      | name | <nameSubstring> |
    Then the service will return 200 status code
    And the service will reply this list of pets: '<response>'
    Examples:
      | customerId                           | page | size | nameSubstring | response                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
      | bf01879f-3a17-4b57-95ea-0881a58f7c44 | 1    | 20   |               | {"page":1,"pageSize":20,"total":2,"items":[{"id":"1caf959e-6b52-48f6-b049-e2e6f261b8d3","name":"Toto","type":"DOG","observation":"","birthdate":"2018-07-13T00:00:00","owner":{"id":"bf01879f-3a17-4b57-95ea-0881a58f7c44","name":"Karen Carmo","phone":"11994313242","email":"karen.carmo@gmail.com"},"status":"ACTIVE","creationDate":"2020-11-10T19:33:10-02:00","lastUpdate":"2020-11-10T19:33:10-02:00"},{"id":"2caf959e-6b52-48f6-b049-e2e6f261b8d2","name":"Fifi","type":"CAT","observation":"","birthdate":"2019-07-29T00:00:00","owner":{"id":"e1d40af7-c41d-4dc5-bf70-1da46d328f92","name":"Mariana Carmo","phone":"11992345678","email":"mariana.carmo@gmail.com"},"status":"ACTIVE","creationDate":"2020-11-10T19:33:10-02:00","lastUpdate":"2020-11-10T19:33:10-02:00"},{"id":"3caf959e-6b52-48f6-b049-e2e6f261b8d1","name":"Rex","type":"DOG","observation":"","birthdate":"2010-07-01T00:00:00","owner":{"id":"684e5119-6c38-4b20-9122-00faa9c8b413","name":"Vitor Carmo","phone":"1192345678","email":"vitor.carmo@petz.com"},"status":"ACTIVE","creationDate":"2020-11-10T19:33:10-02:00","lastUpdate":"2020-11-10T19:33:10-02:00"}]} |
      | e1d40af7-c41d-4dc5-bf70-1da46d328f92 | 1    | 20   | Rex           | {"page":1,"pageSize":20,"total":2,"items":[{"id":"3caf959e-6b52-48f6-b049-e2e6f261b8d1","name":"Rex","type":"DOG","observation":"","birthdate":"2010-07-01T00:00:00","owner":{"id":"684e5119-6c38-4b20-9122-00faa9c8b413","name":"Vitor Carmo","phone":"1192345678","email":"vitor.carmo@petz.com"},"status":"ACTIVE","creationDate":"2020-11-10T19:33:10-02:00","lastUpdate":"2020-11-10T19:33:10-02:00"}]}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
#      | 684e5119-6c38-4b20-9122-00faa9c8b413 | 1    | 20   | Zeus          | {"page":1,"pageSize":20,"total":0,"items":[]}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |

  Scenario: Try to retrieve a pet list when the database is offline
    Given the database is "offline"
    When the user request pets of the customer '684e5119-6c38-4b20-9122-00faa9c8b413':
      | page | 1  |
      | size | 20 |
    Then the service will return 500 status code