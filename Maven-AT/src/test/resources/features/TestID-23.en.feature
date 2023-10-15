#language: en
Feature: [en] Номер ТК
    @smoke
    @regress
    Scenario: Получить животное по id
        When get pet with id "10" status 200
