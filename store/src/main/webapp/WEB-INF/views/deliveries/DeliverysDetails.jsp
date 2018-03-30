<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:detailsPage activePage="DELIVERY" idPrefix="delivery">
    <jsp:attribute name="additionalJs">
    </jsp:attribute>
    <jsp:attribute name="formContent">
        <t:autocomplete labelName="product" labelText="Product" path="product" url="/products/json/autocomplete" />
        <t:autocomplete labelName="store" labelText="Store" path="store" url="/shops/json/autocomplete" />
        <t:formRow labelName="quantity" labelText="Quantity">
            <input class="form-control" name="quantity"
                   type="number" min="1" value="${model.quantity}">
        </t:formRow>
        <t:formRow labelName="status" labelText="Status">
            <f:select path="status" cssClass="form-control">
                <c:forEach var="deliveryStatus" items="${deliveryStatuses}">
                    <f:option  value="${deliveryStatus}">${deliveryStatus}</f:option>
                </c:forEach>
            </f:select>
        </t:formRow>
        <t:formRow labelName="date" labelText="Date">
            <f:input path="date" cssClass="form-control datetimepicker" data-validate="validate(requiredIfEnabled)"/>
        </t:formRow>
    </jsp:attribute>
</t:detailsPage>