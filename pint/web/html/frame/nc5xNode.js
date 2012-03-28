
/**
 * �򿪱����ɵ�NCϵͳ��ĳ���ڵ�
 * @param funcode ���򿪽ڵ�Ľڵ��
 */
function openNCNode(funcode, systemcode){
	//try
	{
		execNCAppletFunction("nc.ui.sm.webcall.OpenNCNode", "openNode", funcode, systemcode);
	}
	//catch(error)
	{
		
	}	
};

/**
 * 
 * @param argStr ������Ĳ���String
 * @param isNcJob �Ƿ��Ǵ�NC��������ڵ㡣
 */
function execNCAppletFunction(className, methodName, argStr, systemcode)
{
	//log("systemcode=" + systemcode);
	if(systemcode == null)
		systemcode = "NC";
   	var ncFrame = $("$NC_FRAME_ONLY_ONE_ID$" + systemcode); 
   	if(ncFrame == null)
   	{ 
   		var param = "systemcode=" + systemcode + "&screenWidth=" + window.screen.width + "&screenHeight=" + window.screen.height;
   		// ��Portal������ץȡNC��ҳ��Դ��
   		var pageSource = loadPage(window.globalPath + "/pt/nc5x/fetch", param, null, null, true, false);
   		if(pageSource.substring(0,6) == "ERROR")
   		{
   			pageSource = decodeURIComponent(pageSource);
   			alert(pageSource.substring(6, pageSource.length));
   			return;
   		}
   		ncFrame = initNCFrame(systemcode);
   		ncFrame.contentWindow.document.write(pageSource);
   	}
//   	showProgressPopup();
	waitLoadNCApplet(className, methodName, argStr, systemcode);
};

function waitLoadNCApplet(className, methodName, argStr, systemcode)
{
	if(window.$loadExecCount != null)
		window.$loadExecCount++;
	var applet = null;
	try {
		var ncFrame = $("$NC_FRAME_ONLY_ONE_ID$" + systemcode);
		if(ncFrame != null)
			applet = ncFrame.contentWindow.document.applets["NCApplet"];
		//log("ncFrame=" + ncFrame + " applet=" + applet);
	}
	catch(error){
		// ����frame src��,src��ָ���ҳ��ִ����Ҫһ����ʱ��,����srcҳ�����õ�document.domain���ܻ�û��ִ��,
		// ��ʱ�ᱨ"�ܾ����ʴ���",����ʱ������Ҫ����,Ҫ��applet��ʼ��һЩʱ��
		if(window.$loadExecCount == null)
			window.$loadExecCount = 0;
		//hideProgressPopup();
		if(window.$loadExecCount > 10)
		{
			alert("get applet error:" + error.name + ":" + error.message);
			window.$loadExecCount = 0;
			return;
		}	
	}
	if(applet == null || !isNcLoaded(applet))
	{
		setTimeout("waitLoadNCApplet('"+ className +"','" + methodName + "','" + argStr + "','" + systemcode + "')", 100);
		return;
	}
	//if(window.$loadExecCount != null)
	//	log("method waitLoadNCApplet exec " + window.$loadExecCount + " count!");
	window.$loadExecCount = null;
	exec(className, methodName, argStr, systemcode);
}; 

function isNcLoaded(applet) {
	try{
		return applet.callNC("nc.ui.sm.webcall.OpenNCNode", "isDesktopShow", null);
	}
	catch(error) {
		return false;	
	}
}

function exec(className, methodName, argStr, systemcode)
{
	 try {
	 	var ncFrame = $("$NC_FRAME_ONLY_ONE_ID$" + systemcode);
    	var applet = ncFrame.contentWindow.document.applets["NCApplet"];
    	//hideProgressPopup();
    	applet.callNC(className, methodName, argStr);
    	/*
    	if(isNcJob)
    		applet.callNC("nc.client.portal.PortalInNCClient", "openMsgPanel", argStr);
    	else
    		applet.callNC("nc.ui.sm.webcall.OpenNCNode", "openNode", argStr);*/
    		
    } catch(error) {
    	//hideProgressPopup();
        alert(error.name + ":" + error);
    }
};

/**
 * ��ʼ������nc applet��"iframe"
 */
function initNCFrame(systemcode)
{
	var frameID = "$NC_FRAME_ONLY_ONE_ID$" + systemcode;
	var frame = $(frameID);
	if(frame == null)
	{
		frame = document.createElement("iframe");
		frame.id = frameID;
		frame.style.position = "relative";
		frame.style.left = "0";
		frame.style.top = "0";
		frame.style.width = 1;
		frame.style.height = 0;
		frame.frameBorder = 0;
		frame.width = 0;
		frame.height = 0;
		frame.src = window.globalPath + "/setdomain.jsp";
		document.body.appendChild(frame);
	}
	return frame;
}; 

/**
 * ��portlet���ν���Viewʱ��Ҫ���ô˷�������ʱ��iframe��Ϣ�������
 */
function clearNCFrame(systemcode)
{
	var frameID = "$NC_FRAME_ONLY_ONE_ID$" + systemcode;
	var ncFrame = $(frameID);
	if(ncFrame != null)
	{
		ncFrame.src = "";
		ncFrame.parentNode.removeChild(ncFrame);
	}
};


/**
* ajax�����װ
* @param path ����url������Ϊ��
* @param queryStr �������,�� a=1&b=2&c=3
* @param returnFunc �ص�������ajax������Ϻ����ô˺���
* @param returnArgs �ص���������Ĳ���
* @param method ����ʽ��"GET" or "POST",Ĭ��POST
* @param asyn �Ƿ��첽��Ĭ���첽
* @param format �Ƿ��Ǹ�ʽ�����ݣ�����ǣ�����xml�����򷵻�text.Ĭ��Ϊtext
*	
*/
function loadPage(pathOrg, queryStringOrg, returnFunction, returnArgs, method, asyn, format) 
{
	var pos = pathOrg.indexOf("?");
	var path = pathOrg;
	var queryString = "";
	if (pos != -1) 
	{
		path = pathOrg.substring(0, pos);
		queryString = pathOrg.substring(pos + 1, pathOrg.length);
	}
	if(queryString != "")
		queryString += "&";
	if(queryStringOrg != null && queryStringOrg != "")
		queryString += queryStringOrg;
	
	if(typeof(getStickString) != "undefined"){
	 	var stickStr = getStickString();
    	if(stickStr != null && stickStr != "")
    		queryString = mergeParameter(queryString, stickStr);
	}
	
	//�Ƿ��첽��Ĭ���첽
	asyn = getBoolean(asyn, true);
	if (queryString == null) {
		queryString = "";
	}

	if (returnFunction == null) {
		returnFunction = function () {};
	}
	
	var xmlHttpReq;

	try {
		xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
		 
	}
	catch (e) {
		try {
			xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
			
		}
		catch (e) {
			try {
				xmlHttpReq = new XMLHttpRequest();
			}
			catch (e) {
			}
		}
	}
	try {
		if (method != null && method == false) {
			// ������ajax����,ӦΪajax���󴫵ݵ����Ĳ����ں�̨����Ҫת���������ȷ���
			var urlParam = path + "?" + queryString + "&isAjax=1";
			if(queryString != "" && queryString[queryString.length - 1] != "&")
				urlParam += "&";
			urlParam += "tmprandid=" + Math.random();
			
			//����get������ͬһ����ַ�������ڶ��η��أ�����Ϊ�������������У�����ַ����������
			xmlHttpReq.open("GET", urlParam , asyn);
			xmlHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			xmlHttpReq.send(null);
			//ͬ����ֱ�ӷ��ؽ��
			if(!asyn)
			{
				if(format)
					return xmlHttpReq.responseXML;
				return xmlHttpReq.responseText; 
			}
		}
		else {
			xmlHttpReq.open("POST", path, asyn);
			xmlHttpReq.setRequestHeader("Method", "POST " + path + " HTTP/1.1");
			xmlHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			xmlHttpReq.send(queryString + "&isAjax=1");
			//ͬ����ֱ�ӷ��ؽ��
			if(!asyn)
			{
				if(format)
					return xmlHttpReq.responseXML;
				return xmlHttpReq.responseText; 
			}
		}

		xmlHttpReq.onreadystatechange = function() {
				if (xmlHttpReq.readyState == 4) {
					if (xmlHttpReq.status == 200) {
						returnFunction(xmlHttpReq, returnArgs, null, [pathOrg, queryStringOrg, returnFunction, returnArgs, method, asyn, format]);
					}
					else if(xmlHttpReq.status == 306) 
					{
						returnFunction(xmlHttpReq, returnArgs, trans("ml_sessioninvalid"));
					}
					//Ϊ�������⴦��ġ�������Ҫ��ȡ��ȥ
					else if(xmlHttpReq.status == 308){
						alert(trans("ml_requestvalid"));
						var bxtop = window;
						if(bxtop.bxmainpage == null)
							bxtop = bxtop.parent;
						bxtop.location.href = "core/main/main.jsp?pageId=main";
						return;
					}
					else{
						returnFunction(xmlHttpReq, returnArgs, "Error occurred,response status is:"+ xmlHttpReq.status); 
					}
				}
			};
	}
	catch (e) {
		if(IS_IE)
			alert(e.name + " " + e.message);
		else
			alert(e);
		
	}
};
function getBoolean(value, defaultValue)
{
	if(value == 'false')
		return false;
	else if(value == 'true')
		return true;
	else if(value != false && value != true)
		return defaultValue;
	else
		return value;
};