#language: ru
Функционал: Номер ТК

    @smoke
    @regress
    Сценарий: Создать и получить животное
        Когда создать животное статус 200
            | photoUrls | string    |   |
            | name      | doggie    |   |
            | id        | 0         |   |
            | category  | string    | 0 |
            | tags      | string    | 0 |
            | status    | available |   |
        Тогда получить животное статус 200
