<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Create Brand</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<form action="<%=request.getContextPath()%>/admin/brands" enctype="multipart/form-data" method="post" accept-charset="utf-8">
    <div class="mb-3">
        <label class="form-label" for="brand-name">Name</label>
        <input type="text" class="form-control" id="brand-name" name="brand-name"/>
    </div>
    <div class="mb-3">
        <label class="form-label" for="brand-origin">Origin</label>
        <input type="text" class="form-control" id="brand-origin" name="brand-origin"/>
    </div>
    <div class="mb-3">
        <label class="form-label" for="brand-logo">Logo</label>
        <input type="file" class="form-control" id="brand-logo" name="brand-logo"/>
    </div>
    <button type="submit" class="btn btn-success">Create</button>
</form>
</body>
</html>
