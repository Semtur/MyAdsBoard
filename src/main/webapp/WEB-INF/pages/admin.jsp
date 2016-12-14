<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>MyAdsBoard - Адміністрування</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div role="tabpanel">
    <!-- Навігаційні елементи вкладок -->
    <ul class="nav nav-tabs" role="tablist">
        <a href="/"><img align="left" src="/images?img=myadsboard_smlogo.gif"/></a>
        <li role="presentation" class="active"><a href="#users" aria-controls="users" role="tab" data-toggle="tab">Користувачі</a></li>
        <li role="presentation"><a href="#categories" aria-controls="categories" role="tab" data-toggle="tab">Категорії</a></li>
        <li role="presentation" class="dropdown">
            <a href="#" id="adminMenu" class="dropdown-toggle" data-toggle="dropdown" aria-controls="adminMenu-contents">${adminName}<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu" aria-labelledby="adminMenu" id="adminMenu-contents">
                <li><a href="#onMain" tabindex="-1" role="tab" id="myProfile" data-toggle="tab" aria-controls="myProfile">Профіль</a></li>
                <li><a href="#onMain" tabindex="-1" role="tab" id="onMain" data-toggle="tab" aria-controls="onMain">На Головну</a></li>
                <li><a href="#logout" tabindex="-1" role="tab" id="logout" data-toggle="tab" aria-controls="logout">Вихід</a></li>
            </ul>
        </li>
    </ul>

    <!-- Вкладки панелі -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="users">
            <table class="table table-hover table-condensed">
                <tr class="active">
                    <td>#</td>
                    <td class="col-lg-2">E-Mail</td>
                    <td class="col-lg-3">Ім'я</td>
                    <td class="col-lg-2">Телефон</td>
                    <td class="col-lg-3">Адреса</td>
                    <td class="col-lg-1">Роль</td>
                    <td class="col-lg-1">Дата реєстрації</td>
                </tr>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.getId()}</td>
                        <td class="col-lg-2">${user.getEmail()}</td>
                        <td class="col-lg-3">${user.getName()}</td>
                        <td class="col-lg-2">${user.getPhone()}</td>
                        <td class="col-lg-3">${user.getAddress().toString()}</td>
                        <td class="col-lg-1">${user.getUserRole()}</td>
                        <td class="col-lg-1">${user.getDateOfRegistration()}</td>
                        <td>
                            <a href="/changeUserProfileForm?id=${user.getId()}" class="btn btn-warning">змінити</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div role="tabpanel" class="tab-pane" id="categories">
            <table class="table table-hover table-condensed">
                <tr>
                    <td>#</td>
                    <td class="col-lg-2">Назва категорії</td>
                    <td class="col-lg-2">Рубрики</td>
                    <td>
                        <a href="/category">
                            <button type="button" id="newCategory" class="btn btn-warning">Додати категорію</button>
                        </a>
                    </td>
                </tr>
                <c:forEach items="${categories}" var="category">
                <tr>
                    <td class="col-lg-1">${category.getId()}</td>
                    <td class="col-lg-2">${category.getName()}</td>
                    <td class="col-lg-2">
                        <c:if test="${category.getRubricList() ne null}">
                            <c:forEach items="${category.getRubricList()}" var="rubric">
                                ${rubric}<br>
                            </c:forEach>
                        </c:if>
                    </td>
                    <td class="col-lg-1">
                        <a href="/category?id=${category.getId()}">
                            <button type="button" id="${category.getId()}" class="btn btn-warning">змінити</button>
                        </a>
                    </td>
                </tr>
                </c:forEach>
        </div>
    </div>
</div>
<script type="application/javascript">
    $('#myProfile').click(function(){
        window.location.href='/changeUserProfileForm?id=${adminId}';
    });
    $('#onMain').click(function(){
        window.location.href='/';
    });
    $('#logout').click(function(){
        window.location.href='/logout';
    });
</script>
</body>
</html>
