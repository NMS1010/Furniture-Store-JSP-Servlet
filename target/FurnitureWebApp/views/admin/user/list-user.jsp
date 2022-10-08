<%@ page import="utils.HtmlClassUtils" %>
<%@ page import="utils.constants.USER_GENDER" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="users" scope="request" type="java.util.ArrayList<view_models.users.UserViewModel>"/>
<jsp:useBean id="roles" scope="request" type="java.util.ArrayList<view_models.roles.RoleViewModel>"/>
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
        <div class="breadcrumb-wrapper breadcrumb-contacts">
          <div>
            <h1>User List</h1>
            <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
              <span><i class="mdi mdi-chevron-right"></i></span>Quản lý tài khoản
            </p>
          </div>
          <div>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                    data-bs-target="#addUser"> Thêm Người dùng
            </button>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="ec-vendor-list card card-default">
              <div class="card-body">
                <div class="table-responsive">
                  <table id="responsive-data-table" class="table">
                    <thead>
                    <tr>
                      <th>Profile</th>
                      <th>Username</th>
                      <th>Họ tên</th>
                      <th>Ngày sinh</th>
                      <th>Giới tính</th>
                      <th>Email</th>
                      <th>Điện thoại</th>
                      <th>Tổng sản phẩm đã mua</th>
                      <th>Trạng thái tài khoản</th>
                      <th>Ngày tham gia</th>
                      <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="user" items="${users}">
                      <tr>
                        <td><img class="vendor-thumb" src="data:image/png;base64 ${user.avatar}" alt="user profile" /></td>
                        <td>${user.username}</td>
                        <td>${user.firstName + user.lastName}</td>
                        <td>${user.dateOfBirth}</td>
                        <td>${user.genderCode}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>${user.totalBought}</td>
                        <td>${user.statusCode}</td>
                        <td>${user.dateCreated}</td>
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
                              <a class="dropdown-item" href="#">Edit</a>
                              <a class="dropdown-item" href="#">Delete</a>
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
        <!-- Add User Modal  -->
        <div class="modal fade modal-add-contact" id="addUser" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
            <div class="modal-content">
              <form>
                <div class="modal-header px-4">
                  <h5 class="modal-title" id="exampleModalCenterTitle">Thêm tài khoản mới</h5>
                </div>

                <div class="modal-body px-4">
                  <div class="form-group row mb-6">
                    <label for="avatar" class="col-sm-4 col-lg-2 col-form-label">Avatar</label>

                    <div class="col-sm-8 col-lg-10">
                      <div class="custom-file mb-1">
                        <input type="file" class="custom-file-input" id="avatar" name="avatar"
                               required>
                        <label class="custom-file-label" for="avatar">Chọn ảnh...</label>
                      </div>
                    </div>
                  </div>

                  <div class="row mb-2">
                    <div class="col-lg-6">
                      <div class="form-group">
                        <label for="firstName">Họ</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" required>
                      </div>
                    </div>

                    <div class="col-lg-6">
                      <div class="form-group">
                        <label for="lastName">Tên</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" required>
                      </div>
                    </div>

                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email"
                               name="email" required>
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="phone">Phone</label>
                        <input type="tel" class="form-control" id="phone" pattern="/(7|8|9)\d{9}/"
                               name="phone" required>
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="dob">Birthday</label>
                        <input type="date" class="form-control" id="dob" name="dob" required>
                      </div>
                    </div>

                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="gender">Giới tính</label>
                        <select id="gender" name="gender" class="form-select">
                          <c:forEach var="g" items="<%=USER_GENDER.Gender%>">
                            <option value="${g.value}" <c:if test="${g.value == user.gender}">selected</c:if>>${g.key}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="address">Địa chỉ</label>
                        <textarea type="text" class="form-control" id="address" name="address"></textarea>
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="userName">User name</label>
                        <input type="text" class="form-control" id="userName"
                               name="userName">
                      </div>
                    </div>

                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password"
                               name="password">
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword"
                               name="confirmPassword">
                      </div>
                    </div>
                    <div class="col-lg-6">
                      <div class="form-group mb-4">
                        <div class="button-group">
                          <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown"><span>Roles</span> <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                            <c:forEach var="role" items="${roles}">
                              <li>
                                <a href="#" class="small" data-value="${role.roleId}" tabIndex="-1">
                                  <input type="checkbox" id="roleCheckBox" name="roleCheckBox" value="${role.roleId}"/>&nbsp;${role.roleName}
                                </a>
                              </li>
                            </c:forEach>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="modal-footer px-4">
                  <button type="button" class="btn btn-secondary btn-pill"
                          data-bs-dismiss="modal">Cancel</button>
                  <button type="button" class="btn btn-primary btn-pill">Save Contact</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="/views/admin/common/footer.jsp"/>
</div>
<jsp:include page="/views/admin/common/common_js.jsp"/>
<script>
  var options = [];

  $( '.dropdown-menu a' ).on( 'click', function( event ) {

    var $target = $( event.currentTarget ),
            val = $target.attr( 'data-value' ),
            $inp = $target.find( 'input' ),
            idx;

    if ( ( idx = options.indexOf( val ) ) > -1 ) {
      options.splice( idx, 1 );
      setTimeout( function() { $inp.prop( 'checked', false ) }, 0);
    } else {
      options.push( val );
      setTimeout( function() { $inp.prop( 'checked', true ) }, 0);
    }

    $( event.target ).blur();

    console.log( options );
    return false;
  });
</script>
</body>
</html>
