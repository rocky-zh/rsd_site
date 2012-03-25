package nc.uap.portal.container.service.itf;

/**
 * As defined in PLT.D of the JSR-168 specification, this class defines a set of
 * attribute names for user information and their intended
 * meaning. To allow portals an automated mapping of commonly used user information
 * attributes portlet programmers should use these attribute names. These attribute names
 * are derived from the Platform for Privacy Preferences 1.0 (P3P 1.0) Specification by the
 * W3C (http://www.w3c.org/TR/P3P).
 *
 */
public interface P3PAttributes {

    /*	NOTE: The user.bdate must consist of a string that represents the time in milliseconds
	since January 1, 1970, 00:00:00 GMT. */
    String USER_BDATE = "user.bdate";
    String USER_GENDER = "user.gender";
    String USER_EMPLOYER = "user.employer";
    String USER_DEPARTMENT = "user.department";
    String USER_JOBTITLE = "user.jobtitle";
    String USER_NAME_PREFIX = "user.name.prefix";
    String USER_NAME_GIVEN = "user.name.given";
    String USER_NAME_FAMILY = "user.name.family";
    String USER_NAME_MIDDLE = "user.name.middle";
    String USER_NAME_SUFFIX = "user.name.suffix";
    String USER_NAME_NICKNAME = "user.name.nickName";
    String USER_HOME_INFO_POSTAL_NAME = "user.home-info.postal.name";
    String USER_HOME_INFO_POSTAL_STREET = "user.home-info.postal.street";
    String USER_HOME_INFO_POSTAL_CITY = "user.home-info.postal.city";
    String USER_HOME_INFO_POSTAL_STATEPROV = "user.home-info.postal.stateprov";
    String USER_HOME_INFO_POSTAL_POSTALCODE = "user.home-info.postal.postalcode";
    String USER_HOME_INFO_POSTAL_COUNTRY = "user.home-info.postal.country";
    String USER_HOME_INFO_POSTAL_ORGANIZATION= "user.home-info.postal.organization";
    String USER_HOME_INFO_TELECOM_TELEPHONE_INTCODE = "user.home-info.telecom.telephone.intcode";
    String USER_HOME_INFO_TELECOM_TELEPHONE_LOCCODE = "user.home-info.telecom.telephone.loccode";
    String USER_HOME_INFO_TELECOM_TELEPHONE_NUMBER = "user.home-info.telecom.telephone.number";
    String USER_HOME_INFO_TELECOM_TELEPHONE_EXT = "user.home-info.telecom.telephone.ext";
    String USER_HOME_INFO_TELECOM_TELEPHONE_COMMENT = "user.home-info.telecom.telephone.comment";
    String USER_HOME_INFO_TELECOM_FAX_INTCODE = "user.home-info.telecom.fax.intcode";
    String USER_HOME_INFO_TELECOM_FAX_LOCCODE = "user.home-info.telecom.fax.loccode";
    String USER_HOME_INFO_TELECOM_FAX_NUMBER = "user.home-info.telecom.fax.number";
    String USER_HOME_INFO_TELECOM_FAX_EXT = "user.home-info.telecom.fax.ext";
    String USER_HOME_INFO_TELECOM_FAX_COMMENT = "user.home-info.telecom.fax.comment";
    String USER_HOME_INFO_TELECOM_MOBILE_INTCODE = "user.home-info.telecom.mobile.intcode";
    String USER_HOME_INFO_TELECOM_MOBILE_LOCCODE = "user.home-info.telecom.mobile.loccode";
    String USER_HOME_INFO_TELECOM_MOBILE_NUMBER = "user.home-info.telecom.mobile.number";
    String USER_HOME_INFO_TELECOM_MOBILE_EXT = "user.home-info.telecom.mobile.ext";
    String USER_HOME_INFO_TELECOM_MOBILE_COMMENT = "user.home-info.telecom.mobile.comment";
    String USER_HOME_INFO_TELECOM_PAGER_INTCODE = "user.home-info.telecom.pager.intcode";
    String USER_HOME_INFO_TELECOM_PAGER_LOCCODE = "user.home-info.telecom.pager.loccode";
    String USER_HOME_INFO_TELECOM_PAGER_NUMBER = "user.home-info.telecom.pager.number";
    String USER_HOME_INFO_TELECOM_PAGER_EXT = "user.home-info.telecom.pager.ext";
    String USER_HOME_INFO_TELECOM_PAGER_COMMENT = "user.home-info.telecom.pager.comment";
    String USER_HOME_INFO_ONLINE_EMAIL = "user.home-info.online.email";
    String USER_HOME_INFO_ONLINE_URI = "user.home-info.online.uri";
    String USER_BUSINESS_INFO_POSTAL_NAME = "user.business-info.postal.name";
    String USER_BUSINESS_INFO_POSTAL_STREET = "user.business-info.postal.street";
    String USER_BUSINESS_INFO_POSTAL_CITY = "user.business-info.postal.city";
    String USER_BUSINESS_INFO_POSTAL_STATEPROV = "user.business-info.postal.stateprov";
    String USER_BUSINESS_INFO_POSTAL_POSTALCODE = "user.business-info.postal.postalcode";
    String USER_BUSINESS_INFO_POSTAL_COUNTRY = "user.business-info.postal.country";
    String USER_BUSINESS_INFO_POSTAL_ORGANIZATION = "user.business-info.postal.organization";
    String USER_BUSINESS_INFO_TELECOM_TELEPHONE_INTCODE = "user.business-info.telecom.telephone.intcode";
    String USER_BUSINESS_INFO_TELECOM_TELEPHONE_LOCCODE= "user.business-info.telecom.telephone.loccode";
    String USER_BUSINESS_INFO_TELECOM_TELEPHONE_NUMBER = "user.business-info.telecom.telephone.number";
    String USER_BUSINESS_INFO_TELECOM_TELEPHONE_EXT = "user.business-info.telecom.telephone.ext";
    String USER_BUSINESS_INFO_TELECOM_TELEPHONE_COMMENT = "user.business-info.telecom.telephone.comment";
    String USER_BUSINESS_INFO_TELECOM_FAX_INTCODE = "user.business-info.telecom.fax.intcode";
    String USER_BUSINESS_INFO_TELECOM_FAX_LOCCODE = "user.business-info.telecom.fax.loccode";
    String USER_BUSINESS_INFO_TELECOM_FAX_NUMBER = "user.business-info.telecom.fax.number";
    String USER_BUSINESS_INFO_TELECOM_FAX_EXT = "user.business-info.telecom.fax.ext";
    String USER_BUSINESS_INFO_TELECOM_FAX_COMMENT = "user.business-info.telecom.fax.comment";
    String USER_BUSINESS_INFO_TELECOM_MOBILE_INTCODE = "user.business-info.telecom.mobile.intcode";
    String USER_BUSINESS_INFO_TELECOM_MOBILE_LOCCODE = "user.business-info.telecom.mobile.loccode";
    String USER_BUSINESS_INFO_TELECOM_MOBILE_NUMBER = "user.business-info.telecom.mobile.number";
    String USER_BUSINESS_INFO_TELECOM_MOBILE_EXT = "user.business-info.telecom.mobile.ext";
    String USER_BUSINESS_INFO_TELECOM_MOBILE_COMMENT = "user.business-info.telecom.mobile.comment";
    String USER_BUSINESS_INFO_TELECOM_PAGER_INTCODE = "user.business-info.telecom.pager.intcode";
    String USER_BUSINESS_INFO_TELECOM_PAGER_LOCCODE = "user.business-info.telecom.pager.loccode";
    String USER_BUSINESS_INFO_TELECOM_PAGER_NUMBER = "user.business-info.telecom.pager.number";
    String USER_BUSINESS_INFO_TELECOM_PAGER_EXT = "user.business-info.telecom.pager.ext";
    String USER_BUSINESS_INFO_TELECOM_PAGER_COMMENT = "user.business-info.telecom.pager.comment";
    String USER_BUSINESS_INFO_ONLINE_EMAIL = "user.business-info.online.email";
    String USER_BUSINESS_INFO_ONLINE_URI = "user.business-info.online.uri";

    String[] ATTRIBUTE_ARRAY = {
            "user.bdate",
            "user.gender",
            "user.employer",
            "user.department",
            "user.jobtitle",
            "user.name.prefix",
            "user.name.given",
            "user.name.family",
            "user.name.middle",
            "user.name.suffix",
            "user.name.nickName",
            "user.home-info.postal.name",
            "user.home-info.postal.street",
            "user.home-info.postal.city",
            "user.home-info.postal.stateprov",
            "user.home-info.postal.postalcode",
            "user.home-info.postal.country",
            "user.home-info.postal.organization",
            "user.home-info.telecom.telephone.intcode",
            "user.home-info.telecom.telephone.loccode",
            "user.home-info.telecom.telephone.number",
            "user.home-info.telecom.telephone.ext",
            "user.home-info.telecom.telephone.comment",
            "user.home-info.telecom.fax.intcode",
            "user.home-info.telecom.fax.loccode",
            "user.home-info.telecom.fax.number",
            "user.home-info.telecom.fax.ext",
            "user.home-info.telecom.fax.comment",
            "user.home-info.telecom.mobile.intcode",
            "user.home-info.telecom.mobile.loccode",
            "user.home-info.telecom.mobile.number",
            "user.home-info.telecom.mobile.ext",
            "user.home-info.telecom.mobile.comment",
            "user.home-info.telecom.pager.intcode",
            "user.home-info.telecom.pager.loccode",
            "user.home-info.telecom.pager.number",
            "user.home-info.telecom.pager.ext",
            "user.home-info.telecom.pager.comment",
            "user.home-info.online.email",
            "user.home-info.online.uri",
            "user.business-info.postal.name",
            "user.business-info.postal.street",
            "user.business-info.postal.city",
            "user.business-info.postal.stateprov",
            "user.business-info.postal.postalcode",
            "user.business-info.postal.country",
            "user.business-info.postal.organization",
            "user.business-info.telecom.telephone.intcode",
            "user.business-info.telecom.telephone.loccode",
            "user.business-info.telecom.telephone.number",
            "user.business-info.telecom.telephone.ext",
            "user.business-info.telecom.telephone.comment",
            "user.business-info.telecom.fax.intcode",
            "user.business-info.telecom.fax.loccode",
            "user.business-info.telecom.fax.number",
            "user.business-info.telecom.fax.ext",
            "user.business-info.telecom.fax.comment",
            "user.business-info.telecom.mobile.intcode",
            "user.business-info.telecom.mobile.loccode",
            "user.business-info.telecom.mobile.number",
            "user.business-info.telecom.mobile.ext",
            "user.business-info.telecom.mobile.comment",
            "user.business-info.telecom.pager.intcode",
            "user.business-info.telecom.pager.loccode",
            "user.business-info.telecom.pager.number",
            "user.business-info.telecom.pager.ext",
            "user.business-info.telecom.pager.comment",
            "user.business-info.online.email",
            "user.business-info.online.uri"
    };
}
