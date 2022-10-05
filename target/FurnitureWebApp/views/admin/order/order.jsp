<%@ page import="utils.HtmlClassUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="orders" scope="request" type="java.util.ArrayList<view_models.orders.OrderViewModel>"/>
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
        <div class="breadcrumb-wrapper breadcrumb-wrapper-2">
          <h1>New Orders</h1>
          <div class="d-flex justify-content-between">
            <a href="<%=request.getContextPath()%>/admin/orders?new=true" class="btn btn-outline-info">View New Orders</a>
            <a href="<%=request.getContextPath()%>/admin/orders?delivered=true" class="btn btn-outline-info">View Delivered Orders</a>
            <a href="<%=request.getContextPath()%>/admin/orders" class="btn btn-outline-info">View All Orders</a>
          </div>
          <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
            <span><i class="mdi mdi-chevron-right"></i></span>Orders
          </p>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="card card-default">
              <div class="card-body">
                <div class="table-responsive">
                  <table id="responsive-data-table" class="table" style="width:100%">
                    <thead>
                    <tr>
                      <th>ID</th>
                      <th>Tên khách hàng</th>
                      <th>Email</th>
                      <th>Tổng sản phẩm</th>
                      <th>Tổng tiền</th>
                      <th>Thanh toán</th>
                      <th>Trạng thái</th>
                      <th>Ngày đặt</th>
                      <th>Ngày thanh toán</th>
                      <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="order" items="${orders}">
                      <tr>
                        <td>${order.orderId}</td>
                        <td><strong>${order.userName}</strong></td>
                        <td>${order.email}</td>
                        <td>${order.totalItem}</td>
                        <td>${order.totalPrice}</td>
                        <td>${order.paymentMethod}</td>
                        <td><span class="mb-2 mr-2 ${order.statusClass}">${order.statusCode}</span>
                        </td>
                        <td>${order.dateCreated}</td>
                        <td>${order.dateDone}</td>
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
                              <a class="dropdown-item" href="#">Detail</a>
                              <a class="dropdown-item" href="#">Track</a>
                              <a class="dropdown-item" href="#">Cancel</a>
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
  </div>
  <jsp:include page="/views/admin/common/footer.jsp"/>
</div>
</div>
<jsp:include page="/views/admin/common/common_js.jsp"/>
<script>
  $(document).ready(function () {
    $('#modal-change-review').on('show.bs.modal', function (event) {
      let id = $(event.relatedTarget).attr('data-id');
      let link = "<%=request.getContextPath()%>/admin/review/editStatus?reviewId=" + id;
      document.getElementById('link-change').href = link
    });
  });
</script>
</body>
</html>
