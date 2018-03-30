<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="idPrefix" required="true" %>
<%@attribute name="summaries" required="true" type="java.util.List<com.manager.store.model.ModelSummary>" %>
<%@attribute name="additionalCss" required="false" %>

<table id="${idPrefix}-table" class="table table-striped data-table-summary main-table ${additionalCss}">
    <thead>
    <tr>
        <th></th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="summary" items="${summaries}">
            <tr>
                <td><input type="checkbox" data-id="${summary.id}"></td>
                <td><c:out value="${summary.name}"/></td>
                <td><c:out value="${summary.description}"/></td>
            </tr>
        </c:forEach>
    </tbody>
</table>