<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainPage idPrefix="users" activePage="WORKERS">

	<jsp:attribute name="tableColumns">
        <th></th>
        <th>Name</th>
        <th>e-Mail</th>
        <th>Salary</th>
        <th>Bonus</th>
        <th>Started date</th>
        <th>Ended date</th>
        <th>Active</th>
    </jsp:attribute>

	<jsp:attribute name="tableBody">
        <c:forEach var="model" items="${list}">
            <tr>
                <td><input type="checkbox" data-id="${model.id}" data-id="${model.workerId}"></td>
                <td><c:out value="${model.firstName} ${model.lastName}" /></td>
                <td><c:out value="${model.mail}" /></td>
                <td><c:out value="${model.salary}" /></td>
                <td><c:out value="${model.bonus}" /></td>
                <td><c:out value="${model.dateFrom}" /></td>
                <td><c:out value="${model.dateUntil}" /></td>
                <td class="logic-value"><c:out value="${model.active}" /></td>
            </tr>
        </c:forEach>
    </jsp:attribute>
</t:mainPage>