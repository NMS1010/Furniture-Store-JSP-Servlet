<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="ec-left-sidebar ec-bg-sidebar">
  <div id="sidebar" class="sidebar ec-sidebar-footer">

    <div class="ec-brand">
      <a href="<%=request.getContextPath()%>/views/admin/index.jsp" title="Ekka">
        <img class="ec-brand-icon" src="<%=request.getContextPath()%>/assets/admin/img/logo/ec-site-logo.png" alt="" />
        <span class="ec-brand-name text-truncate">Ekka</span>
      </a>
    </div>

    <!-- begin sidebar scrollbar -->
    <div class="ec-navigation" data-simplebar>
      <!-- sidebar menu -->
      <ul class="nav sidebar-inner" id="sidebar-menu">
        <!-- Dashboard -->
        <li class="active">
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/home">
            <i class="mdi mdi-view-dashboard-outline"></i>
            <span class="nav-text">Dashboard</span>
          </a>
          <hr>
        </li>

        <!-- Users -->
        <li class="has-sub">
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/users">
            <i class="mdi mdi-account-group"></i>
            <span class="nav-text">Users</span>
          </a>
          <hr>
        </li>

        <!-- Category -->
        <li class="has-sub">
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/categories">
            <i class="mdi mdi-dns-outline"></i>
            <span class="nav-text">Categories</span>
          </a>
        </li>

        <!-- Products -->
        <li class="has-sub">
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/products">
            <i class="mdi mdi-palette-advanced"></i>
            <span class="nav-text">Products</span>
          </a>
        </li>

        <!-- Orders -->
        <li class="has-sub">
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/orders">
            <i class="mdi mdi-cart"></i>
            <span class="nav-text">Orders</span>
          </a>
        </li>

        <!-- Reviews -->
        <li>
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/reviews" >
            <i class="mdi mdi-star-half"></i>
            <span class="nav-text">Reviews</span>
          </a>
        </li>

        <!-- Brands -->
        <li>
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/brands">
            <i class="mdi mdi-tag-faces"></i>
            <span class="nav-text">Brands</span>
          </a>
        </li>

        <!-- Discounts -->
        <li>
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/discounts">
            <i class="mdi mdi-sale"></i>
            <span class="nav-text">Discount</span>
          </a>
          <hr>
        </li>

        <!-- Authentication -->
        <li class="has-sub">
          <a class="sidenav-item-link" href="javascript:void(0)">
            <i class="mdi mdi-login"></i>
            <span class="nav-text">Authentication</span> <b class="caret"></b>
          </a>
          <div class="collapse">
            <ul class="sub-menu" id="authentication" data-parent="#sidebar-menu">
              <li class="">
                <a href="sign-in.html">
                  <span class="nav-text">Sign In</span>
                </a>
              </li>
              <li class="">
                <a href="sign-up.html">
                  <span class="nav-text">Sign Up</span>
                </a>
              </li>
            </ul>
          </div>
        </li>

        <!-- Icons -->
        <li class="has-sub">
          <a class="sidenav-item-link" href="javascript:void(0)">
            <i class="mdi mdi-diamond-stone"></i>
            <span class="nav-text">Icons</span> <b class="caret"></b>
          </a>
          <div class="collapse">
            <ul class="sub-menu" id="icons" data-parent="#sidebar-menu">
              <li class="">
                <a class="sidenav-item-link" href="material-icon.html">
                  <span class="nav-text">Material Icon</span>
                </a>
              </li>
              <li class="">
                <a class="sidenav-item-link" href="font-awsome-icons.html">
                  <span class="nav-text">Font Awsome Icon</span>
                </a>
              </li>
              <li class="">
                <a class="sidenav-item-link" href="flag-icon.html">
                  <span class="nav-text">Flag Icon</span>
                </a>
              </li>
            </ul>
          </div>
        </li>

        <!-- Other Pages -->
        <li class="has-sub">
          <a class="sidenav-item-link" href="javascript:void(0)">
            <i class="mdi mdi-image-filter-none"></i>
            <span class="nav-text">Other Pages</span> <b class="caret"></b>
          </a>
          <div class="collapse">
            <ul class="sub-menu" id="otherpages" data-parent="#sidebar-menu">
              <li class="has-sub">
                <a href="404.html">404 Page</a>
              </li>
            </ul>
          </div>
        </li>
      </ul>
    </div>
  </div>
</div>
