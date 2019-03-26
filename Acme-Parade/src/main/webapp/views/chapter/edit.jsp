<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%> 

<form:form action="chapter/edit.do" modelAttribute="chapterRegisterForm" method="POST">

<acme:textbox code="chapter.name" path="name"/>
<acme:textbox code="chapter.middleName" path="middleName"/>
<acme:textbox code="chapter.surName" path="surName"/>
<acme:textbox code="chapter.photo" path="photo"/>
<acme:textbox code="chapter.email" path="email"/>
<acme:textbox code="chapter.phone" path="phone"/>
<acme:textbox code="chapter.address" path="address"/>
<acme:textbox code="chapter.username" path="username"/>
<acme:password code="chapter.password" path="password"/>
<acme:password code="chapter.confirmPassword" path="confirmPassword"/>

<acme:textbox code="chapter.title" path="title" />

<spring:message code="chapter.check"/><form:checkbox path="terms"/>

<form:errors path="terms" cssClass="error" />
<br/>
<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<button type="submit" onclick="return validatePhoneNumber()" name="save">
				<spring:message code="chapter.save" />
			</button>
		</jstl:when>
		<jstl:otherwise>
			<button type="submit" onclick=" return validatePhoneNumberEs()" name="save">
				<spring:message code="chapter.save" />
			</button>
		</jstl:otherwise>
	</jstl:choose>
<acme:cancel url="/#" code="chapter.cancel"/>
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

