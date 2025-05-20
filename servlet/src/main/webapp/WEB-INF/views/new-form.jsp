<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>new-form</title>
</head>
<body>
<!--
WEB-INF 밑에 있는 자원들은 외부에서 직접 호출해도 노출되지 않는다.
상대 경로 사용, 기본 경로 + /save -->
<form action="save" method="post">
    username: <input type="text" name="username" />
    age:      <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
</body>
</html>
