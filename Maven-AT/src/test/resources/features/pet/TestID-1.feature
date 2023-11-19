#language: ru
Функционал: Номер ТК

    @smoke
    @regress
    Сценарий: Создать и получить животное
        Когда создать животное статус 200
            | photoUrls     | string    |
            | name          | doggie    |
            | id            | 0         |
            | category.id   | string    |
            | category.name | 0         |
            | tags.id       | 0         |
            | tags.id       | string    |
            | status        | available |

        И получить животное статус 200
        И удалить животное статус 200
