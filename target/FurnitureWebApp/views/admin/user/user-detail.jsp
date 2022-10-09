<%@ page import="utils.HtmlClassUtils" %>
<%@ page import="utils.constants.USER_GENDER" %>
<%@ page import="utils.constants.USER_STATUS" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<jsp:useBean id="user" scope="request" class="view_models.users.UserViewModel"/>
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
                                <h1>User Profile</h1>
                                <p class="breadcrumbs"><span><a href="<%=request.getContextPath()%>/admin/home">Home</a></span>
                                    <span><i class="mdi mdi-chevron-right"></i></span>Profile
                                </p>
                            </div>
                            <div>
                                <a href="<%=request.getContextPath()%>/admin/users" class="btn btn-primary">Danh sách người dùng</a>
                            </div>
                        </div>
                        <div class="card bg-white profile-content">
                            <div class="row">
                                <div class="col-lg-4 col-xl-3">
                                    <div class="profile-content-left profile-left-spacing">
                                        <div class="text-center widget-profile px-0 border-0">
                                            <div class="card-img mx-auto rounded-circle">
                                                <img src="data:image/png;base64, ${user.avatar}" alt="user image">
                                            </div>
                                            <div class="card-body">
                                                <h4 class="py-2 text-dark">${user.firstName}<span> ${user.lastName}</span></h4>
                                                <p>${user.email}</p>
                                            </div>
                                        </div>

                                        <div class="d-flex justify-content-between ">
                                            <div class="text-center pb-4">
                                                <h6 class="text-dark pb-2">${user.totalBought}</h6>
                                                <p>Sản phẩm đã mua</p>
                                            </div>

                                            <div class="text-center pb-4">
                                                <h6 class="text-dark pb-2">${user.totalWishListItem}</h6>
                                                <p>Sản phẩm yêu thích</p>
                                            </div>
                                        </div>

                                        <hr class="w-100">

                                        <div class="contact-info pt-4">
                                            <h5 class="text-dark">Thông tin liên hệ</h5>
                                            <p class="text-dark font-weight-medium pt-24px mb-2">Email</p>
                                            <p>${user.email}</p>
                                            <p class="text-dark font-weight-medium pt-24px mb-2">Điện thoại</p>
                                            <p>${user.phone}</p>
                                            <p class="text-dark font-weight-medium pt-24px mb-2">Ngày sinh</p>
                                            <p>${user.dateOfBirth}</p>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-8 col-xl-9">
                                    <div class="profile-content-right profile-right-spacing py-5">
                                        <ul class="nav nav-tabs px-3 px-xl-5 nav-style-border" id="myProfileTab" role="tablist">
                                            <li class="nav-item" role="presentation">
                                                <button class="nav-link active" id="profile-tab" data-bs-toggle="tab"
                                                        data-bs-target="#profile" type="button" role="tab"
                                                        aria-controls="profile" aria-selected="true">Profile</button>
                                            </li>
                                        </ul>
                                        <div class="tab-content px-3 px-xl-5" id="myTabContent">
                                            <div class="tab-pane fade show active" id="profile" role="tabpanel"
                                                 aria-labelledby="profile-tab">
                                                <div class="tab-widget mt-5">
                                                    <form action="<%=request.getContextPath()%>/admin/user/edit" enctype="multipart/form-data" method="post">
                                                        <input type="hidden" name="userId" id="userId" value="${user.id}"/>
                                                        <div class="row ec-vendor-uploads mb-4">
                                                            <label class="col-12 col-form-label" for="avatar">Avatar</label>
                                                            <div class="ec-vendor-img-upload">
                                                                <div class="ec-vendor-main-img">
                                                                    <div class="thumb-upload-set col-md-12">
                                                                        <div class="thumb-upload">
                                                                            <div class="thumb-edit">
                                                                                <input type='file' id="avatar" name="avatar"
                                                                                       class="ec-image-upload"
                                                                                       accept=".png, .jpg, .jpeg"/>
                                                                                <label for="avatar">
                                                                                    <img src="<%=request.getContextPath()%>/assets/admin/img/icons/edit.svg"
                                                                                         class="svg_img header_svg" alt="edit"/>
                                                                                </label>
                                                                            </div>
                                                                            <div class="thumb-preview ec-preview">
                                                                                <div class="image-thumb-preview">
                                                                                    <img class="image-thumb-preview ec-image-preview clear-img"
                                                                                         src="data:image/png;base64, ${user.avatar}"
                                                                                         alt="edit" />
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="row mb-2">
                                                            <div class="col-lg-4">
                                                                <div class="form-group">
                                                                    <label for="firstName">Họ</label>
                                                                    <input type="text" class="form-control" id="firstName"
                                                                           name="firstName" value="${user.firstName}">
                                                                </div>
                                                            </div>

                                                            <div class="col-lg-4">
                                                                <div class="form-group">
                                                                    <label for="lastName">Tên</label>
                                                                    <input type="text" class="form-control" id="lastName"
                                                                           name="lastName" value="${user.lastName}">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <div class="form-group mb-4">
                                                                    <label for="gender">Giới tính</label>
                                                                    <select id="gender" name="gender" class="form-select">
                                                                        <c:forEach var="g" items="<%=USER_GENDER.Gender%>">
                                                                            <option value="${g.value}" <c:if test="${g.value == user.gender}">selected</c:if>>${g.key}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row mb-2">
                                                            <div class="col-lg-4">
                                                                <div class="form-group mb-4">
                                                                    <label for="userName">User name</label>
                                                                    <input type="text" class="form-control" id="username"
                                                                           name="username" value="${user.username}">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-4">
                                                                <div class="form-group mb-4">
                                                                    <label for="password">Password</label>
                                                                    <input type="password" class="form-control" id="password"
                                                                           name="password" value="${user.password}">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-2">
                                                                <div class="form-group mb-4">
                                                                    <div class="dropdown button-group">
                                                                        <button type="button" class="btn btn-default btn-sm dropdown-toggle"  data-bs-toggle="dropdown">Roles</button>
                                                                        <ul class="dropdown-menu">
                                                                            <c:forEach var="role" items="${roles}">
                                                                                <li>
                                                                                    <a href="#" class="small" data-value="${role.roleId}" tabIndex="-1">
                                                                                        <input type="checkbox" id="roleCheckBox-${role.roleId}" name="roleCheckBox" value="${role.roleId}" data-roleId="${role.roleId}" <c:if test="${user.roleIds.contains(role.roleId)}">checked</c:if>/>&nbsp;${role.roleName}
                                                                                    </a>
                                                                                </li>
                                                                            </c:forEach>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-2">
                                                                <div class="form-group mb-4">
                                                                    <label for="status">Trạng thái</label>
                                                                    <select id="status" name="status" class="form-select">
                                                                        <c:forEach var="s" items="<%=USER_STATUS.Status%>">
                                                                            <option value="${s.value}" <c:if test="${s.value == user.status}">selected</c:if>>${s.key}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row mb-2">
                                                            <div class="col-lg-6">
                                                                <div class="form-group mb-4">
                                                                    <label for="email">Email</label>
                                                                    <input type="email" class="form-control" id="email"
                                                                           name="email" value="${user.email}">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="form-group">
                                                                    <label for="phone">Điện thoại</label>
                                                                    <input type="text" class="form-control" id="phone"
                                                                           name="phone" value="${user.phone}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="row mb-2">
                                                            <div class="col-lg-6">
                                                                <div class="form-group mb-4">
                                                                    <label for="dob">Ngày sinh</label>
                                                                    <input type="date" class="form-control" id="dob"
                                                                           name="dob" value="${user.dateOfBirth}">
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6">
                                                                <div class="form-group mb-4">
                                                                    <label for="address">Địa chỉ</label>
                                                                    <input type="text" class="form-control" id="address" name="address" value="${user.address}" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="d-flex justify-content-end mt-5">
                                                            <button type="submit"
                                                                    class="btn btn-primary mb-2 btn-pill">Cập nhật</button>
                                                        </div>
                                                    </form>
                                                </div>
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
            var options = [];
            let $boxes = $('input[name=roleCheckBox]:checked')
            $boxes.each(s => {
                options.push(s.val())
            });

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
