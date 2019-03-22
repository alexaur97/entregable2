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

<acme:display code="legalRecord.title" value="${legalRecord.title}"/>
<acme:display code="legalRecord.description" value="${legalRecord.description}"/>
<acme:display code="legalRecord.legalName" value="${legalRecord.legalName}"/>
<acme:display code="legalRecord.vatNumber" value="${legalRecord.vatNumber}"/>

<br />
<spring:message code="legalRecord.laws" />
:
<ul>
	<jstl:forEach items="${legalRecord.laws}" var="x">
		<li><a href="${x}"><jstl:out value="${x}"/></a></li>
	</jstl:forEach>
</ul>
<acme:button url="/legalRecord/brotherhood/edit.do?legalRecordId=${legalRecord.id}" code="legalRecord.edit"/>
<acme:cancel url="/history/brotherhood/myList.do"
code="legalRecord.cancel" />