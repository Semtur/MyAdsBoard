<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>MyAdsBoard - Forbidden</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div align="center">
    <a href="/"><img src="/images?img=access_denied.gif"/></a>
    <h1>Доступ заборонено для ${userName}!</h1><br>
    <button type="button" id="OnMain" class="btn btn-primary">На Головну</button>
    <button type="button" id="LogOut" class="btn btn-success">ВИХІД</button>
</div>
<script type="application/javascript">
    $('#OnMain').click(function(){
        window.location.href='/';
    });
    $('#LogOut').click(function(){
        window.location.href='/logout';
    });
</script>
</body>
</html>