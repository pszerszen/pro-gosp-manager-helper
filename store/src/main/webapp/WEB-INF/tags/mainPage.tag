<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="activePage" required="true" %>
<%@ attribute name="idPrefix" required="true" %>

<%@ attribute name="tableColumns" fragment="true" %>
<%@ attribute name="tableBody" fragment="true" %>

<%@ attribute name="noDeleteButton" required="false" %>

<t:baseWrapper activePage="${activePage}">
    <jsp:attribute name="additionalJs">
        <t:js resource="mainPage"/>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <t:messageBox type="information"/>
            <t:messageBox type="error"/>
            <f:form id="${idPrefix}-search-form" cssClass="form form-horizontal">
                <t:formRow/>
                <div class="form-group">
                    <div class="col-sm-2">
                        <button type="button" class="btn btn-default searcher">APPLY</button>
                    </div>
                    <div class="col-sm-10">
                        <input type="text" id="${idPrefix}-term-search" class="form-control" placeholder="Search">
                    </div>
                </div>
                <div class="table-responsive">
                    <table id="${idPrefix}-table" class="table table-striped data-table main-table">
                        <thead>
                        <tr>
                            <jsp:invoke fragment="tableColumns"/>
                        </tr>
                        </thead>
                        <tbody>
                        <jsp:invoke fragment="tableBody"/>
                        </tbody>
                    </table>
                </div>

                <div class="form-group">
                    <div class="col-sm-10">
                        <c:url var="addLink" value="/${idPrefix}/new"/>
                        <a href="${addLink}" class="btn btn-default">ADD</a>
                    </div>
                </div>
                <c:if test="${!noDeleteButton}">
                    <div class="form-group">
                        <div class="col-sm-10">
                            <button id="remove-button" type="button" class="btn btn-default dynamic-button disabled">REMOVE</button>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <div class="col-sm-10">
                        <button id="edit-button" type="button" class="btn btn-default dynamic-button disabled">EDIT</button>
                    </div>
                </div>

            </f:form>
        </div>
    </jsp:body>
</t:baseWrapper>