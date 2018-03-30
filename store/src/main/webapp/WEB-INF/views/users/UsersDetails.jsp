<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="isWorker" value="${empty model.workerId}"/>
<t:detailsPage activePage="WORKERS" idPrefix="users">
    <jsp:attribute name="additionalJs">
        <t:js resource="userDetails"/>
    </jsp:attribute>
    <jsp:attribute name="formContent">
        <t:formRow labelName="mail" labelText="E-mail">
            <f:input path="mail" cssClass="form-control" data-validate="validate(valid_requiredIfVisible,email)"/>
        </t:formRow>
        <t:formRow labelName="type" labelText="User Type">
            <f:select path="type" cssClass="form-control">
                <f:options items="${userTypes}"/>
            </f:select>
        </t:formRow>
        <t:formRow labelName="worker-checkbox" labelText="Worker">
            <input class="form-control" type="checkbox" id="worker-checkbox" ${isWorker ? 'checked="checked"' : ''}>
        </t:formRow>
        <div id="worker-section">
            <f:hidden path="workerId"/>
            <t:formRow labelName="firstName" labelText="First Name">
                <f:input path="firstName" cssClass="form-control capitalised"
                         data-validate="validate(valid_requiredIfVisible,alphanumeric,minlength(3))"/>
            </t:formRow>
            <t:formRow labelName="lastName" labelText="Last Name">
                <f:input path="lastName" cssClass="form-control capitalised"
                         data-validate="validate(valid_requiredIfVisible,alphanumeric,minlength(3))"/>
            </t:formRow>
            <t:formRow labelName="salary" labelText="Salary">
                <f:input path="salary" cssClass="form-control price-input" data-validate="validate(valid_requiredIfVisible,valid_price)"/>
            </t:formRow>
            <t:formRow labelName="bonus" labelText="Bonus">
                <f:input path="bonus" cssClass="form-control"/>
            </t:formRow>

            <c:choose>
                <c:when test="${isAdmin}">
                    <t:autocomplete labelName="store" labelText="Store" path="store" url="/shops/json/autocomplete"/>
                </c:when>
                <c:otherwise>
                    <t:formRow labelName="store" labelText="Store">
                        <f:input path="store.name" cssClass="form-control" disabled="true"/>
                        <f:hidden path="store.id"/>
                    </t:formRow>
                </c:otherwise>
            </c:choose>

            <t:datepicker labelName="dateFrom" labelText="Work from" withTime="false"/>
            <t:datepicker labelName="dateUntil" labelText="Work until" withTime="false"/>
            <t:formRow labelName="active" labelText="Active">
                <f:checkbox path="active" cssClass="form-control"/>
            </t:formRow>
            <c:if test="${isAdmin && (pageMode eq 'edit')}">
                <t:formRow/>
                <t:formRow>
                    <button id="reset-password-button" class="btn btn-warning">RESET PASSWORD</button>
                </t:formRow>
            </c:if>
        </div>
    </jsp:attribute>
</t:detailsPage>