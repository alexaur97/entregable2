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
<br/><spring:message code="parade.title"/>: <jstl:out value="${parade.title}"></jstl:out>
<br/><spring:message code="parade.description"/>: <jstl:out value="${parade.description}"></jstl:out>
<br/><spring:message code="parade.moment"/>: <jstl:out value="${parade.moment}"></jstl:out>
<br/><spring:message code="parade.mode"/>: <jstl:out value="${parade.mode}"></jstl:out>
<br/><spring:message code="parade.brotherhood"/>: <jstl:out value="${parade.brotherhood.title}"></jstl:out>

<br/><spring:message code="parade.floats"/>
:<display:table name="floats" id="float"
	requestURI="${requestURI }">
	<display:column titleKey="parade.title" property="title" />

	
</display:table>

