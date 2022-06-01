<#import "blocs/template.ftlh" as t>
<@t.template user>
<div class="container mt-5">
    <h1>${article.name}</h1><hr>
    <#if article.linkFoto??>
      <img src="${article.linkFoto}" height="300"/><br><br>
    </#if>
    <#assign myRange = 0 ..< sizeLinks >
        <#list myRange as range>
            ${textNoLinks[range]}
            <a href="${links[range].textLink}" data-bs-toggle="tooltip" data-bs-placement="top" title="Подробнее">${links[range].identificationWord}</a>
        </#list>
    ${textNoLinks[sizeLinks]}
    <hr>
    <#if middleOption??>
        <p class="fw-bold text-start">Средняя оценка статьи - ${middleOption}</p>
    </#if>
    <#if user.id??>
        <#if optionUser??>
            <p class="text-start">Ваша оценка статьи - ${optionUser}</p>
        </#if>
            <div class="col-md-4">
                <form action="/rate/${article.id}" method="get">
                    Оцените статью: <input type="text" name="option"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Оценить" class="btn btn-primary"/>
                </form>
            </div>
            <br>
            <#if isExpert>
                <div class="col-md-4">
                    <form action="/article-info/edit/${article.id}" method="get">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Редактировать статью" class="btn btn-primary"/>
                    </form>
                </div>
                <br><br>
            </#if>
            <br><br>
    </#if>
</div>
</@t.template>