<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="brands" type="java.util.List<view_models.brands.BrandViewModel>" scope="request"></jsp:useBean>
<html>
<head>
    <title>List Brand</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
    <table class="table">
        <thead>
            <tr>
                <th>Brand Id</th>
                <th>Brand Name</th>
                <th>Origin</th>
                <th>Logo</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${brands}" var="brand">
                <tr>
                    <td><c:out value="${brand.brandId}"></c:out></td>
                    <td><c:out value="${brand.brandName}"></c:out></td>
                    <td><c:out value="${brand.origin}"></c:out></td>
                    <td>
                        <img src="data:image/png;base64,<c:out value="${brand.image}"></c:out>" class="img-fluid img-thumbnail" alt="test"/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
