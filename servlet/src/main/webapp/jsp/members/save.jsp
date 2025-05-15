<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>

<%
    //인텔리제이 무료판은 jsp 활성화(?) 지원 안함
    //request, response 는 그냥 사용 가능

    MemberRepository memberRepository = MemberRepository.getInstance();

    String username = req.getParameter("username");
    int age = Integer.parseInt(req.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>

<html>
<head>
    <title>save</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%> </li>
    <li>username=<%=member.getUsername()%> </li>
    <li>age=<%=member.getAge()%></li>
</ul>
</body>
</html>
