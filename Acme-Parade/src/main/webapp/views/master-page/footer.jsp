<%--
 * footer.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<a href="about/terms.do"><spring:message code="masterpage.terms" /></a>
<a href="about/cookies.do"><spring:message code="masterpage.cookies" /></a>
<a href="about/contact.do"><spring:message code="masterpage.contact" /></a>
<a href="about/privacy.do"><spring:message code="masterpage.privacy" /></a>


<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme Parade Co., Inc.</b>