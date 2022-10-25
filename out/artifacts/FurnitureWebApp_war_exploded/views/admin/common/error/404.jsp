<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 10/1/2022
  Time: 12:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>

        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="description" content="Ekka - Admin Dashboard HTML Template.">

        <title>Ekka - Admin Dashboard</title>
        <jsp:include page="/views/admin/common/common_css.jsp"/>
    </head>
    <body class="ec-header-fixed ec-sidebar-fixed ec-sidebar-dark ec-header-light" id="body">
        <div class="wrapper">
            <jsp:include page="/views/admin/common/sidebar.jsp"/>
            <div class="ec-page-wrapper">
                <jsp:include page="/views/admin/common/header.jsp"/>
                <div class="ec-content-wrapper">
                    <div class="ec-content-wrapper">
                        <div class="content">
                            <div class="error-wrapper border bg-white px-5">
                                <div class="row justify-content-center align-items-center text-center">
                                    <div class="col-xl-4">
                                        <h1 class="text-primary bold error-title">404</h1>
                                        <p class="pt-4 pb-5 error-subtitle">Looks like something went wrong.</p>
                                        <a href="<%=request.getContextPath()%>/views/admin/index.jsp" class="btn btn-primary btn-pill">Back to Home</a>
                                    </div>

                                    <div class="col-xl-6 pt-5 pt-xl-0 text-center">
                                        <img src="<%=request.getContextPath()%>/assets/admin/img/lightenning.png" class="img-fluid" alt="Error Page Image">
                                    </div>
                                </div>
                            </div>
                        </div> <!-- End Content -->
                    </div>
                </div>
                <jsp:include page="/views/admin/common/footer.jsp"/>
            </div>
        </div>
        <jsp:include page="/views/admin/common/common_js.jsp"/>
    </body>
</html>
