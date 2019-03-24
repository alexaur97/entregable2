<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:display code="periodRecord.title" value="${periodRecord.title}"/>
<acme:display code="periodRecord.description" value="${periodRecord.description}"/>
<acme:display code="periodRecord.startYear" value="${periodRecord.startYear}"/>
<acme:display code="periodRecord.endYear" value="${periodRecord.endYear}"/>

<br />
<spring:message code="periodRecord.pictures" />
:
<ul>
	<jstl:forEach items="${periodRecord.pictures}" var="x">
		<li><a href="${x}"><jstl:out value="${x}"/></a></li>
	</jstl:forEach>
</ul>
<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:button
		url="/periodRecord/brotherhood/edit.do?periodRecordId=${periodRecord.id}"
		code="periodRecord.edit" />
	<acme:cancel url="/history/brotherhood/myList.do"
		code="periodRecord.cancel" />
</security:authorize>
<security:authorize access="isAnonymous()">
	<acme:cancel url="/history/list.do"
		code="periodRecord.cancel" />
</security:authorize>