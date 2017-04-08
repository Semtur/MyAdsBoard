<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>MyAdsBoard - ВХІД</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
    <div align="center">
        <a href="/"><img src="/images?img=myadsboard_logo.jpg"/></a>
        <c:url value="/j_spring_security_check" var="loginUrl" />
        <form class="form-inline" action="${loginUrl}" method="POST">
            <input type="text" class="input-small" placeholder="E-Mail" name="j_login"><br>
            <input type="password" class="input-small" placeholder="Password" name="j_password"><br>
            <label class="checkbox">
                <input type="checkbox"> Запам'ятати мене
            </label><br>
            <button type="submit" class="btn btn-success">Увійти</button>
        </form>
        <c:if test="${param.error ne null}">
            <h3>Невірний E-Mail або пароль!</h3>
        </c:if>
        <c:if test="${param.logout ne null}">
            <script language="javascript">
                window.location.href = "http://localhost:8080/"
            </script>
        </c:if>
        <c:if test="${param.registrationSuccessful ne null}">
            <h3>Реєстрація нового користувача пройшла успішно!</h3>
        </c:if>
    </div>
</body>
</html>
