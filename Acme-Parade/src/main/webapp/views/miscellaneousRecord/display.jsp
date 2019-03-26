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

<spring:message code="miscellaneousRecord.title" /> : <jstl:out value="${miscellaneousRecord.title}" />
<br/>
<spring:message code="miscellaneousRecord.description" /> : <jstl:out value="${miscellaneousRecord.description}" />
<br/>
<br/>
<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:button
		url="/miscellaneousRecord/brotherhood/edit.do?miscellaneousRecordId=${miscellaneousRecord.id}"
		code="miscellaneousRecord.edit" />
	<acme:cancel url="/history/brotherhood/myList.do"
		code="miscellaneousRecord.cancel" />
</security:authorize>
<security:authorize access="isAnonymous()">
	<acme:cancel url="/history/list.do"
		code="miscellaneousRecord.cancel" />
</security:authorize>