package by.jawh.emailmicro.consts;


public class MessageTemplates {

    public static final String REGISTRATION_MESSAGE = """
            Поздравляем %s %s. Вы успешно зарегестрировались в сервисе TwittApp под именем пользователя: %s
            """;

    public static final String LOGIN_MESSAGE = """
            Поздравляем %s %s. Вы успешно вошли в аккаунт сервиса TwittApp под именем пользователя: %s
            """;

    public static final String REGISTRATION_SUBJECT = "Вы зарегестрировались";

    public static final String LOGIN_SUBJECT = "Вы вошли в аккаунт";

}
