<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainPage idPrefix="transactions" activePage="TRANSACTIONS">

	<jsp:attribute name="tableColumns">
        <th></th>
        <th>Staff</th>
        <th>Product</th>
        <th>Store</th>
        <th>Quantity</th>
        <th>Date</th>
    </jsp:attribute>

	<jsp:attribute name="tableBody">
        <c:forEach var="model" items="${list}">
            <tr>
                <td><input type="checkbox" data-id="${model.id}" data-staff-id="${model.staff.id}" data-product-id="${model.product.id}" data-store-id="${model.store.id}"></td>
                <td><c:out value="${model.staff.name}" /></td>
                <td><c:out value="${model.product.name}" /></td>
                <td><c:out value="${model.store.name}" /></td>
                <td><c:out value="${model.quantity}" /></td>
                <td><c:out value="${model.date}" /></td>
            </tr>
        </c:forEach>
    </jsp:attribute>
</t:mainPage>