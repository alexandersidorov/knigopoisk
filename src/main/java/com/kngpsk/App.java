package com.kngpsk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App{

    //FIXME Добавить возможность модератором блокировать пользователей
    //FIXME Хранить введенный пользователями системы текст в отдельных файлах, в базе хранить только путь к этим файлам
    //FIXME Сделать для модератора возможность добавления авторов и книг, а также сделать их персональные страницы
    //FIXME Организовать для юзеров возможность поиска по авторам, по книгам
    //FIXME Организовать для юзеров возможность оставлять свои отзывы к книгам
    //FIXME Организовать для юзеров возможность просматривать публикации пользователей, на которых подписан юзер
    //FIXME Сделать постраничное отображение данных (новости,публикации и т. д.) по дате
    //FIXME Написать тесты для сервисов

    public static void main( String[] args ){
        SpringApplication.run(App.class,args);
    }

}