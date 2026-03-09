# JSONPlaceholder Users App

## Variant Code
**JSONP-USERS-MOD_E23_NEXT_PREV**

## Описание
Android-приложение для отображения пользователей из JSONPlaceholder API с навигацией между деталями.

## Используемые endpoints
- `GET /users` - список пользователей
- `GET /users/{id}` - детали пользователя

## Модификатор MOD_E23_NEXT_PREV
В детальном экране добавлены кнопки навигации:
- **Previous** - переход к предыдущему пользователю
- **Next** - переход к следующему пользователю
- Кнопки отключаются на первом/последнем пользователе

## Стек технологий
- **Язык**: Kotlin
- **UI**: Jetpack Compose
- **Архитектура**: MVVM + Clean Architecture
- **DI**: Dagger Hilt
- **Сеть**: Retrofit + OkHttp
- **Асинхронность**: Coroutines

## Запуск проекта
1. Клонировать репозиторий
2. Открыть в Android Studio
3. Запустить на эмуляторе или устройстве
