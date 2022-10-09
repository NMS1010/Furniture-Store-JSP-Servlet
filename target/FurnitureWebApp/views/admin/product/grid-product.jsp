<%@ page import="utils.constants.PRODUCT_STATUS" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="products" scope="request" type="java.util.ArrayList<view_models.products.ProductViewModel>"/>
<jsp:useBean id="categories" scope="request" type="java.util.ArrayList<view_models.categories.CategoryViewModel>"/>
<jsp:useBean id="brands" scope="request" type="java.util.ArrayList<view_models.brands.BrandViewModel>"/>
<html>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <meta name="description" content="Ekka - Admin Dashboard eCommerce HTML Template.">

  <title>Ekka - Admin Dashboard eCommerce</title>
  <jsp:include page="/views/admin/common/common_css.jsp"/>
</head>
<body class="ec-header-fixed ec-sidebar-fixed ec-sidebar-dark ec-header-light" id="body">
<div class="wrapper">
  <jsp:include page="/views/admin/common/sidebar.jsp"/>
  <div class="ec-page-wrapper" >
    <jsp:include page="/views/admin/common/header.jsp"/>
    <div class="ec-content-wrapper">
      <div class="content">
        <div class="breadcrumb-wrapper d-flex align-items-center justify-content-between">
          <div>
            <h1>Product</h1>
            <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
              <span><i class="mdi mdi-chevron-right"></i></span>Product
            </p>
          </div>
          <div>
            <a href="<%=request.getContextPath()%>/admin/products?grid=true" class="btn btn-outline-info active">
              Grid
            </a>
            <a href="<%=request.getContextPath()%>/admin/products" class="btn btn-outline-success ">
              List
            </a>
            <a href="<%=request.getContextPath()%>/admin/product/add" class="btn btn-primary"> Add Product</a>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <div class="card card-default">
              <div class="card-header card-header-border-bottom d-flex justify-content-between">
                <div class="row col-lg-6 col-md-12">
                  <input type="text" class="form-control" id="searchProduct"
                         placeholder="search with product name..">
                </div>
                <div class="card-bar mt-3">
                  <div class="row col-lg-12 col-md-12 ">
                    <div class="col-lg-3 col-md-4 p-space">
                      <span>Category: </span>
                      <select class="form-control" id="dropdownCategory" name="dropdownCategory">
                        <c:forEach var="c" items="${categories}">
                          <option value="${c.categoryId}">${c.name}</option>
                        </c:forEach>
                      </select>
                    </div>

                    <div class="col-lg-3 col-md-4 p-space">
                      <span>Brand: </span>
                      <select class="form-control" id="dropdownBrand" name="dropdownBrand">
                        <c:forEach var="b" items="${brands}">
                          <option value="${b.brandId}">${b.brandName}</option>
                        </c:forEach>
                      </select>
                    </div>
                    <div class="col-lg-3 col-md-4 p-space">
                      <span>Order By: </span>
                      <select class="form-control" id="dropdownOrderBy" name="dropdownOrderBy">
                        <option value="name">Name</option>
                        <option value="l-h">Price Low - High</option>
                        <option value="h-l">Price High - Low</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="card-body">
                <div class="row">
                  <c:forEach var="product" items="${products}">
                    <div class="col-lg-3 col-md-4 col-sm-6">
                      <div class="card-wrapper">
                        <div class="card-container">
                          <div class="card-top">
                            <img class="card-image" src="data:image/png;base64, ${product.image}"
                                 alt="" />
                          </div>
                          <div class="card-bottom">
                            <h1 class="mt-2">${product.name}</h1>
                            <h6 class="mt-2">Category: ${product.categoryName}</h6>
                            <h6 class="mt-2">Brand: ${product.brandName}</h6>
                            <h6 class="mt-2">Origin: ${product.origin}</h6>
                            <div class="d-flex justify-content-between mt-2">
                              <p>${product.price} VND</p>
                              <p>${product.quantity}</p>
                            </div>
                            <p class="mt-2"><span class="${product.statusClass}">${product.statusCode}</span></p>
                          </div>
                          <div class="card-action">
                            <a href="<%=request.getContextPath()%>/admin/product/edit?productId=${product.productId}" class="card-edit">
                              <i class="mdi mdi-circle-edit-outline"></i>
                            </a>
                            <a href="<%=request.getContextPath()%>/admin/product/detail?productId=${product.productId}" class="card-preview">
                              <i class="mdi mdi-eye-outline"></i>
                            </a>
                            <a href="<%=request.getContextPath()%>/admin/product/delete?productId=${product.productId}" class="card-remove">
                              <i class="mdi mdi mdi-delete-outline"></i>
                            </a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </c:forEach>
                </div>
                <div class="row">
                  <nav aria-label="Page navigation example p-0">
                    <ul class="pagination pagination-seperated pagination-seperated-rounded">
                      <li class="page-item">
                        <a class="page-link <c:if test="${pageIndex - 1 <= 0}">
                                                    disabled
                                                </c:if>"
                           href="<c:if test="${pageIndex - 1 > 0}">
                                    <%=request.getContextPath()%>/admin/products?grid=true&pageIndex=${pageIndex - 1}
                                </c:if>
                                <c:if test="${pageIndex - 1 <= 0}">
                                    #
                                </c:if>" aria-label="Previous">
                                        <span aria-hidden="true"
                                              class="mdi mdi-chevron-left mr-1"></span> Prev
                          <span class="sr-only">Previous</span>
                        </a>
                      </li>
                      <c:forEach var="p" begin="1" end="${totalPage}">
                          <li class="page-item ${pageIndex == p ? "active" : ""}">
                            <a class="page-link" href="<%=request.getContextPath()%>/admin/products?grid=true&pageIndex=${p}">${p}</a>
                          </li>
                      </c:forEach>

                      <li class="page-item">
                        <a class="page-link <c:if test="${pageIndex + 1 > totalPage}">
                                                    disabled
                                                </c:if>"
                           href="<c:if test="${pageIndex + 1 <= totalPage}">
                                    <%=request.getContextPath()%>/admin/products?grid=true&pageIndex=${pageIndex + 1}
                                </c:if>
                                <c:if test="${pageIndex + 1 > totalPage}">
                                    #
                                </c:if>" aria-label="Next">
                          Next
                          <span aria-hidden="true"
                                class="mdi mdi-chevron-right ml-1"></span>
                          <span class="sr-only">Next</span>
                        </a>
                      </li>
                    </ul>
                  </nav>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <jsp:include page="/views/admin/common/footer.jsp"/>
  </div>
</div>
<jsp:include page="/views/admin/common/common_js.jsp"/>
<script>

</script>
</body>
</html>
