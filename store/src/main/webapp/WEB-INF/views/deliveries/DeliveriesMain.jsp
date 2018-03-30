<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainPage idPrefix="delivery" activePage="DELIVERY">

	<jsp:attribute name="tableColumns">
        <th></th>
        <th>Store</th>
        <th>Product</th>
        <th>Date</th>
        <th>Status</th>
    </jsp:attribute>

	<jsp:attribute name="tableBody">
        <c:forEach var="model" items="${list}">
            <tr class="data-table-row">
                <td><input type="checkbox" class="deliveries-checkbox" data-id="${model.id}" data-product-id="${model.product.id}"
                           data-store-id="${model.store.id}"></td>
                <td><c:out value="${model.store.name}"/></td>
                <td><c:out value="${model.product.name}"/></td>
                <td><c:out value="${model.date}"/></td>
                <td class="delivery-status"><c:out value="${model.status}"/></td>
            </tr>
        </c:forEach>
    </jsp:attribute>
</t:mainPage>