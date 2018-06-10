<%--
  Created by IntelliJ IDEA.
  User: codingsquid
  Date: 2018. 6. 8.
  Time: PM 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    h1 {
        color: blue;
    }
    h2, #info {
        color: green;
    }
    h3 {
        color: red;
    }
    #order {
        color: blue;
    }
    h4 {
        float: right;
        color: navy;
    }
</style>
<head>
    <title>Url Shortening</title>
</head>
<body bgcolor="#ffff0ff">
    <div align="center">
        <h1>THE LINK KNOWS ALL. SO CAN YOU.</h1>
        <h2>Measure your links with localhost, the world's leading link management platform.</h2>
        <hr>
        <form name="urlForm" action="${pageContext.request.contextPath}/generate/shortening/url" method="POST">
            <table border="1" height="100">
                <tr height="60">
                    <td><input type="text" name="targetUrl" size="80" style="height: 100%; font-size: large;" placeholder="Paste a link to shorten it"></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="SHORTEN" />
                        <input type="reset" value="CLEAR" />
                    </td>
                </tr>
            </table>
            <h3 id="info">URL shortening</h3>
            <%
                /*
                 * isValidUrl의 해당하는 값이 존재하고 그 값이 FALSE이면 유효한 url이 아니라고 출력
                 * url이 유효하다면 shortening된 url을 출력
                 */
                if (request.getAttribute("isValidUrl") != null) {
                    out.println(request.getAttribute("isValidUrl").toString());
                }
                else if (request.getAttribute("shortenedUrl") == null) {
                    out.println("<h3 id='order'>Write url</h3>");
                }
                else {
                    out.println("<h3 id='link'><a href='" + request.getAttribute("originalUrl") + "'>"
                            + request.getAttribute("shortenedUrl") + "</a></h3>");
                }
            %>
        </form>
        <hr>
        <h4>By CodingSquid(코딩하는 오징어)</h4>
    </div>
</body>
</html>
