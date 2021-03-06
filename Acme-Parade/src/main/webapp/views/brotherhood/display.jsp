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

<spring:message code="brotherhood.title" /> : <jstl:out value = "${brotherhood.title}" />
<br>
<spring:message code="brotherhood.establishmentDate" /> : <jstl:out value = "${brotherhood.establishmentDate}" />

<br />
<spring:message code="brotherhood.photo" />
:
<ul>
	<jstl:forEach items="${brotherhood.photos}" var="x">
		<li><a href="${x}"><jstl:out value="${x}" /></a></li>
	</jstl:forEach>
</ul>
<br />
<spring:message code="brotherhood.members" />
:
<ul>
	<jstl:forEach items="${brotherhood.members}" var="x">
		<li><a href="member/display.do?memberId=${x.id}"><jstl:out value="${x.name} ${x.surname}" /></a></li>
	</jstl:forEach>
</ul>
<br>
<br>
<br>

<acme:cancel url="/brotherhood/list.do" code="brotherhood.cancel" />
