<%--
 * action-2.jsp
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


<spring:message code="stats.members.brotherhood.average" />
:
<jstl:out value="${membersPerBrotherhood[0][0]}" />
<br />
<spring:message code="stats.members.brotherhood.min" />
:
<jstl:out value="${membersPerBrotherhood[0][1]}" />
<br />
<spring:message code="stats.members.brotherhood.max" />
:
<jstl:out value="${membersPerBrotherhood[0][2]}" />
<br />
<spring:message code="stats.members.brotherhood.stddev" />
:
<jstl:out value="${membersPerBrotherhood[0][3]}" />
<br />

<h4>
	<spring:message code="stats.largest.brotherhoods" />
	:
</h4>
<ol>
	<jstl:forEach items="${largestBrotherhoods}" var="brotherhood">
		<li><jstl:out value="${brotherhood.title}" /></li>
	</jstl:forEach>
</ol>

<h4>
	<spring:message code="stats.smallest.brotherhoods" />
	:
</h4>
<ol>
	<jstl:forEach items="${smallestBrotherhoods}" var="brotherhood">
		<li><jstl:out value="${brotherhood.title}" /></li>
	</jstl:forEach>
</ol>

<h4>
	<spring:message code="stats.request.ratio" />
	:
</h4>
<spring:message code="stats.approved.request.ratio" />
:
<jstl:out value="${approvedRatio}" />
<br />
<spring:message code="stats.pending.request.ratio" />
:
<jstl:out value="${pendingRatio}" />
<br />
<spring:message code="stats.rejected.request.ratio" />
:
<jstl:out value="${rejectedRatio}" />
<br />
<h4>
	<spring:message code="stats.soon" />
	:
</h4>
<ul>
	<jstl:forEach items="${soon}" var="parade">
		<li><jstl:out value="${parade.title}" /></li>
	</jstl:forEach>
</ul>
<h4>
	<spring:message code="stats.members.ten.percent" />
	:
</h4>
<ul>
	<jstl:forEach items="${members}" var="member">
		<li><jstl:out value="${member.name}" /> <jstl:out
				value="${member.surname}" /></li>
	</jstl:forEach>
</ul>

<spring:message code="stats.brotherhood.area.average" />
:
<jstl:out value="${brotherhoodsPerArea[0][0]}" />
<spring:message code="stats.brotherhood.area.min" />
:
<jstl:out value="${brotherhoodsPerArea[0][1]}" />
<spring:message code="stats.brotherhood.area.max" />
:
<jstl:out value="${brotherhoodsPerArea[0][2]}" />
<spring:message code="stats.brotherhood.area.stddev" />
:
<jstl:out value="${brotherhoodsPerArea[0][3]}" />

<h4>
	<spring:message code="stats.brotherhoods.countAndRatio" />
	:
</h4>
<ul>
	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<jstl:forEach items="${areasCountAndRatio}" var="x">
				<li><jstl:out value="x" /></li>
			</jstl:forEach>
		</jstl:when>
		<jstl:otherwise>
			<jstl:forEach items="${areasCountAndRatioEs}" var="x">
				<li><jstl:out value="x" /></li>
			</jstl:forEach>
		</jstl:otherwise>
	</jstl:choose>
	
</ul>

<jstl:choose>
	<jstl:when test="${lang eq 'en'}">
		<h4>Positions histogram</h4>
		<ul>
			<jstl:forEach items="${positionsHist}" var="p">
				<li><jstl:out value="${p}" /></li>
			</jstl:forEach>
		</ul>
		<p>*Five stars indicates an occurrence of a given position*</p>
	</jstl:when>
	<jstl:otherwise>
		<h4>Histograma de posiciones</h4>
		<ul>
			<jstl:forEach items="${positionsHistEs}" var="p">
				<li><jstl:out value="${p}" /></li>
			</jstl:forEach>
		</ul>
		<p>*Cinco estrellas indican una aparici�n de una posici�n
			determinada*</p>
	</jstl:otherwise>
</jstl:choose>

