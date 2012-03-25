<#include "functions.ftl">
<#--
layout��Ⱦ
@param lay layoutʵ��
@param page pageʵ��
-->
<#macro layout pageLayout page>
<#--����layout��װ��-->
<#import "${layoutLookUp(pageLayout.name,page.module,page.skin)}" as layoutWrap>
<@layoutWrap.wrap layout=pageLayout page=page>
	<#if pageLayout.child??>
	<#if pageLayout.sizes ??  && pageLayout.sizes?contains(",")>
		<table style="width:100%"  border="0" cellspacing="0" cellpadding="0" >
        	<tr  valign="top">
        	 </#if>
			<#list pageLayout.child as child>
				 <#if pageLayout.sizes ??   && pageLayout.sizes?contains(",")>
				 	<#assign sizeArray = pageLayout.sizes?split(",")>

				 	
				 	<#if sizeArray[child_index] ??>
					 	<td width="${sizeArray[child_index]}" oriwd="${sizeArray[child_index]}" style="padding:0px;">
	             	<#else>
	              	    <td>
                    </#if>
	             	<@childRender child=child page=page></@childRender>
	             	</td>
	             <#else>
	                <@childRender child=child page=page></@childRender>
	             </#if>
	             
            </#list>
            <#if pageLayout.sizes ??   && pageLayout.sizes?contains(",")>
           	</tr>
        </table>
         </#if>
    </#if>
</@layoutWrap.wrap>
</#macro>

<#--
layout��Ԫ�ؿ�Ȱ�װ
@param sizes layout��sizes����
@param row �к�
-->
<#macro wideWraper layout row>
    <#if layout.sizes??>
        <#assign sizeArray = layout.sizes?split(",")>
        <#assign leng=sizeArray?size>
        	<table style="border:1px solid red">
        		<tr>
        	<#if sizeArray[row] ??>
        		<div style="width:${sizeArray[row]};float:left;">
        		<div style="margin:3px">
   		</#if>
    </#if>
		<#nested>
    <#if layout.sizes??>
    	  </table>
    </#if>
</#macro>

<#--
layout��Ԫ�ذ�װ
@param child layout����Ԫ��
@param page page����
-->
<#macro childRender child page>
 	<#if checkType(child)="portlet">
 		<#if checkPower(child.name , page.module)>
		    <#assign portlet=child>
			<#assign portalPath= lookUp(page,portlet)>
			<#--����Porlet��CSS&JavaScript-->
			<#assign container=  "$('#_pt_portlet_${portlet.id}')">
			<#--����Porlet��CSS&JavaScript-->
			<@importStyle portalPath=portalPath></@importStyle>
			<@importScript portalPath=portalPath></@importScript>
			<#include "${portalPath}">
		</#if>
	<#else>
		<@layout pageLayout=child page=page></@layout>
	</#if>
</#macro>

<#macro importConstant  constants>
<script>
<#if constants??>
 <#list constants as  const>
	var ${const.fieldName}="${const.fieldVal}";
 </#list>
 </#if>
</script>
</#macro>
