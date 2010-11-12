<%--

    MOTECH PLATFORM OPENSOURCE LICENSE AGREEMENT

    Copyright (c) 2010 The Trustees of Columbia University in the City of
    New York and Grameen Foundation USA.  All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    1. Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

    3. Neither the name of Grameen Foundation USA, Columbia University, or
    their respective contributors may be used to endorse or promote products
    derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY GRAMEEN FOUNDATION USA, COLUMBIA UNIVERSITY
    AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
    BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
    FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL GRAMEEN FOUNDATION
    USA, COLUMBIA UNIVERSITY OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
    LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
    OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
    EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Register TAMA Patient" otherwise="/login.htm" redirect="/module/motechmodule/tama-patient.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<meta name="heading" content="Register Patient" />
<%@ include file="tamaLocalHeader.jsp" %>

<h2><spring:message code="motechmodule.Patient.register"/></h2>

	<form:form method="post" modelAttribute="motechmodule">
		<form:errors cssClass="error" />
		<table>
			<tr>
				<td class="labelcolumn"><form:label path="patientId"><spring:message code="motechmodule.patientId"/>: </form:label></td>
				<td><form:input path="patientId" /></td>
				<td><form:errors path="patientId" cssClass="error" /></td>
			</tr>
			<tr>
				<td class="labelcolumn"><form:label path="gender"><spring:message code="motechmodule.gender"/>:</form:label></td>
				<td>
					<form:radiobutton path="gender" value="M"/>Male <form:radiobutton path="gender" value="F"/>Female <form:radiobutton path="gender" value="T"/>Hijra (Transsexual)
				</td>
				<td><form:errors path="gender" cssClass="error" /></td>
			</tr>
			<tr>
				<td class="labelcolumn"><form:label path="dateOfBirth"><spring:message code="motechmodule.dateOfBirth"/> (DD/MM/YYYY):</form:label></td>
				<td><form:input path="dateOfBirth" /></td>
				<td><form:errors path="dateOfBirth" cssClass="error" /></td>
			</tr>
			<tr>
				<td class="labelcolumn"><form:label path="mobileNumber"><spring:message code="motechmodule.mobileNumber"/>:</form:label></td>
				<td><form:input path="mobileNumber" maxlength="50" /></td>
				<td><form:errors path="mobileNumber" cssClass="error" /></td>
			</tr>
		</table>
		<table>
			<tr>
				<td colspan="2"><input type="submit" value="Register New Patient" /></td>
			</tr>
		</table>
	</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>