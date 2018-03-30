<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:detailsPage activePage="SHOP" idPrefix="shops">

    <jsp:attribute name="additionalJs">
    </jsp:attribute>
    <jsp:attribute name="formContent">
        <t:formRow labelName="name" labelText="Name">
           <f:input path="name" cssClass="form-control capitalised" data-validate="validate(requiredIfEnabled,alphanumeric-spaced,minlength(4))"/>
        </t:formRow>
        <t:formRow labelName="phone" labelText="Phone">
           <f:input path="phone" cssClass="form-control digits-only-input" data-validate="validate(requiredIfEnabled)"/>
        </t:formRow>
        <t:formRow labelName="type" labelText="Type">
           <f:select path="type" cssClass="form-control">
               <c:forEach var="storeType" items="${storeTypes}">
                   <f:option value="${storeType}" cssClass="form-control">${storeType.description}</f:option>
               </c:forEach>
           </f:select>
        </t:formRow>
        <t:formRow labelName="address" labelText="Address">
           <f:textarea path="address" cssClass="form-control" />
        </t:formRow>

        <t:formRow labelName="active" labelText="Active">
           <f:checkbox path="active" cssClass="form-control"/>
        </t:formRow>
    </jsp:attribute>
</t:detailsPage>