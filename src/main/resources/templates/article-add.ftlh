<#import "blocs/template.ftlh" as t>
<@t.template user>
<div class="container mt-5 mb-5">
    <h1>Добавление статьи</h1>
        <#if article??>
        <form action="/article-add/${id}" method="post" enctype="multipart/form-data">
            <#if article.name??>
                Название статьи: <input type="text" name="name" value="${article.name}"/><br><br>
            <#else>
                Название статьи: <input type="text" name="name"/><br><br>
            </#if>
            <#if article.text??>
                Статья: <textarea class="form-control" aria-label="With textarea" style="height: 500px" name="text">${article.text}</textarea>
            <#else>
                Статья: <textarea class="form-control" aria-label="With textarea" style="height: 500px" name="text"></textarea>
            </#if>
            <#if article.textLinks??>
                Ссылки на другие статьи: <textarea class="form-control" aria-label="With textarea" style="height: 500px" name="textLinks" >${article.textLinks}</textarea>
            <#else>
                Ссылки на другие статьи: <textarea class="form-control" aria-label="With textarea" style="height: 500px" name="textLinks"></textarea>
            </#if>
            <#if article.linkFoto??>
                Ссылка на фотографию: <input type="text" name="linkFoto" value="${article.linkFoto}"/><br><br>
            <#else>
                Ссылка на фотографию: <input type="text" name="linkFoto"/><br><br>
            </#if>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Отредактировать статью"/>
            </form>
        <#else>
            <form action="/article-add" method="post" enctype="multipart/form-data">
            Название статьи: <input type="text" name="name"/><br><br>
            Статья: <textarea class="form-control" aria-label="With textarea" style="height: 500px" name="text"></textarea>
            Ссылки на другие статьи: <textarea class="form-control" aria-label="With textarea" style="height: 500px" name="textLinks"></textarea>
            Ссылка на фотографию: <input type="text" name="linkFoto"/><br><br>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Внести статью"/>
            </form>
        </#if>
</div>
</@t.template>