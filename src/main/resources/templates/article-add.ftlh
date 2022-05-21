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
            <span class="fs-4 px-2">Википедия Владимира Бебишева</span>
        </a>
        <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto gy-2">

            <a class="me-3 py-2 text-dark text-decoration-none" href="/">Главная</a>
            <a class="me-3 py-2 text-dark text-decoration-none" href="/article">Статьи</a>
            <a class="me-3 py-2 text-dark text-decoration-none" href="/article-add">Добавить статью</a>
            <form class="d-flex me-5">
                <input class="form-control me-2" type="search" placeholder="Поиск" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Поиск</button>
            </form>
        </nav>
    </div>
<div class="container mt-5 mb-5">
    <h1>Добавление статьи</h1>
    <form action="/article-add" method="post">
        Название статьи: <input type="text" name="name"/><br><br>
        Статья: <textarea class="form-control" aria-label="With textarea" style="height: 500px"></textarea>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Внести статью"/>
    </form>
</div>
</body>
</html>