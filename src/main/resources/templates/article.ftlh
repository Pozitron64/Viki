<#import "blocs/template.ftlh" as t>
<@t.template user>
<form action="/article-search" method="get">
    <input class="form-control me-2" type="search" name="search" placeholder="Поиск" aria-label="Search">
    <button class="btn btn-outline-success" type="submit">Поиск</button>
</form>
<div class="container mt-5">

    <h1>Статьи</h1>
    <#list articles as article>
        <div>
            <p><b>${article.name}</b> ${article.tiny_text}... | <a href="/article/${article.id}">Подробнее...</a></p>
        </div>
        <#else>
            <h3>Статей нет</h3>
    </#list>
</div>
</@t.template>