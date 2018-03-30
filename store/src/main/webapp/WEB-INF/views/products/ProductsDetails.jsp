<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:detailsPage activePage="PRODUCTS" idPrefix="products">

    <jsp:attribute name="additionalJs">
    </jsp:attribute>
    <jsp:attribute name="formContent">
        <t:formRow labelName="name" labelText="Name">
            <f:input path="name" cssClass="form-control capitalised" data-validate="validate(requiredIfEnabled,alphanumeric,minlength(3))"/>
        </t:formRow>
        <t:formRow labelName="model" labelText="Model">
            <f:input path="model" cssClass="form-control capitalised" data-validate="validate(requiredIfEnabled,alphanumeric,minlength(2))"/>
        </t:formRow>
        <t:formRow labelName="producer" labelText="Producer">
            <f:input path="producer" cssClass="form-control capitalised" data-validate="validate(requiredIfEnabled,alphanumeric,minlength(4))"/>
        </t:formRow>
        <t:formRow labelName="purchasePrice" labelText="Purchase Price">
            <f:input path="purchasePrice" cssClass="form-control price-input" data-validate="validate(requiredIfEnabled,valid_price)"/>
        </t:formRow>
        <t:formRow labelName="salesPrice" labelText="Sales Price">
            <f:input path="salesPrice" cssClass="form-control price-input" data-validate="validate(requiredIfEnabled,valid_price)"/>
        </t:formRow>
        <t:formRow labelName="active" labelText="Active">
            <f:checkbox path="active" cssClass="form-control"/>
        </t:formRow>
    </jsp:attribute>
</t:detailsPage>