<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.GameRecord, java.util.List, dao.GameRecordsDAO"%>
<%@ page import="model.UserCredential"%>
<%
UserCredential loginUser = (UserCredential) session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>チップランキング</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body class="body">

    <%
    GameRecordsDAO dao = new GameRecordsDAO();
    List<GameRecord> records = dao.getAllUserChipRecords();
    %>

    <div class="header">
        <p class="header-link-left"">
            ログイン中のユーザー：<%=loginUser.getUserName()%>さん
        </p>
        <a href="MenuServlet" class="header-link-right">メニューへ戻る</a>
    </div>

    <div class="main-wrapper">
        <div class="container-adjustment">
            <table class="styled-table">
                <thead>
                    <tr>
                        <th colspan="3">チップランキング(上位5名)</th>
                    </tr>
                    <tr>
                        <th>順位</th>
                        <th>ユーザー</th>
                        <th>所持チップ数</th>
                    </tr>
                </thead>
                <%
                int rank = 1;
                for (GameRecord record : records) {
                    if (rank <= 5) {
                %>
                <tr>
                    <td><%=rank++%></td>
                    <td><%=record.getUserName()%></td>
                    <td><%=record.getChips()%></td>  <!-- チップの所持数 -->
                </tr>
                <%
                    }
                }
                %>
            </table>
        </div>
    </div>

    <footer class="footer">
        <p class="copyright">Copyright © 2023 Hiroki Suzuki</p>
    </footer>

</body>
</html>
