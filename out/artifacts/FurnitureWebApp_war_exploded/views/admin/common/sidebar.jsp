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
        </li>
        <!-- Roles -->
        <li class="has-sub">
          <a class="sidenav-item-link" href="<%=request.getContextPath()%>/admin/roles">
            <i class="mdi mdi-account-group"></i>
            <span class="nav-text">Roles</span>
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
      </ul>
    </div>
  </div>
</div>
