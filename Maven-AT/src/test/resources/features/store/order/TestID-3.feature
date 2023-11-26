#language: ru
Функционал: Тестирование методов store

    @smoke
    @regress
    Сценарий: Создать заказ
        Когда создать новый заказ статус 200
            | id       | 0                        |
            | petId    | 0                        |
            | quantity | 0                        |
            | shipDate | 2023-09-11T09:37:57.828Z |
            | status   | placed                   |
            | complete | true                     |
