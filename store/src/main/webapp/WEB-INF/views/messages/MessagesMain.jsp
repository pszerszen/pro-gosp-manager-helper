<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:baseWrapper activePage="WRITE_MAIL">
    <jsp:attribute name="additionalJs">
        <t:js resource="messaging"/>
    </jsp:attribute>
	<jsp:attribute name="additionalCss">
        <t:css resource="messages" />
    </jsp:attribute>
	<jsp:body>
        <div class="row">
            <t:messageBox type="information"/>
            <t:messageBox type="error"/>
            <c:url var="formUrl" value="messages/send" />
            <f:form id="sendMail" cssClass="form form-horizontal" modelAttribute="model" method="post" action="${formUrl}" role="form">
                <t:formRow></t:formRow>
                <c:url var="mailAutocomplete" value="/messages/mail/autocomplete"/>
                <t:formRow labelName="to-input" labelText="TO">
                    <select id="to-input" class="selectize-mail" placeholder="Start Typing to see options..." data-url="${mailAutocomplete}">
                    </select>
                </t:formRow>
                <t:formRow labelName="cc-input" labelText="CC">
                    <select id="CC-input" class="selectize-mail" placeholder="Start Typing to see options..." data-url="${mailAutocomplete}">
                    </select>
                </t:formRow>
                <t:formRow labelName="messageContent">
                    <f:textarea id="msg-content" path="messageContent" cssClass="form-control" rows="20" />
                </t:formRow>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button id="send-message" type="button" class="btn btn-default">SEND</button>
                    </div>
                </div>

            </f:form>
        </div>
    </jsp:body>
</t:baseWrapper>