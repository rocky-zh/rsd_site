function chart_function_createtab(src){
	var proxy = new ServerProxy(null,null,false); //������
	proxy.addParam('clc', 'a.TestController' );//������
	proxy.addParam('m_n', 'onPopViewByHyperLink');//������
	proxy.addParam('src',src);//����
	proxy.execute();
}