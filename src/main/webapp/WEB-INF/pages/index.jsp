<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>MyAdsBoard - Головна</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
</head>
<body>
    <table align="center">
        <tr class="bg-primary">
            <td>
                <a href="/"><img align="center" src="/images?img=myadsboard_biglogo.jpg"/></a>
            </td>
            <td align="right">
                <c:if test="${userId == null}">
                    <button type="button" id="login" class="btn btn-sm btn-primary">Вхід</button>
                    <button type="button" id="registration" class="btn btn-sm btn-warning">Реєстрація</button>
                </c:if>
                <c:if test="${userId ne null}">
                    Ви увійшли як ${userName}!
                    <c:if test="${admin}">
                        <br>
                        <a class="btn btn-sm btn-warning" href="/admin">Адміністрування</a>
                    </c:if>
                    <c:if test="${user}">
                        <button class="btn btn-sm btn-warning" id="postNewAd" type="button">+ ДОДАТИ ОГОЛОШЕННЯ</button><br>
                        <a class="btn btn-sm btn-primary" href="/myAds">Оголошення</a>
                        <a class="btn btn-sm btn-primary" href="/myMessages">Повідомлення</a>
                    </c:if>
                    <a class="btn btn-sm btn-primary" href="/changeUserProfileForm?id=${userId}">Профіль</a>
                    <button class="btn btn-sm btn-primary" id="logout" type="button" >Вихід</button>
                </c:if>
                <form class="form-search" action="/search">
                    <select class="form-control input-sm" name="rubric">
                        <option value="${rubricDefVal}">${rubricDefName}</option>
                        <c:forEach var="category" items="${categories}">
                            <c:if test="${category.getRubricList() ne null}">
                                <optgroup label="${category.getName()}">
                                    <c:forEach var="rubric" items="${category.getRubricList()}">
                                        <option value="${rubric}">${rubric}</option>
                                    </c:forEach>
                                </optgroup>
                            </c:if>
                            <c:if test="${category.getRubricList() == null}">
                                <option value="${category.getName()}">${category.getName()}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <input style="color: black" type="text" class="input-medium" name="adTitleTemplate" placeholder="${numOfAds} оголошень" value="${adTitleTemplate}">
                    <input style="color: black" type="text" class="input-medium" name="addressTemplate" placeholder="Київ" value="${addressTemplate}">
                    <button type="submit" class="btn btn-success">ПОШУК</button>
                </form>

            </td>
        </tr>
    </table>
    <div class="col-lg-offset-3 col-lg-6" style="margin-top: 1%">
    <table class="table">
        <tr class="active">
            <td>Фото</td>
            <td>Заголовок</td>
            <td>Ціна</td>
            <td>Продавець</td>
            <td>Дата публікації</td>
            <td></td>
        </tr>
        <c:forEach var="ad" items="${adList}">
            <tr>
                <td>
                    <c:if test="${ad.getPhotos().size() > 0}">
                        <a href="/showAd?id=${ad.getId()}"><img src="/photos?photoId=${ad.getPhotos().get(0).getId()}" class="img-thumbnail" height="100" width="100"></a>
                    </c:if>
                    <c:if test="${ad.getPhotos().size() == 0}">
                        <img src="/images?img=myadsboard_logo.jpg" class="img-thumbnail" height="100" width="100"></a>
                    </c:if>
                </td>
                <td>${ad.getTitle()}</td>
                <td>${ad.getPrice()} грн</td>
                <td>${ad.getUser().getName()}</td>
                <td>${ad.getCreationDate()}</td>
                <td><a class="btn btn-primary" href="/showAd?id=${ad.getId()}">детально</a></td>
            </tr>
        </c:forEach>
    </table>
        </div>
    <script type="application/javascript">
        $('#login').click(function(){
            window.location.href='/login';
        });
        $('#registration').click(function(){
            window.location.href='/registrationForm';
        });
        $('#postNewAd').click(function(){
            window.location.href='/postNewAd';
        });
        $('#logout').click(function(){
            window.location.href='/logout';
        });
    </script>
</body>
</html>
