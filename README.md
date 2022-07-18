# MyMessenger
____
/app/src/main/java/com/example/telecopy/ - Путь к основному коду
____
папка **activities** - Содержит все активности
____
папка **models** - содержит все модели приложения
+ **User** - Используется для чтения и записи всех атрибутов пользователя
+ **CommonModel** - Вспомогательная модель для основного котента внутри приложения(отображения сообщений, контактов)
____
папка **ui** - Пользовательский интерфейс
+ папка **object** - Содержит обьект **AppDrawer**
+ + **AppDrawer** - Используется для отрисовки основных компонентов меню и переключения между ними
+ папка **fragments** - Содержит все фрагменты приложения
+ + **BaseFragment**, **BaseChangeFragment*** - Базовые фрагменты
+ + **ChatFragment** - Фрагмент, отображающий чаты в которых переписывается пользователь
+ + **EnterPhoneNumberFragment**, **EnterCodeFragment** - Фрагменты регистрации пользователя
+ + **ChangeInfoFragment**, **ChangeNameFragment**, **ChangeUsernameFragment** - Фрагменты для изменнения данных пользователя
+ + **ContactsFragment** - Фрагмент, отображающий телефонные контакты пользователя
+ + **SettingsFragment** - Фрагмент настроек
+ + папка **single_chat** - Папка содержащая фрагменты одиночного чата
+ + + **SingleChatAdapter** - Фрагмент, отображающий сообщения в окне диалога
+ + + **SingleChatFragment** - Фрагмент, отбражающий окно диалога
____
папка **utilits** - Содержит вспомогательные файлы
+ **AppState** - ENUM класс для отображения статуса пользователя
+ **funs** - Глобальные функции приложения
+ **firebaseHelper** - Файл для работы с базой данных
+ **appPermissions** - Файл для проверки разрешения на чтение контактов пользователя
+ **AppTextWatcher**, **AppValueEventListener**  - Классы расширяющие программные интерфейсы
