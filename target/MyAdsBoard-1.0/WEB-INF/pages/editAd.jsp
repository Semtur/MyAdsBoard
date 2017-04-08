<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MyAdsBoard - Редагування оголошення</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
    <form class="form-horizontal" enctype="multipart/form-data" action="/updateAd?id=${ad.getId()}" method="post">
        <div class="form-group">
            <div class="col-lg-offset-4 col-lg-1">
                <a href="/"><img align="right" src="/images?img=myadsboard_smlogo.gif"/></a>
            </div>
            <div class="col-lg-3" style="margin-top: 0.25%">
                <a class="btn btn-primary" href="/myAds">Оголошення</a>
                <a class="btn btn-primary" href="/myMessages">Повідомлення</a>
                <a class="btn btn-primary" href="/changeUserProfileForm?id=${userId}">Профіль</a>
                <a class="btn btn-primary" href="/logout">Вихід [ ${userName} ]</a>
            </div>
        </div>
        <div class="col-lg-offset-5 col-lg-5">
            <h5><strong style="color:#ff0000"><c:if test="${param.error ne null}">${errorText}<br></c:if></strong></h5>
        </div>
        <div class="form-group">
            <label for="title" class="col-lg-5 control-label">Заголовок*</label>
            <div class="col-lg-3">
                <input type="text" class="form-control" name="title" value="${ad.getTitle()}">
            </div>
        </div>
        <div class="form-group">
            <label for="rubric" class="col-lg-5 control-label">Оберіть рубрику*</label>
            <div class="col-lg-2">
                <select class="form-control" name="rubric">
                    <option value="${ad.getRubric()}">${rubric}</option>
                    <c:forEach var="category" items="${categories}">
                        <c:if test="${category.getRubricList() ne null}">
                            <optgroup label="${category.getName()}">
                                <c:forEach var="rubric" items="${category.getRubricList()}">
                                    <option value="${category.getName()}->${rubric}">${rubric}</option>
                                </c:forEach>
                            </optgroup>
                        </c:if>
                        <c:if test="${category.getRubricList() == null}">
                            <option value="${category.getName()}">${category.getName()}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="price" class="col-lg-5 control-label">Ціна (грн)*</label>
            <div class="col-lg-1">
                <input type="text" class="form-control" name="price" value="${ad.getPrice()}">
            </div>
        </div>
        <div class="form-group">
            <label for="stateType" class="col-lg-5 control-label">Стан*</label>
            <div class="col-lg-1">
                <select class="form-control" name="stateType">
                    <option value="${ad.getState().toString()}">${stateNameDef}</option>
                    <option value="${state}">${stateName}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="sellerType" class="col-lg-5 control-label">Власник / Бізнес*</label>
            <div class="col-lg-1">
                <select class="form-control" name="sellerType">
                    <option value="${ad.getSeller().toString()}">${sellerNameDef}</option>
                    <option value="${seller}">${sellerName}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-lg-5 control-label">Опис*</label>
            <div class="col-lg-3">
                <textarea class="form-control" name="description" rows="8">${ad.getDescription()}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="photo" class="col-lg-5 control-label">Фотографії</label>
            <div class="col-lg-3">
                <c:forEach var="i" begin="1" end="${numOfPhotos}" step="1">
                    <input type="file" name="photoFile${i}" accept="image/*">
                </c:forEach>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-5 col-lg-3">
                <table>
                    <tr>
                        <c:forEach var="photo" items="${ad.getPhotos()}">
                            <td>
                                <a href="/photos?photoId=${photo.getId()}"><img src="/photos?photoId=${photo.getId()}" class="img-thumbnail" height="200" width="200"></a>
                                <br>
                                <a href="deletePhoto?photoId=${photo.getId()}" class="btn btn-xs btn-danger">видалити</a>
                            </td>
                        </c:forEach>
                    </tr>
                </table>
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-lg-5 control-label">E-Mail</label>
            <div class="col-lg-2">
                <input type="email" class="form-control" name="email" value="${userEmail}" disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-lg-5 control-label">Ім'я</label>
            <div class="col-lg-2">
                <input type="text" class="form-control" name="name" value="${userName}" disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="phone" class="col-lg-5 control-label">Телефон</label>
            <div class="col-lg-1">
                <input type="text" class="form-control" name="phone" value="${userPhone}" disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="state" class="col-lg-5 control-label">Область</label>
            <div class="col-lg-2">
                <input type="text" class="form-control" name="state" value="${userAddress.getState()}" disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="city" class="col-lg-5 control-label">Місто / село</label>
            <div class="col-lg-1">
                <input type="text" class="form-control" name="city" value="${userAddress.getCity()}" disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="street" class="col-lg-5 control-label">Вулиця</label>
            <div class="col-lg-2">
                <input type="text" class="form-control" name="street" value="${userAddress.getStreet()}" disabled>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-5 col-lg-3">
                <p>* - поля обов'язкові для заповнення</p>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-5 col-lg-3">
                <button type="submit" class="btn btn-success">Зберегти зміни</button>
                <a href="/" class="btn btn-primary">На Головну</a>
            </div>
        </div>
    </form>
</body>
</html>