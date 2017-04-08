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
        <br>
        <h4>${adTitle}</h4>
        Від: ${message.getFromEmail()} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Кому: ${message.getToEmail()}
        <h5>Текст повідомлення:</h5>
        <p>${message.getMessageText()}</p>
        <c:if test="${message.getFileName() ne null}">
            <a href="/getAttach?messageId=${message.getId()}&file=${message.getFileName()}" download>${message.getFileName()}</a>
        </c:if>
        <br><br>
        <strong>Написати відповідь: </strong>
        <c:if test="${in}">
            <form class="form-horizontal" enctype="multipart/form-data" action="/sendMessage?adId=${ad.getId()}" method="post">
                <div class="form-group">
                    <label for="fromUser" class="col-lg-2 control-label">Ваш E-Mail</label>
                    <div class="col-lg-3">
                        <input type="email" class="form-control" name="fromUser" placeholder="username@domain.com" value="${userEmail}">
                        <input type="hidden" class="form-control" name="toUser" value="${message.getFromEmail()}">
                        <input type="hidden" class="form-control" name="returnUrl" value="/showMessage?id=${message.getId()}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="messageText" class="col-lg-2 control-label">Повідомлення</label>
                    <div class="col-lg-6">
                        <textarea class="form-control" name="messageText" rows="6"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="file" class="col-lg-2 control-label">Прикріпити файл</label>
                    <div class="col-lg-2">
                        <input type="file" name="file" accept="*">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-offset-2 col-lg-2">
                        <button type="submit" class="btn btn-success">Відправити</button>
                    </div>
                </div>
            </form>
        </c:if>
    </div>
</body>
</html>
