<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="javax.portlet.PortletSession" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://portals.apache.org/pluto/portlet-el_2_0" prefix="portlet-el" %>
<portlet:defineObjects/>
<%
//PortletSession ps = renderRequest.getPortletSession();
String height = (String)renderRequest.getAttribute("if_height");
String iframeId = windowId.replaceAll("\\.","_") + "_iframe";
%>
<div style="overflow:scroll;width:100%;height:590px;">
	<iframe name="portlet" allowtransparency="true"  style="border:0px; padding:0;"  width="100%"  id="<%= iframeId%>" frameborder="0"  vspace="0" hspace="0" <%= renderRequest.getAttribute("if_src_type")%>="<%=renderRequest.getAttribute("if_src")%>">
	</iframe>
</div>
