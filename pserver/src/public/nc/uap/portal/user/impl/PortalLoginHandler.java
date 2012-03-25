package nc.uap.portal.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.rbac.userpassword.IUserPasswordChecker;
import nc.uap.cpb.log.LoginLogHelper;
import nc.uap.cpb.log.loginlog.LoginLogVO;
import nc.uap.cpb.org.itf.ICpUserQry;
import nc.uap.cpb.org.util.CpbServiceFacility;
import nc.uap.cpb.org.vos.CpUserVO;
import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.common.CookieConstant;
import nc.uap.lfw.core.comp.text.ComboBoxComp;
import nc.uap.lfw.core.comp.text.TextComp;
import nc.uap.lfw.core.event.ctx.LfwPageContext;
import nc.uap.lfw.core.exception.LfwBusinessException;
import nc.uap.lfw.core.log.LfwLogger;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.core.processor.EventRequestContext;
import nc.uap.lfw.login.itf.ILoginHandler;
import nc.uap.lfw.login.itf.ILoginSsoService;
import nc.uap.lfw.login.itf.LoginInterruptedException;
import nc.uap.lfw.login.listener.AuthenticationUserVO;
import nc.uap.lfw.login.vo.LfwSessionBean;
import nc.uap.portal.deploy.vo.PtSessionBean;
import nc.uap.portal.exception.PortalServiceException;
import nc.uap.portal.exception.UserAccessException;
import nc.uap.portal.exception.UserLockedException;
import nc.uap.portal.exception.UserNotFoundException;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
import nc.uap.portal.service.PortalServiceUtil;
import nc.uap.portal.service.itf.IPtPortalConfigRegistryService;
import nc.uap.portal.servlet.PortalLoginFilter;
import nc.uap.portal.user.entity.IUserVO;
import nc.uap.portal.user.itf.IUserLoginPlugin;
import nc.uap.portal.util.PortalDsnameFetcher;
import nc.uap.portal.util.ToolKit;
import nc.vo.org.GroupVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.sm.UserVO;
import nc.vo.uap.rbac.userpassword.PasswordSecurityLevelFinder;
import nc.vo.uap.rbac.userpassword.PasswordSecurityLevelVO;
import nc.vo.uap.rbac.util.RbacUserPwdUtil;

import org.apache.commons.lang.StringUtils;

/**
 * Portal��½Ĭ��ʵ��
 * 
 * @author licza
 * 
 */
public class PortalLoginHandler implements ILoginHandler<PtSessionBean> {
	private static final String CHALLDATA = "p_challdata";
	private static final String CA_USER_ID = "p_userId";
	private static final String SIGNDATA = "p_signdata";
	private static final String MAXWIN = "p_maxwin";
	private static final String LANGUAGE = "p_language";
	protected static final String LOGINDATE = "logindate";
	protected static final String FORCE = "force";
	// ��ǰ��¼Ƭ��
//	private LfwWidget currentWidget;

	public PtSessionBean auth(AuthenticationUserVO userInfo) throws LoginInterruptedException{
		List<PtExtension> plugins = PluginManager.newIns().getExtensions(IUserLoginPlugin.ID);
		/**
		 * ��¼ǰ����
		 */
//		if(ToolKit.notNull(plugins)){
//			for(PtExtension ex : plugins){
//				try {
//					ex.newInstance(IUserLoginPlugin.class).beforeLogin(userInfo);
//				} catch (Exception e) {
//					LfwLogger.error(e.getMessage(),e);
//				}
//			}
//		}
		
		HttpServletRequest req = LfwRuntimeEnvironment.getWebContext().getRequest();
		try {
			ICpUserQry userQry = CpbServiceFacility.getCpUserQry();
			CpUserVO cpUserVO = userQry.getGlobalUserByCode(userInfo.getUserID());
			
			if (cpUserVO == null) {
				throw new UserNotFoundException("�û�ID������" + userInfo.getUserID());
			}
			Map<String, String> extMap = (Map<String, String>) userInfo.getExtInfo();
			//ca��֤
			if(UFBoolean.TRUE.equals(cpUserVO.getIsca())){
				String signdata = extMap.get(SIGNDATA);
				if(signdata == null){
					throw new LoginInterruptedException("�û�У�鲻��ȷ��û��ǩ����Ϣ");
				}
//				PluginManager.newIns().getExPoint("CAAUTH").getp
			}
			
			
			//String userPasswd = SecurityUtil.getEncodedPassword(cpUserVO, userInfo.getPassword());
			if(false){
				// if(userVO.getOriginal() != null &&
				// !userVO.getOriginal().equals("") &&
				// getVerifyPlugin(userVO.getOriginal()) != null){
				// IUserVerify verify = (IUserVerify)
				// getVerifyPlugin(userVO.getOriginal());
				// boolean valid = verify.verifyPasswd(userVO.getOriginal_pk(),
				// userInfo.getPassword(), null);
				// if(!valid)
				// throw new
				// UserAccessException(PortalLanguageUtil.get("portal_user_notaccess",
				// userInfo.getUserID()));
				// }
			}
			else {
				
				//��֯nc�û�
				UserVO user = new UserVO();
				user.setPwdlevelcode(cpUserVO.getPwdlevelcode());
				user.setPwdparam(cpUserVO.getPwdparam());
				user.setUser_password(cpUserVO.getUser_password());
				user.setCuserid(cpUserVO.getCuserid());
				user.setUser_code(cpUserVO.getUser_code());
//				PasswordSecurityLevelVO pwdLevel = PasswordSecurityLevelFinder.getPWDLV(user);
//				IUserPasswordChecker upchecher = (IUserPasswordChecker) NCLocator.getInstance().lookup(IUserPasswordChecker.class.getName());
//				try {
//					//��֤���밲ȫ����
//					upchecher.checkNewpassword(cpUserVO.getPrimaryKey(), cpUserVO.getUser_password(),
//							pwdLevel, cpUserVO.getUser_type());
//				} 
//				catch (Exception be) {
//					LfwLogger.error(be.getMessage(), be);
////					if(pwdLevel != null && pwdLevel.getInvalidateLock())
////						throw new LoginInterruptedException("���벻���ϰ�ȫ����!" + be.getMessage() );
////					else if(pwdLevel != null && !pwdLevel.getInvalidateLock()){
////						InteractionUtil.showConfirmDialog("ȷ�϶Ի���", "���벻���ϰ�ȫ����,������½?");
////					}
//				}

 
				if (cpUserVO.getIslocked() != null && cpUserVO.getIslocked().booleanValue())
					throw new UserLockedException("�û�������,����ϵ����Ա!");
	
				
				if (cpUserVO.getDisabledate() != null) {
					UFDate now =  new UFDate(new Date());
					if(cpUserVO.getDisabledate().before(now)){
						throw new PortalServiceException("���û��ѹ���,�޷���½!");
					}
				}
				
				//ʹ��60�����㷨���ܺ�����
//				String userPasswd = RbacUserPwdUtil.getEncodedPassword(user, userInfo.getPassword());
//				String loginPassword = cpUserVO.getUser_password();
//				if (loginPassword == null || !loginPassword.equals(userPasswd)) {
//					userPasswd = PortalServiceUtil.getEncodeService().encode(userInfo.getPassword());
//					if (loginPassword == null || !loginPassword.equals(userPasswd)) {
//						Integer passwordtimes = cpUserVO.getPasswordtrytimes();
//						passwordtimes ++;
//						cpUserVO.setPasswordtrytimes(passwordtimes);
//						CpbServiceFacility.getCpUserBill().updateCpUserVO(cpUserVO);
//						throw new UserAccessException("���벻��ȷ" + userInfo.getUserID());
//					}
//				}
			}
			CpUser ptUser = new CpUser(cpUserVO);
			
			PtSessionBean sbean = processSessionBean(ptUser, req);
			/**
			 * ��¼�����
			 */
//			if(ToolKit.notNull(plugins)){
//				for(PtExtension ex : plugins){
//					try {
//						ex.newInstance(IUserLoginPlugin.class).afterLogin(sbean);
//					} catch (Exception e) {
//						LfwLogger.error(e.getMessage(),e);
//					}
//				}
//			}
			// ��ҳ��ַ
			return sbean;
		} 
		catch (Exception e) {
			LfwLogger.error(e);
			try {
				doLoginLog(getAuthenticateVO(), e.getMessage());
			} catch (Exception e2) {
				LfwLogger.error(e2.getMessage());
			}
			throw new LoginInterruptedException(e.getMessage());
		}
	}
	/**
	 * ��¼��¼��־
	 * @param sbean
	 * @param loginResult
	 * @throws LfwBusinessException
	 */
	public void doLoginLog(AuthenticationUserVO userInfo, String newDetail)
			throws LfwBusinessException {
		HttpServletRequest request = LfwRuntimeEnvironment.getWebContext().getRequest();
		LoginLogVO vo = new LoginLogVO();
		vo.setLogintime(new UFDateTime());
		vo.setClientip(request.getRemoteAddr());
		vo.setLoginresult(UFBoolean.FALSE);
		vo.setUsername(userInfo.getUserID());
		vo.setDetail(newDetail);
		vo.setSessionid(request.getSession().getId());
//			vo.setLogingropcode(newLogingropcode)
		LoginLogHelper.login(vo);
	}
	@Override
	public PtSessionBean doAuthenticate(AuthenticationUserVO userInfo) throws LoginInterruptedException {
 		try {
			String portalDataSource = PortalDsnameFetcher.getPortalDsName();
			if (portalDataSource != null && portalDataSource.trim().length() > 0) {
				if (Logger.isDebugEnabled())
					Logger.debug("���õ�ǰPortal����ԴΪ:" + portalDataSource);
				LfwRuntimeEnvironment.setDatasource(portalDataSource);
			}
			else
				throw new LoginInterruptedException("û��ָ��Portalʹ�õ�����Դ");
			// ��ҳ��ַ
			return auth(userInfo);
		} catch (Exception e) {
			LfwLogger.error(e);
			throw new LoginInterruptedException(e.getMessage());
		}
	}

	private PtSessionBean processSessionBean(CpUser user, HttpServletRequest req) throws BusinessException {
		// ��ֻ��ǰ�û������н�ɫ��������
//		PortalLoginUtil.setUserRoles(user);
//		PtRoleVO[] roles = null;
//		List<PtResourceVO> list = new ArrayList<PtResourceVO>();
//
//		if (user.getUsertype().equals(PtUserVO.USERTYPE_SYSADMIN)) {
//			roles = new PtRoleVO[0];
//		} else {
//			roles = PortalServiceUtil.getPortalServerService().getUserRoles(user.getPk_user(), true);
//			// Collection<PtResourceVO> userResources =
//			// PortalServiceUtil.getPortalServerService().getResourceByUser(user.getPk_user());
//			// Collection<PtResourceVO> notControllers =
//			// PortalServiceUtil.getPortalServerService().getNotControlResource(user.getPk_corp());
//			// list.addAll(userResources);
//			// list.addAll(notControllers);
//		}
		String groupNo;
		if (user.getUser().getUser_type().equals(IUserVO.USERTYPE_GROUP)) {
			GroupVO[] groupVOs = CpbServiceFacility.getCpGroupQry().queryGroupVOsByPKS(new String[]{user.getPk_group()});
			if(groupVOs != null && groupVOs.length == 1){
				groupNo = groupVOs[0].getGroupno();
			}else{
				groupNo = "0000";
			}
		} else
			groupNo = "0000";

		PtSessionBean sbean = new PtSessionBean();
		// user.setPassword(PortalServiceUtil.getEncodeService().decode(user.getPassword()));
		// sbean.setUser(user);

		sbean.setDatasource(LfwRuntimeEnvironment.getDatasource());
		sbean.setGroupNo(groupNo);

		sbean.setUserType(user.getUser().getUser_type());

		// sbean.setResouces(list.toArray(new PtResourceVO[0]));
		// sbean.setRoles(roles);
		sbean.setUser(user);
		// sbean.setCorp(corpVO);
		// sbean.setGroup(groupVO);
		// HttpSession session = req.getSession();

		if (Logger.isDebugEnabled())
			Logger.debug("===AbstractGateFilter��doAuthentication����:�����û���Ϣ��¼����!");

		// ��½�ɹ���,�ٸ��µ�½��Ϣ
		// PortalLoginUtil.getInstance().loginProcess(user.getPk_user(),
		// session.getId(), req.getRemoteAddr());
		// ��������
		// ThemeDisplay display = PortalLoginUtil.processThemeDisplay(sbean,
		// req, res);
		// session.setAttribute(WebKeys.THEME_DISPLAY, display);
		// ����userȫ��Ϣ��session��,�û���Ϣ�޸�ʱ�Ӹ�vo�л�ȡ
		// BbsuserVO bbsvo =
		// PortalServiceUtil.getPortalServerService().getBbsUserVo(user.getPk_user());
		// PtTotalUserVo totalVO = new PtTotalUserVo(user, bbsvo);
		// session.setAttribute(WebKeys.LOGIN_USER, totalVO);

		if (Logger.isDebugEnabled())
			Logger.debug("===AbstractGateFilter��doAuthentication����:����û���Ϣ��¼����");
		return sbean;
	}

	/**
	 * ��õ�¼��ϢVO
	 */
	@Override
	public AuthenticationUserVO getAuthenticateVO() throws LoginInterruptedException {
		AuthenticationUserVO userVO = new AuthenticationUserVO();
		Map<String, String> extMap = new HashMap<String, String>();
		LfwWidget widget = getCurrentWidget();
		TextComp userIdComp = (TextComp) widget.getViewComponents().getComponent("userid");
		TextComp randomImageComp = (TextComp) widget.getViewComponents().getComponent("randimg");
		IPtPortalConfigRegistryService cfg = PortalServiceUtil.getConfigRegistryService();
		String enabledRandomImage = cfg.get("login.randomimage.enabled");
		String openmode = cfg.get("portal.openmode");
		String userId = null;
		if(userIdComp != null){
			if(StringUtils.equals(enabledRandomImage, "true")){
				String rand = (String)LfwRuntimeEnvironment.getWebContext().getRequest().getSession().getAttribute("rand");
				String ricv = randomImageComp.getValue();
				if(!StringUtils.equals(rand, ricv))
					throw new LoginInterruptedException("��֤�����!");
			}
			userId = userIdComp.getValue();
			if (userId == null || userId.equals("")) {
				throw new LoginInterruptedException("�û�������Ϊ��");
			}
		}
		//û���û�ID��������CA��¼
		else{
			LfwPageContext pctx = EventRequestContext.getLfwPageContext();
			userId = pctx.getParameter(CA_USER_ID);
			String signdata = pctx.getParameter(SIGNDATA);
			String challdata = (String) LfwRuntimeEnvironment.getWebContext().getRequest().getSession().getAttribute(CHALLDATA);
			extMap.put(SIGNDATA, signdata);
			extMap.put(CHALLDATA, challdata);
		}

		TextComp passComp = (TextComp) widget.getViewComponents().getComponent("password");
		String passValue = null;
		if(passComp != null){
			passValue = passComp.getValue();
			if (passValue == null || passValue.equals("")) {
				throw new LoginInterruptedException("���벻��Ϊ��");
			}
		}
		
		// �Ƿ�����󻯴�����ʽ��ҳ��
		//CheckBoxComp maxWindowCheckBox = (CheckBoxComp) widget.getViewComponents().getComponent("maxWindowCheckBox");
		ComboBoxComp multiLanguageCombo = (ComboBoxComp) widget.getViewComponents().getComponent("multiLanguageCombo");
		//boolean isMaxWindow = maxWindowCheckBox.isChecked();
		boolean isMaxWindow = StringUtils.equals("1", openmode);
		String language = multiLanguageCombo.getValue();

		userVO.setUserID(userId);
		userVO.setPassword(passValue);
		extMap.put(LANGUAGE, language);
		extMap.put(MAXWIN, isMaxWindow ? "Y" : "N");
		userVO.setExtInfo(extMap);
		return userVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cookie[] getCookies(AuthenticationUserVO userVO) {
		List<Cookie> list = new ArrayList<Cookie>();
		String userId = userVO.getUserID();
		Map<String, String> extMap = (Map<String, String>) userVO.getExtInfo();
		String sysId = "" + LfwRuntimeEnvironment.getSysId();
		String themeId = LfwRuntimeEnvironment.getLfwSessionBean().getThemeId();
		String language = LfwRuntimeEnvironment.getLfwSessionBean().getLangId();
		String maxwin = extMap.get(MAXWIN);
		
		// ����Cookie
		Cookie tc = new Cookie(CookieConstant.THEME_CODE + sysId, themeId);
		tc.setPath("/");
		tc.setMaxAge(CookieConstant.MAX_AGE);
		list.add(tc);
		
		// ����Cookie
		Cookie lc = new Cookie(CookieConstant.LANG_CODE + sysId, language);
		lc.setPath("/");
		lc.setMaxAge(CookieConstant.MAX_AGE);
		list.add(lc);
		
		// ����Cookie
		Cookie uc = new Cookie(CA_USER_ID, userId);
		uc.setPath("/");
		uc.setMaxAge(CookieConstant.MAX_AGE);
		list.add(uc);
		
		Cookie mc = new Cookie("isMaxWindow", maxwin);
		mc.setPath("/");
		mc.setMaxAge(CookieConstant.MAX_AGE);
		list.add(mc);
		return list.toArray(new Cookie[0]);
	}

	@Override
	public ILoginSsoService<PtSessionBean> getLoginSsoService() {
		return new PortalSSOServiceImpl();
	}

	@Override
	public String getSysType() {
		return PortalLoginFilter.PORTAL_SYS_TYPE;
	}

	public LfwWidget getCurrentWidget() {
		return EventRequestContext.getLfwPageContext().getWidgetContext("main").getWidget();
	}

//	public void setCurrentWidget(LfwWidget currentWidget) {
//		this.currentWidget = currentWidget;
//	}
}
