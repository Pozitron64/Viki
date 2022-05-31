<#import "blocs/template.ftlh" as t>
<@t.template user>
<h1 style="text-align: center">Редактирование пользователя ${userEdit.name}</h1><hr>
<form action="/admin/user/edit" method="post">
    <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}" ${userEdit.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
    </#list>
    <hr>
    <#list typeArticles as role>
        <div>
            <label><input type="checkbox" name="${role}" ${userEdit.typeArticles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
    </#list>
    <input type="hidden" value="${userEdit.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit" class="btn btn-dark">Сохранить</button>
</form>

</@t.template>