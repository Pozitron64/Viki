<#import "blocs/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Мои товары</h1>
    <#if user.isAdmin()>
            <a href="/admin">Панель администратора</a>
    </#if>

</@t.template>