<%@ page import="utils.constants.PRODUCT_STATUS" %>
<%@ page import="utils.HtmlClassUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="products" scope="request" type="java.util.ArrayList<models.view_models.products.ProductViewModel>"/>
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
              <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/products">Home</a></span>
                <span><i class="mdi mdi-chevron-right"></i></span>Product</p>
            </div>
            <div>
              <a href="<%=request.getContextPath()%>/admin/products?grid=true" class="btn btn-outline-info">
                Grid
              </a>
              <a href="<%=request.getContextPath()%>/admin/products" class="btn btn-outline-success active">
                List
              </a>
              <a href="<%=request.getContextPath()%>/admin/product/add" class="btn btn-primary"> Add Product</a>
            </div>
          </div>
          <div class="row">
            <div class="col-12">
              <div class="card card-default">
                <div class="card-body">
                  <div class="table-responsive">
                    <table id="responsive-data-table" class="table"
                           style="width:100%">
                      <thead>
                      <tr>
                        <th>Product</th>
                        <th>Name</th>
                        <th>Origin</th>
                        <th>Category</th>
                        <th>Brand</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Purchased</th>
                        <th>Status</th>
                        <th>DateCreated</th>
                        <th>Action</th>
                      </tr>
                      </thead>

                      <tbody>
                        <c:forEach var="product" items="${products}">
                          <tr>
                            <td><img class="tbl-thumb" src="data:image/png;base64, ${product.image}" alt="Product Image" /></td>
                            <td>${product.name}</td>
                            <td>${product.origin}</td>
                            <td>${product.categoryName}</td>
                            <td>${product.brandName}</td>
                            <td>${product.price}</td>
                            <td>${product.quantity}</td>
                            <td>${product.totalPurchased}</td>
                            <td><span class="${product.statusClass}">${product.statusCode}</span></td>
                            <td>${product.dateCreated}</td>
                            <td>
                              <div class="btn-group mb-1">
                                <button type="button"
                                        class="btn btn-outline-success">Info</button>
                                <button type="button"
                                        class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                        data-bs-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false" data-display="static">
                                  <span class="sr-only">Info</span>
                                </button>

                                <div class="dropdown-menu">
                                  <a class="dropdown-item" href="<%=request.getContextPath()%>/admin/product/edit?productId=${product.productId}">Edit</a>
                                  <a class="dropdown-item" href="<%=request.getContextPath()%>/admin/product/delete?productId=${product.productId}">Delete</a>
                                </div>
                              </div>
                            </td>
                          </tr>
                        </c:forEach>

                      </tbody>
                    </table>
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