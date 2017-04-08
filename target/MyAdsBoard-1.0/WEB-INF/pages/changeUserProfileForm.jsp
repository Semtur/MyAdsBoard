<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MyAdsBoard - Редагування профілю ${email}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
    <form class="form-horizontal" action="/changeUserProfile?id=${id}" method="post">
        <div class="col-lg-offset-5 col-lg-1">
            <a href="/"><img align="center" src="/images?img=myadsboard_logo.jpg"/></a>
        </div>
        <div class="col-lg-offset-5 col-lg-4">
            <h5><strong style="color:#ff0000"><c:if test="${param.error ne null}">${errorText}<br></c:if></strong></h5>
            <h5 align="left"><c:if test="${param.change ne null}">${successfully}</c:if></h5>
        </div>
        <div class="form-group">
            <label for="email" class="col-lg-5 control-label">E-Mail*</label>
            <div class="col-lg-2">
                <input type="email" class="form-control" name="email" placeholder="username@domain.com" value="${email}">
            </div>
        </div>
        <c:if test="${myProfile}">
            <div class="form-group">
                <label for="password" class="col-lg-5 control-label">Новий пароль*</label>
                <div class="col-lg-1">
                    <input type="password" class="form-control" name="password" placeholder="введіть пароль">
                </div>
            </div>
            <div class="form-group">
                <label for="passwordConfirm" class="col-lg-5 control-label">Підтвердіть пароль*</label>
                <div class="col-lg-1">
                    <input type="password" class="form-control" name="passwordConfirm" placeholder="повторіть пароль">
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label for="name" class="col-lg-5 control-label">Ім'я*</label>
            <div class="col-lg-2">
                <input type="text" class="form-control" name="name" placeholder="введіть своє ім'я" value="${name}">
            </div>
        </div>
        <div class="form-group">
            <label for="phone" class="col-lg-5 control-label">Телефон</label>
            <div class="col-lg-1">
                <input type="text" class="form-control" name="phone" placeholder="0501234567" value="${phone}">
            </div>
        </div>
        <div class="form-group">
            <label for="state" class="col-lg-5 control-label">Область*</label>
            <div class="col-lg-2">
                <input type="text" class="form-control" name="state" placeholder="Київська область" value="${state}">
            </div>
        </div>
        <div class="form-group">
            <label for="city" class="col-lg-5 control-label">Місто / село*</label>
            <div class="col-lg-1">
                <input type="text" class="form-control" name="city" placeholder="Київ" value="${city}">
            </div>
        </div>
        <div class="form-group">
            <label for="street" class="col-lg-5 control-label">Вулиця</label>
            <div class="col-lg-2">
                <input type="text" class="form-control" name="street" placeholder="Хрещатик" value="${street}">
            </div>
        </div>
        <c:if test="${admin}">
            <div class="form-group">
                <label for="userRole" class="col-lg-5 control-label">Роль користувача</label>
                <div class="col-lg-2">
                    <input type="radio" name="userRole" value="${userRole0}" checked="checked"> ${userRole0}
                    <input type="radio" name="userRole" value="${userRole1}"> ${userRole1}
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <div class="col-lg-offset-5 col-lg-3">
                <p>* - поля обов'язкові для заповнення</p>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-5 col-lg-3">
                <button type="submit" class="btn btn-success">Зберегти зміни</button>
                <button type="button" class="btn btn-primary" id="onMain">Повернутись на головну</button>
            </div>
        </div>
    </form>
    <form onSubmit="return confirm('Ви дійсно хочете видалити профіль користувача ${name}?')" action="/deleteUserProfile?id=${id}" method="post">
        <div class="form-group">
            <div class="col-lg-offset-5 col-lg-1">
                <button type="submit" class="btn btn-danger" id="deleteUserProfile">Видалити профіль</button>
            </div>
        </div>
    </form>
    <script type="application/javascript">
        $('#onMain').click(function(){
            window.location.href='/';
        });
    </script>
</body>
</html>
