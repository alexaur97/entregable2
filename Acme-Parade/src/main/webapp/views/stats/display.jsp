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



<table>
	<tr>
		<th><spring:message code="stats.brotherhood.area.average" /></th>
		<th><spring:message code="stats.brotherhood.area.min" /></th>
		<th><spring:message code="stats.brotherhood.area.max" /></th>
		<th><spring:message code="stats.brotherhood.area.stddev" /></th>
	</tr>
	<tr>
		<jstl:forEach items="${brotherhoodsPerArea}" var="x">
			<td><jstl:out value="${x}" /></td>
		</jstl:forEach>
	</tr>
</table>

<h4>
	<spring:message code="stats.brotherhoods.countAndRatio" />
	:
</h4>
<ul>
	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<jstl:forEach items="${areasCountAndRatio}" var="x">
				<li><jstl:out value="${x}" /></li>
			</jstl:forEach>
		</jstl:when>
		<jstl:otherwise>
			<jstl:forEach items="${areasCountAndRatioEs}" var="x">
				<li><jstl:out value="${x}" /></li>
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
		<p>*Cinco estrellas indican una aparición de una posición
			determinada*</p>
	</jstl:otherwise>
</jstl:choose>

<spring:message code="stats.records.history.average" />
:
<jstl:out value="${recordsPerHistory[0][0]}" />
<br />
<spring:message code="stats.records.history.min" />
:
<jstl:out value="${recordsPerHistory[0][1]}" />
<br />
<spring:message code="stats.records.history.max" />
:
<jstl:out value="${recordsPerHistory[0][2]}" />
<br />
<spring:message code="stats.records.history.stddev" />
:
<jstl:out value="${recordsPerHistory[0][3]}" />
<br />

<h4>
	<spring:message code="stats.largest.history.brotherhood" />
	:
</h4>
<jstl:out value="${largestHistoryBrotherhood.title}" />
<br />
<h4>
	<spring:message code="stats.larger.history.brotherhoods" />
	:
</h4>
<ol>
	<jstl:forEach items="${largerHistoryBrotherhoods}" var="brotherhood">
		<li><jstl:out value="${brotherhood.title}" /></li>
	</jstl:forEach>
</ol>

<h4><spring:message code="stats.finder" /></h4>

<table>
	<tr>
		<th><spring:message code="stats.finder.avg" /></th>
		<th><spring:message code="stats.finder.min" /></th>
		<th><spring:message code="stats.finder.max" /></th>
		<th><spring:message code="stats.finder.stddev" /></th>
	</tr>
	<tr>
		<td><jstl:out value="${statsFinder[0][0]}" /></td>
		<td><jstl:out value="${statsFinder[0][1]}" /></td>
		<td><jstl:out value="${statsFinder[0][2]}" /></td>
		<td><jstl:out value="${statsFinder[0][3]}" /></td>
	</tr>
</table>

<table>
	<tr>
		<th><spring:message code="stats.finder.empty" /></th>
		<th><spring:message code="stats.finder.nonEmpty" /></th>
	</tr>
	<tr>
			<td><jstl:out value="${emptyVsNonEmpty[0][0]}" /></td>
		<td><jstl:out value="${emptyVsNonEmpty[0][1]}" /></td>
	</tr>
</table>

