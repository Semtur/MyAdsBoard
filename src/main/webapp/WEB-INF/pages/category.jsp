<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MyAdsBoard - Адміністрування</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
    <c:if test="${category ne null}">
        <c:url value="/updateCategory?id=${category.getId()}" var="updateCategoryUrl"></c:url>
    </c:if>
    <c:if test="${category == null}">
        <c:url value="/updateCategory" var="updateCategoryUrl"></c:url>
    </c:if>
    <form class="form-horizontal" action="${updateCategoryUrl}" method="post">
        <div class="form-group">
            <a href="/"><img class="col-lg-offset-5 col-lg-1" src="/images?img=myadsboard_logo.jpg"/></a>
        </div>
        <div class="form-group">
            <label for="name" class="col-lg-5 control-label">Назва категорії*</label>
            <div class="col-lg-2">
                <input type="text" class="form-control" name="name" placeholder="Комплектуючі для ПК" value="${category.getName()}">
            </div>
        </div>
        <div class="form-group">
            <label for="rubricList" class="col-lg-5 control-label">
                Рубрики
                <c:if test="${category ne null}">**</c:if>
            </label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="rubricList" placeholder="Материнські плати; Процесори; Пам'ять; Відеокарти; Жорсткі диски" value="${category.rubricsToString()}">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-5 col-lg-4">
                <p>* - поля обов'язкові для заповнення</p>
                <p><c:if test="${category ne null}">** - для видалення рубрики поставне перед іменем "-" (наприклад: -Материнські плати)</c:if></p>
            </div>
            <div class="col-lg-offset-5 col-lg-1">
                <button type="submit" class="btn btn-success">Зберегти</button>
            </div>
        </div>
    </form>
    <form onSubmit="return confirm('Ви дійсно хочете видалити профіль категорію ${category.getName()}?')" action="/deleteCategory?id=${category.getId()}" method="post">
        <div class="form-group">
            <div class="col-lg-offset-5 col-lg-1">
                <button type="submit" class="btn btn-danger" id="deleteUserProfile">Видалити категорію</button>
            </div>
        </div>
    </form>
</body>
</html>
