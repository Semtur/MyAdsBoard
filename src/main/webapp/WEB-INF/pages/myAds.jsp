<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MyAdsBoard - Мої оголошення</title>
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
            <a class="btn btn-warning" href="/postNewAd">+ ДОДАТИ ОГОЛОШЕННЯ</a>
            <a class="btn btn-primary" href="/myAds">Оголошення</a>
            <a class="btn btn-primary" href="/myMessages">Повідомлення</a>
            <a class="btn btn-primary" href="/changeUserProfileForm?id=${userId}">Профіль</a>
            <a class="btn btn-primary" href="/logout">Вихід [ ${userName} ]</a>
        </div>
    </div>
    <div class="col-lg-5" style="margin-top: 1%">
        <table class="table">
            <tr class="active">
                <td></td>
                <td>Дата</td>
                <td></td>
                <td>Заголовок</td>
                <td>Ціна</td>
                <td>Повідомлення</td>
                <td>Редагування</td>
                <td>Видалення</td>
            </tr>
            <c:forEach var="ad" items="${adList}">
                <tr>
                    <td><input type="checkbox" name="toDelete[]" value="${ad.getId()}" id="checkbox_${ad.getId()}"/></td>
                    <td>${ad.getCreationDate()}</td>
                    <td>
                        <c:if test="${ad.getPhotos().size() > 0}">
                            <img src="/photos?photoId=${ad.getPhotos().get(0).getId()}" class="img-thumbnail" height="50" width="50">
                        </c:if>
                    </td>
                    <td>${ad.getTitle()}</td>
                    <td>${ad.getPrice()} грн</td>
                    <td><a href="/myMessages?adId=${ad.getId()}" class="btn btn-xs btn-primary">${ad.getMessages().size()}</a></td>
                    <td><a href="/editAd?id=${ad.getId()}" class="btn btn-xs btn-warning">редагувати</a></td>
                    <td><a href="/deleteAd?id=${ad.getId()}" class="btn btn-xs btn-danger">видалити</a></td>
                </tr>
            </c:forEach>
        </table>
        <button class="btn btn-sm btn-danger" id="deleteAds" type="button">Видалити відмічені оголошення</button>
    </div>
    <script>
        $('#deleteAds').click(function(){
            var data = { 'toDelete[]' : []};
            $(":checked").each(function() {
                data['toDelete[]'].push($(this).val());
            });
            $.post("/deleteAds", data, function(data, status) {
                window.location.reload();
            });
        });
    </script>
</body>
</html>
