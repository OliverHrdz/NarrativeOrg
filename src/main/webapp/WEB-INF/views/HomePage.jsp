<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

 <!-- automatically reload auction listings 
 <meta http-equiv="refresh" content="3; URL=http://localhost:8080">
 -->

 <title>Auctioning</title>
</head>
<body>

<div>
    <!-- TODO use DataTables jQuery plugin -->
	<table id="auctionTable">
		<thead class="bg-primary">
			<tr>
				<th>Auction #</th>
				<th>Seller</th>
				<th>Item</th>
				<th>Current Bid</th>
				<th>Highest Bidder</th>
				<th>Active</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${model.listings}" var="a" varStatus="loop">
				<tr>
					<td><c:out value="${a.id}" /></td>
					<td><c:out value="${a.seller}" /></td>
					<td><c:out value="${a.item.description}" /></td>
					<td><c:out value="${a.currentBid}" /></td>
					<td><c:out value="${a.highestBidderName}" /></td>
					<td><c:out value="${a.active}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div></div>

<div>
<c:if test="${model.bidResult != null}">
<c:out value="${model.bidResult.message}" />
</c:if>
</div>
<div>
        <form method="POST" action="${pageContext.request.contextPath}/bid">
          User:
          <br>
          <select name="userId">
            <option value="1">Brian</option>
            <option value="2">Patrick</option>
            <option value="3">Oliver</option>
          </select>
          <br>
          Auction ID:
          <br>
          <input type="number" name="auctionId">
          <br>
          Bid Amount:
          <br>
          <input type="number" name="bid">
          <br>
          <br>
          <input type="submit" value="Place Bid"/></td>
        </form>
</div>

</body>
</html>