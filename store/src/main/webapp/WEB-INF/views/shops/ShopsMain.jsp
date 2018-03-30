<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainPage idPrefix="shops" activePage="SHOP">

	<jsp:attribute name="tableColumns">
        <th></th>
        <th>Name</th>
        <th>Phone</th>
        <th>Type</th>
        <th>Address</th>
        <th>Active</th>
    </jsp:attribute>

	<jsp:attribute name="tableBody">
        <c:forEach var="model" items="${list}">
            <tr>
                <td><input type="checkbox" data-id="${model.id}"></td>
                <td><c:out value="${model.name}" /></td>
                <td><c:out value="${model.phone}" /></td>
                <td><c:out value="${model.type.description}" /></td>
                <td><c:out value="${model.address}" /></td>
                <td class="logic-value"><c:out value="${model.active}" /></td>
            </tr>
        </c:forEach>
    </jsp:attribute>
</t:mainPage>