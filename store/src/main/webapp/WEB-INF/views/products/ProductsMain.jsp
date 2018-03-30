<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainPage idPrefix="products" activePage="PRODUCTS" noDeleteButton="true">

    <jsp:attribute name="tableColumns">
        <th></th>
        <th>Name</th>
        <th>Model</th>
        <th>Producer</th>
        <th>Purchase Price</th>
        <th>Sales Price</th>
        <th>Active</th>
    </jsp:attribute>

    <jsp:attribute name="tableBody">
        <c:forEach var="model" items="${list}">
            <tr>
                <td><input type="checkbox" data-id="${model.id}"></td>
                <td><c:out value="${model.name}"/></td>
                <td><c:out value="${model.model}"/></td>
                <td><c:out value="${model.producer}"/></td>
                <td><c:out value="${model.purchasePrice}"/></td>
                <td><c:out value="${model.salesPrice}"/></td>
                <td class="logic-value"><c:out value="${model.active}" /></td>
            </tr>
        </c:forEach>
    </jsp:attribute>
</t:mainPage>