<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="brotherhood/edit.do"
	modelAttribute="brotherhoodRegisterForm" method="POST">

	<acme:textbox code="brotherhood.name" path="name" />
	<acme:textbox code="brotherhood.middleName" path="middleName" />
	<acme:textbox code="brotherhood.surName" path="surName" />
	<acme:textbox code="brotherhood.photo" path="photo" />
	<acme:textbox code="brotherhood.email" path="email" />
	<acme:textbox code="brotherhood.phone" path="phone" />
	<acme:textbox code="brotherhood.address" path="address" />
	<acme:textbox code="brotherhood.username" path="username" />
	<acme:password code="brotherhood.password" path="password" />
	<acme:password code="brotherhood.confirmPassword"
		path="confirmPassword" />

	<acme:textbox code="brotherhood.title" path="title" />
	<acme:textbox placeholder="dd/MM/yyyy"
		code="brotherhood.establishmentDate" path="establishmentDate" />
	<acme:textarea code="brotherhood.photos" path="photos" />

	<spring:message code="brotherhood.check" />
	<form:checkbox path="terms" />

	<form:errors path="terms" cssClass="error" />
	<br />
	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<button type="submit" class="btn btn-primary"
				onclick="return validatePhoneNumber()" name="save">
				<spring:message code="brotherhood.save" />
			</button>&nbsp;
		</jstl:when>
		<jstl:otherwise>
			<button type="submit" class="btn btn-primary"
				onclick="return validatePhoneNumberEs()" name="save">
				<spring:message code="brotherhood.save" />
			</button>&nbsp;
		</jstl:otherwise>
	</jstl:choose>

	<acme:cancel url="/#" code="brotherhood.cancel" />
	<script type="text/javascript">
		function validatePhoneNumber() {
			var phoneNumber = document.getElementById("phone");
			if (!(phoneNumber.value).match("\\+\\d{2}([ ]{1}[(]{1}\\d{1,3}[)]{1})? \\d{4,}|\\+\\d{2} \\d{4,}|\\d{4,}|Null")) { return confirm("Phone number doesn't adhere to the correct pattern. Do you want to continue?"); }
		}

		function validatePhoneNumberEs() {
			var phoneNumber = document.getElementById("phone");
			if (!(phoneNumber.value).match("\\+\\d{2}([ ]{1}[(]{1}\\d{1,3}[)]{1})? \\d{4,}|\\+\\d{2} \\d{4,}|\\d{4,}|Null")) { return confirm("El teléfono no se ajusta al patrón correcto. ¿Desea continuar?"); }
		}
	</script>
</form:form>


