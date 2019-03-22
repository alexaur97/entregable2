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

<form:form modelAttribute="legalRecord" action="legalRecord/brotherhood/edit.do">
<form:hidden path="id"/>
<form:hidden path="version"/>
<acme:textbox code="legalRecord.title" path="title"/>
<acme:textarea code="legalRecord.description" path="description"/>
<acme:textarea code="legalRecord.legalName" path="legalName"/>
<acme:textarea code="legalRecord.vatNumber" path="vatNumber"/>
<acme:textarea code="legalRecord.laws" path="laws"/>
<acme:submit name="save" code="legalRecord.save"/>
<acme:cancel url="/legalRecord/brotherhood/display.do?legalRecordId=${legalRecord.id}"
code="legalRecord.cancel" />
</form:form>