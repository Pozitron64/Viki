<#import "blocs/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Информация о пользователе</h1>
    <hr>
    <div style="text-align: center">
        <h2>${user.name}</h2>
        <br>
        Номер телефона: <b>${user.numberPhone}</b>
    </div>
    <br>
    <hr>
    <h1 style="text-align: center">Статьи пользователя ${user.name}</h1>
    <br>
    <div class="card-columns">
        <#list articles as article>
            <a href="/article/${article.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product">
                    <div class="card-body">
                        <h5 class="card-title">${article.name}</h5>
                        <p class="card-text">${article.tiny_text}</p>
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>
</@t.template>