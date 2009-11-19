<ul id="menu">
	<openmrs:hasPrivilege privilege="View Administration Functions">
		<li	class="first">
			<a href="${pageContext.request.contextPath}/admin"><spring:message code="admin.title.short"/></a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Register MoTeCH Clinic">
		<li <c:if test='<%= request.getRequestURI().contains("clinic") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/motechmodule/clinic.form">
				<spring:message code="motechmodule.Clinic.register"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Register MoTeCH Maternal Visit">
		<li <c:if test='<%= request.getRequestURI().contains("maternalVisit") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/motechmodule/maternalVisit.form">
				<spring:message code="motechmodule.MaternalVisit.register"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Register MoTeCH Nurse">
		<li <c:if test='<%= request.getRequestURI().contains("nurse") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/motechmodule/nurse.form">
				<spring:message code="motechmodule.Nurse.register"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Register MoTeCH Patient">
		<li <c:if test='<%= request.getRequestURI().contains("patient") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/motechmodule/patient.form">
				<spring:message code="motechmodule.Patient.register"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Register MoTeCH Pregnancy">
		<li <c:if test='<%= request.getRequestURI().contains("pregnancy") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/motechmodule/pregnancy.form">
				<spring:message code="motechmodule.Pregnancy.register"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
		<openmrs:hasPrivilege privilege="Manage MoTeCH Troubled Phones">
		<li <c:if test='<%= request.getRequestURI().contains("troubledphone") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/motechmodule/troubledphone.form">
				<spring:message code="motechmodule.TroubledPhone.manage"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Manage MoTeCH Blackout">
		<li <c:if test='<%= request.getRequestURI().contains("blackout") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/motechmodule/blackout.form">
				<spring:message code="motechmodule.Blackout.manage"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Run MoTeCH Test">
		<li <c:if test='<%= request.getRequestURI().contains("quick") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/motechmodule/quick.form">
				<spring:message code="motechmodule.QuickTest.run"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="View MoTeCH Data">
		<li <c:if test='<%= request.getRequestURI().contains("viewdata") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/motechmodule/viewdata.form">
				<spring:message code="motechmodule.Data.view"/>
			</a>
		</li>
	</openmrs:hasPrivilege>
</ul>