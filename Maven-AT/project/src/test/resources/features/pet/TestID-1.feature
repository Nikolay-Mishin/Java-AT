#language: ru
Функционал: Номер ТК

    @smoke
    @regress
    Сценарий: Создать и получить животное
        Когда создать животное статус 200
            | id            | 0         |
            | category.id   | 0         |
            | category.name | string    |
            | photoUrls     | string    |
            | name          | doggie    |
            | tags.id       | 0         |
            | tags.name     | string    |
            | status        | available |

        И получить животное статус 200
        И удалить животное статус 200
