<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MyAdsBoard - Мої повідомлення</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
</head>
<body>
<div align="center">
    <div class="col-lg-offset-3 col-lg-1">
        <a href="/"><img align="right" src="/images?img=myadsboard_smlogo.gif"/></a>
    </div>
    <div class="col-lg-5" style="margin-top: 0.25%">
        <a class="btn btn-warning" href="/postNewAd" >+ ДОДАТИ ОГОЛОШЕННЯ</a>
        <a class="btn btn-primary" href="/myAds">Оголошення</a>
        <a class="btn btn-primary" href="/myMessages">Повідомлення</a>
        <a class="btn btn-primary" href="/changeUserProfileForm?id=${userId}">Профіль</a>
        <a class="btn btn-primary" href="/logout">Вихід [ ${userName} ]</a>
    </div>
</div>
<div class="col-lg-5" style="margin-top: 1%">
    <a class="btn btn-xs btn-primary" href="/myMessages">Всі</a>
    <a class="btn btn-xs btn-primary" href="/myMessages?type=in">Вхідні</a>
    <a class="btn btn-xs btn-primary" href="/myMessages?type=out">Вихідні</a>
    <c:if test="${adTitle ne null}"><strong>${adTitle}</strong></c:if>
    <table class="table">
        <tr class="active">
            <td></td>
            <td>Дата</td>
            <td>Від</td>
            <td>Кому</td>
            <td>Текст</td>
            <td>Вкладення</td>
            <td></td>
        </tr>
        <c:forEach var="message" items="${messageList}">
            <tr>
                <td><input type="checkbox" name="toDelete[]" value="${message.getId()}" id="checkbox_${message.getId()}"/></td>
                <td>${message.getCreationDate()}</td>
                <td>${message.getFromEmail()}</td>
                <td>${message.getToEmail()}</td>
                <td><a href="/showMessage?id=${message.getId()}" class="btn btn-xs btn-primary">перегляд</a></td>
                <td>
                    <c:if test="${message.getFileName() ne null}">
                        <a href="/getAttach?messageId=${message.getId()}&file=${message.getFileName()}" download>${message.getFileName()}</a>
                    </c:if>
                </td>
                <td><a href="/deleteMessage?id=${message.getId()}" class="btn btn-xs btn-danger">видалити</a></td>
            </tr>
        </c:forEach>
    </table>
    <button class="btn btn-sm btn-danger" id="deleteAds" type="button">Видалити відмічені повідомлення</button>
</div>
<script>
    $('#deleteAds').click(function(){
        var data = { 'toDelete[]' : []};
        $(":checked").each(function() {
            data['toDelete[]'].push($(this).val());
        });
        $.post("/deleteMessages", data, function(data, status) {
            window.location.reload();
        });
    });
</script>
</body>
</html>