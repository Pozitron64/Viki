<#macro template user>
    <!DOCTYPE html>
    <html lang="en" xmlns:th="http://www.thymleaf.org">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Title</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="d-flex flex-column flex-md-row align-items-center pb-3 mb-4 border-bottom">
            <a href="/" class="d-flex align-items-center text-dark text-decoration-none">
                <span class="fs-4 px-2">Википедия</span>
            </a>
            <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto gy-2">
                <#if user.isAdmin()>
                    <a class="me-3 py-2 text-dark text-decoration-none" href="/admin">Панель администратора</a>
                </#if>
                <a class="me-3 py-2 text-dark text-decoration-none" href="/">Главная</a>
                <a class="me-3 py-2 text-dark text-decoration-none" href="/article">Статьи</a>
                <#if user??>
                    <#if user.email??>
                        <a class="me-3 py-2 text-dark text-decoration-none" href="/article-add">Добавить статью</a>
                        <form action="/logout" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <input type="submit" value="Выйти"/>
                        </form>
                    <#else>
                        <a class="me-3 py-2 text-dark text-decoration-none" href="/login">Войти</a>
                    </#if>
                </#if>
            </nav>
        </div>
        <div class="container mt-4">
            <#nested/>
        </div>
    </body>
    </html>
</#macro>