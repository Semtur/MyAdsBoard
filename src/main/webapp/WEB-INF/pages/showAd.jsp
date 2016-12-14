<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${ad.getTitle()}</title>
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
                        <button class="btn btn-sm btn-warning" id="postNewAd" type="button">Розмістити нове оголошення</button><br>
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
    <div align="center" class="col-lg-offset-4 col-lg-4">
        <table  class="table">
            <tr class="info">
                <td><h1>${ad.getTitle()} - ${ad.getPrice()} грн</h1></td>
            </tr>
            <tr><td><h4><strong>Рубрика</strong> => ${ad.getRubric()}</h4></td></tr>
            <tr><td><strong>Стан товару:</strong> ${stateType}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Продавець:</strong> ${sellerType}</td></tr>
            <tr><td><strong>Опис товару:</strong><br>${ad.getDescription()}</td></tr>
            <c:forEach var="photo" items="${ad.getPhotos()}">
                <tr><td><img align="center" src="/photos?photoId=${photo.getId()}" class="img-thumbnail" width="640" height="480"></td></tr>
            </c:forEach>
            <tr><td>
                <h4><strong>Контактна інформація продавця:</strong></h4>
                <strong>Ім'я:</strong> ${ad.getUser().getName()}
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <strong>Телефон:</strong> ${ad.getUser().getPhone()}
                <br>
                <strong>Адреса:</strong> ${ad.getAddress()}
            </td></tr>
            <tr><td>
                <h5><strong>Написати повідомлення продавцю:</strong></h5>
                <form class="form-horizontal" enctype="multipart/form-data" action="/sendMessage?adId=${ad.getId()}" method="post">
                    <div class="form-group">
                        <label for="fromUser" class="col-lg-3 control-label">Ваш E-Mail</label>
                        <div class="col-lg-5">
                            <input type="email" class="form-control" name="fromUser" placeholder="username@domain.com" value="${userEmail}">
                            <input type="hidden" class="form-control" name="toUser" value="${ad.getUser().getEmail()}">
                            <input type="hidden" class="form-control" name="returnUrl" value="/showAd?id=${ad.getId()}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="messageText" class="col-lg-3 control-label">Повідомлення</label>
                        <div class="col-lg-9">
                            <textarea class="form-control" name="messageText" rows="6"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="file" class="col-lg-3 control-label">Прикріпити файл</label>
                        <div class="col-lg-3">
                            <input type="file" name="file" accept="*">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-offset-3 col-lg-2">
                            <button type="submit" class="btn btn-success">Відправити</button>
                        </div>
                    </div>
                </form>
            </td></tr>
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
