<#import "blocs/template.ftlh" as t>
<@t.template user>
<form action="/article-search" method="get">
    <div class="input-group mb-3">
        <input class="form-control" type="search" name="search" placeholder="Поиск" aria-label="Search" >
        <button class="btn btn-outline-success" type="submit" >Поиск</button>
    </div>
</form>
    <#list articles as article>
        <a class="text-uppercase fw-bold" href="/article/${article.id}">${article.name}</a>
        <p class="fw-normal">${article.tiny_text}...</p>
        <hr>
    <#else>
      <h3>Статей нет</h3>
    </#list>
</@t.template>